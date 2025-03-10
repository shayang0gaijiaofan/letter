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
@Table(name = "data_feed_back")
public class DataFeedBack  {//extends BaseEntity

    @Id
    //@GeneratedValue
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "create_time", insertable = false, updatable = false, columnDefinition = "datetime default current_timestamp")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    @Column(length=50)
    private Date updateTime;

    @Column(length=50)
    private String updateUser;

    @Column(length=50)
    private String isDelete;
    @Column(length = 200)
    private String userAcc; // 操作账号
    @Column(length = 200)
    private String overdueName; //逾期人姓名
    @Column(length = 200)
    private String sendTel; //发送手机号
    @Column(length = 200)
    private String letNum; //函件编号
    @Column(length = 200)
    private String letPic; //函件图片
    @Column(length = 200)
    private String letType; //函件类型
    @Column(length = 200)
    private String batchNum; //函件批次号
    @Column(length = 200)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date letSendTime; //函件发送时间
    @Column(length = 200)
    private String sendType; //发送类型
    @Column(length = 200)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date firstQueryTime; //首次查看时间
    @Column(length = 200)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastQueryTime; //最近查看时间
    private Integer viewCnt; // 查看次数
    @Column(length = 200)
    private String sendStatus; // 发送状态
    @Column(length = 200)
    private String customerMessage; // 客户留言



}
