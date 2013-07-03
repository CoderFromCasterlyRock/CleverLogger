package org.coder.from.casterly.rock.cleverlogger.utils;


public class LoggerUtils{
    
    public static final String SPACE        = " ";
    public static final String COLON        = ":";
    public static final String DASH         = " - ";
    public static final String O_BRAKCET    = " [";
    public static final String C_BRAKCET    = "] ";
    public static final String LOG_PH       = "{}";
    
    public static final int NEGATIVE_ONE    = -1;
    public static final int ZERO            =  0;
    public static final int ONE             =  1;
    public static final int TWO             =  2;
    public static final int FIVE            =  5;
    public static final int SIXTEEN         = 16;
    public static final int TWENTY_FOUR     = 24;
    public static final int THIRTY_TWO      = 32;
    public static final int SIXTY           = 60;
    public static final int SIXTY_FOUR      = 64;
    public static final int THOUSAND        = 1000;
    
    
    public static final StringBuilder EMPTY_BUFFER  = new StringBuilder();
    public static final String LINE_SEP             = System.getProperty("line.separator");
       
    
    public static boolean powerOfTwo( int number ){
        return ((number & -number) == number) ? true : false;
    }
    
   
    public static StringBuilder formatTime( long millis, StringBuilder builder ){
        int ms      = (int) ( millis % SIXTY );
        int seconds = (int) ( millis / THOUSAND) % SIXTY;
        int minutes = (int) ((millis / (THOUSAND * SIXTY )) % SIXTY);
        int hours   = (int) ((millis / (THOUSAND * SIXTY * SIXTY )) % TWENTY_FOUR);
        
        builder.append( hours ).append( COLON ).append( minutes ).append( COLON ).append( seconds ).append( COLON ).append( ms);
        
        return builder;
    }
    
    
    public static boolean isEmpty( String str ){
        return str == null || str.length() == ZERO;
    }
    
    
    public static String replaceOnce( StringBuilder builder, String text, String replacement ){
        return replace( builder, text, LOG_PH, replacement, ONE );
    }
    
    
    public static String replace( StringBuilder builder, String text, String searchString, String replacement, int max ){
        
        if ( isEmpty(text) || isEmpty(searchString) || replacement == null || max == ZERO ){
            return text;
        }
        
        int start   = ZERO;
        int end     = text.indexOf( searchString, start );
        if( end == NEGATIVE_ONE ) return text;
        
        int replLength  = searchString.length();
        int increase    = replacement.length() - replLength;
        increase        = (increase < ZERO ? ZERO : increase);
        increase        *= (max < ZERO ? SIXTEEN : (max > SIXTY_FOUR ? SIXTY_FOUR : max));
        
        while( end != NEGATIVE_ONE ){
            builder.append( text.substring(start, end) ).append( replacement );
            start = end + replLength;
            if( --max == ZERO ){
                break;
            }
            
            end = text.indexOf(searchString, start);
        }
        
        builder.append( text.substring(start) );
        return builder.toString();
    
    }
    
   
}
