package com.jude.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * 客户实体
 * @author jude
 *
 */
@Data
@Entity
@Table(name="t_letter_street")
public class LetterStreet {
	//extends BaseEntity
	@Id
	//@GeneratedValue
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column
	private Date createTime;	// 创建时间

	@Column(length=50)
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updateTime;

	@Column(length=50)
	private String updateUser;

	@Column(length=200)
	private String phoneNum   ; // 发送手机号
	@Column(length=200)
	private String phoneAddr   ; // 号码所属
	@Column(length=200)
	private String overduePerson   ; // 逾期人姓名
	@Column(length=200)
	private String overdueAddr   ; // 逾期人地址
	@Column(length=20)
	private String addrNormStatus   ; // 地址规范状态
	@Column(length=200)
	private String tempNum;	// 函件模板编号
	@Column(length=200)
	private String letterNum;	// 函件编号
	private Integer debtAmount   ; // 欠款金额
	@Column(length=200)
	private String letterPic;	// 函件图片
	private Integer letterPages   ; // 函件页数
	@Column(length=200)
	private String letterType    ; 	// 函件类型
	@Column(length=50)
	private String streetStatus     ; // 直邮状态
	@Column(length=200)
	private String trackingNum     ;	// 物流单号
}
