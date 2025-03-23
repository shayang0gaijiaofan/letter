package com.jude.service.impl;

import com.jude.common.ResponseEntity;
import com.jude.entity.Letter;
import com.jude.entity.LetterTemplate;
import com.jude.entity.dto.LetterTempWithFile;
import com.jude.entity.dto.LetterTemplateWithTime;
import com.jude.repository.LetterTemplateRepository;
import com.jude.service.LetterTemplateService;
import com.jude.util.StringUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 供应商Service实现类
 * @author jude
 *
 */
@Service("LetterTemplateService")
public class LetterTemplateServiceImpl implements LetterTemplateService {

	@Resource
	private LetterTemplateRepository LetterTemplateRepository;
	

	@Override
	public void save(LetterTemplate temp) {
		// update
		LetterTemplate letTemp = LetterTemplateRepository.getOne(temp.getId());
		// 审批状态发生变更
		if (!letTemp.getReviewStatus().equals(temp.getReviewStatus())) {
			temp.setReviewTime(new Date());
		}
		LetterTemplateRepository.save(temp);
	}

	@Override
	public void insert(LetterTemplate temp) {
		// insert
		LetterTemplateRepository.save(temp);
	}

	@Override
	public List<LetterTemplate> list(LetterTemplateWithTime LetterTemplate, Integer page, Integer pageSize, Direction direction, String... properties) {
		Pageable pageable=new PageRequest(page-1, pageSize, direction,properties);
		Page<LetterTemplate> pageLetterTemplate= LetterTemplateRepository.findAll(
			new Specification<LetterTemplate>() {
			@Override
			public Predicate toPredicate(Root<LetterTemplate> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				if(LetterTemplate!=null){
					if(StringUtil.isNotEmpty(LetterTemplate.getTempNum())){
						predicate.getExpressions().add(cb.like(root.get("tempNum"), "%"+LetterTemplate.getTempNum().trim()+"%"));
					}
					if(StringUtil.isNotEmpty(LetterTemplate.getTempName())){
						predicate.getExpressions().add(cb.like(root.get("tempName"), "%"+LetterTemplate.getTempName().trim()+"%"));
					}
					if(StringUtil.isNotEmpty(LetterTemplate.getLawFirmName())){
						predicate.getExpressions().add(cb.like(root.get("lawFirmName"), "%"+LetterTemplate.getLawFirmName().trim()+"%"));
					}
					if(StringUtil.isNotEmpty(LetterTemplate.getType())
						&& !LetterTemplate.getType().equals("all")){
						predicate.getExpressions().add(cb.like(root.get("type"), "%"+LetterTemplate.getType().trim()+"%"));
					}
					if(StringUtil.isNotEmpty(LetterTemplate.getReviewStatus())
							&& !LetterTemplate.getReviewStatus().equals("all")){
						predicate.getExpressions().add(cb.like(root.get("reviewStatus"), "%"+LetterTemplate.getReviewStatus().trim()+"%"));
					}
					if (LetterTemplate.getCreateStartTime() != null) {
						predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("createTime"), LetterTemplate.getCreateStartTime()));
					}
					if (LetterTemplate.getCreateEndTime() != null) {
						// 设置时间为23时59分59秒
						Date date = LetterTemplate.getCreateEndTime();
						date.setHours(23);
						date.setMinutes(59);
						date.setSeconds(59);
						predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("createTime"), date));
					}
				}
				return predicate;
			}
		},
				pageable);
		return pageLetterTemplate.getContent();
	}

	@Override
	public Long getCount(LetterTemplate LetterTemplate) {
		Long count= LetterTemplateRepository.count(new Specification<LetterTemplate>() {

			@Override
			public Predicate toPredicate(Root<LetterTemplate> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
//				if(LetterTemplate!=null){
//					if(StringUtil.isNotEmpty(LetterTemplate.getName())){
//						predicate.getExpressions().add(cb.like(root.get("name"), "%"+LetterTemplate.getName().trim()+"%"));
//					}
//				}
				return predicate;
			}
		});
		return count;
	}

	@Override
	public void delete(Integer id) {
		LetterTemplateRepository.delete(id);
	}

	@Override
	public List<Map<String, String>> querySendSupportTemplateList(String temId, String supplier) {

		return Collections.emptyList();
	}

	@Override
	public LetterTemplate findById(Integer id) {
		return LetterTemplateRepository.findOne(id);
	}

	@Override
	public List<LetterTemplate> findByNum(String tempNum) {
		return LetterTemplateRepository.getLetterTemplatesByTempNum(tempNum);
	}

	@Override
	public List<LetterTemplate> findAll() {
		return LetterTemplateRepository.findAll();
	}


}
