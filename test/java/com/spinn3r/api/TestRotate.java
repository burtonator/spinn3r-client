package com.spinn3r.api;

import org.junit.Test;
import org.mockito.Mockito;

import com.google.inject.Provider;

public class TestRotate {

    /*
     * Test to see if logs rotate correctly when the log is full
     */
    @Test
    public void testRotate()
    {    
        /*
         * Crate two TransactionHistoryManagers. The first one is full,
         * the next one is empty. The Provider returns the full one first
         * the empty one second.
         */
        TransactionHistoryManager fullManager = Mockito.mock(TransactionHistoryManager.class);
        Mockito.when(fullManager.bytesWritten()).thenReturn(1001);
        
        TransactionHistoryManager emptyManager = Mockito.mock(TransactionHistoryManager.class);
        
        Provider<TransactionHistoryManager> fakeProvider = Mockito.mock(Provider.class);
        Mockito.when(fakeProvider.get()).thenReturn(fullManager).thenReturn(emptyManager);
        
        RotatingFileManager logManager = new RotatingFileManager(1000, fakeProvider);
        
        /*
         * Try to log Hello World!
         */
        logManager.log("Hello World!");
        
        /*
         * Make sure the full manager was closed and the 
         * empty manager received the log message
         */
        Mockito.verify(fullManager).close();
        Mockito.verify(fullManager, Mockito.never()).log(Mockito.anyString());
        Mockito.verify(fakeProvider, Mockito.times(2)).get();
        Mockito.verify(emptyManager).log("Hello World!");
    }
}
