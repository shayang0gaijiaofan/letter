package com.jude.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 供应商实体
 * @author jude
 *
 */
@Entity
@Data
@Table(name="t_cop_case")
public class CaseCop{
	@Id
	@GeneratedValue
	private Integer id; // 编号
	
	@Column(length=200)
	private String copId; // 公司id


	@Column(length=50)
	private String caseId; // 模版id

	@Column(length=50)
	private String tmpTitle; // 模版标题
}
