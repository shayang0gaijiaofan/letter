package com.jude.controller;

import com.jude.entity.*;
import com.jude.repository.CaseInfoRepository;
import com.jude.repository.CaseRepository;
import com.jude.repository.CaseTmpDetailRepository;
import com.jude.repository.CaseTmpRepository;
import com.jude.service.CaseInfoService;
import com.jude.service.EmployeeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 函件发送
 * @author jude
 *
 */
@RestController
@RequestMapping("/caseInfo")
public class CaseInfoController {

	@Resource
	private CaseInfoService caseInfoService;
	@Resource
	private EmployeeService employeeService;
	@Resource
	private CaseInfoRepository caseInfoRepository;
	@Resource
	private CaseRepository caseRepository;
	@Resource
	private CaseTmpDetailRepository caseTmpDetailRepository;



	/**
	 * 分页查询供应商信息
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	@Transactional
	//@RequiresPermissions(value = { "供应商管理" })
	public Map<String,Object> list(String tmpId,String date, @RequestParam(value="page",required=false)Integer page, @RequestParam(value="rows",required=false)Integer rows)throws Exception{
		List<Map> CaseInfoList= caseInfoService.list(tmpId,date);
		//Long total= caseInfoService.getCount(caseInfo);
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("rows", CaseInfoList);
		resultMap.put("total", CaseInfoList.size());
		//logService.save(new Log(Log.SEARCH_ACTION,"查询供应商信息")); // 写入日志
		return resultMap;
	}

	/**
	 * 分页查询供应商信息
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listEmployee")
	@Transactional
	//@RequiresPermissions(value = { "供应商管理" })
	public Map<String,Object> listEmployee(HttpSession session,String tmpId, @RequestParam(value="page",required=false)Integer page, @RequestParam(value="rows",required=false)Integer rows)throws Exception{
		User currentUser=(User) session.getAttribute("currentUser");
		Employee currentEmployee = employeeService.findByUserId(currentUser.getId());

		List<Map> CaseInfoList= caseInfoService.listEmployee(tmpId,currentEmployee.getId().toString());
		//Long total= caseInfoService.getCount(caseInfo);
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("rows", CaseInfoList);
		resultMap.put("total", CaseInfoList.size());
		//logService.save(new Log(Log.SEARCH_ACTION,"查询供应商信息")); // 写入日志
		return resultMap;
	}

	@RequestMapping("/listCop")
	@Transactional
	//@RequiresPermissions(value = { "供应商管理" })
	public Map<String,Object> listCop(HttpSession session,String tmpId,String date, @RequestParam(value="page",required=false)Integer page, @RequestParam(value="rows",required=false)Integer rows)throws Exception{
		User currentUser=(User) session.getAttribute("currentUser");
		Employee currentEmployee = employeeService.findByUserId(currentUser.getId());

		List<Map> CaseInfoList= caseInfoService.listCop(tmpId,currentEmployee.getId().toString());
		//Long total= caseInfoService.getCount(caseInfo);
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("rows", CaseInfoList);
		resultMap.put("total", CaseInfoList.size());
		//logService.save(new Log(Log.SEARCH_ACTION,"查询供应商信息")); // 写入日志
		return resultMap;
	}




	@RequestMapping("/listAll")
	//@RequiresPermissions(value = { "供应商管理" })
	public List<CaseInfo> listAll()throws Exception{
		List<CaseInfo> caseInfoList= caseInfoRepository.findAll();
		return caseInfoList;
	}


	@RequestMapping("/getBase")
	public Map<String,Object> getBase(Integer id)throws Exception{
		CaseInfo pic= caseInfoService.getBase(id);
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("rows", pic);
		resultMap.put("total", 1);
		return resultMap;
	}




	/**
	 * 添加或者修改供应商信息
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/save")
	@Transactional
	//@RequiresPermissions(value = { "供应商管理" })
	public Map<String,Object> save(HttpSession session,@RequestParam  List<String> data,String tmpId,Integer tmpSize)throws Exception{
		User currentUser=(User) session.getAttribute("currentUser");
		Employee CurrentEmployee = employeeService.findByUserId(currentUser.getId());
		Case caseEntity;
		CaseInfo caseInfo;
		//26 13

		List<CaseTmpDetail> caseTmpDetailList = caseTmpDetailRepository.findByTmpId(tmpId);
		tmpSize=caseTmpDetailList.size();
		int caseNum=data.size()/tmpSize;
		String value;
		for(int i=0;i<caseNum;i++){
			caseEntity=new Case();
			caseEntity.setEmployeeId(CurrentEmployee.getId().toString());
			caseEntity.setTmpId(tmpId);
			caseEntity.setSubmitDate(getNowString());
			caseEntity=  caseRepository.save(caseEntity);
			for (int j=0;j<tmpSize;j++){
				caseInfo=new CaseInfo();
				caseInfo.setCaseId(caseEntity.getId().toString());
				caseInfo.setTmpId(tmpId);
				if (j!=tmpSize-1){
					caseInfo.setValue(getContextStr(data.get(i*tmpSize+j)));
				}else {
					caseInfo.setValue(getContextLast(data.get(i*tmpSize+j)));
				}

				caseInfoRepository.save(caseInfo);
			}
		}
		//data.get(0)
		Map<String, Object> resultMap = new HashMap<>();
		//caseInfoService.save(null);
		resultMap.put("success", true);
		return resultMap;
	}

	@PostMapping("/update")
	@Transactional
	//@RequiresPermissions(value = { "供应商管理" })
	public Map<String,Object> update(HttpSession session,@RequestParam  String data,String caseId,String type,String tmpId)throws Exception{
		User currentUser=(User) session.getAttribute("currentUser");
		Employee currentEmployee = employeeService.findByUserId(currentUser.getId());
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("success", true);
		try {
			if ("0".equals(type)) {
				//String caseId,Employee currentEmployee,String data
				employeeService.saveOneCase(caseId, currentEmployee, data);
			} else if ("1".equals(type)) {
				//String tmpId,Employee currentEmployee,String data
				employeeService.createOneCase(tmpId, currentEmployee, data);

			}
		}catch (Exception e){
			resultMap.put("fail",e.getMessage());
			return resultMap;
		}




		return resultMap;
	}

	@PostMapping("/updateAdmin")
	@Transactional
	//@RequiresPermissions(value = { "供应商管理" })
	public Map<String,Object> updateAdmin(HttpSession session,@RequestParam  String data,String caseId)throws Exception{
		User currentUser=(User) session.getAttribute("currentUser");
		Employee currentEmployee = employeeService.findByUserId(currentUser.getId());
		Case case1=caseRepository.findOne(Integer.parseInt(caseId));

		caseInfoRepository.deleteByCaseId(Integer.parseInt(caseId));
		//String decodedString = new String(a.toCharArray(), StandardCharsets.UTF_8);
		String[] s= data.split("&c");

		CaseInfo caseInfo;
		for (int i=2;i<s.length;i++){
			caseInfo=new CaseInfo();
			caseInfo.setCaseId(caseId);
			caseInfo.setTmpId(case1.getTmpId());
			caseInfo.setValue(s[i].substring(s[i].indexOf('=')+1,s[i].length()));
			caseInfoRepository.save(caseInfo);
		}


		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("success", true);
		return resultMap;
	}

	@RequestMapping("/delete")
	@Transactional
	//@RequiresPermissions(value = { "供应商管理" })
	public Map<String,Object> delete(HttpSession session, String ids)throws Exception{
		User currentUser=(User) session.getAttribute("currentUser");
		Employee currentEmployee = employeeService.findByUserId(currentUser.getId());
		Map<String, Object> resultMap = new HashMap<>();
		String []idsStr=ids.split(",");
		for(int i=0;i<idsStr.length;i++){
			int id=Integer.parseInt(idsStr[i]);
			Case case1=caseRepository.findOne(id);
			if(currentEmployee.getId().toString().equals(case1.getEmployeeId())){
				caseInfoService.delete(id);
			}else {
				throw  new Exception("后台检测操作异常，撤销操作");
			}


		}
		resultMap.put("success", true);
		return resultMap;
	}

	@RequestMapping("/deleteAdmin")
	@Transactional
	//@RequiresPermissions(value = { "供应商管理" })
	public Map<String,Object> deleteAdmin(HttpSession session, String ids)throws Exception{
		User currentUser=(User) session.getAttribute("currentUser");
		Employee currentEmployee = employeeService.findByUserId(currentUser.getId());
		Map<String, Object> resultMap = new HashMap<>();
		String []idsStr=ids.split(",");
		for(int i=0;i<idsStr.length;i++){
			int id=Integer.parseInt(idsStr[i]);
			Case case1=caseRepository.findOne(id);
			caseInfoService.delete(id);


		}
		resultMap.put("success", true);
		return resultMap;
	}





	private  String getContextStr(String input){
		return   input.substring(input.indexOf(':')+1,input.length()).replace("\"", "");
	}

	private  String getStrByUrl(String input){
		return   input.substring(input.indexOf(':')+1,input.length()).replace("\"", "");
	}
	private  String getContextLast(String input){
		return   input.substring(input.indexOf(':')+1,input.length()-2).replace("\"", "");
	}

	private String getNowString(){
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return currentDate.format(formatter);
	}


	public static void main(String[] args) {
		String a="c1=13&c2=1&c3=2&c4=张三&c5=张三&c6=张三&c7=6&c8=7&c9=8&c10=9&c11=7&c12=1&c13=0&c14=11}]";
		//String decodedString = new String(a.toCharArray(), StandardCharsets.UTF_8);
		String[] s= a.split("&c");
		for (String  str :s){
				System.out.println(str.substring(str.indexOf('=')+1,str.length()));
		}
	}






}
