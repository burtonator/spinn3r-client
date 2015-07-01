# Introduction #


The Spinn3r protocol buffer was designed to run as a daemon from the command
line.  The great thing about protocol buffers is that there are multiple
production ready language bindings available in Python, Java, C, C++, etc.

For a full list of languages:

http://code.google.com/p/protobuf/wiki/ThirdPartyAddOns

Configuring the Spinn3r reference client to write to a given directory is easy:

```
mkdir -p /tmp/spinn3r

java -Xmx2200M -cp spinn3r-client-3.1.56-beta3.jar:lib/protobuf-java-2.0.3.jar:lib/jzlib-1.0.7.jar com.spinn3r.api.Main \
             --vendor=XXXX \
             --show_results=0 \
             --save=/var/spool/spinn3r
```

You then just need to write a protocol buffer client in whatever language you
would like to access Spinn3r.

# Java Reader Example #

Here's an example of one in Java.

```

public class DiskReader {

    private static int SLEEP_TIME = 1000 * 5; // five seconds

    private final String watchDir;

    public DiskReader ( String watchDir_value ) {
        watchDir = watchDir_value;
    }

    public ContentApi.Response getNext () throws ParseException, FileNotFoundException, IOException  {
        
        ContentApi.Response res = null;

        File dir = new File ( watchDir );

        if ( ! dir.isDirectory() )
            throw new ParseException 
                ( String.format( "Watch directory path is not a directory: %s", watchDir ) );

        File proto_file = null;

        for ( String name : dir.list() ) {
            File current = new File ( watchDir, name );

            if ( proto_file == null || current.lastModified() < proto_file.lastModified() )
                proto_file = current;
        }

        if ( proto_file != null ) {
            res = ContentApi.Response.parseFrom( new FileInputStream ( proto_file.getAbsoluteFile() ) );
            proto_file.delete();
        }

        return res;
    }

    public static String readContent ( ContentApi.Content content ) throws Exception {

        CompressedBLOB content_blob =
            new CompressedBLOB ( content.getData().toByteArray() );

        return content_blob.decompress();
    }

    public static void main( String[] args ) throws Exception {

        if ( args.length != 1 ) {
            System.out.printf( "Usage: DiskReader watch-dir/\n" );
            System.exit( 1 );
        }

        DiskReader reader = new DiskReader ( args[0] );

        while ( true ) {
            ContentApi.Response result = reader.getNext();

            if ( result == null ) {
                System.out.printf( "nothing files found sleeping...\n" );
                Thread.sleep( SLEEP_TIME );
            }

            else {
                System.out.printf( "" );

                for ( ContentApi.Entry entry : result.getEntryList() ) {

                    String content = readContent( entry.getPermalinkEntry().getContent() );

                    if ( content == null )
                        content = "";

                    System.out.printf( "Found entry for item length %s: %s\n",
                                       content.length(),
                                       entry.getPermalinkEntry().getCanonicalLink().getHref() );
                }
            }

        }
        
    }
}

```

# Output #

```
Found entry for item length 17514: http://www.financial24.org/bonds/nuveen-insured-dividend-advantage-municipal-fund-preferred-shares-began-trading-on-nyse/
Found entry for item length 22136: http://www.getafreelancer.com/projects/Copywriting-Articles/rewriting-project-for-khepra.html
Found entry for item length 24470: http://www.getafreelancer.com/projects/Link-Building/require-minimum-one-way-non.html
Found entry for item length 16219: http://www.financial24.org/commodities/caledonias-zim-mine-receives-local-award/
Found entry for item length 28463: http://www.getafreelancer.com/projects/SEO-SEM-Adwords/SEO-Services-for-new-Website.html
Found entry for item length 33252: http://feeds.feedblitz.com/~/2488552/t4iuq/alternet~NeoNazis-Protest-Immigration-in-Phoenix-Get-Outshouted-by-Reasonable-People-Who-Came-to-Protest-Them
Found entry for item length 27357: http://nord-pas-de-calais.covoiturage.fr/trajet/725158-bordeaux-nantes
Found entry for item length 40719: http://feeds.thejapannews.net/?rid=29533622&cat=c4f2dd8ca8c78044
Found entry for item length 28610: http://nord-pas-de-calais.covoiturage.fr/trajet/725157-rennes-pluvigner
Found entry for item length 49738: http://www.steepandcheap.com/steepcheap/index.html?cmp_id=ODAL_RSS5001&amp;mv_pc=r205&amp;cmp_sku=CDM0083
Found entry for item length 30940: http://nord-pas-de-calais.covoiturage.fr/trajet/725156-strasbourg-grenoble
Found entry for item length 118227: http://online.wsj.com/article/SB10001424052748704402404574526002158444142.html?mod=rss_whats_news_us_business
Found entry for item length 44758: http://toronto.kijiji.ca/c-buy-and-sell-health-special-needs-Tony-Littles-Gazelle-Edge-Trainer-Full-Body-Workout-W0QQAdIdZ167725523
nothing files found sleeping...

```