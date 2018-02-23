package com.app.controller.system;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class MyTaskListener implements TaskListener{
	private static final long serialVersionUID = 7150859081998730672L;
	public void notify(DelegateTask delegateTask) {
		delegateTask.addCandidateGroup("1");
	}
}
