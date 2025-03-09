package com.jude.service.impl;

import com.jude.entity.Case;
import com.jude.entity.CaseInfo;
import com.jude.entity.Employee;
import com.jude.repository.CaseInfoRepository;
import com.jude.repository.CaseRepository;
import com.jude.repository.EmployeeRepository;
import com.jude.service.EmployeeService;
import com.jude.service.EmployeeService;
import com.jude.util.StringUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

/**
 * 客户Service实现类
 * @author jude
 *
 */
@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {

	@Resource
	private EmployeeRepository employeeRepository;
	@Resource
	private CaseRepository caseRepository;
	@Resource
	private CaseInfoRepository caseInfoRepository;





	@Override
	public void save(Employee Employee) {
		employeeRepository.save(Employee);
	}

	@Override
	public List<Employee> list(Employee employee, Integer page, Integer pageSize, Direction direction, String... properties) {
		Pageable pageable=new PageRequest(page-1, pageSize, direction,properties);

		Page<Employee> pageEmployee=employeeRepository.findAll(new Specification<Employee>() {
			@Override
			public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				if(employee!=null){
					if(StringUtil.isNotEmpty(employee.getCopId())){
						if (StringUtil.isNotEmpty(employee.getName())){
							predicate.getExpressions().add(cb.like(root.get("name"), "%"+employee.getName().trim()+"%"));
						}
						predicate.getExpressions().add(cb.equal(root.get("copId"), employee.getCopId()));
					}	
				}
				return predicate;
			}
		}, pageable);
		return pageEmployee.getContent();
	}

	@Override
	public Long getCount(Employee Employee) {
		Long count=employeeRepository.count(new Specification<Employee>() {
			@Override
			public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				if(Employee!=null){
					if(StringUtil.isNotEmpty(Employee.getName())){
						predicate.getExpressions().add(cb.like(root.get("name"), "%"+Employee.getName().trim()+"%"));
					}	
				}
				return predicate;
			}
		});
		return count;
	}

	@Override
	public void delete(Integer id) {
		employeeRepository.delete(id);
	}

	@Override
	public void saveOneCase(String caseId,Employee currentEmployee,String data) throws Exception {
		Case case1=caseRepository.findOne(Integer.parseInt(caseId));
		if(!currentEmployee.getId().toString().equals(case1.getEmployeeId())) {
			throw  new Exception("后台检测操作异常，撤销操作");
		}
			caseInfoRepository.deleteByCaseId(Integer.parseInt(caseId));
		//String decodedString = new String(a.toCharArray(), StandardCharsets.UTF_8);
		String[] s= data.split("&c");

		CaseInfo caseInfo;
		for (int i=1;i<s.length;i++){
			caseInfo=new CaseInfo();
			caseInfo.setCaseId(caseId);
			caseInfo.setTmpId(case1.getTmpId());
			caseInfo.setValue(s[i].substring(s[i].indexOf('=')+1,s[i].length()));
			caseInfoRepository.save(caseInfo);
		}
	}

	@Override
	public void createOneCase(String tmpId,Employee currentEmployee,String data) {
		Case case1=new Case();
		case1.setEmployeeId(currentEmployee.getId().toString());
		case1.setSubmitDate(getNowString());
		case1.setTmpId(tmpId);
		caseRepository.save(case1);
		String[] s= data.split("&c");

		CaseInfo caseInfo;
		for (int i=1;i<s.length;i++){
			caseInfo=new CaseInfo();
			caseInfo.setCaseId(case1.getId().toString());
			caseInfo.setTmpId(case1.getTmpId());
			caseInfo.setValue(s[i].substring(s[i].indexOf('=')+1,s[i].length()));
			caseInfoRepository.save(caseInfo);
		}


	}
	private String getNowString(){
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return currentDate.format(formatter);
	}

	@Override
	public Employee findById(Integer id) {
		return employeeRepository.findOne(id);
	}

	@Override
	public List<Employee> findByName(String name) {
		return employeeRepository.findByName(name);
	}

	@Override
	public Employee  findByUserId(Integer userId) {
		List<Employee> employees = employeeRepository.findByUserId(userId);
		if (!CollectionUtils.isEmpty(employees))
		return employees.get(0);
		else
			return null;
	}


}
