# Migration to Protocol Buffers #

We will probably be migrating the Spinn3r API to use protocol buffers ad the performance numbers here are really amazing.

This document may be updated in the future to reflect this change.

# Overview #

Spinn3r uses REST (XML over HTTP) for all of our API implementations. This is used for maximum ease of use when designing new clients. Anyone familiar with HTTP and XML should be able to write software that uses Spinn3r.

# Reference Implementations #

We have a [client reference implementation](http://code.google.com/p/spinn3r-client/) available in Java.

The underlying implementation is only 1500 lines of code which makes it highly portable to other languages.

The reference implementation is Open Source and distributed under the [Apache Public License 2.0](http://www.apache.org/licenses/LICENSE-2.0).

# Available Interfaces #

## api.spinn3r.com/rss/permalink.getDelta ##

Returns full HTML content for all sources in our index. This also includes our [content extract support](http://blog.spinn3r.com/2008/01/announcing-spin.html) which allows our users to skip indexing chrome within HTML posts.

Each item is the full HTML of a permalink published by a site in our index.

Each time we visit a site we fingerprint URLs on the front page and only crawl new URLs. We also crawl every URL found via RSS and Atom feeds.

# Output Format and Encoding #

All API calls return [UTF-8](http://en.wikipedia.org/wiki/UTF-8) encoded [RSS 2.0](http://en.wikipedia.org/wiki/RSS_(file_format)) feeds with entity encoded HTML included in the description element.

This format was used to provide maximum flexibility and compatibility due to the the sheer number of RSS 2.0 parsers available.

# Core Parameters #

| **Name** | **Required** | **Description** |
|:---------|:-------------|:----------------|
| vendor   | yes          | Name of the company (or contact info) using the API. |
| after    | no           | Date that we should return feed items after. Format is seconds since epoch (January 1, 1970) or an ISO 8601 data string. Default is Jan 1, 1970. |
| offset   | no           | Return results from a given offset. Should be an interval of 100. Default is zero. |
| version  | no           | See below for list of all available versions. |
| limit    | no           | By default the API returns 100 items at a time. With this parameter the results can be reduced. Normally the default is fine but if your application has memory constraints you can limit the number of results returned. |

When successful the resulting HTTP status code will be 200.

Resulting XML format is RSS.  Dublin core namespaces are used for ISO 8601 timestamps on the items.

# Duplicate Results #

At any time it's possible for the API to issue the same document twice at different time intervals.

This is necessary because Spinn3r might receive updated metainfo (author, title, etc), typo corrections by the author, or other changes.

Specifically, this method will be used in later versions of Spinn3r to ship additional features including recrawling content to detect updates of the page.

# Pagination #

Spinn3r API results are ordered by chronology. If you need more content you'll need to fetch another batch of results.

The current required way of fetching results is to use the `next_request_url` value in the `channel` output of your last API response. This will add two additional parameters to your request URL which will allow you to fetch the next page of results from Spinn3r.

# Date and Time Formats #

Spinn3r uses three main date values for each post.

pubDate
> RSS 2.0 `pubDate` element which is in RFC 822 format.  This is the time our robots found this post.
post:date\_found
> The time our robot found this post but in ISO 8601 date time format.
atom:published
> The time this item was published in the source feed. Identical semantics to atom:published.

# Weblog Ranking #

We currently expose two ranking values to clients:

## weblog:indegree ##

The raw number of inbound links to the blog since this blog has been part of our index.

## weblog:iranking ##

The 'influence ranking' of this weblog.  This is measured by how successful it is at creating and participating in memes on the Internet. Think of it as a function of how successful it is at appearing on [Tailrank](http://tailrank.com).

# Returning Top Weblogs #

This feature separates blogs into 'tiers' which are the raw rank value divided by 1000.

Ranking for blogs is computed based on the influence rank which drives our memetracker algorithms and the raw inbound link count.

Please avoid using too many concurrent fetches as each API query does a full scan of our database from the given time stamp forward.

## Available Langage Codes ##

All our language codes are [ISO 639 two letter lang codes](http://ftp.ics.uci.edu/pub/ietf/http/related/iso639.txt). We use the special lang code of `U` when we are unable to determine the language from the underlying text - usually because we don't have enough data.

Chinese language support can be difficult to determine because of the fact that Chinese, Japanese, and Korean text have overlapping character codes. If the document is ambiguous we return `cjk` to denote that it's at least one of these langauges. If the classifier is certain it will return `ja` for Japanese, `ko` for Korean or `zh` for Chinese.

### Currently Supported Language Codes: ###

| U | unknown |
|:--|:--------|
| ar | arabic  |
| bg | bulgarian |
| cjk | chinese, japanese, korean |
| cs | czech   |
| da | danish  |
| de | german  |
| el | greek   |
| en | english |
| es | spanish |
| et | estonian |
| fa | farsi   |
| fi | finnish |
| fr | french  |
| fy | frisian |
| ga | irish   |
| he | hebrew  |
| hi | hindi   |
| hr | croatian |
| hu | hungarian |
| io | ido     |
| is | icelandic |
| it | italian |
| ja | japanese |
| ko | korean  |
| nl | dutch   |
| no | norwegian |
| pl | polish  |
| pt | portuguese |
| ro | romanian |
| ru | russian |
| sl | slovenian |
| sv | swedish |
| th | thai    |
| uk | ukranian |
| vi | vietnamese |
| zh | chinese |


# Versions #

The API we use is versioned so that we can extend it in the future without breaking existing users.

When implementing the API for the first time always use the greatest version which exposes the most features to your application.

### 0.2 ###

Adds a Dublin Core dc:source URL within entries which includes the root URL that publishes the current item.

### 0.3 ###

Includes the title and description of the weblog for the current item.

### 0.4 ###

Includes a two letter ISO language code representing the content. If our content classification engine was unsuccessful the value of 'U' (or unknown) is given.

Includes a new 'tier' value which shows the specific tier that the post's weblog is a member of.

Adds a new value named `next_request_url` which enables faster pagination of the result set.

### 0.5 ###

Includes author metadata including name, email, and link.  This data is returned directly from content provided by RSS and Atom feeds.

If the source feed has Technorati tags, Dublin core categories or RSS 2.0 categories they are included here in the result set as RSS 2.0 categories.

For example:

```
<category>Live.com</category>
<category>Microsoft</category>
<category>Search</category>
```

Would represent tags for "Live.com", "Microsoft", and "Search".

### 2.1 ###

Language encoding now possible in the permalink.getDelta API including a new content\_extract field.  See the [official release announcement](http://blog.spinn3r.com/2008/01/announcing-spin.html) for more details.

### 2.1.1 ###

Included a new parameter named `skip_description` which will skip the RSS description element if necessary.

A new element is included in the `permalink.getDelta` API call which specifies a `weblog:publisher_type`. This can be set to the symbols WEBLOG or MAINSTREAM\_NEWS.

This is intended for use by customers who wish to differentiate between the types of news sources in our index.

### 2.1.2 ###

Adds a number of new features including:
  * Support for API tiers in the `permalink.getDelta` interface. This is available in the API response but net yet available as a query parameter.
  * Support for the original creation date of items found in the original RSS or Atom feed. This is now provided with `atom:published`. The original crawl data is also preserved and included as `post:date_found`. Both of these values are ISO 8601 timestamps. This is only available via the `feed.getDelta` method.
  * Extended support for the title and description of a weblog in API responses. This is now supported by more weblogs and included in the response of both the `feed.getDelta` and `permalink.getDelta` methods.

# Examples Requests #

## Fetching HTML content from our mainstream media crawler: ##

`http://api.spinn3r.com/rss/permalink.getDelta?vendor=acme&version=0.5&limit=10&after=2006-01-01T12:00:00Z`