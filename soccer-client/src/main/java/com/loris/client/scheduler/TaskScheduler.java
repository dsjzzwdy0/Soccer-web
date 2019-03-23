/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  League.java   
 * @Package com.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.client.scheduler;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.loris.client.exception.HostForbiddenException;
import com.loris.client.exception.NoProcessPluginException;
import com.loris.client.model.SchedulerStatus;
import com.loris.client.task.Task;
import com.loris.client.task.TaskExecutor;
import com.loris.client.task.TaskProducer;
import com.loris.client.task.TaskVector;
import com.loris.client.task.context.TaskPluginContext;
import com.loris.client.task.event.TaskEvent;
import com.loris.client.task.event.TaskEventListener;
import com.loris.client.task.plugin.TaskPlugin;
import com.loris.client.task.plugin.TaskProcessPlugin;
import com.loris.client.task.plugin.TaskProducePlugin;
import com.loris.client.task.event.TaskEvent.TaskEventType;
import com.loris.client.task.util.IdleThreadInfo;
import com.loris.client.task.util.TaskQueue;
import com.loris.client.task.util.ThreadUtil;

import static com.loris.client.model.SchedulerStatus.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @ClassName: TaskSchedulerThread
 * @Description: 任务调度管理器
 * @author: 东方足彩
 * @date: 2019年1月28日 下午8:59:32
 * 
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved.
 *             注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class TaskScheduler implements TaskPluginContext, TaskEventListener, TaskVector, Scheduler
{
	/** */
	private static Logger logger = Logger.getLogger(TaskScheduler.class);
	
	/** 状态 */
	private SchedulerStatus status;
	
	/** 是否停止 */
	private boolean isStopped = false;
	
	/** 任务调度完成 */
	private boolean isFinished = false;
	
	/** 是否已经初始化 */
	private boolean initialized =false;

	/** 空闲线程信息处理器 */
	IdleThreadInfo idleThreadInfo = null;

	/** 任务列表 */
	TaskQueue taskQueue = new TaskQueue();

	/** 任务生成器 */
	TaskProducer taskProducer;

	/** 任务处理工具类 */
	TaskExecutor taskExecutor;

	/** 运行中的线程 */
	List<Task> runningTaskThreads = new ArrayList<>();
	
	/**
	 * Create a new instance of TaskScheduler.
	 */
	public TaskScheduler()
	{
		this(new SchedulerStatus());
	}
	
	/**
	 * Create a new instance of AbstractTaskScheduler
	 */
	public TaskScheduler(SchedulerStatus status)
	{
		this.status = status;
		idleThreadInfo = new IdleThreadInfo(this, status.getMaxActiveTaskThread());
		taskProducer = new TaskProducer(this);
		taskProducer.addTaskEventListener(this);
		taskExecutor = new TaskExecutor(this);
		taskExecutor.addTaskEventListener(this);
		initialized = false;
	}
	
	/**
	 * 初始运行应用
	 * @throws IOException
	 */
	protected void initialize() throws IOException
	{
		try
		{
			taskProducer.initialize(this);			
			taskProducer.run();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		taskExecutor.initialize(this);
		
		initialized = true;
	}

	/**
	 * 加入运行中的线程
	 * 
	 * @param thread
	 */
	public void addRunningTask(Task task)
	{
		removeRunningTask(task);
		try
		{
			runningTaskThreads.add(task);
		}
		catch (Exception e) {
		}
	}

	/**
	 * 从线程中移除
	 * 
	 * @param thread
	 */
	public void removeRunningTask(Task task)
	{
		try
		{
			runningTaskThreads.remove(task);
		}
		catch (Exception e) {
		}
	}

	/**
	 * 最大的激活的线程数
	 * 
	 * @return
	 */
	public int getMaxActiveTaskThread()
	{
		return status.getMaxActiveTaskThread();
	}

	/**
	 * 设置最大的在运行的线程数
	 * 
	 * @param maxActiveTaskThread
	 */
	public void setMaxActiveTaskThread(int maxActiveTaskThread)
	{
		status.setMaxActiveTaskThread(maxActiveTaskThread);
		idleThreadInfo.setMaxActiveThreadNum(maxActiveTaskThread);
	}

	/**
	 * 开始一个新的运务运行
	 * 
	 * @param task
	 *            任务
	 */
	protected void startNewTaskProcessThread(Task task)
	{
		// 设置监听器,将会启动一个新的线程来完成任务
		if (taskExecutor != null)
		{
			taskExecutor.process(task);
		}
		else
		{
			notify(new TaskEvent(task, TaskEventType.Error, new IllegalArgumentException("The TaskProcess is null.")));
		}
	}

	/**
	 * 任务处理监听器 (non-Javadoc)
	 * 
	 * @see com.loris.client.task.event.TaskEventListener#notify(com.loris.client.task.event.TaskEvent)
	 */
	@Override
	public void notify(TaskEvent event)
	{
		TaskEvent.TaskEventType taskType = event.getType();
		Task task = event.getTask();
		if (taskType == TaskEventType.Created)
		{
			logger.debug("Create the task " + task.getName());
			taskQueue.add(task);
		}
		else if (taskType == TaskEventType.Excute)
		{
			logger.debug("Starting to excute " + task.getName());
			status.setInfo(task.getName());
			addRunningTask(task);
		}
		else if (taskType == TaskEventType.Executed)
		{
			removeRunningTask(task);
			logger.info("Finished to excute " + task.getName());
		}
		else if (taskType == TaskEventType.Error)
		{
			removeRunningTask(task);
			Throwable throwable = event.getError();
			if(throwable instanceof HostForbiddenException)      //如果是被禁止，则停止当前的类型的数据
			{
				String type = task.getType();
				if(StringUtils.isNotEmpty(type))
				{
					logger.info("The host forbidden the client to get data, remove all '" + type + "' task to be executed.");
					taskQueue.removeByType(type);
				}
			}
			else if(throwable instanceof NoProcessPluginException)
			{
				logger.warn("Warn: " + throwable.getMessage());
			}
			else
			{
				logger.info("Error occured '" + throwable.getMessage() + "' when excuting " + task.getName() + ", push back to TaskQueue.");				
				taskQueue.pushBack(task);
			}					
		}
	}

	/**
	 * 运行线程
	 */
	@Override
	public void run()
	{
		try
		{
			if(!initialized)
			{
				initialize();
			}
		}
		catch (Exception e)
		{
			logger.info("Error occured when initialize the " + getName() + " scheduler, exit.");
			return;		
		}
		
		try
		{
			// 输出任务总的信息
			logger.info("MainTaskScheduler " + status.getName() + " has " + taskQueue.total() + " task and left "
					+ taskQueue.left() + " to be processed.");

			// 处理任务
			while (hasMoreTask())
			{
				// 如果有空闲的线程，则新建一个任务处理
				if (hasIdleThread())
				{
					logger.info("INFO: " + getSchedulerStatus());
					Task task = popup();
					if (task != null)
					{
						startNewTaskProcessThread(task);
					}
				}

				// 线程等待时间
				ThreadUtil.sleep(status.getIntervaltime(), status.getRandTimeSeed());

				// 如果设置停止标志，则中断执行线程
				if (isStopped())
				{
					status.setStoptime();
					status.setInfo("The TaskProcuder " + taskProducer.getName() + " has been set to stopped, interrupted now.");
					logger.info(status.getInfo());
					return;
				}
			}
			finish();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			status.setInfo("Error occured when process TaskProcuder " + taskProducer.getName() + ", exit now.");
			logger.warn(status.getInfo());
		}
		
		//logger.info("Thread has been stopped, exit.");
	}

	/**
	 * 结束任务处理调度工具
	 */
	protected void finish()
	{
		isFinished = true;
		status.setFinishtime();
		status.setInfo("MainTashScheduler[" + status.getName() + "] has been finished, exit now");
		logger.info(status.getInfo());
	}

	/**
	 * 检测系统中是否有空闲的线程可用
	 * 
	 * @return
	 */
	protected boolean hasIdleThread()
	{
		int curRunningThreadNum = runningTaskThreads.size();
		return idleThreadInfo.hasIdleThread(curRunningThreadNum);
	}

	/**
	 * 是否还有需要处理的任务
	 * 
	 * @return 是否有任务的标志
	 */
	public boolean hasMoreTask()
	{
		return !taskQueue.isEmpty();
	}
	
	/**
	 *  (non-Javadoc)
	 * @see com.loris.client.task.context.TaskPluginContext#getMainTaskScheduler()
	 */
	@Override
	public TaskScheduler getMainTaskScheduler()
	{
		return this;
	}

	/**
	 * 关闭任务调度器
	 * 
	 * @see java.io.Closeable#close()
	 */
	@Override
	public void close() throws IOException
	{
		runningTaskThreads.clear();
		runningTaskThreads = null;
		idleThreadInfo = null;
		taskQueue.clear();
		taskQueue = null;
		taskExecutor.close();
		taskExecutor = null;
		taskProducer.close();
		taskProducer = null;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.loris.client.task.TaskVector#add(com.loris.client.task.Task)
	 */
	@Override
	public void add(Task task)
	{
		taskQueue.add(task);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.loris.client.task.TaskVector#remove(com.loris.client.task.Task)
	 */
	@Override
	public void remove(Task task)
	{
		taskQueue.remove(task);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.loris.client.task.TaskVector#clear()
	 */
	@Override
	public void clear()
	{
		taskQueue.clear();
	}
	
	/**
	 * 加入任务处理插件工具
	 * @param plugin 插件
	 */
	@Override
	public void addTaskPlugin(TaskPlugin plugin)
	{
		if(plugin instanceof TaskProducePlugin)
		{
			taskProducer.addTaskProducePlugin((TaskProducePlugin)plugin);
		}
		if(plugin instanceof TaskProcessPlugin)
		{
			taskExecutor.addTaskProcessPlugin((TaskProcessPlugin)plugin);
		}
	}
	
	/**********************************/
	/** Getter and Setter methods. */
	/**********************************/
	public String getName()
	{
		return status.getName();
	}
	
	public TaskProducer getTaskProducer()
	{
		return taskProducer;
	}

	public void setTaskProducer(TaskProducer tasksProducer)
	{
		tasksProducer.addTaskEventListener(this);
		this.taskProducer = tasksProducer;
	}

	public TaskExecutor getTaskProcessor()
	{
		return taskExecutor;
	}

	public void setTaskProcessor(TaskExecutor taskProcessor)
	{
		taskProcessor.addTaskEventListener(this);
		this.taskExecutor = taskProcessor;
	}
	public int total()
	{
		return taskQueue.total();
	}

	public Task popup()
	{
		return taskQueue.poll();
	}

	public boolean isStopped()
	{
		return isStopped;
	}

	public void setStopped(boolean isStopped)
	{
		this.isStopped = isStopped;
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.client.task.context.TaskPluginContext#getTaskVector()
	 */
	@Override
	public TaskVector getTaskVector()
	{
		return this;
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.client.task.TaskVector#removeTask(java.lang.String)
	 */
	@Override
	public void removeTask(String name)
	{
		taskQueue.removeByName(name);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.client.task.TaskVector#removeTaskByType(java.lang.String)
	 */
	@Override
	public void removeTaskByType(String type)
	{
		taskQueue.removeByType(type);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.client.task.context.TaskPluginContext#getTaskEventListener()
	 */
	@Override
	public TaskEventListener getTaskEventListener()
	{
		return this;
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.client.scheduler.Scheduler#getSchedulerStatus()
	 */
	@Override
	public SchedulerStatus getSchedulerStatus()
	{
		updateStatus();
		return status;
	}
	
	protected void updateStatus()
	{
		int state = isFinished ? STATUS_FINISHED : isStopped ? STATUS_FINISHED : STATUS_INIT;
		status.setState(state);
		status.setLeftsize(taskQueue.left());
		status.setTotal(taskQueue.total());
	}
}
