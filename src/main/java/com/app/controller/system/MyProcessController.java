package com.app.controller.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.app.model.system.SysRole;
import com.app.model.system.SysUser;
import com.app.service.system.SysRoleService;
import com.base.basecontroller.BaseController;
import com.base.util.CommonAjax;
import com.base.util.CommonConstant;
import com.base.util.CommonUtil;
import com.base.util.JackSonSerializeUtil;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping(value="/app/system")
public class MyProcessController extends BaseController{

	@Autowired
	private TaskService taskService;
	
	@Autowired
	private SysRoleService sysRoleService;
	
	@RequestMapping(value="/myProcessList",method=RequestMethod.GET)
	public ModelAndView myProcessList(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = new ModelAndView("system/myProcessList");
		return mv;
	}
	
	@RequestMapping(value="/myProcessList",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMyProcessList(HttpServletRequest req){
		
		SysUser sysUser = (SysUser) req.getSession().getAttribute(CommonConstant.SESSION_USER);
		
		//查找用户对应的角色
		List<SysRole> roles = sysRoleService.selectRoleByUserId(sysUser.getUserId());
		
		List<String> paramList = new ArrayList<String>();
		for (SysRole sysRole : roles) {
			paramList.add(String.valueOf(sysRole.getRoleId()));
		}
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
		
	//	TaskQuery taskQuery = taskService.createTaskQuery().taskCandidateGroupIn(paramList);
		
		for (Task task : taskService.createTaskQuery().taskCandidateGroupIn(paramList).list()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", task.getId());
			map.put("executionId", task.getExecutionId());
			map.put("processInstanceId", task.getProcessInstanceId());
			map.put("processDefinitionId", task.getProcessDefinitionId());
			map.put("taskDefinitionKey", task.getTaskDefinitionKey());
			map.put("name", task.getName());
			map.put("assignee", task.getAssignee());
			map.put("createTime", task.getCreateTime());
			list.add(map);
		}
		return pageToJson(new PageInfo<Map<String, Object>>(list));
	}
	
	/**
     * 完成任务
     */
    @RequestMapping(value="/completeTask",method=RequestMethod.POST)
    @ResponseBody
    public String completeTask(HttpServletRequest request, HttpServletResponse response) {
    	CommonAjax<Task> ajax = new CommonAjax<Task>();
    	
    	String taskId = request.getParameter("taskId");
    	
    	try {
    		
    		SysUser sysUser = (SysUser) request.getSession().getAttribute(CommonConstant.SESSION_USER);
    		
    		//签收任务
    		taskService.claim(taskId, String.valueOf(sysUser.getUserId()));
    		
    		//完成任务
    		taskService.complete(taskId);
    		ajax.setState(CommonUtil.SUCCESS);
     		ajax.setContent("处理成功");
        	 
        } catch (Exception e) {
        	e.printStackTrace();
        	ajax.setState(CommonUtil.NOTPASSERROR);
    		ajax.setContent("处理失败");
        }
        return JackSonSerializeUtil.ObjectToJson(ajax);
    }
}
