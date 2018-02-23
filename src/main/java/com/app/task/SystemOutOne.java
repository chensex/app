package com.app.task;
import org.springframework.stereotype.Service;

import com.base.annotation.CronTask;
import com.base.annotation.CronTaskMethod;
@Service
@CronTask("打印1定时任务")
public class SystemOutOne{
	@CronTaskMethod("打印1方法")
	public void systemOutOne(){
		System.out.println("one");
		System.out.println("hahahaha");
	}
}
