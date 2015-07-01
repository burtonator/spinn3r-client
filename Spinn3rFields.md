# Fields on Items in the Spinn3r API #

This is a legacy page and explains the fields used by our older XML protocol.


| title | Title of the post. |
|:------|:-------------------|
| link  | URL to the post that can be specified in a browser. |
| guid  | RSS 2.0 GUID if available. "guid stands for globally unique identifier. It's a string that uniquely identifies the item. When present, an aggregator may choose to use this string to determine if an item is new." |
| description | HTML for a post or the RSS content for a post.  Possibly entity encode HTML.  Might be plain text.|
| pubDate | The date our crawler saw the post. |
| dc:source | The source URL for the post. |
| dc:lang | Language of the blog as decided by our language detection algorithms |
| source:tier | API tier of a specfic source.  Tiers may be specified in the API to restrict results.  See [Spinn3r Documentation](http://spinn3r.com/documentation) for more details |
| source:title | Title for the current source |
| source:description | Human readable description about the source |
| atom:author | Author information for a user.  See the below fields which specify additional author information |
| atom:name | Nested under author.  Name of author |
| atom:email | Email for author if known. |
| atom:link | Link to the author if known. |
| feed:url | URL to the RSS feed for the post which published this item |
| source:indegree | raw number if inbound links known for this source |
| source:iranking | influence ranking for this source.  Computed by past behavior on how memes are promoted from this blog |
| atom:published | The time this blog post as published by the author |
| post:date\_found | The time spinn3r found this post by our robot.  Identical to pubDate but in ISO 8601 format |
| source:publisher\_type | The type of publisher for this source.  Current values include: WEBLOG, MAINSTREAM\_NEWS, CLASSIFIED, FORUM, REVIEW, MICROBLOG |
| post:content\_extract | Represents the chrome-free content extract or 'meat' of a given HTML post. |
| feed:hashcode | Specific hashcode for a feed within Spinn3r |
| source:hashcode | Specific hashcode for a source within Spinn3r |