package com.hugqq.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hugqq.entity.BaseEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 第三方测评表
 * @author hugqq
 * @since 2023-03-27
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("third_party_evaluation")
public class ThirdPartyEvaluation{

    @TableId(value = "id", type = IdType.AUTO)
    public String id;
    /**
     * 点位ID（点位id为空，随机点位）
     */
    @TableField("point_id")
    private String pointId;

    /**
     * 点位名称
     */
    @TableField("point_name")
    private String pointName;

    /**
     * 经度
     */
    @TableField("longitude")
    private BigDecimal longitude;

    /**
     * 纬度
     */
    @TableField("latitude")
    private BigDecimal latitude;

    /**
     * 区域id
     */
    @TableField("area_id")
    private String areaId;

    /**
     * 街道/乡镇id
     */
    @TableField("street_id")
    private String streetId;

    /**
     * 地址
     */
    @TableField("address")
    private String address;

    /**
     * 回单时间
     */
    @TableField("back_time")
    private LocalDateTime backTime;

    /**
     * 状态：1草稿，2已发布
     */
    @TableField("`status`")
    private Integer status;

    /**
     * 单位id
     */
    @TableField("org_id")
    private String orgId;

    /**
     * 问题类型id
     */
    @TableField("pro_type_id")
    private String proTypeId;

    /**
     * 问题描述
     */
    @TableField("pro_description")
    private String proDescription;

    /**
     * 问题图片url
     */
    @TableField("pro_urls")
    private String proUrls;

    /**
     * 删除标识（0未删除，1已删除）
     */
    @TableField("del_flag")
    private String delFlag;

    /**
     * 备注
     */
    @TableField("remarks")
    private String remarks;

    /**
     * 创建时间
     */
    @TableField("create_date")
    private LocalDateTime createDate;

    /**
     * 更新时间
     */
    @TableField("update_date")
    private LocalDateTime updateDate;


}
