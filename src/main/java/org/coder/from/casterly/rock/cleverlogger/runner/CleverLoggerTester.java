package org.coder.from.casterly.rock.cleverlogger.runner;

import java.io.*;

import org.coder.from.casterly.rock.cleverlogger.core.*;

import static org.coder.from.casterly.rock.cleverlogger.utils.LoggerUtils.*;


public class CleverLoggerTester{

	private static final String NAME       = CleverLoggerTester.class.getSimpleName();
	private static final String TEMPLATE   = "Reverse message count:{} Producer :: This is a simulated message.";
	
	public static void main( String[] args ) throws IOException{

        int iteration       = Integer.valueOf( args[ZERO] );
        String logFileName  = args[ ONE ];
        
	    CleverLogger logger = new CleverLogger( iteration, logFileName );
        logger.init();
    
        int logCount        = iteration;
        long iTime          = System.nanoTime();
        
        while( iteration > ZERO ){
            
            LoggableEntity data = logger.poll( );
            data.debug( NAME, TEMPLATE, iteration );
            logger.offer( data );
            
            --iteration;
        }
     
        long tTaken             = (System.nanoTime() - iTime );
        long avgTime            = ( tTaken/ logCount );
        
        System.out.println("Total number of lines written in the log file = " + logCount );
        System.out.println("Total time taken to write those lines (in nanos) = " + tTaken );
        System.out.println("Avg time taken to write those lines (in nanos) = " + avgTime );
        
        logger.stop();
        
	}
	

}
