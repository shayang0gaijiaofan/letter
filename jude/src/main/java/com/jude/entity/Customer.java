package com.jude.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 客户实体
 * @author jude
 *
 */
//@Data
@Entity
@Table(name="t_customer")
public class Customer  {//extends BaseEntity

	@Override
	public String toString() {
		return "Customer{" +
				"id=" + id +
				", name='" + name + '\'' +
				", contact='" + contact + '\'' +
				", number='" + number + '\'' +
				", address='" + address + '\'' +
				", remarks='" + remarks + '\'' +
				'}';
	}
	@Id
	@GeneratedValue
	private Integer id;
	@Column(length=200)
	private String name; // 供应商名称
	
	@Column(length=50)
	private String contact; // 联系人
	
	@Column(length=50)
	private String number; // 联系电话
	
	@Column(length=300)
	private String address; // 联系地址
	
	@Column(length=1000)
	private String remarks; // 备注


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
