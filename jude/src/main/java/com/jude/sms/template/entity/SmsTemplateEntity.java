package com.jude.sms.template.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "t_sms_template")
public class SmsTemplateEntity {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "tem_id")
    private int temId;
    @Size(max = 32)
    @Column(name = "supplier", length = 32)
    private String supplier;

    @Size(max = 32)
    @Column(name = "template_id", length = 32)
    private String templateid;

    @Size(max = 18)
    @Column(name = "account_id", length = 18)
    private String accountId;

    @Size(max = 30)
    @Column(name = "template_name", length = 30)
    private String templateName;

    @Size(max = 18)
    @Column(name = "template_sign", length = 18)
    private String templateSign;


    @Lob
    @Column(name = "template_content")
    private String templateContent;

    @Column(name = "template_auth")
    private Integer templateAuth;

    @Size(max = 2)
    @Column(name = "verify_status", nullable = false, length = 2)
    private String verifyStatus;

    @Column(name = "version")
    private Integer version;

    @Column(name = "is_latest")
    private Boolean isLatest = false;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
}