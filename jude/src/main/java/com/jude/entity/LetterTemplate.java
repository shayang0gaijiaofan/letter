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
@Table(name="t_letter_template")
public class LetterTemplate {
	//extends BaseEntity
	@Id
	//@GeneratedValue
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "create_time", insertable = false, updatable = false, columnDefinition = "datetime default current_timestamp")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;	// 申请时间

	@Column(length=50)
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updateTime;

	@Column(length=50)
	private String updateUser;

	@Column(length=200)
	private String tempNum   ;// 模板编号
	@Column(length=200)
	private String tempName   ;// 模板名称
	@Column(length=200)
	private String type   ;// 函件类型
	@Column(length=200)
	private String lawFirmName   ; // 律所名称
	@Column(length=200)
	private String lawyerName   ; // 律师姓名
	@Column(length=200)
	private String validateUrl   ; // 函件验证网址
	@Column(length=200)
	private String applyPerson   ; // 申请人姓名
	@Column(length=200)
	private String reviewStatus   ; // 审核状态

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date reviewTime;	// 审核时间
}
