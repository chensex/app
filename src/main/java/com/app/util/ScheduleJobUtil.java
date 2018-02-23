package com.app.util;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;

import com.app.model.system.SysTask;

public class ScheduleJobUtil {	
	public static void addScheduleJob(Scheduler scheduler,SysTask task){
		
		Class<? extends Job> clazz = TaskJobFactory.class;
		
		
		//构建一个作业实例
		JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(task.getTaskName(),task.getGroupName()).build();
		
		//构建一个表达式
		//表达式调度构建器
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(task.getTimeExpression());
		
        //放入参数，运行时的方法可以获取
        jobDetail.getJobDataMap().put("JOB_PARAM_KEY", task);
        
		CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(task.getTaskName(), task.getGroupName()).
				withSchedule(scheduleBuilder).build();
		
		 try {
			 scheduler.scheduleJob(jobDetail, trigger);
			 scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteScheduleJob(Scheduler scheduler,SysTask task){
			try {
				scheduler.deleteJob(getJobKey(task.getTaskName(), task.getGroupName()));
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	/**
     * 获取jobKey
     *
     * @param jobName the job name
     * @param jobGroup the job group
     * @return the job key
     */
    public static JobKey getJobKey(String jobName, String jobGroup) {
        return JobKey.jobKey(jobName, jobGroup);
    }
}
