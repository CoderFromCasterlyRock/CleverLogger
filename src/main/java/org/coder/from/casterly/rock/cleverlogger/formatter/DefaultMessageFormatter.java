package org.coder.from.casterly.rock.cleverlogger.formatter;

import ch.qos.logback.classic.*;

import org.coder.from.casterly.rock.cleverlogger.core.*;

import static org.coder.from.casterly.rock.cleverlogger.utils.LoggerUtils.*;


public class DefaultMessageFormatter implements MessageFormatter{
    
    
    @Override
    public final void formatMessage( StringBuilder buffer, LoggableEntity data ){
        
        Level level         = data.getLevel();
        if ( Level.OFF == level ) return;
    
        String name         = data.getName();
        String template     = data.getTemplate();
        
        formatTime( System.currentTimeMillis(), buffer ).append( DASH );
        buffer.append( O_BRAKCET ).append( name ).append( C_BRAKCET );
        buffer.append( level ).append( DASH );
        
        Object message      = data.getMessage1();
        if ( message != null ){
            template = replaceOnce( buffer, template, String.valueOf( message ) );
        }

        message             = data.getMessage2();
        if ( message != null ){
            template = replaceOnce( buffer, template, String.valueOf( message ) );
        }
                
        message             = data.getMessage3();
        if ( message != null ){
            template = replaceOnce( buffer, template, String.valueOf( message ) );
        }
        
        message             = data.getMessage4();
        if ( message != null ){
            template = replaceOnce( buffer, template, String.valueOf( message ) );
        }
        
        message             = data.getMessage5();
        if ( message != null ){
            template = replaceOnce( buffer, template, String.valueOf( message ) );
        }
        
        Throwable ex        = data.getException();
       
        if ( ex != null ){
            String errorMsg = ex.toString();
            
            if ( errorMsg != null ){
                buffer.append( LINE_SEP ).append( errorMsg ).append( LINE_SEP );
                for( StackTraceElement e : ex.getStackTrace() ){
                    buffer.append( e ).append( LINE_SEP );
                }
            }
        
        }

        buffer.append( LINE_SEP );

    }   
    

}
