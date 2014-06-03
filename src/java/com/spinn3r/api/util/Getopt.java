package com.spinn3r.api.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class Getopt {

    private Map<String,String> params = new HashMap();
    private List<String> values = new ArrayList();

    /**
     * Parse command line arguments like --foo=bar where foo is the key and bar
     * is the value.
     *
     */
    public Getopt( String[] args ) {

        for( String arg : args ) {

            if ( ! arg.startsWith( "--" ) ) {
                values.add( arg );
                continue;
            }

            String[] split = arg.split( "=" );

            String key = split[0];

            if ( split.length != 2 )
                continue;

            if ( key.startsWith( "--" ) )
                key = key.substring( 2, key.length() );

            String value = split[1];

            params.put( key, value );

        }

    }

    public Map<String,String> getParams() {
        return params;
    }

    public List<String> getValues() {
        return values;
    }

    public String getString( String key ) {
        return getString( key, null );
    }

    public String getString( String key, String _default ) {

        if ( params.containsKey( key ) ) {
            return params.get( key );
        }

        return _default;

    }

    public boolean getBoolean( String key ) {
        return getBoolean( key, false );
    }

    public boolean getBoolean( String key, boolean _default ) {

        if ( params.containsKey( key ) ) {
            return "true".equals( params.get( key ) );
        }

        return _default;

    }

    public int getInt( String key ) {
        return getInt( key, 0 );
    }

    public int getInt( String key, int _default ) {

        if ( params.containsKey( key ) ) {
            return Integer.parseInt( params.get( key ) );
        }

        return _default;

    }

    public static void main( String[] args ) {

        //example usage.

        Getopt getopt = new Getopt( args );

        Map<String,String> params = getopt.getParams();
        List<String> values = getopt.getValues();

    }

}
