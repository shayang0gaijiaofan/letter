package com.jude.service.impl;

import com.jude.entity.Letter;
import com.jude.repository.LetterRepository;
import com.jude.service.LetterService;
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
@Service("LetterService")
public class LetterServiceImpl implements LetterService {

	@Resource
	private LetterRepository letterRepository;
	

	@Override
	public void save(Letter Letter) {
		letterRepository.save(Letter);
	}

	@Override
	public List<Letter> list(Letter Letter, Integer page, Integer pageSize, Direction direction, String... properties) {
		Pageable pageable=new PageRequest(page-1, pageSize, direction,properties);
		Page<Letter> pageLetter= letterRepository.findAll(
			new Specification<Letter>() {
			@Override
			public Predicate toPredicate(Root<Letter> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				if(Letter!=null){
					if(StringUtil.isNotEmpty(Letter.getBatchNum())){
						predicate.getExpressions().add(cb.like(root.get("batchNum"), "%"+Letter.getBatchNum().trim()+"%"));
					}
					if (StringUtil.isNotEmpty(Letter.getTemNum())){
						predicate.getExpressions().add(cb.like(root.get("temNum"), "%"+Letter.getTemNum().trim()+"%"));
					}
				}
				return predicate;
			}
		},
				pageable);
		return pageLetter.getContent();
	}

	@Override
	public Long getCount(Letter Letter) {
		Long count= letterRepository.count(new Specification<Letter>() {

			@Override
			public Predicate toPredicate(Root<Letter> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
//				if(Letter!=null){
//					if(StringUtil.isNotEmpty(Letter.getName())){
//						predicate.getExpressions().add(cb.like(root.get("name"), "%"+Letter.getName().trim()+"%"));
//					}
//				}
				return predicate;
			}
		});
		return count;
	}

	@Override
	public void delete(Integer id) {
		letterRepository.delete(id);
	}

	@Override
	public Letter findById(Integer id) {
		return letterRepository.findOne(id);
	}

	@Override
	public List<Letter> findAll() {
		return letterRepository.findAll();
	}


}
