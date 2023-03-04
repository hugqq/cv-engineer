package com.hugqq.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据Entity类
 */
@Data
public abstract class BaseEntity{

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    public String id;


    /**
     * 搜索值
     */
    @TableField(exist = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String searchValue;

    /**
     * 创建者
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date createTime;

    /**
     * 更新者
     */
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date updateTime;



    /**
     * 备注
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String remark;


    /**
     * 请求参数
     */
    @TableField(exist = false)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, Object> params = new HashMap<>();

}
