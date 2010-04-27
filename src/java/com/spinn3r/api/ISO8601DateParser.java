
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

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * ISO 8601 date parsing utility.  Designed for parsing the ISO subset used in
 * Dublin Core, RSS 1.0, and Atom.
 *
 * Example:
 *
 * 1994-11-05T13:15:30Z
 *
 * @author <a href="mailto:burton@apache.org">Kevin A. Burton (burtonator)</a>
 * @version $Id: ISO8601DateParser.java 155416 2005-02-26 13:00:10Z dirkv $
 */
public class ISO8601DateParser {

    public static Date parse( String input ) {

        if ( input == null )
            return null;
        
        int min_length = 20;
        int max_length = 26;

        if ( input.length() < min_length ) {
            return null;
        }

        if ( input.length() > max_length ) {
            return null;
        }

        String timezone = input.substring( 19, input.length() );

        //NOTE: it was MUCH faster to cache this....
        TimeZone tz = TimeZone.getTimeZone( "UTC" );

        if ( ! "Z".equals( timezone ) ) {

            int multiplier = 1;
            if ( timezone.charAt( 0 ) == '-' )
                multiplier = -1;

            int timezone_hours = regionToInt( timezone, 1, 2 );

            if ( timezone_hours == -1 )
                return null;

            //NOTE: is this safe??
            tz.setRawOffset( multiplier * timezone_hours * 60 * 60 * 1000 );
            
        }
        
        //now we can read the ranges we want without fear of breaking.

        int year = regionToInt( input, 0, 4 );
        
        if ( year == -1 ) {
            return null;
        }

        int month = regionToInt( input, 5, 2 );

        if ( month == -1 ) {
            return null;
        }

        int day = regionToInt( input, 8, 2 );

        if ( day == -1 ) {
            return null;
        }

        int hour = regionToInt( input, 11, 2 );

        if ( hour == -1 ) {
            return null;
        }

        int minute = regionToInt( input, 14, 2 );

        if ( minute == -1 ) {
            return null;
        }

        int second = regionToInt( input, 17, 2 );

        if ( second == -1 ) {
            return null;
        }

        Calendar c = Calendar.getInstance( tz );
        c.set( Calendar.YEAR, year );
        c.set( Calendar.MONTH, month - 1 );
        c.set( Calendar.DAY_OF_MONTH, day );
        c.set( Calendar.HOUR_OF_DAY, hour );
        c.set( Calendar.MINUTE, minute );
        c.set( Calendar.SECOND, second );

        //clear out milliseconds.  this is important or we'll have
        //nondeterministic results.
        c.set( Calendar.MILLISECOND, 0 );

        c.setTimeZone( tz );
        
        return c.getTime();

    }

    private static int regionToInt( String input,
                                    int offset,
                                    int length ) {

        return parseInt( input.substring( offset, offset + length ) );
        
    }

    public static int parseInt(String s ) {
        return parseInt( s, 10 );
    }

    public static int parseInt(String s, int radix)
    {
        if (s == null) {
            return -1;
        }

        if (radix < Character.MIN_RADIX) {
            return -1;
        }

        if (radix > Character.MAX_RADIX) {
            return -1;
        }

        int result = 0;
        boolean negative = false;
        int i = 0, max = s.length();
        int limit;
        int multmin;
        int digit;

        if (max > 0) {
            if (s.charAt(0) == '-') {
                negative = true;
                limit = Integer.MIN_VALUE;
                i++;
            } else {
                limit = -Integer.MAX_VALUE;
            }
            multmin = limit / radix;
            if (i < max) {
                digit = Character.digit(s.charAt(i++),radix);
                if (digit < 0) {
                    return -1;
                } else {
                    result = -digit;
                }
            }
            while (i < max) {
                // Accumulating negatively avoids surprises near MAX_VALUE
                digit = Character.digit(s.charAt(i++),radix);
                if (digit < 0) {
                    return -1;
                }
                if (result < multmin) {
                    return -1;
                }
                result *= radix;
                if (result < limit + digit) {
                    return -1;
                }
                result -= digit;
            }
        } else {
            return -1;
        }
        if (negative) {
            if (i > 1) {
                return result;
            } else {    /* Only got "-" */
                return -1;
            }
        } else {
            return -result;
        }
    }

    public static String toString( Date date ) {

        if ( date == null )
            return null;
        
        TimeZone tz = TimeZone.getTimeZone( "UTC" );
        
        Calendar c = Calendar.getInstance( tz );

        c.setTime( date );
        
        String result = String.format( "%d-%02d-%02dT%02d:%02d:%02dZ",
                                       c.get( Calendar.YEAR ),
                                       c.get( Calendar.MONTH ) + 1,
                                       c.get( Calendar.DAY_OF_MONTH ),
                                       c.get( Calendar.HOUR_OF_DAY ),
                                       c.get( Calendar.MINUTE ),
                                       c.get( Calendar.SECOND ) );
                                 
        return result;
        
    }

}
