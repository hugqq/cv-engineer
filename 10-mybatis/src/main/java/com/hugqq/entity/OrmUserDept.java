package com.hugqq.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class OrmUserDept {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 部门id
     */
    private Integer deptId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 上次更新时间
     */
    private LocalDateTime lastUpdateTime;
}