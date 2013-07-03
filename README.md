CleverLogger
============

Introduction
------------
CleverLogger is a super-fast, gc friendly, asynchronous, lock-free, thread-safe logging framework.

Quick Usage Guide
-----------------
Create and initialize CleverLogger.

	CleverLogger logger = new CleverLogger( 1024, "CleverLogger.log" );
	logger.init();

To log, retrieve the pre-created LoggableEntity object.

	LoggableEntity data = logger.poll( );

Log using the logback's syntax and store the LoggableEntity object back in the ring.

	data.debug("Producer1", "This is #{} simulated message.",  1 );
    logger.offer( data );

Optionally, stop the logger.

	logger.stop();
	

Internals
---------
CleverLogger uses a custom **CircularRing**; the ring pre-creates and stores LoggableEntity objects.

When a producer (producer of log messages) wishes to log, it retrieves the next LoggableEntity and writes messages to it.
It then stores the updated LoggableEntity object back in the ring. 
If the ring's capacity is reached while storing, we simply wrap and overwrite the entries at the beginning.


Optimizations
-------------
The CircularRing is thread-safe but it doesn't use locks. It provides atomicity and memory visibility using Atomic operations.
It also uses cache padded AtomicInteger to separate the producer and the writer indices to different cache line.
 
Further, it's extremely gc-friendly as it recycles LoggableEntity objects rather then creating it every time.

The producer never blocks but wraps when the capacity is reached.
The writer blocks, currently by spinning, if there are no new logs to be written.
The logger's syntax is similar to logback and it provides the benefits associated with delayed evaluation of place holders ({}).

Disclaimer
----------
The idea of using cache padded atomic variables was stolen from *Disruptor*.

Ideas expressed here are my own and do not, in any way, represent those of my employer.  