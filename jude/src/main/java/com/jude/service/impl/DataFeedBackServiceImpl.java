package com.jude.service.impl;

import com.jude.entity.DataFeedBack;
import com.jude.repository.DataFeedBackRepository;
import com.jude.service.DataFeedBackService;
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
@Service("DataFeedBackService")
public class DataFeedBackServiceImpl implements DataFeedBackService {

	@Resource
	private DataFeedBackRepository DataFeedBackRepository;
	

	@Override
	public void save(DataFeedBack DataFeedBack) {
		DataFeedBackRepository.save(DataFeedBack);
	}

	@Override
	public List<DataFeedBack> list(DataFeedBack DataFeedBack, Integer page, Integer pageSize, Direction direction, String... properties) {
		Pageable pageable=new PageRequest(page-1, pageSize, direction,properties);
		Page<DataFeedBack> pageDataFeedBack= DataFeedBackRepository.findAll(
//			new Specification<DataFeedBack>() {
//			@Override
//			public Predicate toPredicate(Root<DataFeedBack> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//				Predicate predicate=cb.conjunction();
//				if(DataFeedBack!=null){
//					if(StringUtil.isNotEmpty(DataFeedBack.getName())){
//						predicate.getExpressions().add(cb.like(root.get("name"), "%"+DataFeedBack.getName().trim()+"%"));
//					}
//				}
//				return predicate;
//			}
//		},
				pageable);
		return pageDataFeedBack.getContent();
	}

	@Override
	public Long getCount(DataFeedBack DataFeedBack) {
		Long count= DataFeedBackRepository.count(new Specification<DataFeedBack>() {

			@Override
			public Predicate toPredicate(Root<DataFeedBack> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
//				if(DataFeedBack!=null){
//					if(StringUtil.isNotEmpty(DataFeedBack.getName())){
//						predicate.getExpressions().add(cb.like(root.get("name"), "%"+DataFeedBack.getName().trim()+"%"));
//					}
//				}
				return predicate;
			}
		});
		return count;
	}

	@Override
	public void delete(Integer id) {
		DataFeedBackRepository.delete(id);
	}

	@Override
	public DataFeedBack findById(Integer id) {
		return DataFeedBackRepository.findOne(id);
	}

	@Override
	public List<DataFeedBack> findAll() {
		return DataFeedBackRepository.findAll();
	}


}
