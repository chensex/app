package com.app.model.system;

import java.util.Date;
import javax.persistence.*;

import com.base.model.BaseModel;

/**
 * 类说明：日志
 * @author CHENWEI
 * 2016年9月5日
 */
@Table(name = "sys_log")
public class SysLog extends BaseModel{
	private static final long serialVersionUID = 1663372592247670268L;

	/**
     * ID
     */
    @Id
    @Column(name = "log_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long logId;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 操作事件
     */
    @Column(name = "event_name")
    private String eventName;

    /**
     * 日志类型(1:操作日志 0:异常日志)
     */
    @Column(name = "log_type")
    private Integer logType;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 用户IP
     */
    @Column(name = "user_ip")
    private String userIp;

    /**
     * 描述
     */
    private String description;

    /**
     * 获取ID
     *
     * @return log_id - ID
     */
    public Long getLogId() {
        return logId;
    }

    /**
     * 设置ID
     *
     * @param logId ID
     */
    public void setLogId(Long logId) {
        this.logId = logId;
    }

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取操作事件
     *
     * @return event_name - 操作事件
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * 设置操作事件
     *
     * @param eventName 操作事件
     */
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    /**
     * 获取日志类型(1:操作日志 0:异常日志)
     *
     * @return log_type - 日志类型(1:操作日志 0:异常日志)
     */
    public Integer getLogType() {
        return logType;
    }

    /**
     * 设置日志类型(1:操作日志 0:异常日志)
     *
     * @param logType 日志类型(1:操作日志 0:异常日志)
     */
    public void setLogType(Integer logType) {
        this.logType = logType;
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
     * 获取用户IP
     *
     * @return user_ip - 用户IP
     */
    public String getUserIp() {
        return userIp;
    }

    /**
     * 设置用户IP
     *
     * @param userIp 用户IP
     */
    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    /**
     * 获取描述
     *
     * @return description - 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置描述
     *
     * @param description 描述
     */
    public void setDescription(String description) {
        this.description = description;
    }
}