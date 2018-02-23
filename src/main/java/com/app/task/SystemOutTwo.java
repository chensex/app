package com.app.task;
import org.springframework.stereotype.Service;

import com.base.annotation.CronTask;
import com.base.annotation.CronTaskMethod;
@Service
@CronTask("打印2定时任务")
public class SystemOutTwo{
	@CronTaskMethod("打印2方法")
	public void systemOutTwo(){
		System.out.println("111111");
	}
}
