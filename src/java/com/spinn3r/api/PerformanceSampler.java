/*
 * Copyright 2007 Tailrank, Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.spinn3r.api;

import java.util.*;

/**
 * Simple performance monitoring system similar to benchmark4j.
 */
public class PerformanceSampler {

    long interval = -1;
    
    double performance = -1.0;
    
    long duration = 0;
    
    long start = 0;
    
    long content_start = -1;
    long content_end = -1;

    public PerformanceSampler( long interval ) {
        this.interval = interval;
    }
    
    public void sample( Date date ) {
        
        if ( date == null ) 
            return;
        
        content_end = date.getTime();
        
        //initialize performance...
        if ( start <= 0 ) {
            start = System.currentTimeMillis();
            content_start = date.getTime();
            return;
        }

        update();

    }
    
    public double getPerformance() {
        return performance;
    } 

    private void update() {

        duration = System.currentTimeMillis() - start;
        
        if ( duration >= interval ) {
            
            performance = (double)(content_end - content_start) / (double)duration ;
            
            //reset for next sample...
            start = 0;
            
        }

    }
    
}