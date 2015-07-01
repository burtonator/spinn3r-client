# Overview #

There are a number of small (and common) design issues with about 90% of production implementations of Spinn3r.

These are implemented in our reference client and provided here to aid other implementors of our API.

# Requirements #

## No Infinite DNS Caching ##

You MUST NOT enable DNS caching for more than 5 minutes.  This will prevent us from moving servers using the API.

## HTTP Connect and Read Timeouts ##

TCP connection and read timeouts MUST be set and NOT use default values.  For example, Java uses infinity by default which could kill your application at some random point in the future.

## 'Limit' Fallback ##

If you're using a 'limit' parameter greater than 10, and you encounter an HTTP 500 error from Spinn3r. You MUST fall back to using 10, verify that this worked, and then you may return to using your larger limit parameter.

## Retries ##

You MUST attempt to retry failed HTTP requests including HTP 50x errors.  You MUST implement a minimum 30 second sleep duration between retries.

## User-Agent ##

Use a meaningful HTTP user agent.  Do not use the standard Mozilla/Firefox/IE user agent.  Including the language, version, and contact details is also advised.

# Optional #

## Content Encoding ##

All clients SHOULD support UTF-8 encodings for XML results.  The resulting data SHOULD be stored in unicode data structures.  Spinn3r regularly returns Chinese, Japanese, Thai, French, German, etc and if you are using plain ascii strings you won't be able to preserve these characters.

## Handle HTTP 302/301 Redirects ##

HTTP 301/302 handing SHOULD be supported.  We might decide to use HTTP 301/302 redirects at some point for management issues.

## Handle GZIP and Deflate Compression ##

This can speed performance in some cases.  Your HTTP client library SHOULD support compression.