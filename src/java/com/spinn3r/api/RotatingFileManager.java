package com.spinn3r.api;

import java.util.ArrayList;
import java.util.List;

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
    private final Provider<TrackingTransactionManager> logManagerProvider;

    /**
     * Logs that are waiting to be "closed". A log is still "open" when there
     * are start() messages that are not yet matched by end() or error()
     * messages. Logs in this list have reached the maximum size, but have still
     * have "open" entries.
     */
    private final List<TrackingTransactionManager> closingLogs = new ArrayList<TrackingTransactionManager>();

    /**
     * The current log, where new messages are written.
     */
    private TrackingTransactionManager currentLog;

    /**
     * The number of bytes that have been written in "closed" logs.
     */
    private int closedBytesWritten = 0;

    @Inject
    public RotatingFileManager(int maxSize,
            Provider<TrackingTransactionManager> logManagerProvider) {
        this.maxSize = maxSize;
        this.logManagerProvider = logManagerProvider;

        currentLog = logManagerProvider.get();
    }

    @Override
    public synchronized void end(String url) {

        TrackingTransactionManager log = getForUrl(url);

        log.end(url);
        cleanup(log);
    }

    @Override
    public synchronized void error(String url, String message) {

        TrackingTransactionManager log = getForUrl(url);

        log.error(url, message);
        cleanup(log);

    }

    private TrackingTransactionManager getForUrl(String url) {
        if (currentLog.isActive(url))
            return currentLog;

        for (TrackingTransactionManager log : closingLogs) {
            if (log.isActive(url))
                return log;
        }

        throw new IllegalArgumentException();
    }

    @Override
    public synchronized void start(String url) {
        currentLog.start(url);
        cleanup(currentLog);
    }

    @Override
    public synchronized void close() {
        for (TransactionHistoryManager logEntry : closingLogs)
            logEntry.close();

        currentLog.close();
    }

    private void cleanup(TrackingTransactionManager log) {
        if (log.bytesWritten() < maxSize)
            return;

        if (log.equals(currentLog)) {
            closingLogs.add(log);
            currentLog = logManagerProvider.get();
        }

        if (log.numActive() > 0) {
            closedBytesWritten += log.bytesWritten();
            log.close();
            closingLogs.remove(log);
        }

    }

    @Override
    public synchronized int bytesWritten() {
        int currentTotal = closedBytesWritten;

        for (TransactionHistoryManager log : closingLogs) {
            currentTotal += log.bytesWritten();
        }

        return currentTotal;
    }

}
