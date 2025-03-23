package com.jude.repository;

import com.jude.entity.CaseCop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 供应商Repository接口
 * @author jude
 *
 */
public interface CaseCopRepository extends JpaRepository<CaseCop, Integer>,JpaSpecificationExecutor<CaseCop>{
	@Query(value="select * from t_case_info where  case_id = ?1",nativeQuery=true)
	List<CaseCop> listByCaseId(String string);


	@Query(value="select * from t_cop_case where  cop_id = ?1",nativeQuery=true)
	List<CaseCop> findByCopId(String copId);

	/**
	 * 根据名称模糊查询供应商信息
	 * @param name
	 * @return
	 */
	//@Query(value="select * from t_letter_send")
	//public List<LetterSend> findAll();
	
}
