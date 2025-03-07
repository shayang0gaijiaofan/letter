package com.jude.repository;

import com.jude.entity.CaseTmp;
import com.jude.entity.CaseTmpDetail;
import com.jude.entity.CopInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 供应商Repository接口
 * @author jude
 *
 */
public interface CaseTmpDetailRepository extends JpaRepository<CaseTmpDetail, Integer>,JpaSpecificationExecutor<CaseTmpDetail>{

	/**
	 * 根据名称模糊查询供应商信息
	 * @param name
	 * @return
	 */
	@Query(value="select * from t_case_tmp_detail where tmp_id = ?1 ",nativeQuery=true)
	public List<CaseTmpDetail> findByTmpId(String tmpId);

}
