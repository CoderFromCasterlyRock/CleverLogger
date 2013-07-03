package org.coder.from.casterly.rock.cleverlogger.formatter;

import org.coder.from.casterly.rock.cleverlogger.core.LoggableEntity;

public interface MessageFormatter{

    public void formatMessage( StringBuilder buffer, LoggableEntity data );
     
}
