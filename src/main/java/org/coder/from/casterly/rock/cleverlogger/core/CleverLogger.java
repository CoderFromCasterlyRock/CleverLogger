package org.coder.from.casterly.rock.cleverlogger.core;

import java.io.*;

import org.coder.from.casterly.rock.cleverlogger.formatter.*;
import org.coder.from.casterly.rock.cleverlogger.writer.*;
import org.slf4j.*;

import java.util.concurrent.*;
import java.util.concurrent.locks.*;


import static org.coder.from.casterly.rock.cleverlogger.utils.LoggerUtils.*;


public final class CleverLogger{
    
    private volatile boolean keepLogging;
    
    private final CircularRing circularRing;
    private final ExecutorService executor;
    private final BackgroundCircularLogger backLogger;
        
    private final static int DEFAULT_BLOCK_SIZE = THIRTY_TWO * SIXTY_FOUR;
    private final static String NAME            = "CleverLogger";
    private final static Logger LOGGER          = LoggerFactory.getLogger( NAME ); 
    
    
    public CleverLogger( int ringSize, String fileName ) throws IOException{
        this( DEFAULT_BLOCK_SIZE, ringSize, fileName, new DefaultMessageFormatter() );
    }
    
    public CleverLogger( int blockSize, int ringSize, String fileName ) throws IOException{
        this( blockSize, ringSize, fileName, new DefaultMessageFormatter() );
    }
    
    
    public CleverLogger( int blockSize, int ringSize, String fileName, MessageFormatter formatter ) throws IOException{
        this.circularRing   = new CircularRing( ringSize );
        this.backLogger     = new BackgroundCircularLogger( blockSize, fileName, formatter );
        this.executor       = Executors.newCachedThreadPool( new LoggerThreadFactory( NAME ) );
    }
    
    
    public final void init(){
        keepLogging = true;
        executor.execute( backLogger );
        
        LOGGER.info("Successfully initialized logger.");
    }
    
    
    public final LoggableEntity poll( ){
        return circularRing.poll( );
    }
    
    
    public final void offer( LoggableEntity data ){
        circularRing.offer( data );
    }
        
   
    public final void stop(){
        try {
            executor.shutdown();
            executor.awaitTermination( FIVE, TimeUnit.SECONDS );
            keepLogging = false;
            LOGGER.info("Stopping logger.");
            
        }catch( InterruptedException e ){
            LOGGER.warn("Exception while stopping logger.", e);
        }
    }
   
    
    private final class BackgroundCircularLogger implements Runnable{

        private int lIndex;
        private int wIndex;
        private int items;
        
        private final int blockSize;
        private final MessageFormatter formatter;
        private final DefaultLogWriter logWriter;
        
        public BackgroundCircularLogger( int blockSize, String fileName, MessageFormatter formatter ) throws IOException{
            this.lIndex     = NEGATIVE_ONE;
            this.wIndex     = NEGATIVE_ONE;
            this.blockSize  = blockSize;
            this.formatter  = formatter;
            this.logWriter  = new DefaultLogWriter(  fileName );
        }
        
        
        @Override
        public void run( ){
             
           while( keepLogging ){

               while ( keepLogging && (wIndex = circularRing.getWriterIndex()) == (lIndex = circularRing.getLoggerIndex()) ){
                   LockSupport.parkNanos( ONE );
               }
                               
               items                = lIndex - wIndex;
               items                = ( items > blockSize ) ? blockSize : items;
               StringBuilder buff   = new StringBuilder( items * SIXTEEN * SIXTY_FOUR );
               
               while( items > ZERO ){
                    int nextIdx             = circularRing.getNextWriterIndex();
                    LoggableEntity data     = circularRing.poll( nextIdx );
                    
                    formatter.formatMessage( buff, data );
                    --items;
               }

               logWriter.writeBuffered( buff );
               
           }
           
           LOGGER.info("Background Logger thread successfully stopped.");
           
        }
               
        
    }
         
           
}
 