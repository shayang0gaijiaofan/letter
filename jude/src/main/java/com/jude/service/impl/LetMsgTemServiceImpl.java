package com.jude.service.impl;

import com.jude.entity.LetMsgTem;
import com.jude.entity.dto.LetMsgTemWithTime;
import com.jude.repository.LetMsgTemRepository;
import com.jude.service.LetMsgTemService;
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
@Service("LetMsgTemService")
public class LetMsgTemServiceImpl implements LetMsgTemService {

	@Resource
	private LetMsgTemRepository letterMsgRepository;
	

	@Override
	public LetMsgTem save(LetMsgTem letterMsg) {
		return letterMsgRepository.save(letterMsg);
	}

	@Override
	public List<LetMsgTem> list(LetMsgTemWithTime letMsgTem, Integer page, Integer pageSize, Direction direction, String... properties) {
		Pageable pageable=new PageRequest(page-1, pageSize, direction,properties);
		Page<LetMsgTem> pageLetterMsg= letterMsgRepository.findAll(new Specification<LetMsgTem>() {
			
			@Override
			public Predicate toPredicate(Root<LetMsgTem> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				if(letMsgTem!=null){
					if(StringUtil.isNotEmpty(letMsgTem.getMsgTemNum())){	// 消息模板编号
						predicate.getExpressions().add(cb.like(root.get("msgTemNum"), "%"+letMsgTem.getMsgTemNum().trim()+"%"));
					}
					if(StringUtil.isNotEmpty(letMsgTem.getMsgTemName())){	// 消息模板名称
						predicate.getExpressions().add(cb.like(root.get("msgTemName"), "%"+letMsgTem.getMsgTemName().trim()+"%"));
					}
					if(StringUtil.isNotEmpty(letMsgTem.getApproveStatus())
						&& !letMsgTem.getApproveStatus().equals("all")){	// 消息模版审核状态
						predicate.getExpressions().add(cb.like(root.get("approveStatus"), "%"+letMsgTem.getApproveStatus().trim()+"%"));
					}
					if (letMsgTem.getCreateStartTime() != null) {
						predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("createTime"), letMsgTem.getCreateStartTime()));
					}
					if (letMsgTem.getCreateEndTime() != null) {
						// 设置时间为23时59分59秒
						Date date = letMsgTem.getCreateEndTime();
						date.setHours(23);
						date.setMinutes(59);
						date.setSeconds(59);
						predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("createTime"), date));
					}
				}
				return predicate;
			}
		}, pageable);
		return pageLetterMsg.getContent();
	}

	@Override
	public Long getCount(LetMsgTem letterMsg) {
		//Long count= letterMsgRepository.count(new Specification<LetMsgTem>() {
/*
			@Override
			public Predicate toPredicate(Root<LetMsgTem> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				if(letterMsg!=null){
					if(StringUtil.isNotEmpty(letterMsg.getName())){
						predicate.getExpressions().add(cb.like(root.get("name"), "%"+letterMsg.getName().trim()+"%"));
					}	
				}
				return predicate;
			}
		});*/
		return 0L;
	}

	@Override
	public void delete(Integer id) {
		letterMsgRepository.delete(id);
	}

	@Override
	public LetMsgTem findById(Integer id) {
		return letterMsgRepository.findOne(id);
	}

	@Override
	public List<LetMsgTem> findAll() {
		return letterMsgRepository.findAll();
	}


}
