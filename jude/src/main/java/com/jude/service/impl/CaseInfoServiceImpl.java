package com.jude.service.impl;

import com.jude.entity.Case;
import com.jude.entity.CaseInfo;
import com.jude.entity.Employee;
import com.jude.entity.LetterSend;
import com.jude.repository.CaseInfoRepository;
import com.jude.repository.CaseRepository;
import com.jude.repository.EmployeeRepository;
import com.jude.service.CaseInfoService;
import com.jude.util.StringUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * 供应商Service实现类
 *
 * @author jude
 */
@Service("CaseInfoService")
public class CaseInfoServiceImpl implements CaseInfoService {

    @Resource
    private CaseInfoRepository caseInfoRepository;
    @Resource
    private CaseRepository caseRepository;
    @Resource
    private EmployeeRepository employeeRepository;




    @Override
    public void save(CaseInfo caseInfo) {
        caseInfoRepository.save(caseInfo);
    }

    @Override
    public List<Map> list(String tmpId,String date) {
        List<Map> result=new ArrayList<>();
        List<Case> caseList = caseRepository.listByTemIdDate(tmpId,date);
        Map<String,String> caseData=null;
        List<CaseInfo> caseInfoList=null;
        Employee employee;
        for (Case caseEntity : caseList) {
            caseInfoList = caseInfoRepository.listByCaseId(caseEntity.getId().toString());
            caseData=new HashMap();
            caseData.put("c1",caseEntity.getId().toString());
            employee= employeeRepository.findOne(Integer.parseInt(caseEntity.getEmployeeId()));
            caseData.put("c2",employee.getName().toString());
            for(int i=3;i<caseInfoList.size()+3;i++){
                caseData.put("c"+i,caseInfoList.get(i-3).getValue());
            }
            result.add(caseData);
        }
        return result;
    }

    @Override
    public List<Map> listEmployee(String tmpId,String employeeId) {
        List<Map> result=new ArrayList<>();
        List<Case> caseList = caseRepository.listByTemIdEmployeeId(tmpId,employeeId);
        Map<String,String> caseData=null;
        List<CaseInfo> caseInfoList=null;
        for (Case caseEntity : caseList) {
            caseInfoList = caseInfoRepository.listByCaseId(caseEntity.getId().toString());
            caseData=new HashMap();
            caseData.put("c1",caseEntity.getId().toString());
            for(int i=2;i<caseInfoList.size()+2;i++){
                caseData.put("c"+i,caseInfoList.get(i-2).getValue());
            }
            result.add(caseData);
        }
        return result;
    }

    @Override
    public Long getCount(CaseInfo CaseInfo) {
        return null;
    }

    @Override
    public void delete(Integer id) {
        caseRepository.delete(id);
        caseInfoRepository.deleteByCaseId(id);
    }

    @Override
    public CaseInfo getBase(Integer id) {
        return caseInfoRepository.findOne(id);
    }

    @Override
    public List<Map> listCop(String tmpId, String string) {
        return Collections.emptyList();
    }

    @Override
    public CaseInfo findById(Integer id) {
        return caseInfoRepository.findOne(id);
    }

    @Override
    public List<CaseInfo> findAll() {
        return null;
    }


}
