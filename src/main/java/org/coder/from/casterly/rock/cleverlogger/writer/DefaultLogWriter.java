package org.coder.from.casterly.rock.cleverlogger.writer;

import java.io.*;
import org.slf4j.*;

import static org.coder.from.casterly.rock.cleverlogger.utils.LoggerUtils.*;


public class DefaultLogWriter implements LogWriter{
    
    private final File file;
    private final BufferedWriter bufferedWriter;
        
    private final static int DEFAULT_BUFFER_SIZE = SIXTY_FOUR * SIXTY_FOUR;
    private final static String NAME             = DefaultLogWriter.class.getSimpleName();
    private final static Logger LOGGER           = LoggerFactory.getLogger( NAME );
    
    
    public DefaultLogWriter( String name ) throws IOException{
        this( name, DEFAULT_BUFFER_SIZE );
    }
    
    
    public DefaultLogWriter( String name, int bufferSize ) throws IOException{
        this.file           = new File( name );
        this.bufferedWriter = new BufferedWriter( new FileWriter( file ), bufferSize );
        LOGGER.info("Created logfile with buffer size of {} at {}", bufferSize, name );
    }
    
    
    @Override
    public String getName(){
        return NAME;
    }
       
    
    @Override
    public final void writeBuffered( StringBuilder buffer ){
        
        try{
            bufferedWriter.write( String.valueOf(buffer) ) ;
            bufferedWriter.flush();
            
        }catch( Exception e ){
            LOGGER.error("Exception while writing buffered message>> {}", buffer, e );
        }
        
    }
    
      
    
    @Override
    public final boolean close(){
        boolean success = false;
        
        try {
            bufferedWriter.flush();
            bufferedWriter.close();
            success = true;
            
        }catch( Exception e ){
            LOGGER.error("Exception while closing writers.", e );
        }
        
        if( success ){
            LOGGER.info("Successfully closed writers." );
        }
        
        return success;
    }
    

}
