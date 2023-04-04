package com.hugqq.user.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.http.ResponseEntity;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;


import com.hugqq.user.entity.ThirdPartyEvaluation;
import com.hugqq.user.service.ThirdPartyEvaluationService;

/**
 * @author hugqq
 * @since 2023-03-27
 */

@RestController
@RequestMapping("thirdPartyEvaluation")
@Slf4j
public class ThirdPartyEvaluationController {

    @Autowired
    private ThirdPartyEvaluationService thirdPartyEvaluationService;

    @GetMapping("list")
    public ResponseEntity list(ThirdPartyEvaluation thirdPartyEvaluation, HttpServletRequest request) {
        LambdaQueryWrapper<ThirdPartyEvaluation> lambdaQueryWrapper = new LambdaQueryWrapper<ThirdPartyEvaluation>();
        List<ThirdPartyEvaluation> list = thirdPartyEvaluationService.list(lambdaQueryWrapper);
        return ResponseEntity.ok(list);
    }

    @GetMapping("page")
    public ResponseEntity pageList(ThirdPartyEvaluation thirdPartyEvaluation,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest request) {
        LambdaQueryWrapper<ThirdPartyEvaluation> lambdaQueryWrapper = new LambdaQueryWrapper<ThirdPartyEvaluation>();
        Page<ThirdPartyEvaluation> page = thirdPartyEvaluationService.page(new Page<>(pageNo, pageSize), lambdaQueryWrapper);
        return ResponseEntity.ok(page);
    }

    @GetMapping("getOne")
    public ResponseEntity getOne(ThirdPartyEvaluation thirdPartyEvaluation, HttpServletRequest request) {
        LambdaQueryWrapper<ThirdPartyEvaluation> lambdaQueryWrapper = new LambdaQueryWrapper<ThirdPartyEvaluation>();
        ThirdPartyEvaluation one = thirdPartyEvaluationService.getOne(lambdaQueryWrapper);
        return ResponseEntity.ok(one);
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(String id, HttpServletRequest request) {
        ThirdPartyEvaluation one = thirdPartyEvaluationService.getById(id);
        return ResponseEntity.ok(one);
    }

    @PostMapping("saveOrUpdate")
    public ResponseEntity saveOrUpdate(ThirdPartyEvaluation thirdPartyEvaluation, HttpServletRequest request) {
        return ResponseEntity.ok(thirdPartyEvaluationService.saveOrUpdate(thirdPartyEvaluation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity del(@PathVariable String id, HttpServletRequest request) {
        return ResponseEntity.ok(thirdPartyEvaluationService.removeById(id));
    }


    @GetMapping("/update")
    public ResponseEntity update() {
        List<ThirdPartyEvaluation> list = thirdPartyEvaluationService.list(new LambdaQueryWrapper<ThirdPartyEvaluation>()
                .orderByAsc(ThirdPartyEvaluation::getCreateDate).last("limit 513,1600"));
        extracted(list);
        return ResponseEntity.ok("");
    }
    int i = 513;
    private void extracted(List<ThirdPartyEvaluation> list) {
        for (ThirdPartyEvaluation thirdPartyEvaluation : list) {
            boolean update = thirdPartyEvaluationService.update(
                    new UpdateWrapper<ThirdPartyEvaluation>()
                            .setSql("`pro_urls` = REPLACE (`pro_urls`,'" + thirdPartyEvaluation.getProUrls() + "," + "','')")
                            .ne("id", thirdPartyEvaluation.getId())
                            .like("pro_urls", thirdPartyEvaluation.getProUrls() + ","
                            ));
            LocalDateTime createDate = thirdPartyEvaluation.getCreateDate();
            if (update){
                list = thirdPartyEvaluationService.list(new LambdaQueryWrapper<ThirdPartyEvaluation>()
                        .ge(ThirdPartyEvaluation::getCreateDate, createDate)
                        .orderByAsc(ThirdPartyEvaluation::getCreateDate));
                i++;
                log.info("第{}次", i);
                extracted(list);
            }
        }

    }

}


