package com.yonyou.cloud.mom.core.transaction.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * 事务提交的base
 * 
 * @author BENJAMIN
 *
 */
public abstract class BaseTransactionExecutorImpl extends TransactionSynchronizationAdapter implements TransactionExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseTransactionExecutorImpl.class);
    protected final ThreadLocal<List<Runnable>> RUNNABLES = new ThreadLocal<List<Runnable>>();
    
    protected ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("mom-tran-pool-%d").build();
           
    
    /**
     * 事务执行线程池
     */
    protected ExecutorService executor = new ThreadPoolExecutor(10, 20, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(1024),namedThreadFactory);

    
	/**
	 * 注册到事务处理器
	 */
    @Override
	public void execute(Runnable command) {
		  LOGGER.debug("Submitting new runnable {} to run around commit", command);

	        if (!TransactionSynchronizationManager.isSynchronizationActive()) {
	            LOGGER.debug("事务同步未激活. 直接开始执行线程 {}", command);
	            executor.execute(command);
	            return;
	        }
	        List<Runnable> threadRunnables = RUNNABLES.get();
	        if (threadRunnables == null) {
	            threadRunnables = new ArrayList<Runnable>();
	            RUNNABLES.set(threadRunnables);
	            TransactionSynchronizationManager.registerSynchronization(this);
	        }
	        threadRunnables.add(command);
	}
  
}
