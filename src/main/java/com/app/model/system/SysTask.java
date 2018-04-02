package com.app.model.system;

import java.util.Date;
import javax.persistence.*;

import com.base.model.BaseModel;
/**
 * 类说明：任务管理
 * @author CHENWEI
 * 2016年9月6日
 */
@Table(name = "sys_task")
public class SysTask extends BaseModel{
	private static final long serialVersionUID = 1630210352363103308L;

	/**
     * id
     */
    @Id
    @Column(name = "task_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    /**
     * 任务名称
     */
    @Column(name = "task_name")
    private String taskName;

    /**
     * 运行状态(RUN: 运行：WAIT：等待中
     */
    private String runState;
    
    /**
     * 任务状态 DISABLE：停用， ENABLE：启用
     */
    private String taskState;

    /**
     * 创建时间
     */
    @Column(name = "create_time",updatable=false)
    private Date createTime;

    /**
     * 时间表达式
     */
    @Column(name = "time_expression")
    private String timeExpression;
    
    /**
     * 任务组名
     */
    @Column(name = "group_name")
    private String groupName;
    
    /**
     * 任务服务类
     */
    @Column(name = "task_service")
    private String taskService;
    
    /**
     * 任务服务方法
     */
    @Column(name = "task_method")
    private String taskMethod;

    /**
     * 获取id
     *
     * @return task_id - id
     */
    public Long getTaskId() {
        return taskId;
    }

    /**
     * 设置id
     *
     * @param taskId id
     */
    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    /**
     * 获取任务名称
     *
     * @return task_name - 任务名称
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * 设置任务名称
     *
     * @param taskName 任务名称
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取时间表达式
     *
     * @return time_expression - 时间表达式
     */
    public String getTimeExpression() {
        return timeExpression;
    }

    /**
     * 设置时间表达式
     *
     * @param timeExpression 时间表达式
     */
    public void setTimeExpression(String timeExpression) {
        this.timeExpression = timeExpression;
    }

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getTaskService() {
		return taskService;
	}

	public void setTaskService(String taskService) {
		this.taskService = taskService;
	}

	public String getTaskMethod() {
		return taskMethod;
	}

	public void setTaskMethod(String taskMethod) {
		this.taskMethod = taskMethod;
	}

	public String getRunState() {
		return runState;
	}

	public void setRunState(String runState) {
		this.runState = runState;
	}

	public String getTaskState() {
		return taskState;
	}

	public void setTaskState(String taskState) {
		this.taskState = taskState;
	}
}