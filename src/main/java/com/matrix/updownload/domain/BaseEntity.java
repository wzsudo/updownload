package com.matrix.updownload.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * @ClassName BaseEntity
 * @Author wby
 * @Date 2019/4/22 15:03
 * @Version 1.0
 * @Description TODO
 **/
@Data
@MappedSuperclass
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = -1714643429788724210L;
    @Id
    @GeneratedValue(generator = "uuid", strategy = GenerationType.AUTO)
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;
}
