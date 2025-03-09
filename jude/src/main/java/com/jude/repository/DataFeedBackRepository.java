package com.jude.repository;

import com.jude.entity.DataFeedBack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 供应商Repository接口
 * @author jude
 *
 */
public interface DataFeedBackRepository extends JpaRepository<DataFeedBack, Integer>,JpaSpecificationExecutor<DataFeedBack>{

	/**
	 * 根据名称模糊查询供应商信息
	 * @param name
	 * @return
	 */
	//@Query(value="select * from t_DataFeedBack")
	//public List<DataFeedBack> findAll();
	
}
