package com.jude.repository;

import com.jude.entity.Letter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 供应商Repository接口
 * @author jude
 *
 */
public interface LetterRepository extends JpaRepository<Letter, Integer>,JpaSpecificationExecutor<Letter>{

	/**
	 * 根据名称模糊查询供应商信息
	 * @param name
	 * @return
	 */
	//@Query(value="select * from t_letter")
	//public List<Letter> findAll();
	
}
