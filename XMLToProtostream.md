# Mapping XML to Protocol Buffers #

This table provides  a mapping between the XML schema an the protocol buffer schema. In the table, ` entry ` is an object of type ` ContentApi.Entry `.

| **XML Element** | **Protobuf object call** |
|:----------------|:-------------------------|
| ` title `       | ` entry.getPermalinkEntry().getTitle() `  |
| ` link `        | ` entry.getPermalinkEntry().getLink(0).getHref() `  |
| ` guid `        | ` entry.getPermalinkEntry().getCanonicalLink().getResource() `  |
| ` pubDate `     | ` entry.getPermalinkEntry().getLastPublished() `  |
| ` dc:lang `     | ` entry.getPermalinkEntry().getLang(0).getCode() `  |
| ` atom:published ` | ` entry.getPermalinkEntry().getLastPublished() `  |
| ` post:date_found ` | ` entry.getPermalinkEntry().getDateFound() `  |
| ` post:timestamp ` | ` entry.getPermalinkEntry().getIdentifier() `  |
| ` post:spam_probability ` | ` entry.getPermalinkEntry().getSpamProbability() `  |
| ` post:resource_guid ` | ` entry.getPermalinkEntry().getHashcode() `  |
| ` post:hashcode ` | ` entry.getPermalinkEntry().getHashcode() `  |
| ` description ` | ` entry.getPermalinkEntry().getContent() `  |
| ` post:content_extract ` | ` entry.getPermalinkEntry().getContentExtract() `  |
| ` weblog:title ` | ` entry.getSource().getTitle() `  |
| ` source:title ` | ` entry.getSource().getTitle() `  |
| ` weblog:description ` | ` entry.getSource().getDescription() `  |
| ` source:description ` | ` entry.getSource().getDescription() `  |
| ` weblog:indegree ` | ` entry.getSource().getIndegree() `  |
| ` source:indegree ` | ` entry.getSource().getIndegree() `  |
| ` dc:source `   | ` entry.getSource().getLink(0).getHref() `  |
| ` source:resource ` | ` entry.getSource().getLink(0).getHref() `  |
| ` weblog:publisher_type ` | ` entry.getSource().getPublisherType() `  |
| ` source:publisher_type ` | ` entry.getSource().getPublisherType() `  |
| ` weblog:tier ` | ` entry.getSource().getTier() `  |
| ` source:tier ` | ` entry.getSource().getTier() `  |
| ` category `    | ` entry.getFeedEntry().getCategory(0) `  |
| ` post:body `   | ` entry.getFeedEntry().getContent() `  |
| ` post:title `  | ` entry.getFeedEntry().getTitle() `  |
| ` atom:name `   | ` entry.getFeedEntry().getAuthor(0).getTitle() `  |
| ` atom:email `  | ` entry.getFeedEntry().getAuthor(0).getEmail() `  |
| ` atom:link `   | ` entry.getFeedEntry().getAuthor(0).getLink(0).getHref() `  |

Notes: The result of the ` getContent() ` calls is a ` Content ` object. The content object contains a encoding type and a mime type. The data may be compressed. The encoding type will indicate this.