package org.coder.from.casterly.rock.cleverlogger.core;

import java.util.concurrent.atomic.AtomicInteger;


public class PaddedAtomicInteger extends AtomicInteger{
    
    private long p1, p2, p3, p4, p5, p6;
    
    private static final long serialVersionUID = 1L;
    
    public PaddedAtomicInteger( ){
        super( );
    }
    
    public PaddedAtomicInteger( int value ){
        super( value );
    }
    
    
    public long foilJITOptimization(){
        return p1 + p2 + p3 + p4 + p5 + p6;
    }
    

}
