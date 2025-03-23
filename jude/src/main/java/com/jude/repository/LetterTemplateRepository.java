package com.jude.repository;

import com.jude.entity.LetterTemplate;
import com.jude.entity.SaleListGoods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 供应商Repository接口
 * @author jude
 *
 */
public interface LetterTemplateRepository extends JpaRepository<LetterTemplate, Integer>,JpaSpecificationExecutor<LetterTemplate>{
	/**
	 * 根据模板编号查询模板
	 *
	 */
	@Query(value="SELECT * FROM t_letter_template WHERE temp_num = ?1", nativeQuery=true)
	public List<LetterTemplate> getLetterTemplatesByTempNum(String tempNum);
}
