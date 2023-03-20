package com.hugqq.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hugqq.entity.ChunkInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

@Mapper
public interface ChunkInfoMapper  extends BaseMapper<ChunkInfo>  {

    @Select("""
    select
        chunk_number
    from chunk_info
        where identifier = #{identifier}
    and filename = #{filename}
    """)
    ArrayList<Integer> selectChunkNumbers(ChunkInfo chunk);
}
