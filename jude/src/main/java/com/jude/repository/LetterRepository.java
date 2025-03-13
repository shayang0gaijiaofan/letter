package com.jude.repository;

import com.jude.entity.Letter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

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

//	@Query(value="SELECT let.*, temp.temp_num, temp.type  FROM `t_letter` AS let, `t_letter_template` AS temp WHERE let.tem_num = temp.temp_num",nativeQuery=true)
//	public List<LetterWithTemp> list();
	
}
