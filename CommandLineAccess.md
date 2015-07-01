# Introduction #

The reference client ships with a command line version of Spinn3r which you can access easily once you download the reference client.

The [Javadoc](http://spinn3r-client.googlecode.com/svn/trunk/javadoc/com/spinn3r/api/Main.html) and [source](http://code.google.com/p/spinn3r-client/source/browse/trunk/src/java/com/spinn3r/api/Main.java) make for excellent documentation on using the API.

Running the following command:

```

java -cp spinn3r-client-3.3.07.jar com.spinn3r.api.Main

```

will print out the standard options:

(Note. Update the classpath with the correct jar filename).

```

Usage: com.spinn3r.api.Main [OPTION]

Required params:

    --vendor=VENDOR       Specify the vendor name for provisioning.

Optional params:

    --api=API             Specify the name of the API (permalink, comment, link).
                          Default: permalink

    --recover             Enable the client to recover from a previous state. Also use this 
                          this flag to have the client save it's state so it may 
                          recover in the future. Must be used with the permalink api 
                          and the --save option.

    --after=NNN           Time in millis for when we should start indexing.
                          This can also be an ISO 8601 time stamp.  

                            Example: 2007-12-23T00:00:00Z

                          Default: last 60 minutes

    --before=DATE         ISO date (or millis)  All results need to occur
                          before this date.

                            Example: 2007-12-23T00:00:00Z

                          Default: none

    --show_results=NN     Show each result returned by the API.
                             0 - show no fields
                             1 - show only the link
                             2 - show title/description
                          Default: 10

    --range=NNNN          Unix time duration (in millis) to terminate the API.
                          Default: none

    --save=DIRECTORY      Save result XML to disk in the specified directory.
                          Default: none

    --host=hostname       Custom hostname for making calls against. Dev use only.
                          Default: api.spinn3r.com

    --skip_description    When true do NOT return the RSS description from the server.  This can
                          save bandwidth for users who only need metadata.
                          Default: false

    --memory              Print current memory settings and exit.  Useful or debugging..

```

The only required option is the `--vendor` parameter.

# Examples #

## Access Spinn3r for the last day: ##

(Note. Set your own vendor code.  Acme won't work as it's just an example).

```
java -cp target/spinn3r-client-2.1r2.jar com.spinn3r.api.Main \
            --vendor=acme \
            --after=2008-01-10T00:00:00Z \
            --before=2008-01-11T00:00:00Z 
```

## Fetch full HTML for each blog post for the last day, and save content to disk ##

```
java -cp target/spinn3r-client-2.1r2.jar com.spinn3r.api.Main \
            --vendor=acme \
            --api=permalink \
            --after=2008-01-10T00:00:00Z \
            --before=2008-01-11T00:00:00Z \
            --save=/tmp/spinn3r-example
```

# Progress #

When running the command line app will give you the status of the crawl after each successful fetch.

For example:

```
-------------------------------------------
API fetch duration (including sleep, download, and parse): 0
--
API call duration:        3739
API sleep duration:       0
--
Number items returned:    10
Last request URL:         http://dev.api.spinn3r.com/rss/feed.getDelta?limit=10&vendor=acme&version=2.1.1&lang=en&tier=0:100&after=1199923234034101538
Next request URL:         http://dev.api.spinn3r.com/rss/feed.getDelta?limit=10&vendor=acme&version=2.1.1&lang=en&tier=0:100&after=1199923235034101610
Seconds behind present:   592142
```

The `Seconds behind present` time is valuable for help debugging API performance.  If this value rises, you're falling behind.  If it's falling, you're catching up.