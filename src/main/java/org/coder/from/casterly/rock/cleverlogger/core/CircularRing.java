package org.coder.from.casterly.rock.cleverlogger.core;

import org.slf4j.*;
import java.util.concurrent.atomic.*;

import static org.coder.from.casterly.rock.cleverlogger.utils.LoggerUtils.*;


public final class CircularRing{
    
    private final int ringCapacity;
    private final AtomicInteger writerIndex;
    private final AtomicInteger loggerIndex;
    private final LoggableEntity[] ringBuffer;
    
    private final static int DEFAULT_RING_CAPACITY  = SIXTY_FOUR * SIXTY_FOUR * SIXTY_FOUR;
    private final static Logger LOGGER              = LoggerFactory.getLogger( "CircularRing" );
    
    
    public CircularRing( ){
        this( DEFAULT_RING_CAPACITY );
    }    
        
    
    public CircularRing( int ringCapacity ){
       
        if( !powerOfTwo( ringCapacity ) ){
            throw new IllegalArgumentException("Ring capacity [" + ringCapacity + "] must be a power of 2.");
        }
        
        this.ringCapacity     = ringCapacity;
        this.writerIndex      = new PaddedAtomicInteger( NEGATIVE_ONE );
        this.loggerIndex      = new PaddedAtomicInteger( NEGATIVE_ONE );
        this.ringBuffer       = new LoggableEntity[ ringCapacity ];
    
        for( int i=ZERO; i< ringCapacity; i++ ){
            offer( i , new LoggableEntity( ) );
        }
        
        LOGGER.info("Successfully created and preloaded CircularRing with a capacity of {}.", ringCapacity );
    
    }
    
    
    protected final int getLoggerIndex(){
        return loggerIndex.get();
    }
    
    
    protected final int getWriterIndex(){
        return writerIndex.get();
    }
        
    
    public final int getNextWriterIndex(){
        return writerIndex.incrementAndGet();
    }
          
  
    public final LoggableEntity poll(){
        for( ;; ){
            int current = loggerIndex.get();
            int next    = (current + ONE ) % ringCapacity;
                       
            if( loggerIndex.compareAndSet(current, next) ){
                return ringBuffer[ next ];
            }
        }
    }
    
    
    public final LoggableEntity poll( int index ){
        return ringBuffer[ index ];
    }
    
    
    public final void offer( LoggableEntity entity ){
        offer( loggerIndex.get(), entity );
    }
    
    
    private final void offer( int index, LoggableEntity entity ){
        ringBuffer[ index ] = entity;
    }
   

}
