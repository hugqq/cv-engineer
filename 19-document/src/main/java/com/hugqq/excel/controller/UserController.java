package com.hugqq.excel.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.excel.EasyExcel;
import com.hugqq.excel.entity.User;
import com.hugqq.excel.listener.ExcelExceptionListener;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.IntStream;

@RestController
@Slf4j
public class UserController {

    // 在线图片
    private final String ONLINE_IMG_PATH = "https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png";

    // 本地图片
    private final String IMG_PATH = "C:\\Users\\33047\\IdeaProjects\\cv-engineer\\19-document\\src\\main\\resources\\static\\demo.png";

    private final String EXCEL_PATH = Objects.requireNonNull(this.getClass().getResource("/")).getPath();

    @SneakyThrows
    private List<User> getStudentEntities() {
        // 本地图片
//        byte[] image = FileUtils.readFileToByteArray(new File(IMG_PATH));
        // 在线图片
//        byte[] bytes = HttpUtil.createGet(ONLINE_IMG_PATH).execute().bodyBytes();

        List<User> class1 = new ArrayList<>();
        IntStream.range(0,1000).forEach(e->{
            class1.add(User.builder().id(e).name("张三"+e).sex(1).phone("13333333333")
                    .email("123456789@qq.com").birthday(LocalDateTime.now())
                    .card(RandomUtil.randomNumbers(18))
                    .registrationDate(LocalDateTime.now())
//                    .img(bytes)
                    .build());
        });
        return class1;
    }

    @SneakyThrows
    @RequestMapping(value = "exportExcel")
    public void exportExcel(HttpServletResponse response) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("generateExcel");
        List<User> list = getStudentEntities();
        String randomFileName = IdUtil.simpleUUID() + ".xlsx";
        // 临时文件
        String temporaryFile = EXCEL_PATH + randomFileName;
        // 排除字段
        Set<String> excludeColumnFiledNames = new HashSet<>();
        excludeColumnFiledNames.add("id");
        // 指定字段
        // Set<String> includeColumnFiledNames = new HashSet<String>();
        // includeColumnFiledNames.add("name");
//        // 头的策略
//        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
//        // 背景设置为红色
//        headWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE1.getIndex());
//        WriteFont headWriteFont = new WriteFont();
//        headWriteFont.setFontHeightInPoints((short) 20);
//        headWriteCellStyle.setWriteFont(headWriteFont);
//        // 内容的策略
//        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
//        // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色.头默认了 FillPatternType所以可以不指定
//        contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
//        // 背景绿色
//        contentWriteCellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
//        WriteFont contentWriteFont = new WriteFont();
//        // 字体大小
//        contentWriteFont.setFontHeightInPoints((short) 20);
//        contentWriteCellStyle.setWriteFont(contentWriteFont);
//        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
//        HorizontalCellStyleStrategy horizontalCellStyleStrategy =
//                new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
        // 如果这里想使用Excel2003 则 传入excelType参数即可
        EasyExcel.write(temporaryFile, User.class)
                .autoCloseStream(true)
                .excludeColumnFiledNames(excludeColumnFiledNames)
//                .registerWriteHandler(horizontalCellStyleStrategy)
                .sheet("学生信息")
                .doWrite(list);
        // 读到流中
        InputStream inStream = new FileInputStream(temporaryFile);// 文件的存放路径
        stopWatch.stop();
        // 清空response
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        String fileName = "user.xlsx";
        response.addHeader("Content-Disposition", "attachment;filename=" +
                new String(fileName.getBytes(StandardCharsets.UTF_8)));
        stopWatch.start("DownExcel");
        // 循环取出流中的数据
        byte[] b = new byte[512];
        int len;
        while ((len = inStream.read(b)) > 0) {
            response.getOutputStream().write(b, 0, len);
        }
        inStream.close();
        // 下载完成后删除临时文件
        FileUtil.del(temporaryFile);
        stopWatch.stop();
        log.info(stopWatch.prettyPrint());
        log.info("耗时：{}", stopWatch.getTotalTimeSeconds());
    }


    @RequestMapping(value = "importExcel")
    public void importExcel() {
        String fileName = "C:\\Users\\33047\\Desktop\\user.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(fileName, User.class, new ExcelExceptionListener<User>()).sheet().doRead();
    }

}
