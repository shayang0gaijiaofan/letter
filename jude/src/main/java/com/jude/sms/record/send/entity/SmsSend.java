package com.jude.sms.record.send.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;


@Getter
@Setter
@Entity
@Table(name = "t_sms_send")
public class SmsSend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 40)
    @Column(name = "sms_id", length = 40)
    private String smsId;

    @Size(max = 20)
    @Column(name = "phone", length = 20)
    private String phone;

    @Size(max = 2)
    @Column(name = "status", length = 2)
    private String status;

    @Size(max = 50)
    @Column(name = "operation_account", length = 50)
    private String operationAccount;

    @Size(max = 50)
    @Column(name = "letter_id", length = 50)
    private String letterId;

    @Column(name = "tem_id")
    private Integer temId;

    @Column(name = "sms_tem_id")
    private Integer smsTemId;

    @Size(max = 2)
    @Column(name = "suplier", length = 2)
    private String suplier;

    @Lob
    @Column(name = "sms_content")
    private String smsContent;
    @Lob
    @Column(name = "sms_params")
    private String smsParams;

    @Lob
    @Column(name = "resp_message")
    private String respMessage;

    @Column(name = "receive_time")
    private Date receiveTime;

    @Column(name = "charging_num")
    private int chargingNum;

    @Column(name = "create_time")
    private Date create_time;

    @Column(name = "update_time")
    private Date update_time;

}