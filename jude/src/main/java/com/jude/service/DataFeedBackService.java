package com.jude.service;

import com.jude.entity.DataFeedBack;
import com.jude.entity.dto.DataFeedBackWithTime;
import org.springframework.data.domain.Sort.Direction;

import java.util.List;

/**
 * 供应商Service接口
 * @author jude
 *
 */
public interface DataFeedBackService {

	/**
	 * 全部获取
	 * @return
	 */
	public List<DataFeedBack> findAll();
	
	/**
	 * 根据id查询实体
	 * @param id
	 * @return
	 */
	public DataFeedBack findById(Integer id);
	
	/**
	 * 修改或者修改供应商信息
	 * @param DataFeedBack
	 */
	public void save(DataFeedBack DataFeedBack);
	
	/**
	 * 根据条件分页查询供应商信息
	 * @param DataFeedBack
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param properties
	 * @return
	 */
	public List<DataFeedBack> list(DataFeedBackWithTime DataFeedBack, Integer page, Integer pageSize, Direction direction, String... properties);
	
	/**
	 * 获取总记录数
	 * @param DataFeedBack
	 * @return
	 */
	public Long getCount(DataFeedBack DataFeedBack);
	
	/**
	 * 根据id删除供应商
	 * @param id
	 */
	public void delete(Integer id);
}
