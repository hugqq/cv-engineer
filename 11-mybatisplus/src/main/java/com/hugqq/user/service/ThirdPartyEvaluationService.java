package com.hugqq.user.service;


import com.baomidou.mybatisplus.extension.service.IService;

import com.hugqq.user.entity.ThirdPartyEvaluation;


public interface ThirdPartyEvaluationService extends IService<ThirdPartyEvaluation> {

    void myUpdate(String s1, String id);
}

