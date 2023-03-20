package com.hugqq.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 文件模型
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("file_info")
public class FileInfo {
	//附件编号
	private String id;

	//附件名称
    private String filename;

    @TableField(exist = false)
    private String nameSearch;

    //附件MD5标识
    private String identifier;

    //附件总大小
    private Long totalSize;
    @TableField(exist = false)
    private String totalSizeName;

    //附件类型
    private String type;

    //附件存储地址
    private String location;
    
    //删除标志
    @TableLogic
    private String delFlag;
    
    //文件所属目标（项目、学生、档案等，预留字段）
    private String refProjectId;
    
    //上传人
    private String uploadBy;

    //上传时间
    private Date uploadTime;
    @TableField(exist = false)
    private String uploadTimeString;

	public void setTotalSize(Long totalSize) {
		this.totalSize = totalSize;
		if(1024*1024 > this.totalSize && this.totalSize >= 1024 ) {
			this.totalSizeName = String.format("%.2f",this.totalSize.doubleValue()/1024) + "KB";
		}else if(1024*1024*1024 > this.totalSize && this.totalSize >= 1024*1024 ) {
			this.totalSizeName = String.format("%.2f",this.totalSize.doubleValue()/(1024*1024)) + "MB";
		}else if(this.totalSize >= 1024*1024*1024 ) {
			this.totalSizeName = String.format("%.2f",this.totalSize.doubleValue()/(1024*1024*1024)) + "GB";
		}else {
			this.totalSizeName = this.totalSize.toString() + "B";
		}
	}
}
