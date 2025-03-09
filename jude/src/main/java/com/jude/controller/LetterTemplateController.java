package com.jude.controller;

import com.jude.entity.LetterTemplate;
import com.jude.entity.Log;
import com.jude.service.LetterTemplateService;
import com.jude.service.LogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 函件发送
 * @author jude
 *
 */
@RestController
@RequestMapping("/LetterTemplate")
public class LetterTemplateController {

	@Resource
	private LetterTemplateService LetterTemplateService;

	@Resource
	private LogService logService;

	/**
	 * 分页查询供应商信息
	 * @param LetterTemplate
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	//@RequiresPermissions(value = { "供应商管理" })
	public Map<String,Object> list(LetterTemplate LetterTemplate, @RequestParam(value="page",required=false)Integer page, @RequestParam(value="rows",required=false)Integer rows)throws Exception{
		List<LetterTemplate> LetterTemplateList= LetterTemplateService.list(LetterTemplate, page, rows, Sort.Direction.ASC, "id");
		Long total= LetterTemplateService.getCount(LetterTemplate);
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("rows", LetterTemplateList);
		resultMap.put("total", total);
		//logService.save(new Log(Log.SEARCH_ACTION,"查询供应商信息")); // 写入日志
		return resultMap;
	}


	/**
	 * 添加或者修改供应商信息
	 * @param LetterTemplate
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
//	@RequiresPermissions(value = { "添加或者修改供应商信息" })
	public Map<String,Object> save(LetterTemplate LetterTemplate)throws Exception{
		if(LetterTemplate.getId()!=null){ // 写入日志
			logService.save(new Log(Log.UPDATE_ACTION,"更新供应商信息"+LetterTemplate));
		}else{
			logService.save(new Log(Log.ADD_ACTION,"添加供应商信息"+LetterTemplate));
		}
		Map<String, Object> resultMap = new HashMap<>();
		LetterTemplateService.save(LetterTemplate);
		resultMap.put("success", true);
		return resultMap;
	}


	/**
	 * 删除供应商信息
	 * @param id
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	//@RequiresPermissions(value = { "供应商管理" })
	public Map<String,Object> delete(String ids)throws Exception{
		Map<String, Object> resultMap = new HashMap<>();
		String []idsStr=ids.split(",");
		for(int i=0;i<idsStr.length;i++){
			int id=Integer.parseInt(idsStr[i]);
			//logService.save(new Log(Log.DELETE_ACTION,"删除供应商信息"+ LetterTemplateService.findById(id)));  // 写入日志
			LetterTemplateService.delete(id);
		}
		resultMap.put("success", true);
		return resultMap;
	}
}
