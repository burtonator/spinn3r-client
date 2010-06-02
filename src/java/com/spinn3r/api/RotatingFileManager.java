package com.spinn3r.api;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * The Rotating File Manger logs status messages through a rotating set of log
 * files. Messages are written to one log file until that file has reached the
 * maximum size. Further log messages are written to a new log file. The
 * Rotating Log Manager also deletes old log files once the data is no longer
 * useful.
 * 
 * @author jwu
 * 
 */
public class RotatingFileManager implements TransactionHistoryManager {

    /**
     * The maximum number of bytes allowed in a log file.
     */
    private final int maxSize;

    /**
     * Factory for new log files.
     */
    private final Provider<? extends TransactionHistoryManager> logManagerProvider;

    /**
     * This is a log being closed. We don't close the last log until we have successfully
     * written a URL to the new log. Therefore, if we fail to create a new log file,
     * there will be some record of the state of the application.
     */
    private TransactionHistoryManager closingLog;

    /**
     * The current log, where new messages are written.
     */
    private TransactionHistoryManager currentLog;

    /**
     * The number of bytes that have been written in "closed" logs.
     */
    private int closedBytesWritten = 0;

    @Inject
    public RotatingFileManager(int maxSize,
            Provider<? extends TransactionHistoryManager> logManagerProvider) {
        this.maxSize = maxSize;
        this.logManagerProvider = logManagerProvider;

        currentLog = logManagerProvider.get();
    }

    @Override
    public synchronized void log(String url) {
        /*
         * If we have gone over the maximum size, create a new log file.
         * Keep the old file around until we have successfully written the
         * the new file
         */
        if(currentLog.bytesWritten() > maxSize) {
            closingLog = currentLog;
            currentLog = logManagerProvider.get();
        }
        
        currentLog.log(url);
        
        if(closingLog != null) {
            closedBytesWritten += closingLog.bytesWritten();
            closingLog.close();
            closingLog = null;
        }
    }

  

    @Override
    public synchronized void close() {
        currentLog.close();
    }

    @Override
    public synchronized int bytesWritten() {
        return closedBytesWritten + currentLog.bytesWritten();
    }

}
