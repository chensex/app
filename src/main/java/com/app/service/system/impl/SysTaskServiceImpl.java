package com.app.service.system.impl;

import java.util.List;
import java.util.Map;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.mapper.system.SysTaskMapper;
import com.app.model.system.SysTask;
import com.app.service.system.SysTaskService;
import com.base.service.impl.BaseServiceImpl;
import com.github.pagehelper.PageHelper;

@Service
public class SysTaskServiceImpl extends BaseServiceImpl implements SysTaskService{
	@Autowired
	private SysTaskMapper sysTaskMapper;
	
	@Autowired
	private Scheduler scheduler;
	
	public List<SysTask> selectSysTaskList(Map<String, Object> map, int page, int pageSize) {
		PageHelper.startPage(page, pageSize);
		List<SysTask> sysTasks = sysTaskMapper.querySysTaskList(map);
		return sysTasks;
	}

	public void saveAndEditTask(SysTask sysTask) {
		if(sysTask.getTaskId()==null){
			sysTaskMapper.insert(sysTask);
		}else{
			sysTaskMapper.updateByPrimaryKey(sysTask);
		}
	}

	public Scheduler getScheduler() {
		return scheduler;
	}

	public SysTask selectTaskById(Long taskId) {
		return sysTaskMapper.queryTaskById(taskId);
	}
	
	public SysTask selectTaskByMethod(String taskMethod) {
		return sysTaskMapper.queryTaskByMethod(taskMethod);
	}
	
	public List<SysTask> selectTaskListByState(String taskState) {
		return sysTaskMapper.queryTaskListByState(taskState);
	}
}