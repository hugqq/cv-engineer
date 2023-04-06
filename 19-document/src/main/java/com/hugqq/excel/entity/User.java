package com.hugqq.excel.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.hugqq.excel.listener.GeneralConverter;
import com.hugqq.excel.listener.SexConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    private int id;

    @ExcelProperty(value = {"用户表", "用户名称"})
    private String name;

    @ExcelProperty(value = {"用户表", "用户性别"},converter = SexConverter.class)
    private int sex;

    @ColumnWidth(30)
    @DateTimeFormat("yyyy年MM月dd日 HH时mm分ss秒")
    @ExcelProperty(value = {"用户表", "出生日期"})
    private LocalDateTime birthday;

    @ColumnWidth(35)
    @DateTimeFormat("yyyy年MM月dd日 HH时mm分ss秒")
    @ExcelProperty(value = {"用户表", "入学日期"})
    private LocalDateTime registrationDate;

    @ExcelProperty(value = {"用户表", "用户身份证"},converter = GeneralConverter.class)
    private String card;


    @ColumnWidth(20)
    @ExcelProperty(value = {"用户表", "用户电话"},converter = GeneralConverter.class)
    private String phone;

    @ColumnWidth(20)
    @ExcelProperty(value = {"用户表", "电子邮箱"},converter = GeneralConverter.class)
    private String email;

    @ExcelProperty(value = {"用户表", "头像"})
    private byte[] img;

}
