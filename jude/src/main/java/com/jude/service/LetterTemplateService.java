package com.jude.service;

import com.jude.common.ResponseEntity;
import com.jude.entity.LetterTemplate;
import com.jude.entity.dto.LetterTempWithFile;
import com.jude.entity.dto.LetterTemplateWithTime;
import org.springframework.data.domain.Sort.Direction;

import java.util.List;
import java.util.Map;

/**
 * 供应商Service接口
 * @author jude
 *
 */
public interface LetterTemplateService {

	/**
	 * 全部获取
	 * @return
	 */
	public List<LetterTemplate> findAll();
	
	/**
	 * 根据id查询实体
	 * @param id
	 * @return
	 */
	public LetterTemplate findById(Integer id);

	public List<LetterTemplate> findByNum(String tempNum);
	
	/**
	 * 修改函件模板信息
	 * @param temp
	 */
	public void save(LetterTemplate temp);

	/**
	 * 添加函件模板信息
	 * @param temp
	 */
	public void insert(LetterTemplate temp);
	
	/**
	 * 根据条件分页查询供应商信息
	 * @param LetterTemplate
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param properties
	 * @return
	 */
	public List<LetterTemplate> list(LetterTemplateWithTime LetterTemplate, Integer page, Integer pageSize, Direction direction, String... properties);
	
	/**
	 * 获取总记录数
	 * @param LetterTemplate
	 * @return
	 */
	public Long getCount(LetterTemplate LetterTemplate);
	
	/**
	 * 根据id删除供应商
	 * @param id
	 */
	public void delete(Integer id);

	List<Map<String, String>> querySendSupportTemplateList(String temId, String supplier);
}
