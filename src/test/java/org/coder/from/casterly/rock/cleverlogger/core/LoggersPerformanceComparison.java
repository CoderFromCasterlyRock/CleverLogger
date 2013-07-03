package org.coder.from.casterly.rock.cleverlogger.core;

import java.io.*;

import org.coder.from.casterly.rock.cleverlogger.core.CleverLogger;
import org.coder.from.casterly.rock.cleverlogger.core.LoggableEntity;
import org.slf4j.*;


public class LoggersPerformanceComparison{
	
	public enum TestLoggerType{
	    LOGBACK,    
	    CLEVER_LOGGER; 
	}


    private static Logger logback               = null;
    private static CleverLogger cLogger         = null;
    
    private static final String NAME            = "PerfTester";
    private static final String MSG_TEMPLATE    = "This is a log message #{} to simulate a typical log message one may use in a non-trivial application.";
    
    
    public static void main( String[] args ) throws IOException{

        int iterations          = Integer.parseInt( args[0] );
        TestLoggerType logType  = TestLoggerType.valueOf( args[1] );
        
        float warmupPerc        = 0.25f;
        @SuppressWarnings("unused")
		int warmup              = (int) Math.round( warmupPerc * iterations );

        // please note that detailed benchmark produces garbage when measuring so it cannot be used when you want to profile the gc...
        boolean async           = true;
        boolean isDetailed      = true;
        Benchmarker bench       = isDetailed ? new DetailedBenchmarker( ) : new Benchmarker( );
        
        System.out.println("Testing " + logType + " performance test, will log " + iterations + " entries in async mode.");
        
        setup( iterations, logType, async );
      //  triggerGC( 5, 2000 );
        
        System.out.println("Starting...");

        long freeMemory         = 0;
        long mem                = 0;
        long totalMemory        = 0;
        long logsWithNoMemory   = 0;
        long logsWithMemory     = 0;
        long logsTotal          = 0;
        long logsGC             = 0;
                
        for( int i = 0; i < iterations; i++ ){
            String test = String.valueOf( i );    
            
            freeMemory = Runtime.getRuntime().freeMemory();
            bench.mark();
            log( logType, test );
            bench.measure();
            
            mem = freeMemory - Runtime.getRuntime().freeMemory();
            logsTotal++;
                
            if( mem > 0 ){
                totalMemory += mem;
                logsWithMemory++;
                
            }else if( mem == 0 ){
                logsWithNoMemory++;
                
            }else{
                logsGC++;
            }
                
        }
        
        long freeMemory2 = Runtime.getRuntime().freeMemory();
            
        System.out.println("Performance Results:");
        System.out.println("----------------------");
        System.out.println("Wrote " + logsTotal + " log lines to a file!");
        System.out.println("Memory Allocated: " + (freeMemory - freeMemory2 ) );
        System.out.println("Benchmark: " + bench.results());
        System.out.println("Logs that allocated memory: " + logsWithMemory   + " (" + p(logsWithMemory, logsTotal) + ")");
        System.out.println("Logs that did not allocate any memory: "  + logsWithNoMemory + " (" + p(logsWithNoMemory, logsTotal) + ")");
        System.out.println("Logs that triggered GC: " + logsGC + " (" + p(logsGC, logsTotal) + ")");
        System.out.println("Memory allocated: " + totalMemory + " bytes");
  
        System.out.println();
        
        stop( logType );
    
    }


    private final static void setup( int iterations, TestLoggerType logType, boolean isAsynchronous ) throws IOException{
        if( TestLoggerType.LOGBACK == logType ){
            logback = LoggerFactory.getLogger("LogbackPerfTest");
 
        }else if( TestLoggerType.CLEVER_LOGGER == logType ){
            cLogger = new CleverLogger( iterations, "PerfTest.log" );
            cLogger.init();
        }
    }
    
    
    private final static void stop( TestLoggerType logType ){
        if( TestLoggerType.LOGBACK == logType ){
            logback = null;
 
        }else if( TestLoggerType.CLEVER_LOGGER == logType ){
            cLogger.stop();
        }
    }
    

    private final static void log( TestLoggerType loggerType, String msg ){
      
        if( loggerType == TestLoggerType.LOGBACK ){
            logback.debug( MSG_TEMPLATE, msg );
        
        }else if( loggerType == TestLoggerType.CLEVER_LOGGER ){
            LoggableEntity entity = cLogger.poll();
            entity.debug( NAME, MSG_TEMPLATE, msg );
            cLogger.offer( entity );
            
        }
        
    }
 
    
    @SuppressWarnings("unused")
	private final static void triggerGC( int times, int pause ){
        System.out.println("Performing GC before starting...");
        try {
            for (int i = 0; i < times; i++) System.gc();
            Thread.sleep( pause );
        
        }catch( Exception e ){}
    }
    
    
    private static String p(final long l, final long total) {
        final double d = ((double) l) / ((double) total) * 100D;
        final double rounded = Math.round(d * 100) / 100D;
        return String.valueOf(rounded) + "%";
    }
    
}
