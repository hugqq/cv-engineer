package com.hugqq.service.impl;

import java.util.List;

import javax.annotation.Resource;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hugqq.entity.ChunkInfo;
import com.hugqq.entity.FileInfo;
import com.hugqq.entity.QueryInfo;
import com.hugqq.mapper.ChunkInfoMapper;
import com.hugqq.mapper.FileInfoMapper;
import com.hugqq.service.FileInfoService;
import org.springframework.stereotype.Service;


/**
 * 文件处理类
 * @author 洋葱骑士
 *
 */
@Service
public class FileInfoServiceImpl  extends ServiceImpl<FileInfoMapper, FileInfo> implements FileInfoService {

}
