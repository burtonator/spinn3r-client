
package com.spinn3r.api;

import java.util.List;
import java.util.ArrayList;

import org.w3c.dom.Element;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class XMLUtils {

    // **** XML parsing utilities ***********************************************

    public static final String NS_API     = "http://tailrank.com/ns/#api";
    public static final String NS_DC      = "http://purl.org/dc/elements/1.1/" ;
    public static final String NS_ATOM    = "http://www.w3.org/2005/Atom" ;
    public static final String NS_WEBLOG  = "http://tailrank.com/ns/#weblog" ;
    public static final String NS_SOURCE  = "http://tailrank.com/ns/#source" ;
    public static final String NS_POST    = "http://tailrank.com/ns/#post" ;
    public static final String NS_FEED    = "http://tailrank.com/ns/#feed" ;
    public static final String NS_LINK    = "http://spinn3r.com/ns/link" ;
    public static final String NS_TARGET  = "http://spinn3r.com/ns/#target" ;

    public static final String NS_COMMENT = "http://tailrank.com/ns/#comment" ;


    public static boolean empty( String value ) {

        return value == null || "".equals( value );
        
    }


    public static int parseInt( String v ) {

        if ( v == null || v.equals( "" ) )
            return 0;

        return Integer.parseInt( v );
        
    }

    public static long parseLong( String v ) {

        if ( v == null || v.equals( "" ) )
            return 0L;

        return Long.parseLong( v );
        
    }

    public static float parseFloat( String v, float _default ) {

        if ( v == null || v.equals( "" ) )
            return _default;

        return Float.parseFloat( v );

    }
    
    public static List parseTags( Element current ) {
        return parseChildNodesAsList( current, "category" );
    }

    public static List parseChildNodesAsList( Element current, String name ) {

        List result = new ArrayList();
        
        NodeList nodes = current.getElementsByTagName( name );
        
        for( int i = 0; i < nodes.getLength(); ++i ) {

            Element e = (Element)nodes.item( i );

            Node child = e.getFirstChild();
            
            if ( child == null )
                continue;
        
            result.add( child.getNodeValue() );

        }

        return result;
    }

    public static Element getElementByTagName( Element current,
                                               String name ) {
        return getElementByTagName( current, name, null );
    }
    
    public static Element getElementByTagName( Element current,
                                               String name,
                                               String namespace ) {

        NodeList elements = null;

        if ( namespace == null )
            elements = current.getElementsByTagName( name );
        else 
            elements = current.getElementsByTagNameNS( namespace, name );

        if ( elements == null )
            return null;
        
        if ( elements.getLength() != 1 )
            return null;

        return (Element)elements.item( 0 );

    }
    
    public static String getElementCDATAByTagName( Element current, String name ) {

        return getElementCDATAByTagName( current, name, null );
    }

    public static String getElementCDATAByTagName( Element current,
                                                   String name,
                                                   String namespace ) {

        Element e = getElementByTagName( current, name, namespace );

        if ( e == null )
            return null;

        Node child = e.getFirstChild();

        if ( child == null )
            return null;
        
        return child.getNodeValue();

    }
}