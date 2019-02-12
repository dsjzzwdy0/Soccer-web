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
import com.loris.client.task.Task;
import com.loris.client.task.TaskExecutor;
import com.loris.client.task.TaskPostProcessor;
import com.loris.client.task.TaskProducer;
import com.loris.client.task.TaskVector;
import com.loris.client.task.context.TaskPluginContext;
import com.loris.client.task.event.TaskEvent;
import com.loris.client.task.event.TaskEventListener;
import com.loris.client.task.plugin.TaskPlugin;
import com.loris.client.task.plugin.TaskPostProcessPlugin;
import com.loris.client.task.plugin.TaskProcessPlugin;
import com.loris.client.task.plugin.TaskProducePlugin;
import com.loris.client.task.event.TaskEvent.TaskEventType;
import com.loris.client.task.util.IdleThreadInfo;
import com.loris.client.task.util.TaskQueue;
import com.loris.client.task.util.ThreadUtil;

import static com.loris.client.scheduler.SchedulerStatus.*;

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
public class MainTaskScheduler implements TaskPluginContext, TaskEventListener, TaskVector, TaskScheduler
{
	/** */
	private static Logger logger = Logger.getLogger(MainTaskScheduler.class);
	
	/** The MainTaskScheduler id value. */
	private String id;

	/** 任务调度管理器的名称 */
	private String name;

	/** 最大的在运行状态的线程数 */
	private int maxActiveTaskThread = 5;

	/** The thread number. */
	protected int threadIndex = 1;

	/** 两次任务处理过程中的间隔时间 */
	private int interval = 200;

	/** 随机时间的种子值 */
	private int randTimeSeed;

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
	private TaskProducer taskProducer;

	/** 任务处理工具类 */
	private TaskExecutor taskExecutor;

	/** 任务后处理工具 */
	private TaskPostProcessor taskPostProcessor;

	/** 运行中的线程 */
	private List<Task> runningTaskThreads = new ArrayList<>();
	
	/**
	 * Create a new instance of AbstractTaskScheduler
	 */
	public MainTaskScheduler()
	{
		randTimeSeed = -100;
		idleThreadInfo = new IdleThreadInfo(this, maxActiveTaskThread);
		taskProducer = new TaskProducer(this);
		taskProducer.addTaskEventListener(this);
		taskExecutor = new TaskExecutor(this);
		taskExecutor.addTaskEventListener(this);
		taskPostProcessor = new TaskPostProcessor(this);
		taskPostProcessor.addTaskEventListener(this);
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
		taskPostProcessor.initialize(this);
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
		runningTaskThreads.add(task);
	}

	/**
	 * 从线程中移除
	 * 
	 * @param thread
	 */
	public void removeRunningTask(Task task)
	{
		runningTaskThreads.remove(task);
	}

	/**
	 * 最大的激活的线程数
	 * 
	 * @return
	 */
	public int getMaxActiveTaskThread()
	{
		return maxActiveTaskThread;
	}

	/**
	 * 设置最大的在运行的线程数
	 * 
	 * @param maxActiveTaskThread
	 */
	public void setMaxActiveTaskThread(int maxActiveTaskThread)
	{
		this.maxActiveTaskThread = maxActiveTaskThread;
		idleThreadInfo.setMaxActiveThreadNum(maxActiveTaskThread);
	}

	/**
	 * 开始一个新的运务运行
	 * 
	 * @param task
	 *            任务
	 */
	protected void startNewTaskThread(Task task)
	{
		// 设置监听器,将会启动一个新的线程来完成任务
		if (taskExecutor != null)
		{
			taskExecutor.process(task);
		}
		else
		{
			notify(new TaskEvent(task, TaskEventType.Error, new Exception("The TaskProcess is null.")));
		}
	}

	/**
	 * 启动任务后处理线程
	 */
	protected void startTaskPostProcess(Task task)
	{
		try
		{
			taskPostProcessor.execute(task);
		}
		catch(Exception e)
		{
			e.printStackTrace();
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
			addRunningTask(task);
		}
		else if (taskType == TaskEventType.Finished)
		{
			removeRunningTask(task);
			logger.info("Finished to excute " + task.getName());
			if (taskPostProcessor != null)
			{
				startTaskPostProcess(task);
			}
		}
		else if (taskType == TaskEventType.Error)
		{
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
			else
			{
				logger.info("Error occured when excuting " + task.getName() + ", push back to TaskQueue.");				
				taskQueue.pushBack(task);
				removeRunningTask(task);
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
				initialize();
		}
		catch (Exception e)
		{
			logger.info("Error occured when initialize the " + getName() + " scheduler, exit.");
			return;		
		}
		
		try
		{
			// 输出任务总的信息
			logger.info("TaskScheduler " + getName() + " has " + taskQueue.total() + " task and left "
					+ taskQueue.left() + " to be processed.");

			// 处理任务
			while (hasMoreTask())
			{
				// 如果有空闲的线程，则新建一个任务处理
				if (hasIdleThread())
				{
					Task task = popup();
					if (task != null)
					{
						startNewTaskThread(task);
					}
				}

				// 线程等待时间
				ThreadUtil.sleep(interval, randTimeSeed);

				// 如果设置停止标志，则中断执行线程
				if (isStopped())
				{
					logger.info("The TaskProcuder " + taskProducer.getName() + " has been set to stopped, interrupted now.");
					return;
				}
			}
			finish();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.info("Error occured when process TaskProcuder " + taskProducer.getName() + ", exit now.");
		}
	}

	/**
	 * 结束任务处理调度工具
	 */
	protected void finish()
	{
		isFinished = true;
		logger.info("MainTashScheduler[" + getName() + "] has been finished, exit now");
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
	public MainTaskScheduler getMainTaskScheduler()
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
		taskPostProcessor.close();
		taskPostProcessor = null;
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
		else if(plugin instanceof TaskProcessPlugin)
		{
			taskExecutor.addTaskProcessPlugin((TaskProcessPlugin)plugin);
		}
		else if(plugin instanceof TaskPostProcessPlugin)
		{
			taskPostProcessor.addTaskPostProcessPlugin((TaskPostProcessPlugin)plugin);
		}
	}
	
	/**********************************/
	/** Getter and Setter methods. */
	/**********************************/
	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
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

	public TaskPostProcessor getTaskPostProcessor()
	{
		return taskPostProcessor;
	}

	public void setTaskPostProcessor(TaskPostProcessor taskPostProcessor)
	{
		taskPostProcessor.addTaskEventListener(this);
		this.taskPostProcessor = taskPostProcessor;
	}

	public int getInterval()
	{
		return interval;
	}

	public void setInterval(int interval)
	{
		this.interval = interval;
	}

	public int getRandTimeSeed()
	{
		return randTimeSeed;
	}

	public void setRandTimeSeed(int randTimeSeed)
	{
		this.randTimeSeed = randTimeSeed;
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

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
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
	 * @see com.loris.client.scheduler.TaskScheduler#getSchedulerStatus()
	 */
	@Override
	public SchedulerStatus getSchedulerStatus()
	{
		int state = isFinished ? STATUS_FINISHED : isStopped ? STATUS_FINISHED : STATUS_INIT;
		return new SchedulerStatus(id, name, taskQueue.total(), taskQueue.left(), state);
	}
}
