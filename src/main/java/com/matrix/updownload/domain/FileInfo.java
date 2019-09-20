package com.matrix.updownload.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @ClassName FileInfo
 * @Author wby
 * @Date 2019/4/22 14:58
 * @Version 1.0
 * @Description TODO
 **/
@Data
@Entity
@Table(name = "files")
public class FileInfo extends BaseEntity {
    //文件名
    private String name;
    //文件路径
    private String url;
}
