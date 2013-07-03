package org.coder.from.casterly.rock.cleverlogger.core;

public class Benchmarker{

    private final static Benchmarker instance       = new Benchmarker();

    private static final int         DEFAULT_WARMUP = 0;

    private long                     time;
    private long                     count          = 0;
    private long                     totalTime      = 0;
    private long                     minTime        = Long.MAX_VALUE;
    private long                     maxTime        = Long.MIN_VALUE;

    private final int                warmup;

    
    public Benchmarker(){
        this( DEFAULT_WARMUP );
    }
    
    public Benchmarker( int warmup ){
        this.warmup = warmup;
        
        try {
            // initialize it here so when you measure for the first time gargage
            // is not created...
            Class.forName("java.lang.Math");
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }


    public final static Benchmarker instance(){
        return instance;
    }


    public void reset() {
        time = 0;
        count = 0;
        totalTime = 0;
        minTime = Long.MAX_VALUE;
        maxTime = Long.MIN_VALUE;
    }

    
    public void mark(){
        time = System.nanoTime();
    }

    
    public long measure(){
        if( time > 0 ){
            final long lastTime = System.nanoTime() - time;
            final boolean counted = measure(lastTime);
            if( counted ){
                return lastTime;
            }
        }
        
        return -1;
    }

    
    public boolean measure( long lastTime ){
        if (++count > warmup) {
            totalTime += lastTime;
            minTime = Math.min(minTime, lastTime);
            maxTime = Math.max(maxTime, lastTime);
            return true;
        }
        
        return false;
    }

    
    private final double avg() {
        final long realCount = count - warmup;
        if (realCount <= 0) {
            return 0;
        }
        final double avg = ((double) totalTime / (double) realCount);
        final double rounded = Math.round(avg * 100D) / 100D;
        return rounded;
    }

    
    public String results() {
        
    	final StringBuilder sb 	= new StringBuilder(128);
        final long realCount 	= count - warmup;
        
        sb.append("Iterations: ").append(realCount);
        sb.append(" | Avg Time: ").append(String.valueOf(avg())).append(" nanos");
        if (realCount > 0) {
            sb.append(" | Min Time: ").append(minTime);
            sb.append(" nanos | Max Time: ").append(maxTime);
            sb.append(" nanos");
        }

        return sb.toString();
    }
}
