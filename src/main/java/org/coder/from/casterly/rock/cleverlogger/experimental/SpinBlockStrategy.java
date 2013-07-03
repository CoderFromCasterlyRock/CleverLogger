package org.coder.from.casterly.rock.cleverlogger.experimental;

import java.util.concurrent.locks.LockSupport;

import static org.coder.from.casterly.rock.cleverlogger.utils.LoggerUtils.*;


public class SpinBlockStrategy{
     
    private int sleepTime               = ZERO;
    private final boolean incrementalBlock;
     
 
    public SpinBlockStrategy( ){
        this( false );
    }
 
    public SpinBlockStrategy( boolean incrementalBlock ){
        this.incrementalBlock = incrementalBlock;
    }
     
    
    public final void block(){
        if (incrementalBlock) {
            LockSupport.parkNanos( ++sleepTime );
        } else {
            LockSupport.parkNanos( ONE );
        }
    }
 
    public final void reset() {
        sleepTime = ZERO;
    }
         
}