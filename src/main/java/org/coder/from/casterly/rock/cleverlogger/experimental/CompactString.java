package org.coder.from.casterly.rock.cleverlogger.experimental;

import java.io.*;
import java.util.Arrays;

import static org.coder.from.casterly.rock.cleverlogger.utils.LoggerUtils.*;

public final class CompactString implements CharSequence, Serializable{
 
    private final int offset;
    private final int end;
    private final byte[] data;

    private static final long serialVersionUID  = ONE;
    private static final String ENCODING        = "ISO-8859-1";

    
    public CompactString( String str ){
      try{
          this.offset   = ZERO;
          this.data     = str.getBytes( ENCODING );
          this.end      = data.length;
      
      }catch( UnsupportedEncodingException e ){
          throw new RuntimeException("Unexpected: " + ENCODING + " not supported!");
      }
    
    }
    
    
    private CompactString( byte[] data, int offset, int end ){
        this.data   = data;
        this.offset = offset;
        this.end    = end;
    }

    
    @Override
    public int length(){
      return end - offset;
    }
    
    
    @Override
    public char charAt( int index ){
        int ix = index + offset;
        
        if( ix >= end ){
            throw new StringIndexOutOfBoundsException("Invalid index " + index + " length " + length() );
        }
        
        return (char) (data[ix] & 0xff);
    }

    
    @Override
    public CharSequence subSequence( int startIndex, int endIndex ){
        if (startIndex < ZERO || endIndex >= (end-offset) ){
            throw new IllegalArgumentException("Illegal range " + startIndex + "-" + end + " for sequence of length " + length() );
        }
        
        return new CompactString(data, startIndex + offset, end + offset);
      
    }
    
    
    @Override
    public int hashCode(){
        final int prime = 31;
        int result = ONE;
        result = prime * result + Arrays.hashCode(data);
        result = prime * result + end;
        result = prime * result + offset;
        return result;
    }


    @Override
    public boolean equals( Object obj ){
        if( this == obj ) return true;
        if( obj == null ) return false;
        
        if( getClass() != obj.getClass() ) return false;
        
        CompactString other = (CompactString) obj;
        
        if( end != other.end ) return false;
        if( offset != other.offset ) return false;
        if( !Arrays.equals(data, other.data) ) return false;
        
        return true;
    }
    
    
    @Override
    public String toString() {
        try{
          return new String( data, offset, end-offset, ENCODING );
        
        }catch( UnsupportedEncodingException e ){
            throw new RuntimeException("Unexpected: " + ENCODING + " not supported");
        }
    }

}