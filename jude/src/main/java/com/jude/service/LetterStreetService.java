package com.jude.service;

import com.jude.entity.LetterStreet;
import org.springframework.data.domain.Sort.Direction;

import java.util.List;

/**
 * 供应商Service接口
 * @author jude
 *
 */
public interface LetterStreetService {

	/**
	 * 全部获取
	 * @return
	 */
	public List<LetterStreet> findAll();
	
	/**
	 * 根据id查询实体
	 * @param id
	 * @return
	 */
	public LetterStreet findById(Integer id);
	
	/**
	 * 修改或者修改供应商信息
	 * @param LetterStreet
	 */
	public void save(LetterStreet LetterStreet);
	
	/**
	 * 根据条件分页查询供应商信息
	 * @param LetterStreet
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param properties
	 * @return
	 */
	public List<LetterStreet> list(LetterStreet LetterStreet,Integer page,Integer pageSize,Direction direction,String... properties);
	
	/**
	 * 获取总记录数
	 * @param LetterStreet
	 * @return
	 */
	public Long getCount(LetterStreet LetterStreet);
	
	/**
	 * 根据id删除供应商
	 * @param id
	 */
	public void delete(Integer id);
}
