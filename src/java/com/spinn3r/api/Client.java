
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

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * Base interface for interacting with clients.
 */
public interface Client<T> {

    public void fetch() throws IOException,
        ParseException,
        InterruptedException;
    
    public List<T> getResults();

    public void setConfig( Config<T> config );

    public Config<T> getConfig();
    
    public String getNextRequestURL();

    public String getLastRequestURL();

    public long getCallDuration();

    public long getSleepDuration();

    public void setSleepDuration( long v );

    public InputStream getInputStream() throws IOException;

    public InputStream getInputStream( boolean decompress ) throws IOException;

    public Date getRestartPoint();

    public long getParseDuration();

    public void setParseDuration( long v );

}

