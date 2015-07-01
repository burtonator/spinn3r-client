# Fields in the Protostream API #

## Source ##

Container for all references for the source that published this item.  A source would be a URL like http://techcrunch.com

|source.link.href|The URL to the source that published this item.|
|:---------------|:----------------------------------------------|
|source.link.resource| Tokenized form of source.link.href            |
|source.title    | The title for this source                     |
|source.description | Description of this source                    |
|source.hashcode | Globally unique hashcode representing this source|
|source.lang.code | Mathematically classified language for this source |
|source.lang.probability | Future support for probability of this language |
|source.date\_found | The date this source was found and added to our index|
|source.resource\_status | The HTTP response code of the last fetch of this source |
|source.last\_posted | The last time this source has posted if the feed supports publication times|
|source.last\_published | The last time this source has published an article from the robot's time |
|source.tier     | The rank position divided by 1000 for this source (used for filtering and spam handling) |
|source.publisher\_type | The publisher type of this source (WEBLOG, MAINSTREAM\_NEWS, etc) |

## Permalink ##

Container for the permalink fetch of this content.

|permalink.title| Title from this post as parsed from HTML|
|:--------------|:----------------------------------------|
|permalink.hashcode| Globally unique hashcode representing this permalink|
|permalink.author.name| The name of the author who published this story.|
|permalink.author.email| The email of the author who published this story.|
|permalink.author.link.href| Link to the profile of the author.      |
|permalink.spam\_probability| Probability this item is spam.  -1 for unknown. 0.0 if not spam.|
|permalink.last\_published|The time this item was published according to the RSS feed|
|permalink.date\_found|The time this item was found according to our robot|
|permalink.link.href|The URL to the permalink for this item.  |
|permalink.link.resource| Tokenized form of permalink.link.href   |
|permalink.lang.code| Mathematically classified language for this source |
|permalink.lang.probability| Future support for probability of this language |
|permalink.content.mime\_type | The underlying mime type for this content (usually text/html)|
|permalink.content.data| The binary data for this content        |
|permalink.content.encoding|The raw binary encoding.  Usually zlib   |
|permalink.content\_extract.mime\_type | The underlying mime type for this content (usually text/html)|
|permalink.content\_extract.data| The binary data for this content        |
|permalink.content\_extract.encoding|The raw binary encoding.  Usually zlib   |