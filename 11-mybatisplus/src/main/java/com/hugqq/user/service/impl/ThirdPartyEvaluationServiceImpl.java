package com.hugqq.user.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.hugqq.user.entity.ThirdPartyEvaluation;
import com.hugqq.user.mapper.ThirdPartyEvaluationMapper;
import com.hugqq.user.service.ThirdPartyEvaluationService;

/**
 * 第三方测评表 服务实现类
 * @author hugqq
 * @since 2023-03-27
 */

@Transactional
@Service
@Primary
public class ThirdPartyEvaluationServiceImpl extends ServiceImpl<ThirdPartyEvaluationMapper, ThirdPartyEvaluation> implements ThirdPartyEvaluationService {

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void myUpdate(String s1, String id) {
        baseMapper.myUpdate(s1, id);
    }
}

