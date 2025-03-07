package com.jude.service.impl;

import com.jude.entity.Case;
import com.jude.entity.CaseCop;
import com.jude.entity.CaseTmp;
import com.jude.repository.CaseCopRepository;
import com.jude.repository.CaseRepository;
import com.jude.repository.CaseTmpRepository;
import com.jude.service.CaseCopService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 供应商Service实现类
 *
 * @author jude
 */
@Service("CaseCopService")
public class CopCaseServiceImpl implements CaseCopService {

    @Resource
    private CaseCopRepository caseCopRepository;
    @Resource
    private CaseRepository caseRepository;

    @Resource
    private CaseTmpRepository caseTmpRepository;



    @Override
    public CaseCop save(CaseCop CaseCop) {
        CaseTmp caseTmp = caseTmpRepository.findOne(Integer.parseInt(CaseCop.getCaseId()));
        CaseCop.setTmpTitle(caseTmp.getTmpTitle());
        return caseCopRepository.save(CaseCop);
    }

    @Override
    public List<CaseCop> list(CaseCop caseCop, Integer page, Integer pageSize, Sort.Direction direction, String... properties) {
        List<CaseCop> caseCopList = caseCopRepository.findByCopId(caseCop.getCopId());
        return caseCopList;
    }

    @Override
    public Long getCount(CaseCop CaseCop) {
        return null;
    }

    @Override
    public void delete(Integer id) {
        caseCopRepository.delete(id);
    }

    ///@Override
    public CaseCop getBase(Integer id) {
        return caseCopRepository.findOne(id);
    }

    @Override
    public List<CaseCop> findByName(String name) {
        return Collections.emptyList();
    }

    @Override
    public CaseCop findById(Integer id) {
        return null;
    }

    //@Override
    public List<CaseCop> findAll() {
        return null;
    }


}
