package org.coder.from.casterly.rock.cleverlogger.core;

import org.slf4j.*;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import java.lang.management.ThreadMXBean;
import java.lang.management.ManagementFactory;
import java.lang.Thread.UncaughtExceptionHandler;


public final class LoggerThreadFactory implements ThreadFactory{

	private final String prefix;
	private final ThreadMXBean bean;
		
	private final static AtomicInteger USER_THREAD_COUNTER    = new AtomicInteger();
	private final static Logger LOGGER                         = LoggerFactory.getLogger( LoggerThreadFactory.class.getSimpleName() );
	
	static{
		Thread.setDefaultUncaughtExceptionHandler( new CustomThreadExceptionHandler() );
	}
	
	
	public LoggerThreadFactory( String prefix ){
		this.prefix	  = prefix;
		this.bean     = ManagementFactory.getThreadMXBean();
	}
	
	
	@Override
	public Thread newThread( Runnable runnable ){
	    
	    StringBuilder builder = new StringBuilder( 32 );
		builder.append( prefix ).append (": [");
		builder.append( USER_THREAD_COUNTER.incrementAndGet() ).append ("/").append ( bean.getThreadCount() );
		builder.append ("]");
		
		Thread thread = new Thread( runnable, builder.toString() );
		return thread;
	}
		
	
	private static class CustomThreadExceptionHandler implements UncaughtExceptionHandler{

		@Override
		public void uncaughtException( Thread t, Throwable e ){
		    LOGGER.warn("Caught UNHANDLED exception from thread called {}", t.getName() );
			LOGGER.warn("Exception Message: {}", e.getMessage() );
			LOGGER.warn("Exception : {}",e );
		}
		
	}

}
