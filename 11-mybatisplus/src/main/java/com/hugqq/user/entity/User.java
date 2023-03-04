package com.hugqq.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hugqq.entity.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 
 * @author hugqq
 * @since 2023-02-25
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_user")
public class User extends BaseEntity {

    @TableField("`name`")
    private String name;

    @TableField("age")
    private Integer age;

    @TableField("deleted")
    @TableLogic
    @JsonIgnore
    private String deleted;


}
