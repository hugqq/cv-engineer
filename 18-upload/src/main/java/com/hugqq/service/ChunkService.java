package com.hugqq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hugqq.entity.ChunkInfo;

import java.util.ArrayList;


/**
 * 文件块处理
 */
public interface ChunkService  extends IService<ChunkInfo> {
    /**
     * 保存文件块
     */
    public Integer saveChunk(ChunkInfo chunk);

    /**
     * 检查文件块是否存在
     */
    boolean checkChunk(String identifier, Integer chunkNumber);
    
    /**
     * 查询哪些文件块已经上传
     */
    public ArrayList<Integer> checkChunk(ChunkInfo chunk);
}
