package com.hugqq.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class OrmDepartment {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 上级id
     */
    private Integer superior;

    /**
     * 层级
     */
    private Integer levels;

    /**
     * 排序
     */
    private Integer orderNo;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 上次更新时间
     */
    private LocalDateTime lastUpdateTime;
}