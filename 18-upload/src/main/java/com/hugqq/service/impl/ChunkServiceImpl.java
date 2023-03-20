package com.hugqq.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hugqq.entity.ChunkInfo;
import com.hugqq.mapper.ChunkInfoMapper;
import com.hugqq.service.ChunkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class ChunkServiceImpl  extends ServiceImpl<ChunkInfoMapper, ChunkInfo>  implements ChunkService {
    @Autowired
    private ChunkInfoMapper chunkInfoMapper;


    @Override
    public Integer saveChunk(ChunkInfo chunk) {
        chunk.setId(String.valueOf(IdUtil.getSnowflakeNextId()));
        return chunkInfoMapper.insert(chunk);
    }

    @Override
    public ArrayList<Integer> checkChunk(ChunkInfo chunk) {
        return chunkInfoMapper.selectChunkNumbers(chunk);
    }

    @Override
    public boolean checkChunk(String identifier, Integer chunkNumber) {
        return false;
    }

}
