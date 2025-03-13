package com.jude.service.impl;

import com.jude.entity.Letter;
import com.jude.entity.dto.LetterWithTime;
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
import java.util.Date;
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
		Letter let = letterRepository.getOne(Letter.getId());
		let.setBatchNum(Letter.getBatchNum());
		let.setTemNum(Letter.getTemNum());
		letterRepository.save(let);
	}

	@Override
	public List<Letter> list(LetterWithTime let, Integer page, Integer pageSize, Direction direction, String... properties) {
		Pageable pageable=new PageRequest(page-1, pageSize, direction,properties);
		Page<Letter> pageLetter= letterRepository.findAll(
			new Specification<Letter>() {
			@Override
			public Predicate toPredicate(Root<Letter> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				if(let!=null){
					if(StringUtil.isNotEmpty(let.getBatchNum())){
						predicate.getExpressions().add(cb.like(root.get("batchNum"), "%"+let.getBatchNum().trim()+"%"));
					}
					if (StringUtil.isNotEmpty(let.getTemNum())
						&& !let.getTemNum().equals("all")){
						predicate.getExpressions().add(cb.like(root.get("temNum"), "%"+let.getTemNum().trim()+"%"));
					}
					if (let.getCreateStartTime() != null) {
						predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("createTime"), let.getCreateStartTime()));
					}
					if (let.getCreateEndTime() != null) {
						// 设置时间为23时59分59秒
						Date date = let.getCreateEndTime();
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
