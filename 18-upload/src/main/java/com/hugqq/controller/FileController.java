package com.hugqq.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hugqq.entity.*;
import com.hugqq.service.ChunkService;
import com.hugqq.service.FileInfoService;
import com.hugqq.util.FileInfoUtils;
import com.hugqq.util.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


/**
 * 上传下载文件
 */
@RestController
@RequestMapping("/uploader")
@Slf4j
public class FileController {

    @Value("${prop.upload-folder}")
    private String uploadFolder;

    @Autowired
    private FileInfoService fileInfoService;

    @Autowired
    private ChunkService chunkService;

    /**
     * 上传文件块
     */
    @PostMapping("/chunk")
    public String uploadChunk(ChunkInfo chunk) {
        MultipartFile file = chunk.getUploadFile();
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(FileInfoUtils.generatePath(uploadFolder, chunk));
            //文件写入指定路径
            Files.write(path, bytes);
            chunk.setType(file.getContentType());
            if (chunkService.saveChunk(chunk) < 0) {
                return HttpServletResponse.SC_INTERNAL_SERVER_ERROR + "";
            }
        } catch (IOException e) {
            log.error("上传异常：{}", e.getMessage(), e);
            return HttpServletResponse.SC_INTERNAL_SERVER_ERROR + "";
        }
        return HttpServletResponse.SC_OK + "";
    }

    @GetMapping("/chunk")
    public UploadResult checkChunk(ChunkInfo chunk, HttpServletResponse response) {
        UploadResult uploadResult = new UploadResult();
        //默认返回其他状态码，前端不进去checkChunkUploadedByResponse函数，正常走标准上传
        response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
        String file = uploadFolder + "/" + chunk.getIdentifier() + "/" + chunk.getFilename();
        //先判断整个文件是否已经上传过了，如果是，则告诉前端跳过上传，实现秒传
        if (FileInfoUtils.fileExists(file)) {
            uploadResult.setSkipUpload(true);
            uploadResult.setLocation(file);
            response.setStatus(HttpServletResponse.SC_OK);
            uploadResult.setMessage("秒传功能");
            return uploadResult;
        }
        //如果完整文件不存在，则去数据库判断当前哪些文件块已经上传过了，把结果告诉前端，跳过这些文件块的上传，实现断点续传
        ArrayList<Integer> list = chunkService.checkChunk(chunk);
        if (list != null && list.size() > 0) {
            uploadResult.setSkipUpload(false);
            uploadResult.setUploadedChunks(list);
            response.setStatus(HttpServletResponse.SC_OK);
            uploadResult.setMessage("断点续传");
            return uploadResult;
        }
        return uploadResult;
    }

    @PostMapping("/mergeFile")
    public ResponseEntity mergeFile(@RequestBody FileInfoVO fileInfoVO) {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setId(String.valueOf(IdUtil.getSnowflakeNextId()));
        fileInfo.setFilename(fileInfoVO.getName());
        fileInfo.setIdentifier(fileInfoVO.getUniqueIdentifier());
        fileInfo.setId(fileInfoVO.getId());
        fileInfo.setTotalSize(fileInfoVO.getSize());
        fileInfo.setRefProjectId(fileInfoVO.getRefProjectId());
        fileInfo.setType(FileInfoUtils.getFileType(fileInfo.getFilename()));
        //进行文件的合并操作
        String filename = fileInfo.getFilename();
        String file = uploadFolder + File.separator + fileInfo.getIdentifier() + File.separator + filename;
        String folder = uploadFolder + File.separator + fileInfo.getIdentifier();
        String fileSuccess = FileInfoUtils.merge(file, folder, filename);
        fileInfo.setLocation(file);
        //文件合并成功后，保存记录至数据库
        if ("200".equals(fileSuccess)) {
            if (fileInfoService.save(fileInfo)) {
                return ResponseEntity.status(HttpStatus.OK).body("200");
            }
        }
        if ("500".equals(fileSuccess)) {
            fileInfoService.saveOrUpdate(fileInfo, new LambdaQueryWrapper<FileInfo>()
                    .eq(FileInfo::getId, fileInfo.getId())
                    .eq(FileInfo::getRefProjectId, fileInfo.getRefProjectId())
            );
            return ResponseEntity.status(HttpStatus.OK).body("200");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("500");
    }

    /**
     * 查询列表
     */
    @RequestMapping(value = "/selectFileList", method = RequestMethod.POST)
    public ResponseEntity<Page<FileInfo>> selectFileList(@RequestBody QueryInfo query) {
        LambdaQueryWrapper<FileInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StrUtil.isNotEmpty(query.getNameSearch()), FileInfo::getFilename, query.getNameSearch());
        Page<FileInfo> page = fileInfoService.page(new Page<>(query.getPageIndex(), query.getPageSize()), queryWrapper);
        page.getRecords().forEach(record -> record.setUploadTimeString(DateUtil.format(record.getUploadTime(), "yyyy-MM-dd HH:mm:ss")));
        return ResponseEntity.ok(page);
    }

    /**
     * 下载文件
     */
    @GetMapping(value = "/download")
    public void download(HttpServletRequest req, HttpServletResponse resp) {
        String location = req.getParameter("location");
        String fileName = req.getParameter("filename");
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        OutputStream fos = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(location));
            fos = resp.getOutputStream();
            bos = new BufferedOutputStream(fos);
            ServletUtils.setFileDownloadHeader(req, resp, fileName);
            int byteRead = 0;
            byte[] buffer = new byte[8192];
            while ((byteRead = bis.read(buffer, 0, 8192)) != -1) {
                bos.write(buffer, 0, byteRead);
            }
        } catch (Exception e) {
            log.error("下载文件异常: {}", e.getMessage(), e);
        } finally {
            try {
                bos.flush();
                bis.close();
                fos.close();
                bos.close();
            } catch (Exception e) {
                log.error("关闭流异常: {}", e.getMessage(), e);
            }
        }
    }

    /**
     * 删除
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteFile(@PathVariable String id) {
        return fileInfoService.removeById(id) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }
}
