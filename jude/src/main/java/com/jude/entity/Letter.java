package com.jude.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jude.entity.dto.LetterWithTemp;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 客户实体
 * @author jude
 *
 */
@Data
@Entity
@Table(name="t_letter")
@DynamicUpdate
public class Letter implements Serializable {
	//extends BaseEntity
	@Id
	//@GeneratedValue
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "create_time", insertable = false, updatable = false, columnDefinition = "datetime default current_timestamp")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	@Column(length=50)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

	@Column(length=50)
	private String updateUser;

	@Column(length=200)
	private String batchNum   ; //批次编号
	@Column(insertable = false)
	private Integer importCnt	; // 导入数量
	@Column(insertable = false)
	private Integer successCnt ; // 生成成功数量
	@Column(insertable = false)
	private Integer sentCnt ; // 已发送数量
	@Column(insertable = false)
	private Integer streetCnt   ;	// 已转直邮数量
	@Column(length=200)
	private String letterNum   ; //	 函件模板编号
	@Column(length=200)
	private String temNum   ;//		模板编号

	@ManyToOne(targetEntity = LetterTemplate.class, fetch = FetchType.EAGER)
	@org.hibernate.annotations.ForeignKey(name = "none")
	@JoinColumn(name = "temNum", referencedColumnName = "tempNum", insertable = false, updatable = false)
	private LetterTemplate temp;
}

