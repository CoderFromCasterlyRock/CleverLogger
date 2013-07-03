package org.coder.from.casterly.rock.cleverlogger.writer;


public interface LogWriter{
    
    public String getName();
    public void writeBuffered( StringBuilder buffer );
    public boolean close();
 

}
