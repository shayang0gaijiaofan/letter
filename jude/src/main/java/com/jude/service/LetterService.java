package com.jude.service;

import com.jude.entity.Letter;
import com.jude.entity.dto.LetterWithTime;
import org.springframework.data.domain.Sort.Direction;

import java.util.List;

/**
 * 供应商Service接口
 * @author jude
 *
 */
public interface LetterService {

	/**
	 * 全部获取
	 * @return
	 */
	public List<Letter> findAll();
	
	/**
	 * 根据id查询实体
	 * @param id
	 * @return
	 */
	public Letter findById(Integer id);
	
	/**
	 * 修改或者修改供应商信息
	 * @param Letter
	 */
	public void save(Letter Letter);
	
	/**
	 * 根据条件分页查询供应商信息
	 * @param Letter
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param properties
	 * @return
	 */
	public List<Letter> list(LetterWithTime Letter, Integer page, Integer pageSize, Direction direction, String... properties);
	
	/**
	 * 获取总记录数
	 * @param Letter
	 * @return
	 */
	public Long getCount(Letter Letter);
	
	/**
	 * 根据id删除供应商
	 * @param id
	 */
	public void delete(Integer id);
}
