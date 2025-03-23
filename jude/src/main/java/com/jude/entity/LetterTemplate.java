package com.jude.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 客户实体
 * @author jude
 *
 */
@Data
@Entity
@Table(name="t_letter_template")
public class LetterTemplate implements Serializable {
	//extends BaseEntity
	@Id
	//@GeneratedValue
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "create_time", insertable = false, updatable = false, columnDefinition = "datetime default current_timestamp")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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
	@Column(name = "pic_uuid", length=200)
	private String picUUID   ; // 函件模板图片url(UUID)
	@Column(name = "word_uuid", length=200)
	private String wordUUID   ; // 函件模板word原件(UUID)
	@Column(name = "origin_word_name", length=200)
	private String originWordName   ; // 函件模板原始名称

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date reviewTime;	// 审核时间
//
//	@OneToMany(targetEntity = Letter.class, fetch = FetchType.EAGER)
//	@org.hibernate.annotations.ForeignKey(name = "none")
//	@JoinColumn(name = "tempNum", referencedColumnName = "temNum")
//	private List<LetterTemplate> letterList;
}
