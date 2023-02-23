package com.hugqq.entity;

import com.hugqq.entity.base.AbstractAuditModel;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;

/**
 * 部门实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orm_department")
public class Department extends AbstractAuditModel {

    /**
     * 部门名
     */
    @Column(name = "name", columnDefinition = "varchar(255) not null")
    private String name;

    /**
     * 所属层级
     */
    @Column(name = "levels", columnDefinition = "int not null default 0")
    private Integer levels;
    /**
     * 排序
     */
    @Column(name = "order_no", columnDefinition = "int not null default 0")
    private Integer orderNo;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "superior")
    private Collection<Department> children;
    /**
     * 部门下用户集合
     */
    @ManyToMany(mappedBy = "departmentList")
    private Collection<User> userList;

}
