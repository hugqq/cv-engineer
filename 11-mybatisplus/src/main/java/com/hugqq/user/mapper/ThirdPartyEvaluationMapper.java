package com.hugqq.user.mapper;

import com.hugqq.user.entity.ThirdPartyEvaluation;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


@Mapper
public interface ThirdPartyEvaluationMapper extends BaseMapper<ThirdPartyEvaluation> {
    @Update("UPDATE `third_party_evaluation` \n" +
            "SET \n" +
            "`pro_urls` = (SELECT REPLACE (`pro_urls`,#{s1},''))\n" +
            "WHERE\n" +
            "\t`id` != #{id} and `pro_urls` like concat('%',#{s1},'%')")
    @Options(useCache = false)
    void myUpdate(String s1, String id);
}

