# Overview #

The following fields are available in the API response in the [SourceAPI](SourceAPI.md).

# Fields #

| title | Title of the source |
|:------|:--------------------|
| link  | Link to the source which can be fetched via HTTP |

## Feed fields for sources ##

| feed:guid | Globally Unique ID(based on URL) |
|:----------|:---------------------------------|
| feed:resource | Unique tokenized resource URL    |
| feed:link | URL to this feed, non tokenized  |
| feed:resource\_status | HTTP status code for this resource |
| feed:channel\_link | Link for this source returned from the feed |
| feed:channel\_guid | GUID for channel link            |
| feed:channel\_title | Title for this source from feed  |
| feed:channel\_desc | Description for this source returned from the feed |
| feed:date\_found | Date this feed was found (in ISO 8601) |
| feed:etag | Last entity tag or this source   |

# Specific source fields #

| source:guid | Globally Unique ID (based on URL) |
|:------------|:----------------------------------|
| source:resource | Unique tokenized resource URL     |
| source:link | URL to this weblog, non tokenized |
| source:date\_found | Date this source was found        |
| source:tier | API tier for this source (ordered rank / 1000) |
| source:indegree | Raw and current inbound link count. |
| source:lang | ISO language code for this source |
| source:spam\_probability | Probability that this source is spam. |