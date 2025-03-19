package com.jude.controller;

import com.jude.common.ResponseEntity;
import com.jude.entity.LetterTemplate;
import com.jude.entity.Log;
import com.jude.entity.dto.LetterTempWithFile;
import com.jude.entity.dto.LetterTemplateWithTime;
import com.jude.service.LetterTemplateService;
import com.jude.service.LogService;
import com.jude.service.PicService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
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
	private PicService picService;

	@Resource
	private LogService logService;

	/**
	 * 查询所有函件模板
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findAll")
	public List<LetterTemplate> findAll()throws Exception{
		List<LetterTemplate> result = new ArrayList<>();
		result = LetterTemplateService.findAll();
		logService.save(new Log(Log.SEARCH_ACTION,"查询所有函件模板")); // 写入日志
		return result;
	}

	/**
	 * 分页查询函件模板信息
	 * @param LetterTemplate
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	//@RequiresPermissions(value = { "函件模板管理" })
	public Map<String,Object> list(LetterTemplateWithTime LetterTemplate, @RequestParam(value="page",required=false)Integer page, @RequestParam(value="rows",required=false)Integer rows)throws Exception{
		List<LetterTemplate> LetterTemplateList= LetterTemplateService.list(LetterTemplate, page, rows, Sort.Direction.ASC, "id");
		Long total= LetterTemplateService.getCount(LetterTemplate);
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("rows", LetterTemplateList);
		resultMap.put("total", total);
		//logService.save(new Log(Log.SEARCH_ACTION,"查询函件模板信息")); // 写入日志
		return resultMap;
	}


	/**
	 * 修改函件模板信息
	 * @param LetterTemplate
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
//	@RequiresPermissions(value = { "添加或者修改函件模板信息" })
	public Map<String,Object> save(LetterTemplate LetterTemplate) throws Exception{
		if(LetterTemplate.getId()!=null){ // 写入日志
			logService.save(new Log(Log.UPDATE_ACTION,"更新函件模板信息"+LetterTemplate));
		}else{
			logService.save(new Log(Log.ADD_ACTION,"添加函件模板信息"+LetterTemplate));
		}
		Map<String, Object> resultMap = new HashMap<>();
		LetterTemplateService.save(LetterTemplate);
		resultMap.put("success", true);
		return resultMap;
	}

	/**
	 * 添加函件模板信息
	 * @param tempF
	 * @return
	 * @throws Exception
	 */
	@Transactional
	@RequestMapping("/insert")
//	@RequiresPermissions(value = { "添加或者修改函件模板信息" })
	public Map<String,Object> insert(LetterTemplate temp)throws Exception{
		Map<String, Object> resultMap = new HashMap<>();
		LetterTemplateService.insert(temp);

		resultMap.put("success", true);
		return resultMap;
	}

	@Transactional
	@RequestMapping("/updateFile")
	public ResponseEntity updateFile(Integer id, String fileName)throws Exception{
		LetterTemplate temp = LetterTemplateService.findById(id);
		temp.setLetTempPic(fileName);
		LetterTemplateService.save(temp);

		return ResponseEntity.ok("修改成功！");
	}


	/**
	 * 删除函件模板信息
	 * @param id
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	//@RequiresPermissions(value = { "函件模板管理" })
	public Map<String,Object> delete(String ids)throws Exception{
		Map<String, Object> resultMap = new HashMap<>();
		String []idsStr=ids.split(",");
		for(int i=0;i<idsStr.length;i++){
			int id=Integer.parseInt(idsStr[i]);
			//logService.save(new Log(Log.DELETE_ACTION,"删除函件模板信息"+ LetterTemplateService.findById(id)));  // 写入日志
			LetterTemplateService.delete(id);
		}
		resultMap.put("success", true);
		return resultMap;
	}
}
