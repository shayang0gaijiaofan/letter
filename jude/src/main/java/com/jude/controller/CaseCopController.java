package com.jude.controller;

import com.jude.entity.*;
import com.jude.service.CaseCopService;
import com.jude.service.EmployeeService;
import com.jude.service.UserRoleService;
import com.jude.service.UserService;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台管理客户Controller
 *
 * @author jude
 */
@RestController
@RequestMapping("/caseCop")
public class CaseCopController {


    @Resource
    private UserRoleService userRoleService;

    @Resource
    private CaseCopService caseCopService;
    @Resource
    private EmployeeService employeeService;



    @Resource
    private UserService userService;

    @RequestMapping("/list")
    public Map<String, Object> list(CaseCop cop, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "rows", required = false) Integer rows) throws Exception {
        List<CaseCop> copList = caseCopService.list(cop, page, rows, Direction.ASC, "id");
        Long total = caseCopService.getCount(cop);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("rows", copList);
        resultMap.put("total", total);
        return resultMap;
    }



    /**
     * 添加或者修改客户信息
     *
     * @param cop
     * @return
     * @throws Exception
     */
    @Transactional
    @RequestMapping("/save")
    public Map<String, Object> save(CaseCop cop) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        CaseCop CaseCop=caseCopService.save(cop);
        User user = new User();
        user.setPassword("123456");
        resultMap.put("success", true);
        return resultMap;
    }


    /**
     * 删除客户信息
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/delete")
    public Map<String, Object> delete(String ids) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        String[] idsStr = ids.split(",");
        for (int i = 0; i < idsStr.length; i++) {
            int id = Integer.parseInt(idsStr[i]);
            caseCopService.delete(id);
        }
        resultMap.put("success", true);
        return resultMap;
    }

}
