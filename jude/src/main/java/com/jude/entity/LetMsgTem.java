package com.jude.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * 客户实体
 *
 * @author jude
 */
@Data
@Entity
@Table(name = "t_msg_tem")
public class LetMsgTem {//extends BaseEntity
    @Id
    @GeneratedValue
    private Integer id;
    @Column(length=200)
    private String msgTemNum;
    @Column(length=200)
    private String msgTemName;
    @Column(length=200)
    private String msgTitle;
    @Column(length=200)
    private String msfText;
    @Column(length=200)
    private String signature;
    @Column(name = "create_time", insertable = false, updatable = false,  columnDefinition = "datetime default current_timestamp")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;	// 申请时间
    @Column(length=200)
    private String approveStatus;
    @Column(length=200)
    private String approveInfo;

}
