package com.app.mapper.system;

import java.util.List;
import java.util.Map;

import com.app.model.system.SysTask;
import tk.mybatis.mapper.common.Mapper;

/**
 * 类说明：任务管理
 * @author CHENWEI
 * 2016年9月6日
 */
public interface SysTaskMapper extends Mapper<SysTask> {
	public List<SysTask> querySysTaskList(Map<String, Object> map);
	
	public SysTask queryTaskById(Long taskId);
	
	public SysTask queryTaskByMethod(String taskMethod);
	
	public List<SysTask> queryTaskListByState(String taskState);
}