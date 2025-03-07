package com.jude.service;

import com.jude.entity.CaseCop;
import org.springframework.data.domain.Sort.Direction;

import java.util.List;

/**
 * 客户Service接口
 * @author java1234 小锋老师
 *
 */
public interface CaseCopService {

	/**
	 * 根据名称模糊查询客户信息
	 * @param name
	 * @return
	 */
	public List<CaseCop> findByName(String name);
	
	/**
	 * 根据id查询实体
	 * @param id
	 * @return
	 */
	public CaseCop findById(Integer id);
	
	/**
	 * 添加或者修改客户信息
	 * @param CaseCop
	 */
	public CaseCop save(CaseCop CaseCop);
	
	/**
	 * 根据条件分页查询客户信息
	 * @param CaseCop
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param properties
	 * @return
	 */
	public List<CaseCop> list(CaseCop CaseCop,Integer page,Integer pageSize,Direction direction,String... properties);
	
	/**
	 * 获取总记录数
	 * @param CaseCop
	 * @return
	 */
	public Long getCount(CaseCop CaseCop);
	
	/**
	 * 根据id删除客户
	 * @param id
	 */
	public void delete(Integer id);
}
