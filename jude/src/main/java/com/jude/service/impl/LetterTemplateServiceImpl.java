package com.jude.service.impl;

import com.jude.entity.LetterTemplate;
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
import java.util.List;

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
	public void save(LetterTemplate LetterTemplate) {
		LetterTemplateRepository.save(LetterTemplate);
	}

	@Override
	public List<LetterTemplate> list(LetterTemplate LetterTemplate, Integer page, Integer pageSize, Direction direction, String... properties) {
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
					if(StringUtil.isNotEmpty(LetterTemplate.getType())){
						predicate.getExpressions().add(cb.like(root.get("type"), "%"+LetterTemplate.getType().trim()+"%"));
					}
					if(StringUtil.isNotEmpty(LetterTemplate.getReviewStatus())){
						predicate.getExpressions().add(cb.like(root.get("reviewStatus"), "%"+LetterTemplate.getReviewStatus().trim()+"%"));
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
	public LetterTemplate findById(Integer id) {
		return LetterTemplateRepository.findOne(id);
	}

	@Override
	public List<LetterTemplate> findAll() {
		return LetterTemplateRepository.findAll();
	}


}
