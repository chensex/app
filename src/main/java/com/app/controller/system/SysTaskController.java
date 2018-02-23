package com.app.controller.system;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.app.model.system.SysTask;
import com.app.service.system.SysTaskService;
import com.app.util.ScheduleJobUtil;
import com.base.basecontroller.BaseController;
import com.base.entity.TaskInfo;
import com.base.entity.TaskInfoMethod;
import com.base.enums.RunStateEnum;
import com.base.enums.TaskStateEnum;
import com.base.util.CommonAjax;
import com.base.util.CommonUtil;
import com.base.util.JackSonSerializeUtil;
import com.github.pagehelper.PageInfo;
/**
 * 类说明：任务管理
 * @author CHENWEI
 * 2016年9月6日
 */
@Controller
@RequestMapping(value="app/system")
public class SysTaskController extends BaseController{
	@Autowired
	private SysTaskService sysTaskService;
	
	@RequestMapping(value="/sysTaskList",method = RequestMethod.GET)
	public ModelAndView sysTaskList(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = new ModelAndView("system/sysTaskList");
		return mv;
	}
	
	@RequestMapping(value="/sysTaskList",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> sysTaskList(HttpServletRequest req){
		int page = 1;
		int pageSize = 10;
		if(req.getParameter("page") != null){
			page = Integer.valueOf(req.getParameter("page"));
		}
		if(req.getParameter("rows") != null){
			pageSize = Integer.valueOf(req.getParameter("rows"));
		}
		debug("Controller分页日志:page=" + page + "pageSize=" + pageSize);
		return pageToJson(new PageInfo<SysTask>(sysTaskService.selectSysTaskList(
			getRequestParameterAsMap(req), page, pageSize)));
	}
	
	@RequestMapping(value="/saveTask",method = RequestMethod.POST)
	@ResponseBody
	public String saveTask(SysTask sysTask){
		CommonAjax<SysTask> ajax = new CommonAjax<SysTask>();
		
		SysTask task = sysTaskService.selectTaskByMethod(sysTask.getTaskMethod());
		if(task!=null){
			ajax.setState(CommonUtil.NOTPASSERROR);
			ajax.setContent("此任务已经添加，请勿重复添加!");
			return JackSonSerializeUtil.ObjectToJson(ajax);
		}
		sysTask.setCreateTime(CommonUtil.getNowDate());
		sysTaskService.saveAndEditTask(sysTask);
		
		if(TaskStateEnum.ENABLE.getValue().equals(sysTask.getTaskState())){
			try {
				ScheduleJobUtil.addScheduleJob(sysTaskService.getScheduler(),sysTask);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		ajax.setState(CommonUtil.SUCCESS);
		ajax.setContent("保存成功");
		return JackSonSerializeUtil.ObjectToJson(ajax);
	}
	
	@RequestMapping(value="/updateTask",method = RequestMethod.POST)
	@ResponseBody
	public String updateTask(SysTask sysTask){
		CommonAjax<SysTask> ajax = new CommonAjax<SysTask>();
		SysTask task = sysTaskService.selectTaskById(sysTask.getTaskId());
		
		if(RunStateEnum.RUN.getValue().equals(task.getRunState())){
			ajax.setState(CommonUtil.NOTPASSERROR);
			ajax.setContent("运行中的任务不能停止!");
			return JackSonSerializeUtil.ObjectToJson(ajax);
		}
		
		sysTask.setRunState(RunStateEnum.WAIT.getValue());
		sysTaskService.saveAndEditTask(sysTask);
		
		if(TaskStateEnum.ENABLE.getValue().equals(task.getTaskState())){
			try {
				ScheduleJobUtil.deleteScheduleJob(sysTaskService.getScheduler(),task);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(TaskStateEnum.ENABLE.getValue().equals(sysTask.getTaskState())){
			try {
				ScheduleJobUtil.addScheduleJob(sysTaskService.getScheduler(),sysTask);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		ajax.setState(CommonUtil.SUCCESS);
		ajax.setContent("修改成功");
		return JackSonSerializeUtil.ObjectToJson(ajax);
	}
	
	@RequestMapping(value = "/taskClass", method = RequestMethod.POST)
    @ResponseBody
    public List<TaskInfo> queryTaskClassList(){
    	List<TaskInfo> list = new ArrayList<TaskInfo>();
    	for(String key : CommonUtil.TASK_INFO_MAP.keySet()){
    		list.add(CommonUtil.TASK_INFO_MAP.get(key));
    	}
    	return list;
    }
	
	@RequestMapping(value = "/taskMethod", method = RequestMethod.POST)
    @ResponseBody
    public List<TaskInfoMethod> queryTaskMethodList(String className){
		return CommonUtil.TASK_INFO_MAP.get(className).getTaskInfoMethods();
    }
	
	/**
	 * 通过id查询定时任务信息
	 * @param taskId
	 * @return SysTask
	 */
	@RequestMapping(value = "/selectSysTaskById")
	@ResponseBody
	public SysTask selectSysTaskById(Long taskId){
		return sysTaskService.selectTaskById(taskId);
	}
}
