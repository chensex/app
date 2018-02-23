package com.app.controller.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.FormService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.form.FormData;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.app.model.system.SysUser;
import com.base.basecontroller.BaseController;
import com.base.util.CommonAjax;
import com.base.util.CommonConstant;
import com.base.util.CommonUtil;
import com.base.util.JackSonSerializeUtil;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping(value="/app/system")
public class ProcessController extends BaseController{
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private FormService formService;
	
	@RequestMapping(value="/sysProcessList",method=RequestMethod.GET)
	public ModelAndView sysProcessList(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = new ModelAndView("system/sysProcessList");
		return mv;
	}
	
	@RequestMapping(value="/sysProcessList",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySysProcessList(HttpServletRequest req){
		int page = 1;
		int pageSize = 10;
		if(req.getParameter("page") != null){
			page = Integer.valueOf(req.getParameter("page"));
		}
		if(req.getParameter("rows") != null){
			pageSize = Integer.valueOf(req.getParameter("rows"));
		}
		debug("Controller分页日志:page=" + page + "pageSize=" + pageSize);
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		for (ProcessDefinition ProcessDefinition : repositoryService.createProcessDefinitionQuery().list()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", ProcessDefinition.getId());
			map.put("key", ProcessDefinition.getKey());
			map.put("name", ProcessDefinition.getName());
			map.put("version", ProcessDefinition.getVersion());
			map.put("deploymentId", ProcessDefinition.getDeploymentId());
			map.put("resourceName", ProcessDefinition.getResourceName());
			map.put("diagramResourceName", ProcessDefinition.getDiagramResourceName());
			list.add(map);
		}
		return pageToJson(new PageInfo<Map<String, Object>>(list));
	}
	
	/**
	 * 打开表单
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getStartProcess",method=RequestMethod.GET)
	public ModelAndView getStartProcess(HttpServletRequest request, HttpServletResponse response){
		ModelAndView mv = new ModelAndView("system/startForm");
		StartFormData startFormData = formService.getStartFormData(request.getParameter("processDefinitionId"));
		Object object = formService.getRenderedStartForm(request.getParameter("processDefinitionId"));
		System.out.println(object);
		mv.addObject("startFormData",startFormData);
		mv.addObject("formProperty", startFormData.getFormProperties());
		return mv;
	}
	
	 /**
     * 启动流程实例
     */
    @RequestMapping(value="/startProcess",method=RequestMethod.POST)
    @ResponseBody
    public String startProcess(HttpServletRequest request, HttpServletResponse response) {
    	CommonAjax<Model> ajax = new CommonAjax<Model>();
    	try {
    		SysUser sysUser = (SysUser) request.getSession().getAttribute(CommonConstant.SESSION_USER);
    		 //启动流程实例(使用流程定义的key的最新版本启动流程 )
    		Map<String, String> variables = getRequestParameterAsMapString(request);
    		//variables.put("userGroup", String.valueOf(sysUser.getUserId()));
    		//formService.
    		ProcessInstance processInstance = formService.submitStartFormData(request.getParameter("processDefinitionId"), variables);
    		
    		//ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(request.getParameter("processKey"),variables);
        	// formService.submitStartFormData(processDefinitionId, properties)
        	 if(processInstance!=null && processInstance.getId() != null){
        		 ajax.setState(CommonUtil.SUCCESS);
         		 ajax.setContent("启动成功");
        	 }else{
        		 ajax.setState(CommonUtil.NOTPASSERROR);
         		 ajax.setContent("启动失败");
        	 }
        } catch (Exception e) {
        	e.printStackTrace();
        	ajax.setState(CommonUtil.NOTPASSERROR);
    		ajax.setContent("启动失败");
        }
        return JackSonSerializeUtil.ObjectToJson(ajax);
    }
}
