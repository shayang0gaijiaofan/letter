package com.jude.service.impl;

import com.jude.entity.LetterStreet;
import com.jude.repository.LetterStreetRepository;
import com.jude.service.LetterStreetService;
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
@Service("LetterStreetService")
public class LetterStreetServiceImpl implements LetterStreetService {

	@Resource
	private LetterStreetRepository LetterStreetRepository;
	

	@Override
	public void save(LetterStreet LetterStreet) {
		LetterStreetRepository.save(LetterStreet);
	}

	@Override
	public List<LetterStreet> list(LetterStreet LetterStreet, Integer page, Integer pageSize, Direction direction, String... properties) {
		Pageable pageable=new PageRequest(page-1, pageSize, direction,properties);
		Page<LetterStreet> pageLetterStreet= LetterStreetRepository.findAll(
//			new Specification<LetterStreet>() {
//			@Override
//			public Predicate toPredicate(Root<LetterStreet> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//				Predicate predicate=cb.conjunction();
//				if(LetterStreet!=null){
//					if(StringUtil.isNotEmpty(LetterStreet.getName())){
//						predicate.getExpressions().add(cb.like(root.get("name"), "%"+LetterStreet.getName().trim()+"%"));
//					}
//				}
//				return predicate;
//			}
//		},
				pageable);
		return pageLetterStreet.getContent();
	}

	@Override
	public Long getCount(LetterStreet LetterStreet) {
		Long count= LetterStreetRepository.count(new Specification<LetterStreet>() {

			@Override
			public Predicate toPredicate(Root<LetterStreet> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
//				if(LetterStreet!=null){
//					if(StringUtil.isNotEmpty(LetterStreet.getName())){
//						predicate.getExpressions().add(cb.like(root.get("name"), "%"+LetterStreet.getName().trim()+"%"));
//					}
//				}
				return predicate;
			}
		});
		return count;
	}

	@Override
	public void delete(Integer id) {
		LetterStreetRepository.delete(id);
	}

	@Override
	public LetterStreet findById(Integer id) {
		return LetterStreetRepository.findOne(id);
	}

	@Override
	public List<LetterStreet> findAll() {
		return LetterStreetRepository.findAll();
	}


}
