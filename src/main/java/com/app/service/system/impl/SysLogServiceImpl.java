package com.app.service.system.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.mapper.system.SysLogMapper;
import com.app.model.system.SysLog;
import com.app.service.system.SysLogService;
import com.base.service.impl.BaseServiceImpl;

/**
 * 类说明：日志
 * @author CHENWEI
 * 2016年9月5日
 */
@Service
public class SysLogServiceImpl extends BaseServiceImpl implements SysLogService{
	
	@Autowired
	private SysLogMapper sysLogMapper;
	public void saveSysLog(SysLog log) {
		sysLogMapper.insert(log);
	}
}
