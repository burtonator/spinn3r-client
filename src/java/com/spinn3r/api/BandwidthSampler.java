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


/**
 * Simple performance monitoring system similar to benchmark4j.
 */
public class BandwidthSampler {

    long interval = -1;

    /**
     * Bandwidth in bits per second.
     */
    long bandwidth = 0;
    
    long duration = 0;
    
    long start = 0;
    
    long bytes = 0;

    public BandwidthSampler( long interval ) {
        this.interval = interval;
    }
    
    public void sample( int bytes ) {

        this.bytes += bytes;
        
        //initialize ...
        if ( start <= 0 ) {
            start = System.currentTimeMillis();
            return;
        }

        update();

    }
    
    public long getBandwidth() {
        update();
        return bandwidth;
    } 

    /**
     * Get bandwidth in a human metric.  Instead of 10000000 we return "10Mb/s"
     *
     */
    public String getBandwidthAsHumanMetric() {

        return getBandwidthAsHumanMetric( getBandwidth() );

    }

    public static String getBandwidthAsHumanMetric( long v ) {

        if ( v >= 1000000 ) {
            return String.format( "%.1fMb/s", v / 1000000d );
        }

        if ( v >= 1000 ) {
            return String.format( "%.1fkb/s", v / 1000d );
        }

        return v + "b/s";

    }
        
    private void update() {

        duration = System.currentTimeMillis() - start;
        
        if ( duration >= interval ) {
            
            bandwidth = (bytes / 1000) * 8 ;
            
            //reset for next sample...
            start = 0;
            bytes = 0;
            
        }

    }
    
}