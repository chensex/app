package com.app.util;
import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.app.model.system.SysTask;
import com.app.service.system.SysTaskService;
import com.base.enums.RunStateEnum;
import com.base.util.CommonUtil;
import com.base.util.SpringBeanContext;
import com.base.util.SpringInvokeUtil;
@DisallowConcurrentExecution
//@DisallowConcurrentExecution 意思是必须等上一个任务执行完成后才执行下一任务
public class TaskJobFactory implements Job{
	private static final Logger logger = Logger.getLogger(TaskJobFactory.class);
	public void execute(JobExecutionContext context) throws JobExecutionException {
		SysTask task = (SysTask) context.getMergedJobDataMap().get("JOB_PARAM_KEY");
		SysTaskService sysTaskService = SpringBeanContext.getBean(SysTaskService.class);
		logger.info("执行定时任务："+task.getTaskName()+",|服务类："+task.getTaskService()+",|"
				+ "执行方法："+task.getTaskMethod()+",时间表达式："+task.getTimeExpression());
		try {
			task.setRunState(RunStateEnum.RUN.getValue());
			sysTaskService.saveAndEditTask(task);
			SpringInvokeUtil.invokeByService(SpringBeanContext.getBean(CommonUtil.lowerCaseFirstLetter(task.getTaskService())), task.getTaskMethod());
			task.setRunState(RunStateEnum.WAIT.getValue());
			sysTaskService.saveAndEditTask(task);
		} catch (Exception e) {
			logger.info("执行定时任务失败："+task.getTaskName()+",|服务类："+task.getTaskService()+",|"
					+ "执行方法："+task.getTaskMethod()+",时间表达式："+task.getTimeExpression()+"");
			e.printStackTrace();
		}
	}
}
