package com.app.servlet;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.apache.log4j.Logger;
import com.app.model.system.SysTask;
import com.app.service.system.SysTaskService;
import com.app.util.ScheduleJobUtil;
import com.base.annotation.CronTask;
import com.base.annotation.CronTaskMethod;
import com.base.entity.TaskInfo;
import com.base.entity.TaskInfoMethod;
import com.base.util.CommonUtil;
import com.base.util.SpringBeanContext;

public class InitTaskInfoServlet extends HttpServlet{
	//@Autowired
	//private SysTaskService sysTaskService;
	private Logger logger = Logger.getLogger(getClass());
	private static final long serialVersionUID = -995466593580321975L;
	
	@Override
	public void init() throws ServletException {
		Map<String, Object> map = SpringBeanContext.getBeansWithAnnotation(CronTask.class);
		
		Object object = null;
		CronTask cronTask = null;
		CronTaskMethod cronTaskMethod = null;
		
		for(String key : map.keySet()){
			object = map.get(key);
			cronTask = object.getClass().getAnnotation(CronTask.class);
			TaskInfo taskInfo = new TaskInfo();
			taskInfo.setClassName(object.getClass().getSimpleName());
			taskInfo.setServiceName(key);
			taskInfo.setValue(cronTask.value());
			
			List<TaskInfoMethod> taskInfoMethods = new ArrayList<TaskInfoMethod>();
			Method methods[] = object.getClass().getMethods();
			for(Method m : methods){
				cronTaskMethod = m.getAnnotation(CronTaskMethod.class);
				if(cronTaskMethod!=null){
					TaskInfoMethod taskInfoMethod = new TaskInfoMethod();
					taskInfoMethod.setMethodName(m.getName());
					taskInfoMethod.setValue(cronTaskMethod.value());
					taskInfoMethods.add(taskInfoMethod);
				}
				
			}
			taskInfo.setTaskInfoMethods(taskInfoMethods);
			CommonUtil.TASK_INFO_MAP.put(object.getClass().getSimpleName(), taskInfo);
		}
		
		//启动已启动的定时任务
		SysTaskService sysTaskService = SpringBeanContext.getBean(SysTaskService.class);
		List<SysTask> list = sysTaskService.selectTaskListByState("ENABLE");
		for (SysTask task : list) {
			logger.info("启动执行定时任务："+task.getTaskName()+",|服务类："+task.getTaskService()+",|"
					+ "执行方法："+task.getTaskMethod()+",时间表达式："+task.getTimeExpression());
			ScheduleJobUtil.addScheduleJob(sysTaskService.getScheduler(), task);
		}
	}
}
