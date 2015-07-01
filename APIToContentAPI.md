# Old API to Protocol Buffer Schema #

This table maps ` PermlinkItem ` calls to the matching ` ContentAPI.Entry ` object call.

| **` PermalinkItem ` call** | **` ContentAPI.Entry ` call** |
|:---------------------------|:------------------------------|
| ` getTitle() `             | ` entry.getPermalinkEntry().getTitle() ` |
| ` getDescription() `       | `  entry.getPermalinkEntry().getContent() ` |
| ` getLink() `              | `  pentry.getPermalinkEntry().getCanonicalLink().getHref() ` |
| ` getGuid() `              | `  entry.getPermalinkEntry().getCanonicalLink().getResource() ` |
| ` setLang() `              | `  entry.getSource().getLang(0).getCode() ` |
| ` getSource() `            | ` entry.getSource().getLink(0).getHref() ` |
| ` getWeblogTitle() `       | ` entry.getSource().getLink(0).getHref() ` |
| ` getWeblogDescription() ` | ` entry.getSource().getDescription() ` |
| ` getWeblogTier() `        | ` entry.getSource().getTier() ` |
| ` getPublished() `         | ` entry.getPermalinkEntry().getLastPublished() `  |
| ` getWeblogIndegree() `    | ` entry.getSource().getIndegree() `  |
| ` getTags() `              | ` entry.getFeedEntry().getCategoryList() `  |
| ` getAuthorName() `        | ` entry.getPermalinkEntry().getAuthor(0).getName() `  |
| ` getAuthorEmail() `       | ` entry.getPermalinkEntry().getAuthor(0).getEmail() `  |
| ` getAuthorLink() `        | ` entry.getPermalinkEntry().getAuthor(0).getLink(0).getHref() `  |
| ` getContentExtract() `    | ` entry.getPermalinkEntry().getContentExtract() `  |
| ` getFeedURL() `           | ` entry.getFeed().getCanonicalLink().getHref() `  |
| ` getFeedResource() `      | ` entry.getFeed().getCanonicalLink().getResource() `  |
| ` getResourceGUID() `      | ` entry.getPermalinkEntry().getHashcode() `  |
| ` getPostTitle() `         | ` entry.getFeedEntry().getTitle() `  |
| ` getPostBody() `          | ` entry.getFeedEntry().getContent().getData() `  |
| ` getPostHashcode() `      | ` entry.getFeedEntry().getHashcode() `  |
| ` getSourceHashcode() `    | ` entry.getSource().getHashcode() `  |
| ` getFeedHashcode() `      | ` entry.getFeed().getHashcode() `  |