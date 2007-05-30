package org.shiftone.jrat.core.shutdown;



/**
 * Classes that implement this interface wish to know when the system is
 * shutting down.
 *
 * @author Jeff Drost
 *
 */
public interface ShutdownListener {
    void shutdown();
}
