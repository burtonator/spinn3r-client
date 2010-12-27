/*
 * Copyright 2009 Tailrank, Inc (Spinn3r).
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.google.inject.internal.ImmutableList;
import com.spinn3r.api.Config.Format;
import com.spinn3r.api.util.Base64;
import com.spinn3r.api.util.MD5;

/**
 * <a href="http://spinn3r.com">Spinn3r</a> command line debug client for
 * testing the API. Also shows example usage.
 * 
 * This class will fetch the current API results as of INTERVAL minutes and then
 * keep fetching until it's up to date.
 * 
 * @author Kevin Burton
 * 
 */
public class Main<T extends BaseResult> {

    /**
     * When true, do not parse the document, just save it to disk.
     */
    public static boolean ENABLE_FAST_SAVE = true;

    public static boolean ENABLE_NO_PARSE_ON_SAVE = true;

    /**
     * Minimum amount of memory required to run the client if the user chooses
     * to parse the data in memory.
     */
    public static long PARSE_REQUIRED_MEMORY = 2000L * 1000L * 1000L;

    /**
     * Minimum amount of memory required to run the client if the user chooses
     * to save the data to disk.
     */
    public static long SAVE_REQUIRED_MEMORY = 500L * 1000L * 1000L;

    /**
     * Determines how long we should wait between retries.
     */
    public static long RETRY_INTERVAL = 1L * 1000L;

    /**
     * Default for show_results
     */
    public static int DEFAULT_SHOW_RESULTS = 10;

    /**
     * Maximum amount of time we should index the API.
     */
    public static int DEFAULT_RANGE = -1;

    /**
     * How far behind should be start by default?
     */
    public static long INTERVAL = Config.millisecondsToTimestamp(20L * 60L * 1000L);

    /**
     * The date of the last item we found.
     */
    private Date last = null;

    /**
     * Timings for each fetch.
     */
    private long fetch_before = -1;
    private long fetch_after = -1;

    /**
     * Results from the last call.
     */
    private List<? extends BaseResult> results = null;

    /**
     * When true, filter results for each pass.
     */
    private static int show_results = DEFAULT_SHOW_RESULTS;

    /**
     * When true, filter results for each pass.
     */
    private static boolean show_progress = true;

    /**
     * When we have a value. Only print results that match a certain pattern.
     */
    private static String filter = null;

    /**
     * When to end processing the API.
     */
    private static long range = DEFAULT_RANGE;
    
    /**
     * The logging level
     */
    private static Level logLevel = Level.SEVERE;
    
    /**
     * Debug log file path
     */
    private static String debugLogFilePath;

    /**
     * Do we restore from the log of a previous run?
     */
    private static boolean restore = false;

    /**
     * Only return results before this date.
     */
    private static long before = -1;

    private static String save = null;

    private static String save_method = "flat";

    /**
     * Include API timings.
     */
    private static boolean timing = true;

    /**
     * When in dump mode, print out objects in raw mode.
     */
    private static boolean dump = false;

    /**
     * When in dump field mode, print fields, but not content.
     */
    private static boolean dumpFields = false;

    private static boolean csvNeedsHeaders = true;

    private static boolean csv = false;

    private static boolean saveCompressed = true;

    /**
     * Sample performance times...
     */
    private static PerformanceSampler sampler1 = new PerformanceSampler(
            1L * 60L * 1000L);
    private static PerformanceSampler sampler5 = new PerformanceSampler(
            5L * 60L * 1000L);
    private static PerformanceSampler sampler15 = new PerformanceSampler(
            15L * 60L * 1000L);

    private static TransactionHistoryManager logManager;

    public Main() {
    }

    /**
     * Parse the 'after' param from a request URL as a timestamp.
     * 
     */
    long parseAfterTimestampFromRequestURL(String request) {

        if (request != null) {

            Pattern p = Pattern.compile("after=([0-9]+)");
            Matcher m = p.matcher(request);

            if (m.find()) {

                long ts = Long.parseLong(m.group(1));

                ts = (ts / 1000000000) * 1000;

                return ts;

            }

        }

        return -1;

    }

    /**
     * Process results, handling them as necessary.
     */
    void process(List<? extends BaseResult> results, BaseClient<?> client)
            throws Exception {

        if (ENABLE_FAST_SAVE && save != null) {

            // NOTE: this is a bit of a hack to obtain a time sampling from the
            // Spinn3r API as it executes.

            String next_request_url = client.getNextRequestURL();

            long ts = parseAfterTimestampFromRequestURL(next_request_url);

            if (ts != -1) {

                last = new Date(ts);

                sampler1.sample(last);
                sampler5.sample(last);
                sampler15.sample(last);

            }

            // right now the client doesn't support saving to disk.
            return;

        }

        for (BaseResult result : results) {

            // update the state internally so we have a copy of the last item
            // found.
            last = result.getPubDate();

            // no pubdate

            sampler1.sample(last);
            sampler5.sample(last);
            sampler15.sample(last);

            // System.out.printf( "last: %s\n", last.getTime() );
            // System.out.printf( "before: %s\n", before );

            if (before > 0 && last.getTime() >= before) {
                break;
            }

            if (result instanceof BaseItem) {

                BaseItem item = (BaseItem) result;

                if (filter != null) {

                    Pattern p = Pattern.compile(filter);
                    Matcher m = p.matcher(item.getGuid());

                    if (!m.find())
                        continue;

                }

                if (csv) {

                    if (csvNeedsHeaders) {

                        System.out.printf("'%s',", toCSV("link"));
                        System.out.printf("'%s',", toCSV("guid"));
                        System.out.printf("'%s',", toCSV("feed"));
                        System.out.printf("'%s',", toCSV("source"));
                        System.out.printf("'%s',", toCSV("lang"));
                        System.out.printf("'%s'", toCSV("published"));
                        System.out.println();

                        csvNeedsHeaders = false;
                    }

                    String published = "";

                    if (item.getPublished() != null)
                        published = ISO8601DateParser.toString(item
                                .getPublished());

                    System.out.printf("'%s',", toCSV(item.getLink()));
                    System.out.printf("'%s',", toCSV(item.getGuid()));
                    System.out.printf("'%s',", toCSV(item.getFeedURL()));
                    System.out.printf("'%s',", toCSV(item.getSource()));
                    System.out.printf("'%s',", toCSV(item.getLang()));
                    System.out.printf("'%s'", toCSV(published));
                    System.out.println();
                    continue;
                }

                if (dump) {
                    System.out.printf("%s\n", item.dump());
                    continue;
                }

                if (dumpFields) {
                    System.out.printf("%s\n", item.dump(true));
                    continue;
                }

                if (show_results >= 1) {

                    System.out.println("----");
                    System.out.println("link:                   "
                            + item.getLink());
                    System.out.println("guid:                   "
                            + item.getGuid());
                    System.out.println("feed URL:               "
                            + item.getFeedURL());

                }

                if (show_results >= 2) {

                    String pubDate = "";
                    String published = "";

                    if (item.getPubDate() != null)
                        pubDate = ISO8601DateParser.toString(item.getPubDate());

                    if (item.getPublished() != null)
                        published = ISO8601DateParser.toString(item
                                .getPublished());

                    System.out.println("title:                  "
                            + item.getTitle());
                    System.out.println("post title:             "
                            + item.getPostTitle());
                    System.out.println("source:                 "
                            + item.getSource());
                    System.out.println("pubDate:                " + pubDate);
                    System.out.println("published:              " + published);

                    System.out.println("source title:           "
                            + item.getWeblogTitle());
                    System.out.println("source tier:            "
                            + item.getWeblogTier());
                    System.out.println("source publisher type:  "
                            + item.getWeblogPublisherType());
                    System.out.println("source indegree:        "
                            + item.getWeblogIndegree());
                    System.out.println("source resource:        "
                            + item.getWeblogResource());

                    System.out.println("author name:            "
                            + item.getAuthorName());
                    System.out.println("author email:           "
                            + item.getAuthorEmail());
                    System.out.println("author link:            "
                            + item.getAuthorLink());

                    System.out.println("lang:                   "
                            + item.getLang());
                    System.out.println("tags:                   "
                            + item.getTags());

                    String desc = item.getDescription();

                    boolean hasDescription = false;

                    if (desc != null && !desc.trim().equals("")) {
                        hasDescription = true;
                    }

                    System.out.println("has_description:        "
                            + hasDescription);

                    if (item instanceof CommentItem) {

                        CommentItem comment = (CommentItem) item;

                        System.out.printf("comment permalink:       %s\n",
                                comment.getCommentPermalink());
                        System.out.printf("comment permalink title: %s\n",
                                comment.getCommentPermalinkTitle());
                        System.out.printf("comment permalink hash:  %s\n",
                                comment.getCommentPermalinkHashcode());

                    }

                }

                if (show_results >= 3) {

                    System.out.println("description: ");
                    System.out.println("-");
                    System.out.println(item.getDescription());
                    System.out.println("-");

                    System.out.println("content extract: ");
                    System.out.println("-");
                    System.out.println(item.getContentExtract());
                    System.out.println("-");

                    System.out.println("post body: ");
                    System.out.println("-");
                    System.out.println(item.getPostBody());
                    System.out.println("-");

                }

            } else if (result instanceof LinkItem) {

                LinkItem link = (LinkItem) result;

                System.out.println("----");
                System.out.printf("link XML:      %s\n", link.getLinkXml());
                System.out.printf("link title:    %s\n", link.getLinkTitle());

            }

        }

    }

    public String toCSV(String data) {

        if (data == null)
            return "";

        return data;

    }

    /**
     * Print status of the API calls.
     */
    void progress(BaseClient<?> client) {

        if (csv)
            return;

        System.out.println("--");

        System.out.println("Last request URL:         "
                + client.getLastRequestURL());
        System.out.println("Next request URL:         "
                + client.getNextRequestURL());

        System.out.println("-------------------------------------------");

        long fetch_duration = fetch_after - fetch_before;

        if (timing) {

            System.out
                    .println("API fetch duration (including sleep, download, and parse): "
                            + fetch_duration);

            System.out.println("--");

            System.out.println("API call duration:        "
                    + client.getCallDuration());
            System.out.println("API parse duration:       "
                    + client.getParseDuration());
            System.out.println("API sleep duration:       "
                    + client.getSleepDuration());

            System.out.print("API performance:          ");
            System.out.print(sampler1.getPerformance());
            System.out.print("    ");
            System.out.print(sampler5.getPerformance());
            System.out.print("    ");
            System.out.print(sampler15.getPerformance());

            System.out.println();

            /*
             * 
             * TODO: the client SHOULD compute effective bandwidth.
             * 
             * This won't really work because gzip number are hidden and I can't
             * use the raw number of characters in the string because this might
             * be a multi-byte UTC sequence.
             * 
             * System.out.print( "Effective bandwidth:    " ); System.out.print(
             * client.bs1.getBandwidthAsHumanMetric() ); System.out.print(
             * "    " ); System.out.print(
             * client.bs5.getBandwidthAsHumanMetric() ); System.out.print(
             * "    " ); System.out.print(
             * client.bs15.getBandwidthAsHumanMetric() );
             */

            System.out.println();

            if (last != null) {

                long diff = System.currentTimeMillis() - last.getTime();

                System.out
                        .println("Seconds behind present:   " + (diff / 1000));

            }

        }

        if (ENABLE_FAST_SAVE && save != null) {
            // right now the client doesn't support saving to disk.
            return;
        }

        System.out.println("--");

        System.out.println("Number items returned:    " + results.size());

    }

    public void exec(BaseClient<T> client, Config<T> config) throws Exception {

        if (ENABLE_NO_PARSE_ON_SAVE && save != null) {
            config.setDisableParse(true);
        }

        client.setConfig(config);

        while (true) {
            // BUG: this restart all the way from the first request URL each
            // time!!!!!
            try {

                results = doFetch(client);

                System.out.println("Found N results: " + results.size());

                process(results, client);

                progress(client);


                if (range > 0
                        && last.getTime() > client.getConfig().getAfter()
                                .getTime()
                                + range)
                    break;

                if (before > 0 && Config.millisecondsToTimestamp(last.getTime()) >= before) {
                    break;
                }

            } catch (Exception e) {

                System.out.println("Caught exception while processing API:  "
                        + client.getNextRequestURL());
                System.out.println(e.getMessage());
                System.out.println("Retrying in " + RETRY_INTERVAL + "ms");

                e.printStackTrace();

                Thread.sleep(RETRY_INTERVAL);

            }

        }

    }

    /**
     * Perform a fetch of the next API call.
     */
    private List<T> doFetch(BaseClient<T> client) throws Exception {

        // fetch the most recent results. This will block if necessary.

        fetch_before = System.currentTimeMillis();

        client.fetch();

        Config<T> config = client.getConfig();

        fetch_after = System.currentTimeMillis();

        String url = config.getNextRequestURL();

        List<T> results = client.getResults();

        if (save != null) {

            // save the results to disk if necessary.

            File root = new File(save);
            root.mkdirs();

            long now = System.currentTimeMillis();

            File file = null;

            // TODO: use .gz if the XML is compressed from the server directly.

            String extension = "xml";

            // do not use .xml if the user is using protobuffer encoding
            if (config.getFormat() == Format.PROTOBUF
                    || config.getFormat() == Format.PROTOSTREAM)
                extension = "protobuf";

            if ("hierarchical".equals(save_method)) {

                long ts = parseAfterTimestampFromRequestURL(client
                        .getLastRequestURL());

                TimeZone tz = TimeZone.getTimeZone("UTC");
                Calendar c = Calendar.getInstance(tz);
                c.setTime(new Date(ts));

                String path = String.format("%s/%s/%02d/%02d/%s.%s", config
                        .getApi(), c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH) + 1,
                        c.get(Calendar.DAY_OF_MONTH), ts, extension);

                file = new File(root, path);

                new File(file.getParent()).mkdirs();

            } else {

                String path = now + "." + extension;

                String lastRequestURL = client.getLastRequestURL();

                lastRequestURL = lastRequestURL.substring(lastRequestURL
                        .indexOf("/", "http://".length()), lastRequestURL
                        .length());

                path = Base64.encodeFilesafe(MD5.encode(lastRequestURL));
                path += "." + extension;
                path = path.replaceAll("-", "=");

                file = new File(root, path);

            }

            if (client.getIsCompressed() && saveCompressed) {
                file = new File(file.getPath() + ".gz");
            }

            System.out.printf("Writing file to disk: %s\n", file.getPath());

            // swap in the new file, don't expose it until it's fully written.
            file = new File(file.getPath() + ".tmp");

            // getInputStream takes a flag indicating if we want compressed
            // data or not. So if want to save compressed don't decompress
            InputStream is = client.getInputStream(!saveCompressed);

            FileOutputStream os = new FileOutputStream(file);

            byte[] data = new byte[2048];

            int readCount;

            try {
                while( ( readCount = is.read( data )) >= 0 ) {
                    os.write( data, 0, readCount );
                }
            } finally {
                IOUtils.closeQuietly(is);
                IOUtils.closeQuietly(os);
            }

            is.close();
            os.close();

            // now perform the final rename.
            File dest = new File(file.getPath().replaceAll("\\.tmp$", ""));

            if (dest.exists()) {
                dest.delete();
            }

            file.renameTo(dest);

            /*
             * Log the next url that we're going to do. If it flops, we can restart.
             */
            logManager.log(client.getNextRequestURL());

        }

        // get the results found from the last fetch.
        return results;

    }

    private static String getOpt(String v) {
        return getOpt(v, null);
    }

    private static String getOpt(String v, String _default) {

        int start = v.indexOf("=");
        if (start == -1)
            return _default;

        ++start;

        return v.substring(start, v.length());

    }

    private static long getOptAsTimeInMillis(String v) {

        String opt = getOpt(v);
        if (opt == null)
            return -1;

        try {
            return Config.millisecondsToTimestamp(Long.parseLong(opt));
        } catch (Throwable t) {
            return Config.millisecondsToTimestamp(ISO8601DateParser.parse(opt).getTime());
        }

    }

    private static void syntax() {

        System.out.println("Usage: " + Main.class.getName() + " [OPTION]");
        System.out.println("");
        System.out.println("Required params:");
        System.out.println();
        System.out
                .println("    --vendor=VENDOR       Specify the vendor name for provisioning.");
        System.out.println();
        System.out.println("Optional params:");
        System.out.println();
        System.out
                .println("    --api=API             Specify the name of the API (feed, permalink, comment, link).");
        System.out.println("                          Default: feed");
        System.out.println();
        System.out
                .println("    --recover             Enable the client to recover from a previous state. Also use this" +
                		"                           this flag to have the client save it's state so it may" +
                		"                           recover in the future. Must be used with the permalink api" +
                		"                           and the --save option.");
        System.out.println();
        System.out
                .println("    --after=NNN           Time in millis for when we should start indexing.");
        System.out
                .println("                          This can also be an ISO 8601 time stamp.  ");
        System.out.println();
        System.out
                .println("                            Example: 2007-12-23T00:00:00Z");
        System.out.println();
        System.out
                .println("                          Default: last 60 minutes");
        System.out.println();
        System.out
                .println("    --before=DATE         ISO date (or millis)  All results need to occur");
        System.out.println("                          before this date.");
        System.out.println();
        System.out
                .println("                            Example: 2007-12-23T00:00:00Z");
        System.out.println();
        System.out.println("                          Default: none");
        System.out.println();
        System.out
                .println("    --show_results=NN     Show each result returned by the API.");
        System.out.println("                             0 - show no fields");
        System.out
                .println("                             1 - show only the link");
        System.out
                .println("                             2 - show title/description");
        System.out.println("                          Default: 10");
        System.out.println();
        System.out
                .println("    --filter=http://      URL filter.  Only print URLs that match the regex.");
        System.out.println("                          Default: none");
        System.out.println();
        System.out
                .println("    --range=NNNN          Unix time duration (in millis) to terminate the API.");
        System.out.println("                          Default: none");
        System.out.println();
        System.out
                .println("    --limit=xx            Number of items to return per iteration.");
        System.out
                .println("                          Default: 10 for permalink, 100 for feed");
        System.out.println();
        System.out
                .println("    --save=DIRECTORY      Save result XML to disk in the specified directory.");
        System.out.println("                          Default: none");
        System.out.println();
        System.out
                .println("    --save_method=        If 'hierarchical' we use a year/month/day hierarchy to save content.");
        System.out.println("                          Default: flat");
        System.out.println();
        System.out
                .println("    --host=hostname       Custom hostname for making calls against. Dev use only.");
        System.out
                .println("                          Default: api.spinn3r.com");
        System.out.println();
        System.out
                .println("    --skip_description    When true do NOT return the RSS description from the server.  This can");
        System.out
                .println("                          save bandwidth for users who only need metadata.");
        System.out.println("                          Default: false");
        System.out.println();
        System.out
                .println("    --enable3             Enable Spinn3r 3.0 extensions.");
        System.out.println();
        System.out
                .println("    --memory              Print current memory settings and exit.  Useful or debugging..");
        System.out.println();

        System.out
                .println("    --use_protobuf=true   Enable protocol buffer support for permalink client (performance).");
        System.out.println();

        // System.out.println(
        // "    --spam_probability=NN Set the lower bound for spam probability filtering.  Default(0.0)"
        // );
        System.out.println();

    }

    public static void main(String[] args) throws Exception {

        // NOTE this could be cleaned up to pass the values into the config
        // object directly.

        // parse out propeties.

        String api = null;

        for (int i = 0; i < args.length; ++i) {
            String v = args[i];

            if (v.startsWith("--api")) {
                api = getOpt(v);
            }

        }

        if (api == null)
            api = "permalink";

        // First. Determine which API you'd like to use.

        long after = -1;
        Format format = Format.RSS;
        int limit = -1;
        String vendor = null;
        String remoteFilter = null;
        Long sleep_duration = null;
        boolean skip_description = false;
        String host = "api.spinn3r.com";

        for (int i = 0; i < args.length; ++i) {

            String v = args[i];

            if (v.startsWith("--vendor")) {
                vendor = getOpt(v);
                continue;
            }

            if (v.startsWith("--filter")) {
                filter = getOpt(v);
                continue;
            }

            if (v.startsWith("--remote-filter")) {
                remoteFilter = getOpt(v);
                continue;
            }

            if (v.startsWith("--show_results")) {
                show_results = Integer.parseInt(getOpt(v));
                continue;
            }
            
            /*
             * The code for the --afterTimestamp must come
             * before the code for --after because --afterTimestamp
             * also matches startsWith("after");
             */
            if (v.startsWith("--afterTimestamp")) {
            	after =  Long.parseLong(getOpt(v));
            	continue;
            }

            if (v.startsWith("--after")) {
                after = getOptAsTimeInMillis(v);
                continue;
            }
            
            /*
             * The code for the --beforeTimestamp must come
             * before the code for --before because --beforeTimestamp
             * also matches startsWith("before");
             */
            if (v.startsWith("--beforeTimestamp")) {
            	before = Long.parseLong(getOpt(v));
            	continue;
            }

            if (v.startsWith("--before")) {
                before = getOptAsTimeInMillis(v);
                continue;
            }

            if (v.startsWith("--range")) {
                range = Long.parseLong(getOpt(v));
                continue;
            }

            if (v.startsWith("--recover")) {
                restore = true;
                continue;
            }

            if (v.startsWith("--limit")) {
                limit = Integer.parseInt(getOpt(v));
                continue;
            }

            if (v.startsWith("--sleep_duration")) {
                sleep_duration = Long.parseLong(getOpt(v));
                continue;
            }

            if (v.startsWith("--save=")) {
                save = getOpt(v);
                continue;
            }

            if (v.startsWith("--save_method=")) {
                save_method = getOpt(v);
                continue;
            }

            if (v.startsWith("--skip_description=")) {
                skip_description = Boolean.parseBoolean(getOpt(v));
                continue;
            }

            if (v.startsWith("--save_compressed=")) {
                saveCompressed = Boolean.parseBoolean(getOpt(v));
                continue;
            }

            if (v.startsWith("--timing")) {
                timing = "true".equals(getOpt(v));
                continue;
            }
            
            if (v.startsWith("--debug")) {
                logLevel = Level.FINE;
                debugLogFilePath = getOpt(v);
                continue;
            }

            /*
             * if ( v.startsWith( "--spam_probability" ) ) {
             * config.setSpamProbability( Double.parseDouble( getOpt( v ) ) );
             * continue; }
             */

            if (v.startsWith("--use_protobuf")) {
                format = Format.PROTOBUF;
                continue;
            }

            if (v.startsWith("--use_protostream")) {
                if (Boolean.parseBoolean(getOpt(v)))
                    format = Format.PROTOSTREAM;
                continue;
            }

            if (v.startsWith("--dump_fields=")) {
                dumpFields = Boolean.parseBoolean(getOpt(v));
                continue;
            }

            if (v.startsWith("--dump=")) {
                dump = Boolean.parseBoolean(getOpt(v));
                continue;
            }

            if (v.startsWith("--csv=")) {
                csv = Boolean.parseBoolean(getOpt(v));
                continue;
            }

            if (v.startsWith("--memory")) {

                System.out.printf("max memory: %s\n", Runtime.getRuntime()
                        .maxMemory());
                System.exit(0);
            }

            if (v.startsWith("--host")) {
                host = getOpt(v);
                continue;
            }

            if (v.startsWith("--enable3")) {
                // is now default
                continue;
            }

            if (v.startsWith("com.spinn3r"))
                continue;

            if (v.startsWith("--api"))
                continue;

            // That's an unknown command line option. Exit.
            System.err.printf("Unknown command line option: %s\n", v);
            syntax();
            System.exit(1);

        }
        
        /*
         * Set the log level
         */
        Logger anonymousLogger = Logger.getAnonymousLogger();
        anonymousLogger.setLevel(logLevel);
        if(debugLogFilePath != null) {
            anonymousLogger.addHandler(new FileHandler(debugLogFilePath));
        }
        anonymousLogger.info("Spinn3r client started");
        Logger.getLogger("fun").fine("Test message");

        Factory factory = new Factory();
        String restoreURL = null;

        if (save != null && restore) {
            File savedir = new File(save);
            Collection<File> logFiles = getLogFiles(savedir);
            PermalinkLogReaderAdapter adapter = getRestoreURLS(logFiles);
            
            restoreURL = adapter.getLastUrl();
            long ctr = adapter.getLastCtr();
            
            for (File file : logFiles) {
                file.delete();
            }
         
            factory.enableLogging(savedir, 1000000);
            if(restoreURL != null)
            {
                factory.enableRestart(ctr, restoreURL);
            }
            logManager = factory.getInjector().getInstance(TransactionHistoryManager.class);
        } else {
            logManager = factory.getInjector().getInstance(TransactionHistoryManager.class);
        }

        Config<? extends BaseResult> config = null;
        BaseClient<? extends BaseResult> client = null;

        if (api.startsWith("feed")) {
            config = new FeedConfig();
            client = new FeedClient();
        } else if (api.startsWith("crawl")) {
            config = new CrawlConfig();
            client = new CrawlClient();
        } else if (api.startsWith("comment")) {
            config = new CommentConfig();
            client = new CommentClient();
        } else if (api.startsWith("link")) {
            config = new LinkConfig();
            client = new LinkClient();
        } else {
            config = new PermalinkConfig();
            client = new PermalinkClient(restoreURL != null ? ImmutableList.of(restoreURL) : Collections.<String>emptyList());
        }

        config.setCommandLine(StringUtils.join(args, " "));
        
        config.setApi(api);
        if (limit != -1)
            config.setLimit(limit);
        config.setFormat(format);
        config.setVendor(vendor);
        config.setHost(host);
        config.setFilter(remoteFilter);
        config.setSkipDescription(skip_description);
        if (sleep_duration != null)
            client.setSleepDuration(sleep_duration);

        // assert that we have all required options.

        if (config.getVendor() == null) {
            syntax();
            System.exit(1);
        }

        long maxMemory = Runtime.getRuntime().maxMemory();
        long requiredMemory = (save == null) ? PARSE_REQUIRED_MEMORY
                : SAVE_REQUIRED_MEMORY;

        if (maxMemory < requiredMemory) {

            System.out
                    .printf("ERROR: Reference client requires at least 2GB of memory.\n");
            System.out.printf("\n");
            System.out.printf("Now running with: %s vs %s required\n",
                    maxMemory, requiredMemory);
            System.out.printf("\n");
            System.out.printf(
                    "Add -Xmx%dM to your command line and run again.\n",
                    requiredMemory / (1024 * 1024));

            System.exit(1);

        }

        // use defaults

        System.out.println("Using vendor: " + config.getVendor());
        System.out.println("Using api:    " + api);

        if (after > -1)
            System.out.printf("After: %s (%s)\n", ISO8601DateParser
                    .toString(Config.timestampToDate(after)), after);

        if (before > -1)
            System.out.printf("Before: %s (%s)\n", ISO8601DateParser
                    .toString(Config.timestampToDate(before)), before);

        System.out.println("Saving results to disk: " + save);

        // Fetch for the last 5 minutes and then try to get up to date. In
        // production you'd want to call setFirstRequestURL from the
        // getLastRequestURL returned from fetch() below

        if (after == -1) {
            after = Config.millisecondsToTimestamp(System.currentTimeMillis());
            after = after - INTERVAL;
        }

        config.setAfterTimestamp(after);

        new Main().exec(client, config);

    }

    private static PermalinkLogReaderAdapter getRestoreURLS(Collection<? extends File> files)
            throws Exception {
        PermalinkLogReaderAdapter adapter = new PermalinkLogReaderAdapter();
        LogReader logReader = new LogReader();
        logReader.setIgnoreExceptions(true);

        for (File file : files)
            logReader.read(file, adapter);

        return adapter;
    }

    @SuppressWarnings("unchecked")
    private static Collection<File> getLogFiles(File saveDirectory) {
        return FileUtils.listFiles(saveDirectory, new String[] { "log" }, true);
    }

}
