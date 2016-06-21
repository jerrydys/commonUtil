package com.ob.commonUtil.quartz;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;


public class ObQuartzUtils {
	
	private static final Logger logger = Logger.getLogger(ObQuartzUtils.class);
	
    private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();
	
	public static String JOB_NAME = "job_name_";
	public static String JOB_GROUP_NAME = "job_group_name_";
	public static String TRIGGER_NAME = "trigger_name_";
	public static String TRIGGER_GROUP_NAME = "trigger_group_name_";

	
	/**
	 * 添加定时任务,只在给定时间执行一次，（在任务中加上移除）
	 * @param jobId
	 * @param groupId
	 * @param clazz
	 * @param m
	 * @param startTime
	 */
	public static void addJob(String jobId, String groupId,Class<? extends Job> clazz, Map<String,Object> m, Date startTime){
		Scheduler scheduler;
		try {
			scheduler = schedulerFactory.getScheduler();
			/** 任务 */
			JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(JOB_NAME + jobId, JOB_GROUP_NAME+groupId).build();
			/** 传递数据给任务执行 */
			JobDataMap jobDataMap = jobDetail.getJobDataMap();
			setMapValue(jobDataMap,m);
			
			Trigger trigger = TriggerBuilder.newTrigger()
					.withIdentity(TRIGGER_NAME+jobId, TRIGGER_GROUP_NAME+groupId)
					.startAt(startTime)
					.withSchedule(SimpleScheduleBuilder.simpleSchedule()).build();
			scheduler.scheduleJob(jobDetail, trigger);
			/** 启动 */
			if (!scheduler.isShutdown()) {
				scheduler.start();
			}
		}catch (SchedulerException e) {
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * 添加定时任务,在给定的时间后每隔多少秒执行一次
	 * @param jobId
	 * @param groupId
	 * @param clazz
	 * @param m
	 * @param startTime
	 * @param intervalInSeconds
	 */
	public static void addJob(String jobId, String groupId,Class<? extends Job> clazz, Map<String,Object> m, Date startTime,int intervalInSeconds) {
		Scheduler scheduler;
		try {
			scheduler = schedulerFactory.getScheduler();
			/** 任务 */
			JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(JOB_NAME + jobId, JOB_GROUP_NAME+groupId).build();
			/** 传递数据给任务执行 */
			JobDataMap jobDataMap = jobDetail.getJobDataMap();
			setMapValue(jobDataMap,m);
			Trigger trigger = TriggerBuilder.newTrigger()
					.withIdentity(TRIGGER_NAME+jobId, TRIGGER_GROUP_NAME+groupId)
					.startAt(startTime)
					.withSchedule(SimpleScheduleBuilder.simpleSchedule()
				    .withIntervalInSeconds(intervalInSeconds)
				    .repeatForever()).build();
			scheduler.scheduleJob(jobDetail, trigger);
			/** 启动 */
			if (!scheduler.isShutdown()) {
				scheduler.start();
			}
		}catch (SchedulerException e) {
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * 添加定时任务,在给定的Cron表达式执行
	 * @param jobId
	 * @param groupId
	 * @param clazz
	 * @param m
	 * @param startTime
	 * @param cronExpression
	 */
	public static void addJob(String jobId, String groupId,Class<? extends Job> clazz, Map<String,Object> m, Date startTime,String cronExpression) {
		Scheduler scheduler;
		try {
			scheduler = schedulerFactory.getScheduler();
			/** 任务 */
			JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(JOB_NAME + jobId, JOB_GROUP_NAME+groupId).build();
			/** 传递数据给任务执行 */
			JobDataMap jobDataMap = jobDetail.getJobDataMap();
			setMapValue(jobDataMap,m);			
			CronTrigger trigger = TriggerBuilder.newTrigger()
					.withIdentity(TRIGGER_NAME+jobId, TRIGGER_GROUP_NAME+groupId)
					.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).build();
			scheduler.scheduleJob(jobDetail, trigger);
			/** 启动 */
			if (!scheduler.isShutdown()) {
				scheduler.start();
			}
		}catch (SchedulerException e) {
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * 移除任务
	 * @param id
	 * @param groupId
	 */
	public static void removeJob(String id,String groupId){
		Scheduler scheduler;
		try {
			scheduler = schedulerFactory.getScheduler();
			TriggerKey triggerKey = new TriggerKey(TRIGGER_NAME + id,TRIGGER_GROUP_NAME+groupId);
			/** 停止触发器 */
			scheduler.pauseTrigger(triggerKey);
			/** 移除触发器 */
			scheduler.unscheduleJob(triggerKey);
			JobKey jobKey = new JobKey(JOB_NAME + id, JOB_GROUP_NAME+groupId);
			/** 停止任务 */
			scheduler.pauseJob(jobKey);
			/** 删除任务 */
			scheduler.deleteJob(jobKey);
		} catch (SchedulerException e) {
			logger.error(e.getMessage());
		}
	}
		
	private static void setMapValue(JobDataMap jm,Map<String,Object> m){
		if (m != null && !m.isEmpty()) {
			for (String key : m.keySet()) {
				jm.put(key,m.get(key));
			}
		}
	}	
	
}
