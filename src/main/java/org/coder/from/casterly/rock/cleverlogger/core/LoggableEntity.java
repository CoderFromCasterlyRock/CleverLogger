package org.coder.from.casterly.rock.cleverlogger.core;

import ch.qos.logback.classic.Level;

import static ch.qos.logback.classic.Level.*;


public final class LoggableEntity{

    private Level level;
    private String name;
    private String template;
        
    private Object message1;
    private Object message2;
    private Object message3;
    private Object message4;
    private Object message5;
    
    private Throwable throwable;
    
    
    //DEBUG
    public void debug( String name, String template ){
        populateLog( DEBUG, name, null, template, null, null, null, null, null );
    }
    
    public void debug( String name, String template, Object message1 ){
        populateLog( DEBUG, name, null, template, message1, null, null, null, null );
    }
    
    public void debug( String name, String template, Object message1, Object message2 ){
        populateLog( DEBUG, name, null, template, message1, message2, null, null, null );
    }
    
    public void debug( String name, String template, Object message1, Object message2, Object message3 ){
        populateLog( DEBUG, name, null, template, message1, message2, message3, null, null );
    }
    
    public void debug( String name, String template, Object message1, Object message2, Object message3, Object message4){
        populateLog( DEBUG, name, null, template, message1, message2, message3, message4, null );
    }
    
    public void debug( String name, String template, Object message1, Object message2, Object message3, Object message4, Object message5 ){
        populateLog( DEBUG, name, null, template, message1, message2, message3, message4, message5 );
    }
    
    
    
    //INFO
    public void info( String name, String template ){
        populateLog( INFO, name, null, template, null, null, null, null, null );
    }
    
    public void info( String name, String template, Object message1 ){
        populateLog( INFO, name, null, template, message1, null, null, null, null );
    }
    
    public void info( String name, String template, Object message1, Object message2 ){
        populateLog( INFO, name, null, template, message1, message2, null, null, null );
    }
    
    public void info( String name, String template, Object message1, Object message2, Object message3 ){
        populateLog( INFO, name, null, template, message1, message2, message3, null, null );
    }
    
    public void info( String name, String template, Object message1, Object message2, Object message3, Object message4){
        populateLog( INFO, name, null, template, message1, message2, message3, message4, null );
    }
    
    public void info( String name, String template, Object message1, Object message2, Object message3, Object message4, Object message5 ){
        populateLog( INFO, name, null, template, message1, message2, message3, message4, message5 );
    }
    
    
    
    //WARN
    public void warn( String name, String template ){
        populateLog( WARN, name, null, template, null, null, null, null, null );
    }
    
    public void warn( String name, String template, Object message1 ){
        populateLog( WARN, name, null, template, message1, null, null, null, null );
    }
    
    public void warn( String name, String template, Object message1, Object message2 ){
        populateLog( WARN, name, null, template, message1, message2, null, null, null );
    }
    
    public void warn( String name, String template, Object message1, Object message2, Object message3 ){
        populateLog( WARN, name, null, template, message1, message2, message3, null, null );
    }
        
    public void warn( String name, Throwable t, String template ){
        populateLog( WARN, name, t, template, null, null, null, null, null );
    }
    
    public void warn( String name, Throwable t, String template, Object message1 ){
        populateLog( WARN, name, t, template, message1, null, null, null, null );
    }
    
    public void warn( String name, Throwable t, String template, Object message1, Object message2 ){
        populateLog( WARN, name, t, template, message1, message2, null, null, null );
    }
    
    public void warn( String name, Throwable t, String template, Object message1, Object message2, Object message3 ){
        populateLog( WARN, name, t, template, message1, message2, message3, null, null );
    }
    

    //ERROR
    public void error( String name, String template ){
        populateLog( ERROR, name, null, template, null, null, null, null, null );
    }
        
    public void error( String name, String template, Object message1 ){
        populateLog( ERROR, name, null, template, message1, null, null, null, null );
    }
    
    public void error( String name, String template, Object message1, Object message2 ){
        populateLog( ERROR, name, null, template, message1, message2, null, null, null );
    }
    
    public void error( String name, String template, Object message1, Object message2, Object message3 ){
        populateLog( ERROR, name, null, template, message1, message2, message3, null, null );
    }
    
    public void error( String name, Throwable t, String template ){
        populateLog( ERROR, name, t, template, null, null, null, null, null );
    }
    
    public void error( String name, Throwable t, String template, Object message1 ){
        populateLog( ERROR, name, t, template, message1, null, null, null, null );
    }
    
    public void error( String name, Throwable t, String template, Object message1, Object message2 ){
        populateLog( ERROR, name, t, template, message1, message2, null, null, null );
    }
    
    public void error( String name, Throwable t, String template, Object message1, Object message2, Object message3 ){
        populateLog( ERROR, name, t, template, message1, message2, message3, null, null );
    }
    
    
    
    private final void populateLog( Level level, String name, Throwable throwable, String template,
                                    Object message1, Object message2, Object message3, Object message4, Object message5 ){
        this.level      = level;
        this.name       = name;
        this.throwable  = throwable;
        this.template   = template;
        this.message1   = message1;
        this.message2   = message2;
        this.message3   = message3;
        this.message4   = message4;
        this.message5   = message5;
    }
    
    
    public final Level getLevel( ){
        return level;
    }
    
    
    public final String getName( ){
        return name;
    }
    
    
    public final String getTemplate( ){
        return template;
    }
    
    
    public final Object getMessage1( ){
        return message1;
    }
    
    
    public final Object getMessage2( ){
        return message2;
    }
    
    
    public final Object getMessage3( ){
        return message3;
    }
    
    
    public final Object getMessage4( ){
        return message4;
    }
    
    
    public final Object getMessage5( ){
        return message5;
    }
        
    
    public final Throwable getException( ){
        return throwable;
    }
    
        
}
