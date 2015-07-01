# Introduction #

Example of our the permalink history API response.

Note the associated metadata with this post along with the RSS/Atom feed body (post:body), a content extract from the HTML, and the full HTML for the post.

If full HTML isn't need this can also be removed from the response with a skip\_description HTTP request parameter.

# Example #


```
<?xml version="1.0" encoding="UTF-8"?>
<rss version="2.0"
     xmlns:dc="http://purl.org/dc/elements/1.1/"
     xmlns:atom="http://www.w3.org/2005/Atom"
     xmlns:cluster-log="http://tailrank.com/ns/#cluster-log"
     xmlns:post="http://tailrank.com/ns/#post"
     xmlns:source="http://tailrank.com/ns/#source"
     xmlns:link="http://spinn3r.com/ns/link"
     xmlns:target="http://spinn3r.com/ns/#target"
     xmlns:api="http://tailrank.com/ns/#api"
     xmlns:feed="http://tailrank.com/ns/#feed"
     xmlns:weblog="http://tailrank.com/ns/#weblog">
<channel>
<title>Spinn3r permalink3.history results</title>
<link>http://spinn3r.com</link>
<description>Spinn3r results for: XXXXXX</description>
<dc:source>http://api.spinn3r.com/rss/permalink3.history?vendor=XXX&amp;version=2.3.1&amp;source=http%3A%2F%2Fcnn.com&amp;limit=10</dc:source>
<api:next_request_url>http://api.spinn3r.com/rss/permalink3.history?version=2.3.1&amp;limit=10&amp;source=http%3A%2F%2Fcnn.com.com&amp;vendor=spinn3r-console&amp;before=1238500745130967461</api:next_request_url>

<generator>Powered by Spinn3r</generator>

<item>
<title>House passes sweeping national service expansion  - CNN.com</title>
<link>http://www.cnn.com/2009/POLITICS/03/31/national.service/index.html?eref=rss_topstories</link>
<guid>http://cnn.com/2009/POLITICS/03/31/national.service?eref=rss_topstories</guid>
<pubDate>Tue, 31 Mar 2009 20:07:12 GMT</pubDate>
<dc:source>http://www.cnn.com</dc:source>
<dc:lang>en</dc:lang>

<source:resource>http://www.cnn.com</source:resource>
<source:title>CNN.com</source:title>
<source:description>CNN.com delivers up-to-the-minute news and information on the latest top stories, weather, entertainment, politics and more.</source:description>
<source:tier>0</source:tier>
<source:indegree>47518</source:indegree>
<source:iranking>0</source:iranking>
<source:hashcode>ZPim3v4pjCA</source:hashcode>
<source:publisher_type>MAINSTREAM_NEWS</source:publisher_type>

<feed:link>http://rss.cnn.com/rss/cnn_topstories.rss</feed:link>
<feed:hashcode>SlV-L2hduiU</feed:hashcode>
<feed:url>http://rss.cnn.com/rss/cnn_topstories.rss</feed:url>

<atom:published>2009-03-31T19:47:02Z</atom:published>
<post:date_found>2009-03-31T20:07:12Z</post:date_found>
<post:resource_guid>-4CMRbH1yaw</post:resource_guid>
<post:spam_probability>0.0</post:spam_probability>
<post:hashcode>-4CMRbH1yaw</post:hashcode>
<post:timestamp>1238530032187994511</post:timestamp>
<post:title>House OKs sweeping national service bill</post:title>

<post:body>The House of Representatives easily passed legislation Tuesday to strengthen national community service efforts by boosting funding for thousands of volunteers in fields ranging from clean energy to health care and education.&lt;div class="feedflare"&gt;
&lt;a href="http://rss.cnn.com/~ff/rss/cnn_topstories?a=t3K0CmebauA:Al7KW7IsP8I:yIl2AUoC8zA"&gt;&lt;img src="http://feeds2.feedburner.com/~ff/rss/cnn_topstories?d=yIl2AUoC8zA" border="0"&gt;&lt;/img&gt;&lt;/a&gt; &lt;a href="http://rss.cnn.com/~ff/rss/cnn_topstories?a=t3K0CmebauA:Al7KW7IsP8I:7Q72WNTAKBA"&gt;&lt;img src="http://feeds2.feedburner.com/~ff/rss/cnn_topstories?d=7Q72WNTAKBA" border="0"&gt;&lt;/img&gt;&lt;/a&gt; &lt;a href="http://rss.cnn.com/~ff/rss/cnn_topstories?a=t3K0CmebauA:Al7KW7IsP8I:V_sGLiPBpWU"&gt;&lt;img src="http://feeds2.feedburner.com/~ff/rss/cnn_topstories?i=t3K0CmebauA:Al7KW7IsP8I:V_sGLiPBpWU" border="0"&gt;&lt;/img&gt;&lt;/a&gt; &lt;a href="http://rss.cnn.com/~ff/rss/cnn_topstories?a=t3K0CmebauA:Al7KW7IsP8I:qj6IDK7rITs"&gt;&lt;img src="http://feeds2.feedburner.com/~ff/rss/cnn_topstories?d=qj6IDK7rITs" border="0"&gt;&lt;/img&gt;&lt;/a&gt; &lt;a href="http://rss.cnn.com/~ff/rss/cnn_topstories?a=t3K0CmebauA:Al7KW7IsP8I:gIN9vFwOqvQ"&gt;&lt;img src="http://feeds2.feedburner.com/~ff/rss/cnn_topstories?i=t3K0CmebauA:Al7KW7IsP8I:gIN9vFwOqvQ" border="0"&gt;&lt;/img&gt;&lt;/a&gt;

&lt;/div&gt;&lt;img src="http://feeds2.feedburner.com/~r/rss/cnn_topstories/~4/t3K0CmebauA" height="1" width="1"/&gt;</post:body>


<post:content_extract>
&lt;h1&gt;House passes sweeping national service expansion&lt;/h1&gt;
&lt;ul class="noindent"&gt;
&lt;li&gt;Bill would more than triple number of jobs in AmeriCorps, among other things&lt;br /&gt;
&lt;/li&gt;
&lt;li&gt;Almost all Dems supported it; a strong majority of Republicans were opposed&lt;br /&gt;

&lt;/li&gt;
&lt;li&gt;Critics say bill is fiscally irresponsible in light of current economic downturn&lt;br /&gt;
&lt;/li&gt;
&lt;li&gt;Bill is expected to cost roughly $6 billion over the next five years&lt;br /&gt;
&lt;/li&gt;
&lt;/ul&gt;
&lt;div id="cnnSCFontButtons"&gt;

&lt;div id="cnnSCFontLabel"&gt;&lt;img
src="http://i.cdn.turner.com/cnn/.element/img/2.0/global/story_tools/text_size.gif" alt=""
width="38" height="13" /&gt;&lt;/div&gt;
&lt;div id="cnnSCFontMinusBtn"&gt;&lt;img
src="http://i.cdn.turner.com/cnn/.element/img/2.0/global/story_tools/txt_minus.gif" border="0"
width="13" height="13" alt="Decrease font" title="Decrease font" class="cnnDecreaseFont" /&gt;
&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/global/story_tools/txt_minus_dn_.gif"
border="0" width="13" height="13" alt="Decrease font" title="Decrease font"
class="cnnIncreaseFont" /&gt;&lt;/div&gt;
&lt;div id="cnnSCFontPlusBtn"&gt;&lt;img
src="http://i.cdn.turner.com/cnn/.element/img/2.0/global/story_tools/txt_plus.gif" border="0"
width="13" height="13" alt="Enlarge font" title="Enlarge font" class="cnnIncreaseFont" /&gt; &lt;img
src="http://i.cdn.turner.com/cnn/.element/img/2.0/global/story_tools/txt_plus_dn.gif"
border="0" width="13" height="13" alt="Enlarge font" title="Enlarge font"
class="cnnDecreaseFont" /&gt;&lt;/div&gt;
&lt;/div&gt;

&lt;p&gt;&lt;b&gt;WASHINGTON (CNN)&lt;/b&gt; -- The House of Representatives easily passed legislation Tuesday
to strengthen national community service efforts by boosting funding for thousands of
volunteers in fields ranging from clean energy to health care and education.&lt;/p&gt;
&lt;div class="cnnStoryPhotoBox"&gt;
&lt;div id="cnnImgChngr" class="cnnImgChngr"&gt;&lt;img
src="http://i2.cdn.turner.com/cnn/2009/POLITICS/03/31/national.service/art.bill.gi.jpg"
alt="The bill the House passed would increase funding for thousands of volunteers."
width="292" height="219" border="0" /&gt;
&lt;div class="cnnStoryPhotoCaptionBox"&gt;
&lt;div class="cnn3pxTB9pxLRPad"&gt;

&lt;p&gt;The bill the House passed would increase funding for
thousands of volunteers.&lt;/p&gt;
&lt;/div&gt;
&lt;/div&gt;
&lt;div class="cnnWireBoxFooter"&gt;&lt;img
src="http://i.cdn.turner.com/cnn/.element/img/2.0/mosaic/base_skins/baseplate/corner_wire_BL.gif"
alt="" width="4" height="4" /&gt;&lt;/div&gt;
&lt;/div&gt;
&lt;/div&gt;
&lt;p&gt;The Edward M. Kennedy Serve America Act, recently renamed to honor the Massachusetts
senator's sponsorship of the measure, passed the House by a vote of 275-149. Democrats
supported it almost unanimously; a strong majority of Republicans were opposed.&lt;/p&gt;

&lt;p&gt;The Senate passed identical legislation Thursday by a vote of 78-20. President Obama, who
spent several years working as a community organizer, is expected to sign it into law
shortly.&lt;/p&gt;
&lt;p&gt;"At this time of economic crisis, we need service and volunteering more than ever. This
bill will unleash a new era of service for our nation at a time of great need," Sandy Scott, a
spokesman for the federally funded community service program &lt;a
href="http://topics.cnn.com/topics/AmeriCorps" class="cnnInlineTopic"&gt;AmeriCorps&lt;/a&gt;, told
CNN.&lt;/p&gt;
&lt;p&gt;Among other things, the bill would more than triple the number of positions in the
AmeriCorps program, from 75,000 to 250,000, by 2017.&lt;/p&gt;
&lt;p&gt;The increase could have a huge ripple effect in national volunteerism rates. Last year,
75,000 AmeriCorps members recruited and supervised 2.2 million community volunteers, according
to Scott.&lt;/p&gt;

&lt;div class="cnnStoryElementBox"&gt;
&lt;h4&gt;Don't Miss&lt;/h4&gt;
&lt;ul class="cnnRelated"&gt;
&lt;li&gt;&lt;a href="/2009/LIVING/03/26/community2/index.html"&gt;In lean times, Americans rediscover
community&lt;/a&gt;&lt;/li&gt;
&lt;li&gt;&lt;a href="/2009/US/03/26/pimp.this.bum/index.html"&gt;Web site employs irony on homeless man's
behalf&lt;/a&gt;&lt;/li&gt;

&lt;/ul&gt;
&lt;/div&gt;
&lt;p&gt;At the same time, the bill would create four new national service corps and several other
initiatives, including a so-called "Summer of Service" program to spur greater community
outreach by middle and high school students. Older Americans would also be encouraged to
volunteer more through the creation of a "Silver Scholars" program, under which individuals 55
and older who perform 350 hours of service receive a $1,000 award.&lt;/p&gt;
&lt;p&gt;The legislation would increase the existing AmeriCorps educational stipend offered to
volunteers to $5,350 -- the same amount as the maximum Pell college grant.&lt;/p&gt;
&lt;p&gt;Critics contend the bill is fiscally irresponsible in light of the current economic
downturn. They also argue that the concept of volunteerism is undermined by providing
financial compensation for community service.&lt;/p&gt;
&lt;p class="cnnInline"&gt;The bill is expected to cost roughly $6 billion over the next five years.

&lt;/p&gt;
&lt;div class="cnnWsnr" style="display:inline;"&gt;&lt;span class="cnnEmbeddedMosLnk"&gt;&lt;a
href="#"&gt;E-mail to a friend&lt;/a&gt; &lt;img
src="http://i.cdn.turner.com/cnn/.element/img/2.0/mosaic/util/email.gif"
alt="E-mail to a friend" width="17" height="14" border="0" /&gt;&lt;/span&gt; &lt;div class="cnnEmbeddShare" id="cnnEmbeddShareSpan"&gt;
&lt;div class="cnnOverlayMenuContainer"&gt;
&lt;div id="cnnShareThisStory124" class="cnnOverlayMenu"&gt;

&lt;div class="cnnShareThisBox"&gt;
&lt;div class="cnnShareBoxContent"&gt;
&lt;div class="cnnShareContent"&gt;
&lt;div id="cnnShareThisContent"&gt;
&lt;div class="cnnShareThisTitle"&gt;&lt;a
href="javascript:cnnHideOverlay('cnnShareThisStory124')"&gt;&lt;img
src="http://i.cdn.turner.com/cnn/.element/img/2.0/global/icons/btn_close.gif" alt=""
border="0" width="12" height="12" /&gt;&lt;/a&gt; &lt;h6&gt;Share this on:&lt;/h6&gt;

&lt;/div&gt;
&lt;div class="cnnShareThisItem"&gt;&lt;a id="cnnSBtnMixxBot" class="cnnShareMixx"&gt;Mixx&lt;/a&gt; &lt;a
id="cnnSBtnDiggBot" class="cnnShareDigg"&gt;Digg&lt;/a&gt; &lt;a id="cnnSBtnFacebookBot"
class="cnnShareFacebook"&gt;Facebook&lt;/a&gt; &lt;a id="cnnSBtnDeliciousBot"
class="cnnShareDelicious"&gt;del.icio.us&lt;/a&gt; &lt;a id="cnnSBtnRedditBot"
class="cnnShareReddit"&gt;reddit&lt;/a&gt; &lt;a id="cnnSBtnStumbleUponBot"
class="cnnShareStumbleUpon"&gt;StumbleUpon&lt;/a&gt; &lt;a id="cnnSBtnMyspaceBot"
class="cnnShareMyspace"&gt;MySpace&lt;/a&gt;&lt;/div&gt;

&lt;/div&gt;
&lt;/div&gt;
&lt;/div&gt;
&lt;/div&gt;
&lt;/div&gt;
&lt;/div&gt;
| &lt;a href="234" class="cnnMixx" id="cnnMixxEmbedLnk"&gt;Mixx it&lt;/a&gt; | &lt;a class="cnnOverlayLnk"
id="cnnEmbedShareLnk"
href="javascript:cnnShowOverlay('cnnShareThisStory124');"&gt;Share&lt;/a&gt;&lt;/div&gt;

&lt;/div&gt;
&lt;br /&gt;
&lt;br /&gt;
&lt;p class="cnnTopics"&gt;&lt;b&gt;All About&lt;/b&gt; &lt;a
href="http://topics.cnn.com/topics/AmeriCorps"&gt;AmeriCorps&lt;/a&gt; • &lt;a
href="http://topics.cnn.com/topics/Barack_Obama"&gt;Barack Obama&lt;/a&gt; • &lt;a
href="http://topics.cnn.com/topics/U_S_House_of_Representatives"&gt;U.S. House of
Representatives&lt;/a&gt;&lt;/p&gt;

&lt;div id="ad-186105" align="left" style="padding: 0; margin: 0; border: 0;"&gt;&lt;/div&gt;</post:content_extract>


<description>&lt;!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"&gt;&lt;html lang="en"&gt;&lt;head&gt;&lt;script type="text/javascript" src="http://i.cdn.turner.com/cnn/.element/js/2.0/ad_head0.js"&gt;&lt;/script&gt;
&lt;script type="text/javascript" src="http://i.cdn.turner.com/cnn/cnn_adspaces/cnn_adspaces.js"&gt;&lt;/script&gt;

&lt;title&gt;House passes sweeping national service expansion  - CNN.com&lt;/title&gt;&lt;meta name="TITLE" content="House passes sweeping national service expansion  - CNN.com"&gt;&lt;meta name="description" content="The House of Representatives easily passed legislation Tuesday to strengthen national community service efforts by boosting funding for thousands of volunteers in fields ranging from clean energy to health care and education."&gt;&lt;meta name="AUTHOR" content=""&gt;&lt;meta name="SECTION" content="POLITICS"&gt;&lt;meta name="SUBSECTION" content=""&gt;&lt;meta name="KEYWORDS" content="Economic Crisis, Volunteering, Elementary and High School Education, National Economy, U.S. Government, AmeriCorps, Edward M. Kennedy, Education, Massachusetts, U.S. House of Representatives, Business, Economic Issues, Sandy Scott, High School Education, Barack Obama, United States, U.S. Congressional News"&gt;&lt;script type="text/javascript" language="javascript1.2"&gt;var cnnIsIntl = (location.hostname.indexOf('edition.') &gt; -1) ? true : false;var clickID = (cnnIsIntl) ? 212106 : 211911;var cnnShareTitle = encodeURIComponent("House passes sweeping national service expansion ");var cnnShareDesc = encodeURIComponent("The House of Representatives easily passed legislation Tuesday to strengthen national community service efforts by boosting funding for thousands of volunteers in fields ranging from clean energy to health care and education.");&lt;/script&gt;  	&lt;meta http-equiv="content-type" content="text/html; charset=iso-8859-1"&gt;

	&lt;link rel="Start" href="/"&gt;
	&lt;link rel="Search" href="/search/"&gt;
	&lt;link rel="stylesheet" href="http://i.cdn.turner.com/cnn/.element/css/2.0/common.css" type="text/css"&gt;
	&lt;link rel="stylesheet" href="http://i.cdn.turner.com/cnn/.element/css/2.0/mosaic.css" type="text/css"&gt;
	&lt;link rel="alternate stylesheet" type="text/css" href="http://i.cdn.turner.com/cnn/.element/css/2.0/mosaic-alt.css" title="LargeFont"&gt;

	&lt;script src="http://i.cdn.turner.com/cnn/.element/js/2.0/scripts/prototype.js" type="text/javascript"&gt;&lt;/script&gt;

	&lt;script src="http://i.cdn.turner.com/cnn/.element/js/2.0/scripts/scriptaculous.js?load=effects" type="text/javascript"&gt;&lt;/script&gt;
	&lt;script src="http://i.cdn.turner.com/cnn/.element/js/2.0/main.js" type="text/javascript"&gt;&lt;/script&gt;
	&lt;script src="http://i.cdn.turner.com/cnn/.element/js/2.0/health/question.form.js"&gt;&lt;/script&gt;
	&lt;script src="http://i.cdn.turner.com/cnn/.element/js/2.0/recommend.js" type="text/javascript"&gt;&lt;/script&gt;
	&lt;script src="http://i.cdn.turner.com/cnn/.element/js/2.0/StorageManager.js" type="text/javascript"&gt;&lt;/script&gt;

	&lt;script src="http://i.cdn.turner.com/cnn/.element/js/2.0/statemanager.js" type="text/javascript"&gt;&lt;/script&gt;
	&lt;script src="http://i.cdn.turner.com/cnn/.element/js/2.0/mosaic.js" type="text/javascript"&gt;&lt;/script&gt;
	&lt;script src="http://i.cdn.turner.com/cnn/.element/js/2.0/csiManager.js"&gt;&lt;/script&gt;
	&lt;script src="http://i.cdn.turner.com/cnn/.element/ssi/js/1.3/oo_engine.js"&gt;&lt;/script&gt;

	&lt;!-- Copyright 2001-2006, Clickability, Inc. All rights reserved.--&gt;
&lt;script type="text/javascript" language="javascript1.2" src="http://i.cdn.turner.com/cnn/.element/js/2.0/cnnCustomButton.js"&gt;&lt;/script&gt;
&lt;script type="text/javascript"&gt;
// clickability over-ride for sponsorship
popWin="width=810,height=480,resizable=1,scrollbars=1";
&lt;/script&gt;
&lt;script language="JavaScript" type="text/javascript"&gt;var cnnCurrTime = new Date(1238529981754); var cnnCurrHour = 16; var cnnCurrMin = 6; var cnnCurrDay='Tue';&lt;/script&gt;

	&lt;link rel="stylesheet" href="http://i.cdn.turner.com/cnn/.element/css/2.0/politics.css" type="text/css"&gt;	  &lt;script type="text/javascript"&gt;

// FORMAT: cnnad_register(type,width,height,url);
cnnad_registerAd("article", 336, 850, "/cnn_adspaces/2.0/politics/first_100_days/special_report/rgt.336x850.ad");
cnnad_registerAd("video", 336, 850, "/cnn_adspaces/2.0/politics/first_100_days/intg_story/video.336x280_sync.ad");

overrideVideoAd = '/cnn_adspaces/2.0/politics/first_100_days/intg_story/video.preroll_lg.ad';
overrideSyncAd = '/cnn_adspaces/2.0/politics/first_100_days/intg_story/video.336x280_sync.ad';

var cnnVideoCmpntAd = '/cnn_adspaces/2.0/politics/first_100_days/special_report/lft2.180x150.ad';
var cnnPhotoCmpntAd = '/cnn_adspaces/2.0/politics/first_100_days/special_report/lft3.180x150.ad';
var otherTab1Ad = '/cnn_adspaces/2.0/politics/first_100_days/special_report/lft4.180x150.ad';
var otherTab2Ad = '/cnn_adspaces/2.0/politics/first_100_days/special_report/lft5.180x150.ad';

var cnnDefault336Ad = '/cnn_adspaces/2.0/politics/first_100_days/special_report/rgt.336x850.ad';
var cnnDefault180Ad = '/cnn_adspaces/2.0/politics/first_100_days/special_report/lft.180x150.ad';
&lt;/script&gt;

&lt;script language="JavaScript" type="text/javascript"&gt;var cnnSectID = "cnninline_politics";&lt;/script&gt;&lt;script type="text/javascript" src="http://i.cdn.turner.com/cnn/.element/js/2.0/blogs.js?siteid=cnninline_politics"&gt;&lt;/script&gt;&lt;script type="text/javascript"&gt;
	var storage;
	function cnnRecommendStories()
	{
		var topic_array = [{key:'Economic_Crisis',weight:44},{key:'Volunteering',weight:60},{key:'Elementary_and_High_School_Education',weight:47},{key:'National_Economy',weight:43},{key:'U_S_Government',weight:69}];

		relatedContent(topic_array);
	}
	Event.observe(window, 'load', function()
	{
		storage=StorageManager.getInstance().getStorage();
		storage.setOnload( function()
		{
			window.setTimeout("cnnRecommendStories()",3000);
		});
		storage.load();
	}); 
	Event.observe(window, 'onpageshow', function(evt)
	{
		if(evt.persisted){
			storage=StorageManager.getInstance().getStorage();
			storage.setOnload( function()
			{
				window.setTimeout("cnnRecommendStories()",3000);
			});
			storage.load();
		}
	});
&lt;/script&gt;
&lt;/head&gt;&lt;body id="cnnArticleMosaic"&gt; &lt;a name="top_of_page"&gt;&lt;/a&gt;&lt;a href="#ContentArea"&gt;&lt;img src="http://i.cdn.turner.com/cnn/images/1.gif" alt="Skip to main content" align="right" width="10" height="1" border="0" hspace="0" vspace="0" style="display:none;"&gt;&lt;/a&gt;  &lt;div class="cnn728Container"&gt;

	&lt;div class="cnn728Ad"&gt;
		&lt;div&gt;&lt;!-- ADSPACE: politics/first_100_days/special_report/top.728x90 --&gt;

&lt;!-- CALLOUT|http://ads.cnn.com/html.ng/site=cnn&amp;cnn_pagetype=special_report&amp;cnn_position=728x90_top&amp;cnn_rollup=politics&amp;cnn_section=first_100_days&amp;page.allowcompete=yes&amp;params.styles=fs|CALLOUT --&gt;
&lt;div id="ad-949462" align="center" style="padding: 0; margin: 0; border: 0;"&gt;&lt;/div&gt;

&lt;script type="text/javascript"&gt;
cnnad_createAd("949462","http://ads.cnn.com/html.ng/site=cnn&amp;cnn_pagetype=special_report&amp;cnn_position=728x90_top&amp;cnn_rollup=politics&amp;cnn_section=first_100_days&amp;page.allowcompete=yes&amp;params.styles=fs","90","728");
cnnad_registerSpace(949462,728,90);
&lt;/script&gt;




&lt;/div&gt;

	&lt;/div&gt;
&lt;/div&gt;   &lt;div id="cnn_POLITICS"&gt;&lt;div id="cnnHeader"&gt;
	&lt;div class="cnnHeaderContent"&gt;
		&lt;div class="cnnHeaderCeiling"&gt;
			
&lt;map name="logo"&gt;

&lt;area alt="" coords="0,37,77,0" href="/"&gt;
&lt;area alt="" coords="77,0,304,37" href="/POLITICS/index.html"&gt;
&lt;/map&gt;

&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/global/nav/header/cnn_politics.gif" width="304" height="37" border="0" alt="" usemap="#logo"&gt;&lt;span class="cnnSectName"&gt;&lt;/span&gt;
						&lt;div class="cnnHeadColRight"&gt;
				&lt;div class="cnnGlobalHeaderSearch"&gt;

					&lt;form action="http://search.cnn.com/cnn/search" method="get" onsubmit="return cnnSearch(this);"&gt;
						&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/global/nav/header/header_google_logo_politics.gif" class="cnnSrchDomLogo" width="47" height="22" border="0" alt=""&gt;
						&lt;input type="hidden" name="cnnHeadSrchType" id="cnnHeadSrchType" value="web"&gt;
						&lt;input type="text" maxlength="40" class="cnnHeaderTxtField" id="cnnHeadSrchTxt"&gt;
						&lt;input type="image" src="http://i.cdn.turner.com/cnn/.element/img/2.0/global/nav/header/header_search_btn_politics.gif" alt="Submit" class="cnnHeaderSearchBtn"&gt;
					&lt;/form&gt;

				&lt;/div&gt;
			&lt;/div&gt;
		&lt;/div&gt;
	&lt;/div&gt;
		&lt;div class="cnnNavStretch"&gt;
		&lt;div class="cnnHeaderNav"&gt;

			&lt;ul class="cnnNavigation"&gt;
				&lt;li class="cnnNavLeft"&gt;&lt;/li&gt;
				&lt;li&gt;&lt;a href="/" title="Breaking News, U.S., World Weather Entertainment and Video News from CNN.com"&gt;Home&lt;/a&gt;&lt;/li&gt;
				&lt;li&gt;&lt;a href="/WORLD/" title="World News International Headlines Stories and Video from CNN.com"&gt;World&lt;/a&gt;&lt;/li&gt;

				&lt;li&gt;&lt;a href="/US/" title="U.S. News Headlines Stories and Video from CNN.com"&gt;U.S.&lt;/a&gt;&lt;/li&gt;
				&lt;li&gt;&lt;a class="cnnCurPage" href="/POLITICS/" title="2008 Election and Politics News from CNN.com"&gt;Politics&lt;/a&gt;&lt;/li&gt;
				&lt;li&gt;&lt;a href="/CRIME/" title="Crime News Courts Celebrity Docket and Law News from CNN.com"&gt;Crime&lt;/a&gt;&lt;/li&gt;

				&lt;li&gt;&lt;a href="/SHOWBIZ/" title="Entertainment News Celebrities Movies and TV from CNN.com"&gt;Entertainment&lt;/a&gt;&lt;/li&gt;
				&lt;li&gt;&lt;a href="/HEALTH/" title="Health News Medicine Diet Fitness and Parenting from CNN.com"&gt;Health&lt;/a&gt;&lt;/li&gt;
				&lt;li&gt;&lt;a href="/TECH/" title="Technology Computers Internet and Personal Tech News from CNN.com"&gt;Tech&lt;/a&gt;&lt;/li&gt;

				&lt;li&gt;&lt;a href="/TRAVEL/" title="Travel News Vacations Destinations and Video from CNN.com"&gt;Travel&lt;/a&gt;&lt;/li&gt;
				&lt;li&gt;&lt;a href="/LIVING/" title="Living News Personal Work and Home from CNN.com"&gt;Living&lt;/a&gt;&lt;/li&gt;
				&lt;li class="offsite"&gt;&lt;a href="http://money.cnn.com/?cnn=yes" title="Business financial personal finance news from CNNMoney"&gt;Business&lt;/a&gt;&lt;/li&gt;

				&lt;li class="offsite"&gt;&lt;a href="http://sportsillustrated.cnn.com/?cnn=yes" title="Breaking news real-time scores and daily analysis from Sports Illustrated SI.com"&gt;Sports&lt;/a&gt;&lt;/li&gt;
				&lt;li class="offsite"&gt;&lt;a href="http://www.time.com/?cnn=yes" title="Analysis Opinion, Multimedia and Blogs TIME"&gt;Time.com&lt;/a&gt;&lt;/li&gt;
			&lt;/ul&gt;

						&lt;ul class="cnnUtilityNavigation"&gt;
				&lt;li class="cnnVideo"&gt;&lt;a href="/video/?iref=videoglobal" title="Video Breaking News Videos from CNN.com"&gt;Video&lt;/a&gt;&lt;/li&gt;
				&lt;li class="cnnIreport"&gt;&lt;a href="/exchange/?iref=ireportglobal" title="Unedited Unfiltered News iReport.com from CNN.com"&gt;iReport&lt;/a&gt;&lt;/li&gt;

				&lt;li class="cnnImpact"&gt;&lt;a href="/SPECIALS/2007/impact/?iref=impactglobal" title="Impact Your World Special Reports from CNN.com"&gt;Impact&lt;/a&gt;&lt;/li&gt;
			&lt;/ul&gt;
		&lt;/div&gt;
	&lt;/div&gt;
	&lt;div class="cnnHeaderContent"&gt;

	&lt;div class="cnnHeaderBot"&gt;
		&lt;div class="cnnGlobalHeaderTopics"&gt;
			&lt;div style="color:#949494;padding:2px 0px 0px 7px;"&gt;&lt;a href="/topics" style="color:#CA0002;"&gt;Hot Topics &amp;raquo;&lt;/a&gt; &amp;nbsp; &lt;!--&lt;a href="/ELECTION/2008/"&gt;Election Center&lt;/a&gt; &amp;bull;--&gt; &lt;a href="http://www.cnn.com/SPECIALS/2009/44.president/first.100.days/"&gt;First 100 days&lt;/a&gt; &amp;bull; &lt;a href="http://politicalticker.blogs.cnn.com/"&gt;Political Ticker&lt;/a&gt; &amp;bull; &lt;a href="http://www.cnn.com/SPECIALS/2008/news/commentaries/index.html"&gt;Commentary&lt;/a&gt; &amp;bull; &lt;a href="http://www.cnn.com/topics/" class="cnnTopMore"&gt;more topics &amp;raquo;&lt;/a&gt;&lt;/div&gt;

		&lt;/div&gt;
		&lt;div class="cnnHeadColRight"&gt;
			&lt;div class="cnnHeaderWIntl"&gt;&lt;span class="cnnHeadWeather"&gt;&lt;span id="cnnLWPWeather"&gt;&lt;/span&gt;&lt;img src="http://i.cdn.turner.com/cnn/images/1.gif" width="1" height="19" border="0" alt=""&gt; &lt;span class="cnnHeadWDivide"&gt;&amp;nbsp;&lt;/span&gt; &lt;a href="http://edition.cnn.com/"&gt;International Edition&lt;/a&gt;&lt;/span&gt;&lt;/div&gt;

&lt;script type="text/javascript"&gt;

	var allCookies = CNN_getCookies();
	var lwpCookie = allCookies[ "lwp.weather" ] || null;
	var unitCookie = allCookies[ "default.temp.units" ] || null;
	var lwpLocCode='';
	var lwpZip='';
	var lwpQueryStr = '';
	if(lwpCookie)
	{
	        var locationArr=unescape(lwpCookie).split('|');
	        var weatherLocParse = locationArr[0];
	        if(lwpCookie.indexOf('~')==-1)
	        {
	                weatherLocParse=lwpCookie.replace('|','~');
	        }
	        var lwpDataArr = locationArr[0].split('~');
	        lwpLocCode=lwpDataArr[0];
	        if(lwpDataArr.length&gt;0)
	        {
	                lwpZip=lwpDataArr[1];
	        }
	        if(lwpZip)
	        {
	                lwpQueryStr = 'weather='+lwpZip+'.'+lwpLocCode;
	        }
	        else
	        {
	                lwpQueryStr = 'weather='+escape(lwpCookie);
	        }
	        if (unitCookie &amp;&amp; unitCookie == "true") {
	        	lwpQueryStr += '&amp;celcius=true';
	        } else {
	        	lwpQueryStr += '&amp;celcius=false';
	        }
	}

	if (location.host.indexOf('qai.cnn.com') &gt; -1) {
		var cnnLWPWeatherCSIMgr = CSIManager.getInstance().call('http:/\/svcs.qai.cnn.com/weather/wrapper.jsp',lwpQueryStr,'cnnLWPWeather');
	} else if (location.host.indexOf('cnn.com') &gt; -1) {
		var cnnLWPWeatherCSIMgr = CSIManager.getInstance().call('http:/\/svcs.cnn.com/weather/wrapper.jsp',lwpQueryStr,'cnnLWPWeather');
	} else {
		var cnnLWPWeatherCSIMgr = CSIManager.getInstance().call('/.element/ssi/sect/2.0/MAIN/staticWeatherBox.html',lwpQueryStr,'cnnLWPWeather');
	}
&lt;/script&gt;
			&lt;div class="clear"&gt;&lt;img src="http://i.cdn.turner.com/cnn/images/1.gif" width="1" height="1" alt="" border="0"&gt;&lt;/div&gt;

		&lt;/div&gt;
	&lt;/div&gt;
&lt;/div&gt;

&lt;/div&gt;&lt;/div&gt;
&lt;div id="cnnContainer"&gt; &lt;!--ClickabilityRefresh=30m--&gt;&lt;div id="cnnContentContainer"&gt;      &lt;!-- Tracking values --&gt;

&lt;script language="javascript" type="text/javascript"&gt;
&lt;!--
var cnnSectionName = "Politics";
var cnnMosaicDetect = "mosaic";
//--&gt;&lt;/script&gt;  &lt;!-- this is where the breaking news CSI code will go --&gt;
&lt;div id="cnnBannerContainer"&gt;&lt;/div&gt;
&lt;script type="text/javascript"&gt;
CSIManager.getInstance().call('/.element/ssi/www/breaking_news/2.0/banner.html','','cnnBannerContainer',cnnRenderDomesticBanner);
&lt;/script&gt;
&lt;div id="cnnSetEditionContainer"&gt;&lt;/div&gt;

&lt;div id="cnnMakeHPContainer"&gt;&lt;/div&gt;
&lt;script type="text/javascript"&gt;

var CNNintlrefpass=allCookies['intlrefpass']; 
CNNcheck_intlrefpass(); 

if(cnnShow_setPref &amp;&amp; (location.hostname.indexOf('.cnn.com') &gt; -1)) {

	if (document.referrer) { 
		var CNNintlrefpassname = "intlrefpass"; 
		var CNNintlrefpassvalue = document.referrer; 
		CNN_setCookie(CNNintlrefpassname,CNNintlrefpassvalue, '', '/', '.cnn.com');
	} 
	else { CNNcheck_intlrefpass(); }

	cnnSetEditionBox();

}

function CNNcheck_intlrefpass() { 
	if (CNNintlrefpass!=null) { CNN_killCookie('intlrefpass'); }
} 

&lt;/script&gt;
   &lt;div id="cnnSnapShot"&gt;&lt;div class="cnnWCBoxHeader"&gt;&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/mosaic/base_skins/baseplate/corner_dg_TL.gif" width="4" height="4" border="0" alt=""&gt;&lt;/div&gt; &lt;a name="ContentArea"&gt;&lt;/a&gt;&lt;div id="cnnBrandingBanner" class="cnnSnapShotHeader"&gt;&lt;style&gt;

.cnnSnapShotHeader
{border-bottom:1px solid #e1e1e1;height:28px;position:relative;}

#cnnBrandingBanner.cnnSnapShotHeader
{top:-4px;height:87px;width:984px;border-bottom-width:0;}

#cnnBrandingBanner #cnnTimeStamp
{top:77px;}


&lt;/style&gt;

&lt;div&gt;&lt;img src="http://i.cdn.turner.com/cnn/.element/ssi/story/2.0/banner/sprj.inaug09.inc/984x67_44th_prez_tab3.gif" width="984" height="67" alt="" border="0" usemap="#theBanMap"&gt;&lt;/div&gt;
&lt;map name="theBanMap"&gt;
&lt;area alt="" coords="11,4,416,36" href="/SPECIALS/2009/44.president/first.100.days/"&gt;
&lt;area alt="" coords="14,40,137,65" href="/SPECIALS/2008/news/transition.to.power/"&gt;
&lt;area alt="" coords="147,41,235,60" href="/SPECIALS/2009/44.president/inauguration/"&gt;

&lt;area alt="" coords="248,41,347,60" href="/SPECIALS/2009/44.president/first.100.days/"&gt;
&lt;/map&gt;&lt;!--startclickprintinclude--&gt;











	




	


	



	&lt;div id="cnnTimeStamp"&gt;

		&lt;script type="text/javascript"&gt;document.write(cnnRenderTimeStamp(1238528819253,['March 31, 2009 -- Updated 1946 GMT (0346 HKT)','updated 3:46 p.m. EDT, Tue March 31, 2009']));&lt;/script&gt;
	&lt;/div&gt;


&lt;!--endclickprintinclude--&gt;&lt;div class="cnnStoryTools"&gt;
	&lt;ul&gt;
		&lt;!-- start feedback link --&gt;

&lt;style type="text/css"&gt;
	&lt;!--
	.cnnOpinMosaicFeedback a.realmLink {font-weight:bold;font-size:11px;color:#004276;}
	.cnnOpinMosaicFeedback a.realmLink: hover {color:#CA0002;}
	--&gt;
	&lt;/style&gt;

&lt;!-- /feedback link --&gt;

&lt;!-- start feedback link --&gt;
&lt;!--&lt;li class="cnnOpinMosaic"&gt;

&lt;script language="JavaScript"&gt;
//if (typeof(cnnSectionName) != "undefined") {
//if(cnnSectionName == "Health"){
//O_GoT('&lt;img src="http://i.cdn.turner.com/cnn/.element/img/1.3/misc/opinionBlue.gif"   border="0" title="Feedback" style="margin-right:5px;"&gt;Feedback');}}
//&lt;/script&gt;&lt;/li&gt;--&gt;
&lt;!-- /feedback link --&gt;


				&lt;li&gt;
			&lt;div class="cnnOverlayMenuContainer"&gt;&lt;div id="cnnShareThisStory123" class="cnnOverlayMenu"&gt;

	&lt;div class="cnnShareThisBox"&gt;
		&lt;div class="cnnShareBoxHeader"&gt;&lt;div class="cnnShareBoxHeaderTL"&gt;&lt;/div&gt;&lt;div class="cnnShareBoxHeaderTR"&gt;&lt;/div&gt;&lt;/div&gt;
		&lt;div class="cnnShareBoxContent"&gt;
			&lt;div class="cnnShareContent"&gt;

				&lt;div id="cnnShareThisContent"&gt;
					&lt;div class="cnnShareThisTitle"&gt;
						&lt;a href="javascript:cnnHideOverlay('cnnShareThisStory123')"&gt;&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/global/icons/btn_close.gif" alt="" border="0" width="12" height="12" /&gt;&lt;/a&gt;
						&lt;h6&gt;Share this on:&lt;/h6&gt;
					&lt;/div&gt;

										&lt;div class="cnnShareThisItem"&gt;
						&lt;a id="cnnSBtnMixx" class="cnnShareMixx"&gt;Mixx&lt;/a&gt;
						&lt;a id="cnnSBtnDigg" class="cnnShareDigg"&gt;Digg&lt;/a&gt;
						&lt;a id="cnnSBtnFacebook" class="cnnShareFacebook"&gt;Facebook&lt;/a&gt;

						&lt;a id="cnnSBtnDelicious" class="cnnShareDelicious"&gt;delicious&lt;/a&gt;
						&lt;a id="cnnSBtnReddit" class="cnnShareReddit"&gt;reddit&lt;/a&gt;
						&lt;a id="cnnSBtnStumbleUpon" class="cnnShareStumbleUpon"&gt;StumbleUpon&lt;/a&gt;
						&lt;a id="cnnSBtnMyspace" class="cnnShareMyspace"&gt;MySpace&lt;/a&gt;

					&lt;/div&gt;
					&lt;script type="text/javascript"&gt; cnnSetShareLnks(); &lt;/script&gt;

				&lt;/div&gt;&lt;!-- /cnnShareThisContent --&gt;
			&lt;/div&gt;&lt;!-- /cnnShareContent --&gt;

		&lt;/div&gt;&lt;!-- /cnnShareBoxContent --&gt;
		&lt;div class="cnnShareBoxFooter"&gt;&lt;div class="cnnShareBoxFooterBL"&gt;&lt;/div&gt;&lt;div class="cnnShareBoxFooterBR"&gt;&lt;/div&gt;&lt;/div&gt;
	&lt;/div&gt;
			&lt;/div&gt;&lt;/div&gt;

			&lt;a class="cnnOverlayLnk" onmouseover="cnnImgSwap(this,1)" onmouseout="cnnImgSwap(this,0)"
			href="javascript:cnnShowOverlay('cnnShareThisStory123');"&gt;&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/global/story_tools/mixx_share.gif" alt="Share" border="0" /&gt;&lt;/a&gt;&lt;br&gt;
		&lt;/li&gt;
		&lt;li&gt;&lt;a href="#" onmouseover="cnnImgSwap(this,1)" onmouseout="cnnImgSwap(this,0)" onclick="return(ET());"&gt;&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/global/story_tools/email_btn.gif" alt="E-mail" width="36" height="15" border="0"&gt;&lt;/a&gt;&lt;/li&gt;
		&lt;li&gt;&lt;a href="#" onmouseover="cnnImgSwap(this,1)" onmouseout="cnnImgSwap(this,0)" onclick="return(ST());"&gt;&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/global/story_tools/save_btn.gif" alt="Save" width="36" height="15" border="0"&gt;&lt;/a&gt;&lt;/li&gt;

		&lt;li class="cnnPrintThis"&gt;&lt;a href="#" onmouseover="cnnImgSwap(this,1)" onmouseout="cnnImgSwap(this,0)" onclick="return(PT());"&gt;&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/global/story_tools/print_btn.gif" alt="Print" width="36" height="15" border="0"&gt;&lt;/a&gt;&lt;/li&gt;
	&lt;/ul&gt;
&lt;/div&gt;


&lt;/div&gt;&lt;div id="cnnHeaderLeftCol"&gt; &lt;!--startclickprintinclude--&gt;&lt;!-- google_ad_section_start --&gt;&lt;H1&gt;  House passes sweeping national service expansion &lt;/H1&gt;&lt;!-- google_ad_section_end --&gt;&lt;!--endclickprintinclude--&gt;&lt;/div&gt;&lt;div id="cnnHeaderRightCol"&gt;&lt;!--startclickprintinclude--&gt;&lt;ul&gt;&lt;li class="cnnHiliteHeader"&gt;Story Highlights&lt;/li&gt;&lt;!-- google_ad_section_start --&gt;&lt;li&gt; Bill would more than triple number of jobs in AmeriCorps, among other things&lt;br /&gt; &lt;li&gt; Almost all Dems supported it; a strong majority of Republicans were opposed&lt;br /&gt; &lt;li&gt; Critics say bill is fiscally irresponsible in light of current economic downturn&lt;br /&gt; &lt;li&gt; Bill is expected to cost roughly $6 billion over the next five years&lt;br /&gt; &lt;!-- google_ad_section_end --&gt;&lt;!--startclickprintexclude--&gt;&lt;li id="cnnNextStoryCSI" class="cnnNextStory"&gt;&lt;a href="/2009/POLITICS/03/31/clinton.afghanistan/index.html?iref=nextin"&gt;Next Article in Politics &amp;raquo;&lt;/a&gt;&lt;/li&gt;&lt;script type="text/javascript"&gt;

if(window.location.pathname.indexOf('/2009/POLITICS/03/31/clinton.afghanistan/index.html')!=-1){
var nxtStryCSIMgr = CSIManager.getInstance().call('/.element/ssi/auto/2.0/sect/POLITICS/nextStory0.exclude.html','','cnnNextStoryCSI');}
					
						
if(window.location.pathname.indexOf('/2009/POLITICS/03/31/national.service/index.html')!=-1){
var nxtStryCSIMgr = CSIManager.getInstance().call('/.element/ssi/auto/2.0/sect/POLITICS/nextStory1.exclude.html','','cnnNextStoryCSI');}
					
						
if(window.location.pathname.indexOf('/2009/POLITICS/03/31/sebelius.hearing/index.html')!=-1){
var nxtStryCSIMgr = CSIManager.getInstance().call('/.element/ssi/auto/2.0/sect/POLITICS/nextStory2.exclude.html','','cnnNextStoryCSI');}
					
						
if(window.location.pathname.indexOf('/2009/POLITICS/03/31/polis.business/index.html')!=-1){
var nxtStryCSIMgr = CSIManager.getInstance().call('/.element/ssi/auto/2.0/sect/POLITICS/nextStory3.exclude.html','','cnnNextStoryCSI');}
					
						
if(window.location.pathname.indexOf('/2009/POLITICS/03/31/cuba.travel/index.html')!=-1){
var nxtStryCSIMgr = CSIManager.getInstance().call('/.element/ssi/auto/2.0/sect/POLITICS/nextStory4.exclude.html','','cnnNextStoryCSI');}
					
						
if(window.location.pathname.indexOf('/2009/POLITICS/03/31/britain.g20/index.html')!=-1){
var nxtStryCSIMgr = CSIManager.getInstance().call('/.element/ssi/auto/2.0/sect/POLITICS/nextStory5.exclude.html','','cnnNextStoryCSI');}
					
						
if(window.location.pathname.indexOf('/2009/POLITICS/03/31/ny.20/index.html')!=-1){
var nxtStryCSIMgr = CSIManager.getInstance().call('/.element/ssi/auto/2.0/sect/POLITICS/nextStory6.exclude.html','','cnnNextStoryCSI');}
					
						
if(window.location.pathname.indexOf('/2009/POLITICS/03/31/obama.overseas.poll/index.html')!=-1){
var nxtStryCSIMgr = CSIManager.getInstance().call('/.element/ssi/auto/2.0/sect/POLITICS/nextStory7.exclude.html','','cnnNextStoryCSI');}
					
						
if(window.location.pathname.indexOf('/2009/POLITICS/03/30/house.democrats.obama.budget/index.html')!=-1){
var nxtStryCSIMgr = CSIManager.getInstance().call('/.element/ssi/auto/2.0/sect/POLITICS/nextStory8.exclude.html','','cnnNextStoryCSI');}
					
						
if(window.location.pathname.indexOf('/2009/POLITICS/03/30/obama.lands.bill/index.html')!=-1){
var nxtStryCSIMgr = CSIManager.getInstance().call('/.element/ssi/auto/2.0/sect/POLITICS/nextStory9.exclude.html','','cnnNextStoryCSI');}
					
						&lt;/script&gt;&lt;!--endclickprintexclude--&gt;&lt;/ul&gt;&lt;!--endclickprintinclude--&gt;&lt;/div&gt;&lt;div class="clear"&gt;&lt;img src="http://i.cdn.turner.com/cnn/images/1.gif" width="1" height="1" border="0" alt=""&gt;&lt;/div&gt;&lt;div class="cnnWCBoxFooter"&gt;&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/mosaic/base_skins/baseplate/corner_dg_BL.gif" width="4" height="4" border="0" alt=""&gt;&lt;/div&gt;&lt;/div&gt;  &lt;div id="cnnMainContent"&gt;&lt;div class="cnnWCBox"&gt;&lt;div class="cnnWCBoxHeader"&gt;&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/mosaic/base_skins/baseplate/corner_dg_TL.gif" width="4" height="4" border="0" alt=""&gt;&lt;/div&gt;&lt;div class="cnnWCBoxContent"&gt;&lt;div id="cnnLeftCol"&gt;&lt;div class="cnnMosaicContentCol"&gt;&lt;div id="cnnHighLightTrigger"&gt;&lt;div id="cnnTxtCmpnt" class="cnnContentContainer"&gt;&lt;!-- google_ad_section_start --&gt;&lt;!-- CONTENT --&gt;&lt;!-- REAP --&gt;&lt;!-- PURGE --&gt;&lt;!-- KEEP --&gt;&lt;!--startclickprintinclude--&gt;  &lt;script language="JavaScript" type="text/javascript"&gt;var clickExpire = "-1";&lt;/script&gt;&lt;!--startclickprintexclude--&gt; &lt;div id="cnnSCFontButtons"&gt;&lt;!--endclickprintexclude--&gt;&lt;!--startclickprintexclude--&gt;	&lt;div id="cnnSCFontLabel"&gt;

		&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/global/story_tools/text_size.gif" alt="" width="38" height="13"&gt; 
	&lt;/div&gt;
	&lt;div id="cnnSCFontMinusBtn" onclick="setActiveStyleSheet('default'); return false;"&gt;
		&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/global/story_tools/txt_minus.gif" border="0" width="13" height="13" alt="Decrease font" title="Decrease font" class="cnnDecreaseFont"&gt; &lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/global/story_tools/txt_minus_dn_.gif" border="0" width="13" height="13" alt="Decrease font" title="Decrease font" class="cnnIncreaseFont"&gt; 
	&lt;/div&gt;
	&lt;div id="cnnSCFontPlusBtn" onclick="setActiveStyleSheet('LargeFont'); return false;"&gt;

		&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/global/story_tools/txt_plus.gif" border="0" width="13" height="13" alt="Enlarge font" title="Enlarge font" class="cnnIncreaseFont"&gt; &lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/global/story_tools/txt_plus_dn.gif" border="0" width="13" height="13" alt="Enlarge font" title="Enlarge font" class="cnnDecreaseFont"&gt; 
	&lt;/div&gt;

&lt;/div&gt;&lt;!--endclickprintexclude--&gt;&lt;!--startclickprintexclude--&gt; &lt;!--endclickprintexclude--&gt;&lt;p &gt; &lt;b&gt;WASHINGTON (CNN)&lt;/b&gt; -- The House of Representatives easily passed legislation Tuesday to strengthen national community service efforts by boosting funding for thousands of volunteers in fields ranging from clean energy to health care and education.&lt;/p&gt; &lt;!--startclickprintexclude--&gt;




	
	
	
	
	
	
	
	
	
		
			
		
		
	
	
		
			
				
			
			
				
			
		
	
	
		
			
		
		
		
	
	
	
	
	
		
			
		
			
		
			
		
			
		
			
		
			
		
			
		
			
		
			
		
			
		
	
	
		
				
			
				
				
					
						
							
						
						
					
				
					
			
				
						
			
				
				
			
			
			
		
	
	
	
	
			
			
			
				
					    
												
					&lt;div class="cnnStoryPhotoBox"&gt;&lt;div id="cnnImgChngr" class="cnnImgChngr"&gt;&lt;!----&gt;&lt;!--===========IMAGE============--&gt;&lt;img src="http://i2.cdn.turner.com/cnn/2009/POLITICS/03/31/national.service/art.bill.gi.jpg" alt="The bill the House passed would increase funding for thousands of volunteers." width="292" height="219" border="0"&gt;&lt;!--===========/IMAGE===========--&gt;&lt;div class="cnnStoryPhotoCaptionBox"&gt;&lt;div class="cnn3pxTB9pxLRPad"&gt;&lt;p&gt;&lt;!--===========CAPTION==========--&gt;The bill the House passed would increase funding for thousands of volunteers.&lt;!--===========/CAPTION=========--&gt;&lt;/p&gt;&lt;/div&gt;&lt;/div&gt;&lt;div class="cnnWireBoxFooter"&gt;&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/mosaic/base_skins/baseplate/corner_wire_BL.gif" alt="" width="4" height="4"&gt; &lt;/div&gt;&lt;/div&gt;&lt;/div&gt;

				
			
			
			
		
	
	
 &lt;!--endclickprintexclude--&gt;&lt;p &gt; The Edward M. Kennedy Serve America Act, recently renamed to honor the Massachusetts senator's sponsorship of the measure, passed the House by a vote of 275-149. Democrats supported it almost unanimously; a strong majority of Republicans were opposed.&lt;/p&gt;&lt;p &gt; The Senate passed identical legislation Thursday by a vote of 78-20. President Obama, who spent several years working as a community organizer, is expected to sign it into law shortly.&lt;/p&gt;&lt;p &gt;   &amp;quot;At this time of economic crisis, we need service and volunteering more than ever. This bill will unleash a new era of service for our nation at a time of great need,&amp;quot; Sandy Scott, a spokesman for the federally funded community service program &lt;a href="http://topics.cnn.com/topics/AmeriCorps" class="cnnInlineTopic"&gt;AmeriCorps&lt;/a&gt;, told CNN.&lt;/p&gt;&lt;p &gt; Among other things, the bill would more than triple the number of positions in the AmeriCorps program, from 75,000 to 250,000, by 2017.&lt;/p&gt;&lt;p &gt; The increase could have a huge ripple effect in national volunteerism rates. Last year, 75,000 AmeriCorps members recruited and supervised 2.2 million community volunteers, according to Scott.&lt;/p&gt; &lt;!--startclickprintexclude--&gt;




	
	
	
	
	
	
	
	
	
		
			
				
				
				
				
					
					
				
				
			
		
		
	
	
	
		
			
		
		
		
	
	
	
	
	
		
			
		
			
		
			
		
			
		
			
		
			
		
			
		
			
		
			
		
			
		
	
	
	
		
			
				
				
			
		
	
	
	
			
			
				
					
					 &lt;div class="cnnStoryElementBox"&gt;&lt;h4&gt;Don't Miss&lt;/h4&gt; &lt;ul class="cnnRelated"&gt;&lt;li&gt;
&lt;b class="wool"&gt;&lt;/b&gt;&lt;a href="/2009/LIVING/03/26/community2/index.html"&gt;In lean times, Americans rediscover community&lt;/a&gt;

&lt;/li&gt;
&lt;li&gt;
&lt;b class="wool"&gt;&lt;/b&gt;&lt;a href="/2009/US/03/26/pimp.this.bum/index.html"&gt;Web site employs irony on homeless man's behalf&lt;/a&gt;
&lt;/li&gt;
&lt;/ul&gt;&lt;/div&gt; 
				
			
			
			
			
		
	
	
 &lt;!--endclickprintexclude--&gt;&lt;p &gt; At the same time, the bill would create four new national service corps and several other initiatives, including a so-called &amp;quot;Summer of Service&amp;quot; program to spur greater community outreach by middle and high school students. Older Americans would also be encouraged to volunteer more through the creation of a &amp;quot;Silver Scholars&amp;quot; program, under which individuals 55 and older who perform 350 hours of service receive a $1,000 award.&lt;/p&gt;&lt;p &gt; The legislation would increase the existing AmeriCorps educational stipend offered to volunteers to $5,350 -- the same amount as the maximum Pell college grant.&lt;/p&gt;&lt;p &gt; Critics contend the bill is fiscally irresponsible in light of the current economic downturn. They also argue that the concept of volunteerism is undermined by providing financial compensation for community service.&lt;/p&gt;&lt;p class="cnnInline"&gt; The bill is expected to cost roughly $6 billion over the next five years.

&lt;!--startclickprintexclude--&gt;
&lt;div class="cnnWsnr" style="display:inline;"&gt;
&lt;span class="cnnEmbeddedMosLnk"&gt; &lt;a href="#" onclick="return(ET());"&gt;E-mail to a friend&lt;/a&gt; &lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/mosaic/util/email.gif" alt="E-mail to a friend" width="17" height="14" border="0"&gt;&lt;/span&gt; &lt;div class="cnnEmbeddShare" id="cnnEmbeddShareSpan"&gt;
 	&lt;div class="cnnOverlayMenuContainer"&gt;

 	&lt;div id="cnnShareThisStory124" class="cnnOverlayMenu"&gt;
 	&lt;div class="cnnShareThisBox"&gt;
 		&lt;div class="cnnShareBoxHeader"&gt;&lt;div class="cnnShareBoxHeaderTL"&gt;&lt;/div&gt;&lt;div class="cnnShareBoxHeaderTR"&gt;&lt;/div&gt;&lt;/div&gt;
 		&lt;div class="cnnShareBoxContent"&gt;

 			&lt;div class="cnnShareContent"&gt;
 				&lt;div id="cnnShareThisContent"&gt;
 					&lt;div class="cnnShareThisTitle"&gt;
 						&lt;a href="javascript:cnnHideOverlay('cnnShareThisStory124')"&gt;&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/global/icons/btn_close.gif" alt="" border="0" width="12" height="12" /&gt;&lt;/a&gt;
 						&lt;h6&gt;Share this on:&lt;/h6&gt;

 					&lt;/div&gt;
 					&lt;div class="cnnShareThisItem"&gt;
 						&lt;a id="cnnSBtnMixxBot" class="cnnShareMixx"&gt;Mixx&lt;/a&gt;
 						&lt;a id="cnnSBtnDiggBot" class="cnnShareDigg"&gt;Digg&lt;/a&gt;
 						&lt;a id="cnnSBtnFacebookBot" class="cnnShareFacebook"&gt;Facebook&lt;/a&gt;

 						&lt;a id="cnnSBtnDeliciousBot" class="cnnShareDelicious"&gt;del.icio.us&lt;/a&gt;
 						&lt;a id="cnnSBtnRedditBot" class="cnnShareReddit"&gt;reddit&lt;/a&gt;
 						&lt;a id="cnnSBtnStumbleUponBot" class="cnnShareStumbleUpon"&gt;StumbleUpon&lt;/a&gt;
 						&lt;a id="cnnSBtnMyspaceBot" class="cnnShareMyspace"&gt;MySpace&lt;/a&gt;

 					&lt;/div&gt;
 				&lt;/div&gt;&lt;!-- /cnnShareThisContent --&gt;
 			&lt;/div&gt;&lt;!-- /cnnShareContent --&gt;
 		&lt;/div&gt;&lt;!-- /cnnShareBoxContent --&gt;
 		&lt;div class="cnnShareBoxFooter"&gt;&lt;div class="cnnShareBoxFooterBL"&gt;&lt;/div&gt;&lt;div class="cnnShareBoxFooterBR"&gt;&lt;/div&gt;&lt;/div&gt;

 	&lt;/div&gt;
 	&lt;/div&gt;
	&lt;/div&gt;
 | &lt;a href="234" class="cnnMixx" id="cnnMixxEmbedLnk"&gt;Mixx it&lt;/a&gt; | &lt;a class="cnnOverlayLnk"	id="cnnEmbedShareLnk" href="javascript:cnnShowOverlay('cnnShareThisStory124');"&gt;Share&lt;/a&gt;&lt;/div&gt;


&lt;/div&gt;
&lt;!--endclickprintexclude--&gt;
&lt;/p&gt; &lt;p class="cnnTopics"&gt;
&lt;b&gt;All About&lt;/b&gt; &lt;a href="http://topics.cnn.com/topics/AmeriCorps"&gt;AmeriCorps&lt;/a&gt; &amp;bull; &lt;a href="http://topics.cnn.com/topics/Barack_Obama"&gt;Barack Obama&lt;/a&gt; &amp;bull; &lt;a href="http://topics.cnn.com/topics/U_S_House_of_Representatives"&gt;U.S. House of Representatives&lt;/a&gt;&lt;/p&gt;

&lt;!--startclickprintexclude--&gt;    &lt;!-- ADSPACE: politics/article/adlinks.585x280 --&gt;

&lt;!-- CALLOUT|http://ads.cnn.com/html.ng/site=cnn&amp;cnn_pagetype=article&amp;cnn_position=585x280_adlinks&amp;cnn_rollup=politics&amp;params.styles=fs_dynamic|CALLOUT --&gt;
&lt;div id="ad-186105" align="left" style="padding: 0; margin: 0; border: 0;"&gt;&lt;/div&gt;
&lt;script type="text/javascript"&gt;
cnnad_createAd("186105","http://ads.cnn.com/html.ng/site=cnn&amp;cnn_pagetype=article&amp;cnn_position=585x280_adlinks&amp;cnn_rollup=politics&amp;params.styles=fs_dynamic","280","585");
cnnad_registerSpace(186105,585,280);

&lt;/script&gt;



&lt;!--endclickprintexclude--&gt;&lt;!--endclickprintinclude--&gt;&lt;!-- /REAP --&gt;&lt;!-- /CONTENT --&gt;&lt;!-- google_ad_section_end --&gt; &lt;/div&gt;&lt;/div&gt; &lt;div class="cnnStoryToolsFooter"&gt;&lt;div class="cnnStoryTools"&gt;

	&lt;ul&gt;
		&lt;!-- start feedback link --&gt;
&lt;style type="text/css"&gt;
	&lt;!--
	.cnnOpinMosaicFeedback a.realmLink {font-weight:bold;font-size:11px;color:#004276;}
	.cnnOpinMosaicFeedback a.realmLink: hover {color:#CA0002;}
	--&gt;
	&lt;/style&gt;

&lt;!-- /feedback link --&gt;

&lt;!-- start feedback link --&gt;
&lt;!--&lt;li class="cnnOpinMosaic"&gt;
&lt;script language="JavaScript"&gt;
//if (typeof(cnnSectionName) != "undefined") {
//if(cnnSectionName == "Health"){
//O_GoT('&lt;img src="http://i.cdn.turner.com/cnn/.element/img/1.3/misc/opinionBlue.gif"   border="0" title="Feedback" style="margin-right:5px;"&gt;Feedback');}}
//&lt;/script&gt;&lt;/li&gt;--&gt;
&lt;!-- /feedback link --&gt;


		&lt;script type="text/javascript"&gt; cnnSetShareLnks(); &lt;/script&gt;

		&lt;li&gt;&lt;a href="#" onmouseover="cnnImgSwap(this,1)" onmouseout="cnnImgSwap(this,0)" onclick="return(ET());"&gt;&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/global/story_tools/email_btn.gif" alt="E-mail" width="36" height="15" border="0"&gt;&lt;/a&gt;&lt;/li&gt;
		&lt;li&gt;&lt;a href="#" onmouseover="cnnImgSwap(this,1)" onmouseout="cnnImgSwap(this,0)" onclick="return(ST());"&gt;&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/global/story_tools/save_btn.gif" alt="Save" width="36" height="15" border="0"&gt;&lt;/a&gt;&lt;/li&gt;

		&lt;li class="cnnPrintThis"&gt;&lt;a href="#" onmouseover="cnnImgSwap(this,1)" onmouseout="cnnImgSwap(this,0)" onclick="return(PT());"&gt;&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/global/story_tools/print_btn.gif" alt="Print" width="36" height="15" border="0"&gt;&lt;/a&gt;&lt;/li&gt;
	&lt;/ul&gt;
&lt;/div&gt;


&lt;/div&gt;&lt;div class="cnnUGCBox"&gt;

	&lt;div class="cnnUGCBoxHeader"&gt;&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/mosaic/base_skins/UGC/ugc_TL.gif" alt="" width="4" height="4"&gt;&lt;/div&gt;
	&lt;div class="cnnBoxContent"&gt;
		&lt;div class="cnnUGCHeader"&gt;	
			&lt;h3 class="cnnBlogsClosed"&gt;&lt;a href="javascript:void(0);" onclick="cnnToggleUGC('cnnBlogContainer',this);cnnImpressionCheck();return false"&gt;&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/global/ugc-open-arrow.gif" alt="" width="12" height="11" class="opened" border="0"&gt;&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/global/ugc-close-arrow.gif" alt="" width="12" height="11" class="closed" border="0"&gt;From the Blogs: &lt;span&gt;Controversy, commentary, and debate&lt;/span&gt;&lt;/a&gt;&lt;/h3&gt;

		&lt;/div&gt;
		&lt;div id="cnnBlogContainer" style="display:none;"&gt;
		
		&lt;div id="cnnBlogsLoad"&gt;
		&lt;div class="cnnUGCBox"&gt;	
			&lt;div class="cnnUGCBoxHeader"&gt;&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/mosaic/base_skins/UGC/b_ugc_TL.gif" alt="" width="4" height="4"&gt;&lt;/div&gt;
			&lt;div class="cnnBoxContent"&gt;

			
			
					 &lt;h3&gt;&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/mosaic/util/loading.gif" alt="loading"&gt; Sit tight, we're getting to the good stuff&lt;/h3&gt;



			&lt;/div&gt;
			&lt;div class="cnnUGCBoxFooter"&gt;&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/mosaic/base_skins/UGC/b_ugc_BL.gif" alt="" width="4" height="4"&gt;&lt;/div&gt;

		&lt;/div&gt;			
	
					
		&lt;/div&gt;


		&lt;div id="cnnBlogContainerContent" style="display:none;"&gt;&lt;/div&gt;		


		&lt;div class="cnnUGCBoxFooterMeta"&gt;
			&lt;div class="cnnPostCommentsLnk"&gt;powered by &lt;a href="http://www.sphere.com" target="_blank" onclick="var s=s_gi(s_account);s.linkTrackVars='events,products';s.linkTrackEvents='event2';s.events='event2';s.products=';Sphere:From the blogs;;;event2=1;evar23='+ cnnSectionName;void(s.tl(this,'o','Sphere Blogs Clickthrough'));"&gt;Sphere&lt;/a&gt;&lt;/div&gt;

		&lt;/div&gt;
		
		&lt;/div&gt; &lt;!-- /container --&gt;
	&lt;/div&gt;
	&lt;div class="cnnUGCBoxFooter"&gt;&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/mosaic/base_skins/UGC/ugc_BL.gif" alt="" width="4" height="4"&gt;&lt;/div&gt;
&lt;/div&gt;

&lt;script type="text/javascript" language="javascript"&gt;
	if (typeof cnnExpandBlogModule != "undefined") {
		var cnnShowExpandedCont = $('cnnBlogContainer').parentNode.getElementsByTagName('a')[0];
		cnnToggleUGC('cnnBlogContainer',cnnShowExpandedCont );
	}	
&lt;/script&gt;
&lt;div class="cnnTopNewsModule"&gt;&lt;div class="cnnWireBox"&gt;&lt;div class="cnnWireBoxHeader"&gt;&lt;img height="4" width="4" alt="" src="http://i.cdn.turner.com/cnn/.element/img/2.0/mosaic/base_skins/baseplate/corner_wire_TL.gif"&gt;&lt;/div&gt;&lt;div class="cnnBoxContent"&gt;&lt;h3&gt;Top News&lt;/h3&gt;&lt;table border="0" cellspacing="0" cellpadding="0"&gt;&lt;colgroup&gt;&lt;col width="135"&gt;&lt;col width="147"&gt;&lt;col width="135"&gt;&lt;col width="148"&gt;&lt;/colgroup&gt;&lt;tr&gt;&lt;td&gt;&lt;a href="/2009/POLITICS/03/31/national.service/index.html?iref=topnews"&gt;&lt;img src="http://i.cdn.turner.com/cnn/2009/POLITICS/03/31/national.service/tzmos.bill.gi.jpg" border="0" alt=""&gt;&lt;/a&gt;&lt;/td&gt;&lt;td&gt;&lt;a href="/2009/POLITICS/03/31/national.service/index.html?iref=topnews"&gt;&lt;strong&gt;House OKs sweeping national service bill&lt;/strong&gt;&lt;/a&gt;&lt;/td&gt;&lt;td&gt;&lt;a href="/2009/CRIME/03/31/cult.child.death/index.html?iref=topnews"&gt;&lt;img src="http://i.cdn.turner.com/cnn/2009/CRIME/03/31/cult.child.death/tzmos.plea.jpg" border="0" alt=""&gt;&lt;/a&gt;&lt;/td&gt;&lt;td&gt;&lt;a href="/2009/CRIME/03/31/cult.child.death/index.html?iref=topnews"&gt;&lt;strong&gt;Plea deal includes 'resurrection clause'&lt;/strong&gt;&lt;/a&gt;&lt;/td&gt;&lt;/tr&gt;&lt;/table&gt;&lt;/div&gt;&lt;div class="cnnWireBoxFooter"&gt;&lt;img height="4" width="4" alt="" src="http://i.cdn.turner.com/cnn/.element/img/2.0/mosaic/base_skins/baseplate/corner_wire_BL.gif"&gt;&lt;/div&gt;&lt;/div&gt;&lt;/div&gt;&lt;/div&gt;  &lt;/div&gt;&lt;div id="cnnRightCol"&gt;&lt;div class="cnnRRBox"&gt;&lt;div class="cnnRRBoxHeader"&gt;&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/mosaic/base_skins/right_rail/grey_corner_TL.gif" width="4" height="4" border="0" alt=""&gt;&lt;/div&gt;&lt;div class="cnnRRBoxContent"&gt;&lt;div class="cnnMosaic160Container"&gt;&lt;div class="cnnMosaic160Ad"&gt;&lt;div class="cnnRRBox"&gt;&lt;div class="cnnRRBoxHeader"&gt;&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/mosaic/base_skins/right_rail/grey_corner_TL.gif" width="4" height="4" border="0" alt=""&gt;&lt;/div&gt;&lt;div class="cnnRRBoxContent"&gt;  &lt;!-- ADSPACE: politics/first_100_days/special_report/rgt.160x600 --&gt;

&lt;!-- CALLOUT|http://ads.cnn.com/html.ng/site=cnn&amp;cnn_pagetype=special_report&amp;cnn_position=160x600_rgt&amp;cnn_rollup=politics&amp;cnn_section=first_100_days&amp;page.allowcompete=yes&amp;params.styles=fs|CALLOUT --&gt;
&lt;div id="ad-835430" align="center" style="padding: 0; margin: 0; border: 0;"&gt;&lt;/div&gt;
&lt;script type="text/javascript"&gt;
cnnad_createAd("835430","http://ads.cnn.com/html.ng/site=cnn&amp;cnn_pagetype=special_report&amp;cnn_position=160x600_rgt&amp;cnn_rollup=politics&amp;cnn_section=first_100_days&amp;page.allowcompete=yes&amp;params.styles=fs","600","160");
cnnad_registerSpace(835430,160,600);

&lt;/script&gt;





&lt;/div&gt;&lt;div class="cnnRRBoxFooter"&gt;&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/mosaic/base_skins/right_rail/grey_corner_BL.gif" width="4" height="4" border="0" alt=""&gt;&lt;/div&gt;&lt;/div&gt;&lt;/div&gt;&lt;/div&gt;&lt;div class="cnnAdvertTxt336C"&gt;&lt;img width="59" height="5" border="0" alt="" src="http://i.cdn.turner.com/cnn/.element/img/2.0/content/ads/advertisement_right_rail.gif"/&gt;&lt;/div&gt;   &lt;div id="cnnMostPopMosaic"&gt;

	&lt;div class="cnnWcLtgBox"&gt;
		&lt;div class="cnnBoxHeader"&gt;&lt;div&gt;&lt;/div&gt;&lt;/div&gt;
		&lt;div class="cnnBoxContent"&gt;&lt;div class="cnnPad5TB9LR"&gt;

&lt;style&gt;

#cnnMostPopMosaic{margin-top:9px; }
#cnnMostPopMosaic #cnnMostPopMod .cnnWireBox {margin:0; }
.cnnWireRedBox .cnnBoxContent {background-color:#fff;border-left:1px solid #dfdfdf;border-right:1px solid #dfdfdf; }
#cnnMostPopMod .cnnWireRedBox .cnnWireBoxHeader {height:4px;overflow:hidden;background:url(http://i.cdn.turner.com/cnn/.element/img/2.0/mosaic/base_skins/baseplate/corner_wire_TR_bg.gif) 100% 0 repeat-x; }
#cnnMostPopMod .cnnWireRedBox .cnnWireBoxFooter {height:4px;overflow:hidden;background:url(http://i.cdn.turner.com/cnn/.element/img/2.0/mosaic/base_skins/baseplate/corner_wire_BR._bg.gif) 100% 0 no-repeat; }
.cnnMpHead { padding:8px 8px 3px 12px; }
#cnnMostPopMod { height:auto; }
#cnnMostPopMod .cnnBoxContent .cnnMpCat img { padding:4px 9px 4px 6px }
#cnnMpVideo div#cnnMpVideos1 div.cnnMpVideoContent,
#cnnMpVideo div#cnnMpVideos2 div.cnnMpVideoContent,
#cnnMpVideo div#cnnMpVideos3 div.cnnMpVideoContent { height:auto; }

&lt;/style&gt;

&lt;script type="text/javascript"&gt;
cnnad_registerAd("article", 126, 31, "/cnn_adspaces/2.0/most_popular/spon1.126x31.ad");
cnnad_registerAd("video", 126, 31, "/cnn_adspaces/2.0/most_popular/spon1.126x31.ad");
&lt;/script&gt;
&lt;div id="cnnMostPopMod"&gt;



&lt;div class="cnnWireBox"&gt;

	&lt;div class="cnnWireBoxHeader"&gt;&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/mosaic/base_skins/baseplate/corner_wire_TL.gif" alt="" width="4" height="4"&gt;&lt;/div&gt;
		&lt;div class="cnnBoxContent"&gt;
			&lt;div class="cnnMpHeadAd"&gt;&lt;!-- ADSPACE: most_popular/spon1.126x31 --&gt;

&lt;!-- CALLOUT|http://ads.cnn.com/html.ng/site=cnn&amp;cnn_position=126x31_spon1&amp;cnn_rollup=most_popular&amp;page.allowcompete=yes&amp;params.styles=fs|CALLOUT --&gt;

&lt;div id="ad-256124" align="center" style="padding: 0; margin: 0; border: 0;"&gt;&lt;/div&gt;
&lt;script type="text/javascript"&gt;
cnnad_createAd("256124","http://ads.cnn.com/html.ng/site=cnn&amp;cnn_position=126x31_spon1&amp;cnn_rollup=most_popular&amp;page.allowcompete=yes&amp;params.styles=fs","31","126");
cnnad_registerSpace(256124,126,31);
&lt;/script&gt;




&lt;/div&gt;

			&lt;div class="cnnMpHead"&gt;&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/global/misc/most.popular.gif" width="231" height="21" alt="" border="0" alt="Most Popular"&gt;&lt;/div&gt;
			&lt;div class="clear"&gt;&lt;/div&gt;
		&lt;/div&gt;
	&lt;div class="cnnWireBoxFooter"&gt;&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/mosaic/base_skins/baseplate/corner_wire_BL.gif" alt="" width="4" height="4"&gt;&lt;/div&gt;

&lt;/div&gt;


&lt;div class="cnnMpPadTop"&gt;
	&lt;div id="cnnMpStory-head" class="active"&gt;
		&lt;div class="cnnWireRedBox"&gt;
			&lt;div class="cnnWireBoxHeader"&gt;&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/mosaic/base_skins/baseplate/corner_wire_TL.gif" alt="" width="4" height="4"&gt;&lt;/div&gt;

				&lt;div class="cnnBoxContent"&gt;
					&lt;div class="cnnMpCat"&gt;&lt;a href="javascript:cnnToggleMP('cnnMpStory');"&gt;&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/sect/connect/stories_up.gif" width="59" height="8" border="0" alt=""&gt;&lt;/a&gt;&lt;/div&gt;
				&lt;/div&gt;
			&lt;div class="cnnWireBoxFooter"&gt;&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/mosaic/base_skins/baseplate/corner_wire_BL.gif" alt="" width="4" height="4"&gt;&lt;/div&gt;

		&lt;/div&gt;
		&lt;div class="cnnWireBox"&gt;
			&lt;div class="cnnWireBoxHeader"&gt;&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/mosaic/base_skins/baseplate/corner_wire_TL.gif" alt="" width="4" height="4"&gt;&lt;/div&gt;
				&lt;div class="cnnBoxContent"&gt;
					&lt;div class="cnnMpCat"&gt;&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/sect/connect/stories_dwn.gif" width="59" height="8" border="0" alt=""&gt;&lt;/div&gt;

				&lt;/div&gt;
			&lt;/div&gt;
	&lt;/div&gt;&lt;!-- /cnnMpStory-head --&gt;

	&lt;div id="cnnMpStory"&gt;
		&lt;div class="cnnWireBox"&gt;

				&lt;div class="cnnBoxContent"&gt;
					&lt;!-- story content --&gt;
						&lt;table cellpadding="0" cellspacing="0" border="0" width="100%"&gt;
							&lt;tr class="cnnMpTabRow"&gt;
								&lt;td id="cnnMpStoriesTab1" class="active"&gt;&lt;a href="javascript:cnnMpStories(1);"&gt;Most Viewed&lt;/a&gt;&lt;/td&gt;

								&lt;td id="cnnMpStoriesTab2"&gt;&lt;a href="javascript:cnnMpStories(2);"&gt;Most Emailed&lt;/a&gt;&lt;/td&gt;
								&lt;td id="cnnMpStoriesTab3"&gt;&lt;a href="javascript:cnnMpStories(3);"&gt;Top Searches&lt;/a&gt;&lt;/td&gt;
							&lt;/tr&gt;

						&lt;/table&gt;
						&lt;div id="cnnMpStories1"&gt;
							&lt;div class="cnnMpStoryContent"&gt;
								&lt;ol&gt;
&lt;li&gt;
&lt;b&gt;1 &lt;/b&gt;&lt;a href="http://www.cnn.com/2009/CRIME/03/31/sibling.stabbings/index.html?iref=mpstoryview"&gt;Brother beheads little sister&lt;/a&gt;

&lt;/li&gt;
&lt;li&gt;
&lt;b&gt;2 &lt;/b&gt;&lt;a href="http://www.cnn.com/2009/US/03/31/eviction.suicide.death/index.html?iref=mpstoryview"&gt;Near-evictee who shot herself dies&lt;/a&gt;
&lt;/li&gt;
&lt;li&gt;
&lt;b&gt;3 &lt;/b&gt;&lt;a href="http://www.cnn.com/2009/US/03/31/blackwater.falluja.anniversary/index.html?iref=mpstoryview"&gt;'I want Blackwater exposed'&lt;/a&gt;

&lt;/li&gt;
&lt;li&gt;
&lt;b&gt;4 &lt;/b&gt;&lt;a href="http://www.cnn.com/2009/US/03/31/fields.qanda/index.html?iref=mpstoryview"&gt;Ford exec: No thanks, Uncle Sam&lt;/a&gt;
&lt;/li&gt;
&lt;li&gt;
&lt;b&gt;5 &lt;/b&gt;&lt;a href="http://www.cnn.com/2009/CRIME/03/31/grace.coldcase.mauk/index.html?iref=mpstoryview"&gt;Cold Case: Chuck Mauk&lt;/a&gt;

&lt;/li&gt;
&lt;li&gt;
&lt;b&gt;6 &lt;/b&gt;&lt;a href="http://www.cnn.com/2009/TECH/03/31/april.fools.computer.virus/index.html?iref=mpstoryview"&gt;Will April Fools' virus hit you?&lt;/a&gt;
&lt;/li&gt;
&lt;li&gt;
&lt;b&gt;7 &lt;/b&gt;&lt;a href="http://www.cnn.com/2009/LIVING/03/30/nanny.jobs/index.html?iref=mpstoryview"&gt;Economy shakes up family childcare&lt;/a&gt;

&lt;/li&gt;
&lt;li&gt;
&lt;b&gt;8 &lt;/b&gt;&lt;a href="http://www.cnn.com/2009/WORLD/europe/03/31/france.hostages.caterpillar.workers/index.html?iref=mpstoryview"&gt;French workers hold bosses hostage&lt;/a&gt;
&lt;/li&gt;
&lt;li&gt;
&lt;b&gt;9 &lt;/b&gt;&lt;a href="http://www.cnn.com/2009/WORLD/africa/03/31/libya.migrants.plight/index.html?iref=mpstoryview"&gt;Hundreds feared dead off Libya&lt;/a&gt;

&lt;/li&gt;
&lt;li&gt;
&lt;b&gt;10 &lt;/b&gt;&lt;a href="http://www.cnn.com/2009/POLITICS/03/31/cafferty.legal.drugs/index.html?iref=mpstoryview"&gt;Commentary: War on drugs is insane&lt;/a&gt;
&lt;/li&gt;
&lt;/ol&gt;

								&lt;div class="cnnMpMore"&gt;&amp;nbsp;&lt;a href="/mostpopular/"&gt;more most popular &amp;#187;&lt;/a&gt;&lt;/div&gt;

							&lt;/div&gt;
						&lt;/div&gt;
						&lt;div id="cnnMpStories2"&gt;
							&lt;div class="cnnMpStoryContent"&gt;
								&lt;ol&gt;
&lt;li&gt;

&lt;b&gt;1 &lt;/b&gt;&lt;a href="http://cnn.com/2009/TECH/03/31/april.fools.computer.virus/index.html?imw=Y&amp;iref=mpstoryemail"&gt;How will the April Fools' computer...&lt;/a&gt;
&lt;/li&gt;
&lt;li&gt;
&lt;b&gt;2 &lt;/b&gt;&lt;a href="http://cnn.com/2009/HEALTH/03/30/pistachio.recall/index.html?imw=Y&amp;iref=mpstoryemail"&gt;California plant recalls 1 million...&lt;/a&gt;

&lt;/li&gt;
&lt;li&gt;
&lt;b&gt;3 &lt;/b&gt;&lt;a href="http://cnn.com/2009/TECH/03/30/skype.iphone/index.html?imw=Y&amp;iref=mpstoryemail"&gt;Skype for iPhone -- it's official&lt;/a&gt;
&lt;/li&gt;
&lt;li&gt;
&lt;b&gt;4 &lt;/b&gt;&lt;a href="http://cnn.com/2009/WORLD/europe/03/31/france.hostages.caterpillar.workers/index.html?imw=Y&amp;iref=mpstoryemail"&gt;Hundreds of French workers take...&lt;/a&gt;

&lt;/li&gt;
&lt;li&gt;
&lt;b&gt;5 &lt;/b&gt;&lt;a href="http://cnn.com/2009/POLITICS/03/31/cafferty.legal.drugs/index.html?imw=Y&amp;iref=mpstoryemail"&gt;Commentary: War on drugs is insane&lt;/a&gt;
&lt;/li&gt;
&lt;li&gt;
&lt;b&gt;6 &lt;/b&gt;&lt;a href="http://cnn.com/2009/US/03/30/python.patrol/index.html?imw=Y&amp;iref=mpstoryemail"&gt;'Python Patrol' targets giant snakes...&lt;/a&gt;

&lt;/li&gt;
&lt;li&gt;
&lt;b&gt;7 &lt;/b&gt;&lt;a href="http://cnn.com/2009/LIVING/03/30/cafferty.schools/index.html?imw=Y&amp;iref=mpstoryemail"&gt;Commentary: Our schools get lousy grades&lt;/a&gt;
&lt;/li&gt;
&lt;li&gt;
&lt;b&gt;8 &lt;/b&gt;&lt;a href="http://cnn.com/2009/TECH/03/24/conficker.computer.worm?imw=Y&amp;iref=mpstoryemail"&gt;No joke in April Fool's Day computer...&lt;/a&gt;

&lt;/li&gt;
&lt;li&gt;
&lt;b&gt;9 &lt;/b&gt;&lt;a href="http://cnn.com/2009/CRIME/03/31/california.missing.girl/index.html?imw=Y&amp;iref=mpstoryemail"&gt;California girl, 8, disappears after...&lt;/a&gt;
&lt;/li&gt;
&lt;li&gt;
&lt;b&gt;10 &lt;/b&gt;&lt;a href="http://cnn.com/2009/HEALTH/03/30/hm.autism.teacher/index.html?imw=Y&amp;iref=mpstoryemail"&gt;Autism teacher celebrates every gain&lt;/a&gt;

&lt;/li&gt;
&lt;/ol&gt;

								&lt;div class="cnnMpMore"&gt;&amp;nbsp;&lt;a href="/mostpopular/"&gt;more most popular &amp;#187;&lt;/a&gt;&lt;/div&gt;
							&lt;/div&gt;

						&lt;/div&gt;
						&lt;div id="cnnMpStories3"&gt;
							&lt;div class="cnnMpStoryContent"&gt;



&lt;ol&gt;
&lt;li&gt;
&lt;b&gt;1 &lt;/b&gt;&lt;a href="http://search.cnn.com/search.jsp?query=rihanna&amp;type=news&amp;sortBy=date&amp;intl=false&amp;iref=mpstoriessearch"&gt;rihanna&lt;/a&gt;

&lt;/li&gt;
&lt;li&gt;
&lt;b&gt;2 &lt;/b&gt;&lt;a href="http://search.cnn.com/search.jsp?query=chris brown&amp;type=news&amp;sortBy=date&amp;intl=false&amp;iref=mpstoriessearch"&gt;chris brown&lt;/a&gt;
&lt;/li&gt;

&lt;li&gt;
&lt;b&gt;3 &lt;/b&gt;&lt;a href="http://search.cnn.com/search.jsp?query=caylee anthony&amp;type=news&amp;sortBy=date&amp;intl=false&amp;iref=mpstoriessearch"&gt;caylee anthony&lt;/a&gt;
&lt;/li&gt;
&lt;li&gt;

&lt;b&gt;4 &lt;/b&gt;&lt;a href="http://search.cnn.com/search.jsp?query=aig&amp;type=news&amp;sortBy=date&amp;intl=false&amp;iref=mpstoriessearch"&gt;aig&lt;/a&gt;
&lt;/li&gt;
&lt;li&gt;
&lt;b&gt;5 &lt;/b&gt;&lt;a href="http://search.cnn.com/search.jsp?query=mexico&amp;type=news&amp;sortBy=date&amp;intl=false&amp;iref=mpstoriessearch"&gt;mexico&lt;/a&gt;

&lt;/li&gt;
&lt;li&gt;
&lt;b&gt;6 &lt;/b&gt;&lt;a href="http://search.cnn.com/search.jsp?query=obama&amp;type=news&amp;sortBy=date&amp;intl=false&amp;iref=mpstoriessearch"&gt;obama&lt;/a&gt;
&lt;/li&gt;

&lt;li&gt;
&lt;b&gt;7 &lt;/b&gt;&lt;a href="http://search.cnn.com/search.jsp?query=facebook&amp;type=news&amp;sortBy=date&amp;intl=false&amp;iref=mpstoriessearch"&gt;facebook&lt;/a&gt;
&lt;/li&gt;
&lt;li&gt;

&lt;b&gt;8 &lt;/b&gt;&lt;a href="http://search.cnn.com/search.jsp?query=octuplets&amp;type=news&amp;sortBy=date&amp;intl=false&amp;iref=mpstoriessearch"&gt;octuplets&lt;/a&gt;
&lt;/li&gt;
&lt;li&gt;
&lt;b&gt;9 &lt;/b&gt;&lt;a href="http://search.cnn.com/search.jsp?query=stimulus&amp;type=news&amp;sortBy=date&amp;intl=false&amp;iref=mpstoriessearch"&gt;stimulus&lt;/a&gt;

&lt;/li&gt;
&lt;li&gt;
&lt;b&gt;10 &lt;/b&gt;&lt;a href="http://search.cnn.com/search.jsp?query=china&amp;type=news&amp;sortBy=date&amp;intl=false&amp;iref=mpstoriessearch"&gt;china&lt;/a&gt;
&lt;/li&gt;

&lt;/ol&gt;









								&lt;!--include virtual="/.element/ssi/www/auto/2.0/mostpopular/mp.stories.3.txt" --&gt;
								&lt;div class="cnnMpMore"&gt;&amp;nbsp;&lt;a href="/mostpopular/"&gt;more most popular &amp;#187;&lt;/a&gt;&lt;/div&gt;

							&lt;/div&gt;
						&lt;/div&gt;
					&lt;!-- /story content --&gt;
				&lt;/div&gt;
			&lt;div class="cnnWireBoxFooter"&gt;&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/mosaic/base_skins/baseplate/corner_wire_BL.gif" alt="" width="4" height="4"&gt;&lt;/div&gt;

		&lt;/div&gt;
	&lt;/div&gt;&lt;!-- /cnnMpStory --&gt;
&lt;/div&gt;


&lt;div class="cnnMpPadTop"&gt;
	&lt;div id="cnnMpVideo-head" class="closed"&gt;
		&lt;div class="cnnWireRedBox"&gt;

			&lt;div class="cnnWireBoxHeader"&gt;&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/mosaic/base_skins/baseplate/corner_wire_TL.gif" alt="" width="4" height="4"&gt;&lt;/div&gt;
				&lt;div class="cnnBoxContent"&gt;
					&lt;div class="cnnMpCat"&gt;&lt;a href="javascript:cnnToggleMP('cnnMpVideo', true);"&gt;&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/sect/connect/videos_up.gif" width="59" height="8" border="0" alt=""&gt;&lt;/a&gt;&lt;/div&gt;
				&lt;/div&gt;

			&lt;div class="cnnWireBoxFooter"&gt;&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/mosaic/base_skins/baseplate/corner_wire_BL.gif" alt="" width="4" height="4"&gt;&lt;/div&gt;
		&lt;/div&gt;
		&lt;div class="cnnWireBox"&gt;
			&lt;div class="cnnWireBoxHeader"&gt;&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/mosaic/base_skins/baseplate/corner_wire_TL.gif" alt="" width="4" height="4"&gt;&lt;/div&gt;
				&lt;div class="cnnBoxContent"&gt;

					&lt;div class="cnnMpCat"&gt;&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/sect/connect/videos_dwn.gif" width="59" height="8" border="0" alt=""&gt;&lt;/div&gt;
				&lt;/div&gt;
		&lt;/div&gt;
	&lt;/div&gt;&lt;!-- /cnnMpVideo-head --&gt;

	&lt;div id="cnnMpVideo"&gt;

		&lt;div class="cnnWireRedBox"&gt;
				&lt;div class="cnnBoxContent"&gt;
					&lt;!-- video content --&gt;
						&lt;table cellpadding="0" cellspacing="0" border="0" width="100%"&gt;
							&lt;tr class="cnnMpTabRow"&gt;
								&lt;td id="cnnMpVideosTab1" class="active"&gt;&lt;a href="javascript:cnnMpVideos(1);"&gt;Most Viewed&lt;/a&gt;&lt;/td&gt;

								&lt;td id="cnnMpVideosTab2"&gt;&lt;a href="javascript:cnnMpVideos(2);cnnLoadDOMElementOnDemand('cnnMpVideos2Content', cnnMostEmailedVideoCSI);"&gt;Most Emailed&lt;/a&gt;&lt;/td&gt;
								&lt;td id="cnnMpVideosTab3"&gt;&lt;a href="javascript:cnnMpVideos(3);"&gt;Top Searches&lt;/a&gt;&lt;/td&gt;
							&lt;/tr&gt;

						&lt;/table&gt;
						&lt;div id="cnnMpVideos1"&gt;
							&lt;div class="cnnMpVideoContent" id="cnnMpVideos1Content"&gt;
								&lt;ol&gt;&lt;li&gt; &lt;/li&gt;&lt;li&gt; &lt;/li&gt;&lt;li&gt; &lt;/li&gt;&lt;li&gt; &lt;/li&gt;&lt;li&gt; &lt;/li&gt;&lt;/ol&gt;

								&lt;div class="cnnMpMore"&gt; &lt;a href="/mostpopular/"&gt;more most popular »&lt;/a&gt;&lt;/div&gt;
							&lt;/div&gt;
						&lt;/div&gt;
						&lt;div id="cnnMpVideos2"&gt;

							&lt;div class="cnnMpVideoContent" id="cnnMpVideos2Content"&gt;
								&lt;ol&gt;&lt;li&gt; &lt;/li&gt;&lt;li&gt; &lt;/li&gt;&lt;li&gt; &lt;/li&gt;&lt;li&gt; &lt;/li&gt;&lt;li&gt; &lt;/li&gt;&lt;/ol&gt;

								&lt;div class="cnnMpMore"&gt; &lt;a href="/mostpopular/"&gt;more most popular »&lt;/a&gt;&lt;/div&gt;
							&lt;/div&gt;
						&lt;/div&gt;
						&lt;div id="cnnMpVideos3"&gt;

							&lt;div class="cnnMpStoryContent"&gt;
								&lt;ol&gt;
&lt;li&gt;
&lt;b&gt;1 &lt;/b&gt;&lt;a href="http://search.cnn.com/search.jsp?query=video&amp;type=news&amp;sortBy=date&amp;intl=false&amp;iref=mpvideossearch"&gt;video&lt;/a&gt;

&lt;/li&gt;
&lt;li&gt;
&lt;b&gt;2 &lt;/b&gt;&lt;a href="http://search.cnn.com/search.jsp?query=obama&amp;type=news&amp;sortBy=date&amp;intl=false&amp;iref=mpvideossearch"&gt;obama&lt;/a&gt;
&lt;/li&gt;

&lt;li&gt;
&lt;b&gt;3 &lt;/b&gt;&lt;a href="http://search.cnn.com/search.jsp?query=moos&amp;type=news&amp;sortBy=date&amp;intl=false&amp;iref=mpvideossearch"&gt;moos&lt;/a&gt;
&lt;/li&gt;
&lt;li&gt;

&lt;b&gt;4 &lt;/b&gt;&lt;a href="http://search.cnn.com/search.jsp?query=north korea&amp;type=news&amp;sortBy=date&amp;intl=false&amp;iref=mpvideossearch"&gt;north korea&lt;/a&gt;
&lt;/li&gt;
&lt;li&gt;
&lt;b&gt;5 &lt;/b&gt;&lt;a href="http://search.cnn.com/search.jsp?query=mexico&amp;type=news&amp;sortBy=date&amp;intl=false&amp;iref=mpvideossearch"&gt;mexico&lt;/a&gt;

&lt;/li&gt;
&lt;li&gt;
&lt;b&gt;6 &lt;/b&gt;&lt;a href="http://search.cnn.com/search.jsp?query=marijuana&amp;type=news&amp;sortBy=date&amp;intl=false&amp;iref=mpvideossearch"&gt;marijuana&lt;/a&gt;
&lt;/li&gt;

&lt;li&gt;
&lt;b&gt;7 &lt;/b&gt;&lt;a href="http://search.cnn.com/search.jsp?query=casey anthony&amp;type=news&amp;sortBy=date&amp;intl=false&amp;iref=mpvideossearch"&gt;casey anthony&lt;/a&gt;
&lt;/li&gt;
&lt;li&gt;

&lt;b&gt;8 &lt;/b&gt;&lt;a href="http://search.cnn.com/search.jsp?query=china&amp;type=news&amp;sortBy=date&amp;intl=false&amp;iref=mpvideossearch"&gt;china&lt;/a&gt;
&lt;/li&gt;
&lt;li&gt;
&lt;b&gt;9 &lt;/b&gt;&lt;a href="http://search.cnn.com/search.jsp?query=india&amp;type=news&amp;sortBy=date&amp;intl=false&amp;iref=mpvideossearch"&gt;india&lt;/a&gt;

&lt;/li&gt;
&lt;li&gt;
&lt;b&gt;10 &lt;/b&gt;&lt;a href="http://search.cnn.com/search.jsp?query=nancy grace&amp;type=news&amp;sortBy=date&amp;intl=false&amp;iref=mpvideossearch"&gt;nancy grace&lt;/a&gt;
&lt;/li&gt;

&lt;/ol&gt;

								&lt;div class="cnnMpMore"&gt;&amp;nbsp;&lt;a href="/mostpopular/"&gt;more most popular &amp;#187;&lt;/a&gt;&lt;/div&gt;
							&lt;/div&gt;
						&lt;/div&gt;

					&lt;!-- /video content --&gt;
				&lt;/div&gt;
			&lt;div class="cnnWireBoxFooter"&gt;&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/mosaic/base_skins/baseplate/corner_wire_BL.gif" alt="" width="4" height="4"&gt;&lt;/div&gt;
		&lt;/div&gt;
	&lt;/div&gt;&lt;!-- /cnnMpVideo --&gt;

&lt;/div&gt;


&lt;div class="cnnMpPadTop"&gt;
	&lt;div id="cnnMpTopic-head" class="closed"&gt;
		&lt;div class="cnnWireRedBox"&gt;
			&lt;div class="cnnWireBoxHeader"&gt;&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/mosaic/base_skins/baseplate/corner_wire_TL.gif" alt="" width="4" height="4"&gt;&lt;/div&gt;

				&lt;div class="cnnBoxContent"&gt;
					&lt;div class="cnnMpCat"&gt;&lt;a href="javascript:cnnToggleMP('cnnMpTopic');"&gt;&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/sect/connect/topics_up.gif" width="59" height="8" border="0" alt=""&gt;&lt;/a&gt;&lt;/div&gt;
				&lt;/div&gt;
			&lt;div class="cnnWireBoxFooter"&gt;&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/mosaic/base_skins/baseplate/corner_wire_BL.gif" alt="" width="4" height="4"&gt;&lt;/div&gt;

		&lt;/div&gt;
		&lt;div class="cnnWireBox"&gt;
			&lt;div class="cnnWireBoxHeader"&gt;&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/mosaic/base_skins/baseplate/corner_wire_TL.gif" alt="" width="4" height="4"&gt;&lt;/div&gt;
				&lt;div class="cnnBoxContent"&gt;
					&lt;div class="cnnMpCat"&gt;&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/sect/connect/topics_dwn.gif" width="59" height="8" border="0" alt=""&gt;&lt;/div&gt;

				&lt;/div&gt;
		&lt;/div&gt;

	&lt;/div&gt;

	&lt;div id="cnnMpTopic"&gt;
		&lt;div class="cnnWireBox"&gt;

				&lt;div class="cnnBoxContent"&gt;
					&lt;!-- topic content  --&gt;
						&lt;div class="cnnMpTopicContent"&gt;
							&lt;ol&gt;
&lt;li&gt;
&lt;b&gt;1 &lt;/b&gt;&lt;a href="http://topics.cnn.com/topics/iraq?iref=mptopics"&gt;Iraq&lt;/a&gt;

&lt;/li&gt;
&lt;li&gt;
&lt;b&gt;2 &lt;/b&gt;&lt;a href="http://www.cnn.com/SPECIALS/2008/news/coldcases/?iref=mptopics"&gt;Nancy Grace's Cold Cases&lt;/a&gt;
&lt;/li&gt;
&lt;li&gt;
&lt;b&gt;3 &lt;/b&gt;&lt;a href="http://www.cnn.com/SPECIALS/2009/news/road.to.rescue/?iref=mptopics"&gt;Road to Rescue: The CNN Survival Guide&lt;/a&gt;

&lt;/li&gt;
&lt;li&gt;
&lt;b&gt;4 &lt;/b&gt;&lt;a href="http://topics.cnn.com/topics/blackwater_usa?iref=mptopics"&gt;Blackwater USA&lt;/a&gt;
&lt;/li&gt;
&lt;li&gt;
&lt;b&gt;5 &lt;/b&gt;&lt;a href="http://topics.cnn.com/topics/domestic_violence?iref=mptopics"&gt;Domestic Violence&lt;/a&gt;

&lt;/li&gt;
&lt;/ol&gt;

							&lt;div class="cnnMpMore"&gt;&amp;nbsp;&lt;a href="/mostpopular/"&gt;more most popular &amp;#187;&lt;/a&gt;&lt;/div&gt;
						&lt;/div&gt;

					&lt;!-- /topic content  --&gt;
				&lt;/div&gt;
			&lt;div class="cnnWireBoxFooter"&gt;&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/mosaic/base_skins/baseplate/corner_wire_BL.gif" alt="" width="4" height="4"&gt;&lt;/div&gt;
		&lt;/div&gt;
	&lt;/div&gt;&lt;!-- /cnnMpVideo --&gt;

&lt;/div&gt;

&lt;/div&gt;&lt;!-- /cnnMostPopMod --&gt;
&lt;script type="text/javascript"&gt; cnnInitMP(); &lt;/script&gt;
&lt;!--[if IE]&gt; &lt;script type="text/javascript"&gt; var cnnie = true; &lt;/script&gt; &lt;![endif]--&gt;


		&lt;/div&gt;&lt;/div&gt;&lt;!-- /cnnBoxContent --&gt;
		&lt;div class="cnnBoxFooter"&gt;&lt;div&gt;&lt;/div&gt;&lt;/div&gt;
	&lt;/div&gt;&lt;!-- /cnnWcLtgBox --&gt;

&lt;/div&gt;&lt;!-- /cnnMostPopMosaic --&gt;    &lt;style type="text/css"&gt;
&lt;!--
#cnnRightCol
{overflow:visible;}
--&gt;
&lt;/style&gt;

&lt;div class="cnnPad9Top"&gt;
	&lt;div id="cnn_cb336" &gt;
&lt;script language="JavaScript"&gt;

&lt;!--
var cnnPSproducts = "Partner Widget:CareerBuilder";
cnnProducts.push(cnnPSproducts);
//--&gt;&lt;/script&gt;
	&lt;a name="cnnCB"&gt;&lt;/a&gt;
	&lt;div id="cnn_content"&gt;
		&lt;div id="cnn_mainblock"&gt;
			&lt;a id="cnnLnkMopt" href="http://www.careerbuilder.com/JobSeeker/Jobs/jobfindadv.aspx?lr=cbcnn&amp;siteid=" onclick="var s=s_gi(s_account);s.linkTrackVars='events,products';s.linkTrackEvents='event2';s.events='event2';s.products=';Partner Widget:CareerBuilder;;;event2=1;evar23='+ cnnSectionName;void(s.tl(this,'o','CareerBuilder Clickthrough'));"&gt;&lt;img src="http://i.l.cnn.net/cnn/.element/img/2.0/content/partners/cb336/cb_logo.gif" alt="" width="139" height="33" border="0" /&gt;&lt;/a&gt;

			&lt;div id="cnn_maintext"&gt;
				&amp;bull; &lt;span class="text"&gt;&lt;a id="cnnLnkPt" href="http://part-time.careerbuilder.com/?lr=cbcnn&amp;siteid=" onclick="var s=s_gi(s_account);s.linkTrackVars='events,products';s.linkTrackEvents='event2';s.events='event2';s.products=';Partner Widget:CareerBuilder;;;event2=1;evar23='+ cnnSectionName;void(s.tl(this,'o','CareerBuilder Clickthrough'));"&gt;Part Time Jobs&lt;/a&gt;&lt;/span&gt;&lt;br /&gt;
				&amp;bull; &lt;span class="text"&gt;&lt;a id="cnnLnkSal" href="http://sales-marketing.careerbuilder.com/?lr=cbcnn&amp;siteid=" onclick="var s=s_gi(s_account);s.linkTrackVars='events,products';s.linkTrackEvents='event2';s.events='event2';s.products=';Partner Widget:CareerBuilder;;;event2=1;evar23='+ cnnSectionName;void(s.tl(this,'o','CareerBuilder Clickthrough'));"&gt;Sales &amp;amp; Marketing Jobs&lt;/a&gt;&lt;/span&gt;&lt;br /&gt;

				&amp;bull; &lt;span class="text"&gt;&lt;a id="cnnLnkCs" href="http://customer-service.careerbuilder.com/?lr=cbcnn&amp;siteid=" onclick="var s=s_gi(s_account);s.linkTrackVars='events,products';s.linkTrackEvents='event2';s.events='event2';s.products=';Partner Widget:CareerBuilder;;;event2=1;evar23='+ cnnSectionName;void(s.tl(this,'o','CareerBuilder Clickthrough'));"&gt;Customer Service Jobs&lt;/a&gt;&lt;/span&gt;&lt;br /&gt;
			&lt;/div&gt;
		&lt;/div&gt;
		&lt;div id="cnn_searchblock"&gt;

			Quick Job Search&lt;br /&gt;
			&lt;form id="myform1" action="http://www.CareerBuilder.com/PLI/QuickSrch.aspx" method="get" target="_top"&gt;
				&lt;input type="hidden" name="SiteID" value="cbcnn160" /&gt;
				&lt;input id="cnnLnkSiteID" type="hidden" name="lr" value="cbcnn" /&gt;
				&lt;input type="hidden" name="QSSTS" id="QSTS" value="ALL,US"&gt;
				&lt;input type="hidden" name="SearchBtn" value="Search" id="SearchBtn" /&gt;

				&lt;input type="hidden" name="MXJobSrchCriteria_JobTypes" id="MXJobSrchCriteria_JobTypes" value=""&gt;

				&lt;span class="cnnCB336Keyword"&gt;keyword(s):&lt;/span&gt;

				&lt;input type="text" name="QSKWD" class="CBkeywordBox" id="keyword" maxlength="25" /&gt;&lt;br /&gt;
				&lt;span class="cnnCB354CityTxt"&gt;enter city:&lt;/span&gt;

				&lt;input type="text" name="QSCTY" id="keyword" class="CBkeywordBox" maxlength="15" /&gt;&lt;br /&gt;
			&lt;/form&gt;
			&lt;div class="cnnPad1Top"&gt; &lt;/div&gt;
&lt;div id="dd"&gt;
										   &lt;table cellpadding="0" cellspacing="0"&gt;

											&lt;tr&gt;
											&lt;td&gt;
												&lt;div id="cnnDDCBState_wrap"&gt;&lt;/div&gt;
													&lt;form class="cnnHidden"&gt;
													&lt;select id="cnnDDCBState_list" onchange="$('QSTS').value=this.options[this.selectedIndex].value;"&gt;
														 &lt;option value="ALL,US"&gt;State&lt;/option&gt;

														 &lt;option disabled="disabled"&gt;-------&lt;/option&gt;
														 &lt;option value="AL,US"&gt;AL&lt;/option&gt;
														 &lt;option value="AK,US"&gt;AK&lt;/option&gt;
														 &lt;option value="AZ,US"&gt;AZ&lt;/option&gt;

														 &lt;option value="AR,US"&gt;AR&lt;/option&gt;
														 &lt;option value="CA,US"&gt;CA&lt;/option&gt;
														 &lt;option value="CO,US"&gt;CO&lt;/option&gt;
														 &lt;option value="CT,US"&gt;CT&lt;/option&gt;

														 &lt;option value="DE,US"&gt;DE&lt;/option&gt;
														 &lt;option value="DC,US"&gt;DC&lt;/option&gt;
														 &lt;option value="FL,US"&gt;FL&lt;/option&gt;
														 &lt;option value="GA,US"&gt;GA&lt;/option&gt;

														 &lt;option value="HI,US"&gt;HI&lt;/option&gt;
														 &lt;option value="ID,US"&gt;ID&lt;/option&gt;
														 &lt;option value="IL,US"&gt;IL&lt;/option&gt;
														 &lt;option value="IN,US"&gt;IN&lt;/option&gt;

														 &lt;option value="IA,US"&gt;IA&lt;/option&gt;
														 &lt;option value="KS,US"&gt;KS&lt;/option&gt;
														 &lt;option value="KY,US"&gt;KY&lt;/option&gt;
														 &lt;option value="LA,US"&gt;LA&lt;/option&gt;

														 &lt;option value="ME,US"&gt;ME&lt;/option&gt;
														 &lt;option value="MD,US"&gt;MD&lt;/option&gt;
														 &lt;option value="MA,US"&gt;MA&lt;/option&gt;
														 &lt;option value="MI,US"&gt;MI&lt;/option&gt;

														 &lt;option value="MN,US"&gt;MN&lt;/option&gt;
														 &lt;option value="MS,US"&gt;MS&lt;/option&gt;
														 &lt;option value="MO,US"&gt;MO&lt;/option&gt;
														 &lt;option value="MT,US"&gt;MT&lt;/option&gt;

														 &lt;option value="NE,US"&gt;NE&lt;/option&gt;
														 &lt;option value="NV,US"&gt;NV&lt;/option&gt;
														 &lt;option value="NH,US"&gt;NH&lt;/option&gt;
														 &lt;option value="NJ,US"&gt;NJ&lt;/option&gt;

														 &lt;option value="NM,US"&gt;NM&lt;/option&gt;
														 &lt;option value="NY,US"&gt;NY&lt;/option&gt;
														 &lt;option value="NC,US"&gt;NC&lt;/option&gt;
														 &lt;option value="ND,US"&gt;ND&lt;/option&gt;

														 &lt;option value="OH,US"&gt;OH&lt;/option&gt;
														 &lt;option value="OK,US"&gt;OK&lt;/option&gt;
														 &lt;option value="OR,US"&gt;OR&lt;/option&gt;
														 &lt;option value="PA,US"&gt;PA&lt;/option&gt;

														 &lt;option value="PR,US"&gt;PR&lt;/option&gt;
														 &lt;option value="RI,US"&gt;RI&lt;/option&gt;
														 &lt;option value="SC,US"&gt;SC&lt;/option&gt;
														 &lt;option value="SD,US"&gt;SD&lt;/option&gt;

														 &lt;option value="TN,US"&gt;TN&lt;/option&gt;
														 &lt;option value="TX,US"&gt;TX&lt;/option&gt;
														 &lt;option value="UT,US"&gt;UT&lt;/option&gt;
														 &lt;option value="VT,US"&gt;VT&lt;/option&gt;

														 &lt;option value="VA,US"&gt;VA&lt;/option&gt;
														 &lt;option value="WA,US"&gt;WA&lt;/option&gt;
														 &lt;option value="WV,US"&gt;WV&lt;/option&gt;
														 &lt;option value="WI,US"&gt;WI&lt;/option&gt;

														 &lt;option value="WY,US"&gt;WY&lt;/option&gt;
													&lt;/select&gt;
													&lt;/form&gt;
													&lt;script type="text/javascript"&gt; cnnDD.buildDropdown('cnnDDCBState', 73, 105, 10, "cnnDDWire"); &lt;/script&gt;

												&lt;/td&gt;&lt;td&gt;

													&lt;div id="cnnDDCBJob_wrap" class="cnnPad5Left"&gt;&lt;/div&gt;
													&lt;form class="cnnHidden"&gt;
													&lt;select id="cnnDDCBJob_list" onchange="$('MXJobSrchCriteria_JobTypes').value=this.options[this.selectedIndex].value;"&gt;
														&lt;option value=""&gt;Job type&lt;/option&gt;

														&lt;option disabled="disabled"&gt;-----------------&lt;/option&gt;
														&lt;option value="JN001"&gt;Accounting&lt;/option&gt;
														&lt;option value="JN002"&gt;Admin &amp; Clerical&lt;/option&gt;

														&lt;option value="JN054"&gt;Automotive&lt;/option&gt;
														&lt;option value="JN038"&gt;Banking&lt;/option&gt;
														&lt;option value="JN053"&gt;Biotech&lt;/option&gt;
														&lt;option value="JN047"&gt;Broadcast - Journalism&lt;/option&gt;

														&lt;option value="JN019"&gt;Business Development&lt;/option&gt;
														&lt;option value="JN043"&gt;Construction&lt;/option&gt;
														&lt;option value="JN020"&gt;Consultant&lt;/option&gt;
														&lt;option value="JN003"&gt;Customer Service&lt;/option&gt;

														&lt;option value="JN021"&gt;Design&lt;/option&gt;
														&lt;option value="JN027"&gt;Distribution - Shipping&lt;/option&gt;
														&lt;option value="JN031"&gt;Education&lt;/option&gt;
														&lt;option value="JN004"&gt;Engineering&lt;/option&gt;

														&lt;option value="JN022"&gt;Entry Level - New Grad&lt;/option&gt;
														&lt;option value="JN018"&gt;Executive&lt;/option&gt;
														&lt;option value="JN017"&gt;Facilities&lt;/option&gt;
														&lt;option value="JN005"&gt;Finance&lt;/option&gt;

														&lt;option value="JN006"&gt;General Business&lt;/option&gt;
														&lt;option value="JN051"&gt;General Labor&lt;/option&gt;
														&lt;option value="JN046"&gt;Government&lt;/option&gt;
														&lt;option value="JN055"&gt;Grocery&lt;/option&gt;

														&lt;option value="JN023"&gt;Healthcare&lt;/option&gt;
														&lt;option value="JN040"&gt;Hotel - Hospitality&lt;/option&gt;
														&lt;option value="JN007"&gt;Human Resources&lt;/option&gt;
														&lt;option value="JN008"&gt;Information Technology&lt;/option&gt;

														&lt;option value="JN056"&gt;Installation - Maint - Repair&lt;/option&gt;
														&lt;option value="JN034"&gt;Insurance&lt;/option&gt;
														&lt;option value="JN015"&gt;Inventory&lt;/option&gt;
														&lt;option value="JN030"&gt;Legal&lt;/option&gt;

														&lt;option value="JN041"&gt;Legal Admin&lt;/option&gt;
														&lt;option value="JN037"&gt;Management&lt;/option&gt;
														&lt;option value="JN029"&gt;Manufacturing&lt;/option&gt;
														&lt;option value="JN009"&gt;Marketing&lt;/option&gt;

														&lt;option value="JN050"&gt;Nurse&lt;/option&gt;
														&lt;option value="JN010"&gt;Other&lt;/option&gt;
														&lt;option value="JN049"&gt;Pharmacy&lt;/option&gt;
														&lt;option value="JN024"&gt;Professional Services&lt;/option&gt;

														&lt;option value="JN016"&gt;Purchasing - Procurement&lt;/option&gt;
														&lt;option value="JN025"&gt;QA - Quality Control&lt;/option&gt;
														&lt;option value="JN026"&gt;Research&lt;/option&gt;
														&lt;option value="JN035"&gt;Restaurant - Food Service&lt;/option&gt;

														&lt;option value="JN033"&gt;Retail&lt;/option&gt;
														&lt;option value="JN011"&gt;Sales&lt;/option&gt;
														&lt;option value="JN012"&gt;Science&lt;/option&gt;
														&lt;option value="JN013"&gt;Skilled Labor - Trades&lt;/option&gt;

														&lt;option value="JN028"&gt;Strategy - Planning&lt;/option&gt;
														&lt;option value="JN014"&gt;Supply Chain&lt;/option&gt;
														&lt;option value="JN048"&gt;Telecomm&lt;/option&gt;
														&lt;option value="JN032"&gt;Training&lt;/option&gt;

														&lt;option value="JN044"&gt;Transportation&lt;/option&gt;
														&lt;option value="JN045"&gt;Warehouse&lt;/option&gt;
													&lt;/select&gt;
													&lt;/form&gt;
												&lt;script type="text/javascript"&gt; cnnDD.buildDropdown('cnnDDCBJob', 90, 240, 10, "cnnDDWire"); &lt;/script&gt;

									   &lt;/td&gt;&lt;/tr&gt;&lt;/table&gt;
									   &lt;/div&gt;
		&lt;/div&gt;
	&lt;/div&gt;

	&lt;div id="cnn_bottom"&gt;

		&lt;span class="more"&gt;&lt;a id="cnnLnkMopt2" href="http://www.careerbuilder.com/JobSeeker/Jobs/jobfindadv.aspx?lr=cbcnn&amp;siteid=" onclick="var s=s_gi(s_account);s.linkTrackVars='events,products';s.linkTrackEvents='event2';s.events='event2';s.products=';Partner Widget:CareerBuilder;;;event2=1;evar23='+ cnnSectionName;void(s.tl(this,'o','CareerBuilder Clickthrough'));"&gt;more options &amp;raquo;&lt;/a&gt;&lt;/span&gt;
		&lt;a href="#cnnCB" onClick="var s=s_gi(s_account);s.linkTrackVars='events,products';s.linkTrackEvents='event2';s.events='event2';s.products=';Partner Widget:CareerBuilder;;;event2=1;evar23='+ cnnSectionName;void(s.tl(this,'o','CareerBuilder Clickthrough'));document.getElementById('myform1').submit();"&gt;&lt;img class="cnnCBSearchBtn" src="http://i.l.cnn.net/cnn/.element/img/2.0/content/partners/cb/btn_search.gif" border="0"&gt;&lt;/a&gt;
	&lt;/div&gt;

&lt;/div&gt;

&lt;script type="text/javascript"&gt;cnnSetCBVars();&lt;/script&gt;
&lt;/div&gt;  &lt;div id="relatedBox" style="display:none;"&gt;&lt;/div&gt;&lt;div id="sectionRelateds"&gt;&lt;/div&gt;&lt;/div&gt;&lt;div class="cnnRRBoxFooter"&gt;&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/mosaic/base_skins/right_rail/grey_corner_BL.gif" width="4" height="4" border="0" alt=""&gt;&lt;/div&gt;&lt;/div&gt;&lt;/div&gt;&lt;div class="clear"&gt;&lt;img src="http://i.cdn.turner.com/cnn/images/1.gif" width="1" height="1" border="0" alt=""&gt;&lt;/div&gt;&lt;/div&gt;&lt;div class="cnnWCBoxFooter"&gt;&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/mosaic/base_skins/baseplate/corner_dg_BL.gif" width="4" height="4" border="0" alt=""&gt;&lt;/div&gt;&lt;/div&gt;&lt;/div&gt;  &lt;/div&gt;&lt;!-- /cnnContentContainer --&gt; &lt;div id="csiIframe" style="visibility:hidden;height:0px;width:0px;"&gt;&lt;/div&gt;  &lt;div id="cnnFooter"&gt;

	&lt;div class="cnnFooterBox"&gt;
		&lt;div class="cnnFooterBoxHeader"&gt;&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/global/nav/footer/corner_footer_tl.gif" id="cnnFootCnrTL" width="4" height="4" border="0" alt=""&gt;&lt;/div&gt;
		&lt;div class="cnnFooterBoxContent"&gt;
			&lt;div class="cnnGFooterBoxLinksTop"&gt;&lt;a href="/" title="Breaking News, U.S., World Weather Entertainment and Video News from CNN.com"&gt;Home&lt;/a&gt; &amp;nbsp;|&amp;nbsp; &lt;a href="/WORLD/" title="World News International Headlines Stories and Video from CNN.com"&gt;World&lt;/a&gt; &amp;nbsp;|&amp;nbsp; &lt;a href="/US/" title="U.S. News Headlines Stories and Video from CNN.com"&gt;U.S.&lt;/a&gt; &amp;nbsp;|&amp;nbsp; &lt;a href="/POLITICS/" title="Politics News from CNN.com"&gt;Politics&lt;/a&gt; &amp;nbsp;|&amp;nbsp; &lt;a href="/CRIME/" title="Crime News Courts Celebrity Docket and Law News from CNN.com"&gt;Crime&lt;/a&gt; &amp;nbsp;|&amp;nbsp; &lt;a href="/SHOWBIZ/" title="Entertainment News Celebrities Movies and TV from CNN.com"&gt;Entertainment&lt;/a&gt; &amp;nbsp;|&amp;nbsp; &lt;a href="/HEALTH/" title="Health News Medicine Diet Fitness and Parenting from CNN.com"&gt;Health&lt;/a&gt; &amp;nbsp;|&amp;nbsp; &lt;a href="/TECH/" title="Technology Computers Internet and Personal Tech News from CNN.com"&gt;Tech&lt;/a&gt; &amp;nbsp;|&amp;nbsp; &lt;a href="/TRAVEL/" title="Travel News Vacations Destinations and Video from CNN.com"&gt;Travel&lt;/a&gt; &amp;nbsp;|&amp;nbsp; &lt;a href="/LIVING/" title="Living News Personal Work and Home from CNN.com"&gt;Living&lt;/a&gt; &amp;nbsp;|&amp;nbsp; &lt;a href="/money/?cnn=yes" title="Business financial personal finance news from CNNMoney"&gt;Business&lt;/a&gt; &amp;nbsp;|&amp;nbsp; &lt;a href="http://sportsillustrated.cnn.com/?cnn=yes" title="Breaking news real-time scores and daily analysis from Sports Illustrated SI.com"&gt;Sports&lt;/a&gt; &amp;nbsp;|&amp;nbsp; &lt;a href="http://www.time.com/?cnn=yes" title="Analysis Opinion, Multimedia and Blogs TIME"&gt;Time.com&lt;/a&gt;&lt;/div&gt;

			&lt;div class="cnnGFooterBoxLinksBot"&gt;&lt;a href="/tools/index.html" title="Tools and Extras Widgets and downloads from CNN.com"&gt;Tools &amp;amp; Widgets&lt;/a&gt; &amp;nbsp;|&amp;nbsp; &lt;a href="/services/podcasting/" title="Podcasting from CNN.com"&gt;Podcasts&lt;/a&gt; &amp;nbsp;|&amp;nbsp; &lt;a href="/exchange/blogs/" title="Blogs from CNN.com"&gt;Blogs&lt;/a&gt; &amp;nbsp;|&amp;nbsp; &lt;a href="/togo/" title="Mobile Services from CNN.com"&gt;CNN Mobile&lt;/a&gt; &amp;nbsp;|&amp;nbsp; &lt;a href="/profile/"&gt;My Profile&lt;/a&gt;&amp;nbsp;|&amp;nbsp; &lt;a href="/profile/?view=alert" title="Email Alerts from CNN.com"&gt;E-mail Alerts&lt;/a&gt; &amp;nbsp;|&amp;nbsp; &lt;a href="javascript:CNN_openPopup('/audio/radio/preferences.html','radioplayer','toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,width=360,height=573');"&gt;CNN Radio&lt;/a&gt; &amp;nbsp;|&amp;nbsp; &lt;a href="/shop/" title="The Turner Store Online"&gt;CNN Shop&lt;/a&gt; &amp;nbsp;|&amp;nbsp; &lt;a href="/sitemap/" title="CNN.com's Sitemap"&gt;Site Map&lt;/a&gt; &lt;/div&gt;

			&lt;div class="cnnGFooterBoxSearch"&gt;
	&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/global/nav/footer/footer_google_logo.gif" class="cnnSrchDomLogo" width="47" height="22" border="0" alt=""&gt;
	&lt;form action="http://search.cnn.com/cnn/search" method="get" onsubmit="return cnnFootSearch(this);"&gt;
		&lt;input type="text" class="cnnGFooterSearchField" id="cnnFootSrchTxt"&gt;
		&lt;input type="image" src="http://i.cdn.turner.com/cnn/.element/img/2.0/global/nav/footer/footer_search_btn.gif" border="0" alt="Submit" class="cnnGFooterSearchBtn"&gt;
	&lt;/form&gt;

&lt;/div&gt;
&lt;div class="cnnCrumb"&gt;&lt;img src="http://i.cdn.turner.com/cnn/images/1.gif" alt="" width="1" height="1"&gt;&lt;/div&gt;

		&lt;/div&gt; &lt;!-- /cnnFooterBoxContent --&gt;
		&lt;div class="cnnFooterBoxFooter"&gt;&lt;img src="http://i.cdn.turner.com/cnn/.element/img/2.0/global/nav/footer/corner_footer_bl.gif" id="cnnFootCnrBL" width="4" height="4" border="0" alt=""&gt;&lt;/div&gt;

	&lt;/div&gt;&lt;!-- /cnnFooterBox --&gt;
	&lt;div class="cnnFooterSub"&gt;
		&lt;div class="cnnFootRight"&gt;
			&lt;div&gt;&lt;a href="/espanol/"&gt;CNN en Espa&amp;#328;ol&lt;/a&gt; &amp;nbsp;|&amp;nbsp; &lt;a href="http://arabic.cnn.com" target="new"&gt;Arabic&lt;/a&gt; &amp;nbsp;|&amp;nbsp; &lt;a href="http://www.cnn.co.jp" target="new"&gt;Japanese&lt;/a&gt; &amp;nbsp;|&amp;nbsp; &lt;a href="http://news.joins.com/cnn" target="new"&gt;Korean&lt;/a&gt; &amp;nbsp;|&amp;nbsp; &lt;a href="http://www.cnnturk.com" target="new"&gt;Turkish&lt;/a&gt;&lt;/div&gt;

			&lt;div class="cnnPad9Top"&gt;&lt;a href="http://edition.cnn.com"&gt;International Edition&lt;/a&gt; &amp;nbsp;|&amp;nbsp; &lt;a href="/CNN/Programs/"&gt;CNN TV&lt;/a&gt; &amp;nbsp;|&amp;nbsp; &lt;a href="/CNNI/"&gt;CNN International&lt;/a&gt; &amp;nbsp;|&amp;nbsp; &lt;a href="/HLN/"&gt;HLN&lt;/a&gt; &amp;nbsp;|&amp;nbsp; &lt;a href="/TRANSCRIPTS/"&gt;Transcripts&lt;/a&gt;&lt;/div&gt;

		&lt;/div&gt;
		&lt;div class="cnnFootLeft"&gt;
			&lt;div&gt;&amp;copy; 2009 Cable News Network. &lt;a href="/tbs/index.html"&gt;Turner Broadcasting System, Inc.&lt;/a&gt; All Rights Reserved.&lt;/div&gt;
			&lt;div class="cnnPad9Top"&gt;&lt;a href="/interactive_legal.html" rel="nofollow"&gt;Terms of service&lt;/a&gt; &amp;nbsp;|&amp;nbsp; &lt;a href="/privacy.html" rel="nofollow"&gt;Privacy guidelines&lt;/a&gt; &amp;nbsp;|&amp;nbsp; &lt;a href="/services/advertise/main.html" rel="nofollow"&gt;Advertise with us&lt;/a&gt; &amp;nbsp;|&amp;nbsp; &lt;a href="/about/"&gt;About us&lt;/a&gt; &amp;nbsp;|&amp;nbsp; &lt;a href="/feedback/" rel="nofollow"&gt;Contact us&lt;/a&gt; &amp;nbsp;|&amp;nbsp; &lt;a href="/help/" rel="nofollow"&gt;Help&lt;/a&gt;&lt;/div&gt;

		&lt;/div&gt;
		&lt;div class="clear"&gt;&lt;img src="http://i.cdn.turner.com/cnn/images/1.gif" width="1" height="1" border="0" alt=""&gt;&lt;/div&gt;
	&lt;/div&gt;
&lt;/div&gt;&lt;!-- /cnnFooter --&gt;
&lt;script src="http://content.dl-rms.com/rms/mother/5721/nodetag.js"&gt;&lt;/script&gt;


&lt;script type="text/javascript"&gt;
if(typeof(StorageManager) != 'function') {
    document.write("&lt;script src=\"http://i.cdn.turner.com/cnn/.element/js/2.0/StorageManager.js\" type=\"text/javascript\"&gt;&lt;\/script&gt;");
}
if(typeof(ms_QueueManager) != 'function') {
    document.write("&lt;script src=\"http://i.cdn.turner.com/cnn/.element/js/2.0/connect/connect/queueManager.js\" type=\"text/javascript\"&gt;&lt;\/script&gt;");
}
if(typeof(scrubMrv) != 'function') {
    document.write("&lt;script src=\"http://i.cdn.turner.com/cnn/.element/js/2.0/connect/utils.js\" type=\"text/javascript\"&gt;&lt;\/script&gt;");
}
&lt;/script&gt;
&lt;script type="text/javascript" src="http://i.cdn.turner.com/cnn/.element/js/2.0/connect/utils.js"&gt;&lt;/script&gt;

&lt;script type="text/javascript" src="http://i.cdn.turner.com/cnn/.element/js/2.0/connect/connectMrv.js"&gt;&lt;/script&gt;
&lt;script type="text/javascript"&gt;
var cnnURL_toSave = document.URL + '';
if(document.domain != 'cnn.com') document.domain = 'cnn.com';

if((cnnURL_toSave.indexOf('/profile/') &lt; 0) &amp;&amp; (cnnURL_toSave.indexOf(location.host + '/POLITICS/') &lt; 0) &amp;&amp; pagetypeTS != 'homepage') { 

	var saveMrvQm = new ms_QueueManager();
	saveMrvQm.init('saveMrvFrame', 'http://audience.cnn.com/services/cnn/blank.api?callback=saveMrvQm.setIframeReady');

	Event.observe(window, 'load', function() {
		window.setTimeout("cnn_SaveMRV()",200);
	});

}
	
function cnn_SaveMRV() {
	saveMrvUrl(document.title, cnnURL_toSave);
}
&lt;/script&gt;
&lt;div id='saveMrvFrame'&gt;&lt;/div&gt;


&lt;script language="javascript" type="text/javascript"&gt; var cnnBrandingValue = "sprj.first.100.days"; var cnnOmniBranding = "44th President: First 100 Days";&lt;/script&gt; &lt;img src="http://i.cdn.turner.com/cnn/images/1.gif" alt="" id="TargetImage" name="TargetImage" width="1" height="1" onLoad="getAdHeadCookie(this)"&gt;&lt;img src="http://i.cdn.turner.com/cnn/images/1.gif" alt="" id="TargetImageDE" name="TargetImageDE" width="1" height="1" onLoad="getDEAdHeadCookie(this)"&gt;&lt;img src="http://i.cdn.turner.com/cnn/images/1.gif" alt="" width="0" height="0" border="0" vspace="0" hspace="0" name="cookieCrumb" id="cookieCrumb" align="right"&gt;

&lt;!-- SiteCatalyst code --&gt;
&lt;script language="JavaScript" src="http://i.cdn.turner.com/cnn/.element/js/2.0/s_code.js"&gt;&lt;/script&gt;

&lt;script language="JavaScript"&gt;
&lt;!--
var cnnWinLoc = window.location.pathname;
var cnnWinExtra = window.location.hash;
var cnnWinLocRegExp = /\/$/; 
var cnnWinExtraRegExp = /\?/;
if(cnnWinLocRegExp.test(cnnWinLoc)){cnnWinLoc = cnnWinLoc + "index.html";}
if(cnnWinExtra != "undefined") {
if(cnnWinExtraRegExp.test(cnnWinExtra)){var cnnOmniExtra = cnnWinExtraRegExp.split(cnnWinExtra);cnnWinLoc = cnnWinLoc + cnnOmniExtra[0];}
else {cnnWinLoc = cnnWinLoc + cnnWinExtra;}}
if (typeof(cnnPageName) != "undefined") {s.pageName = cnnPageName;s.eVar1 = cnnPageName;} else {s.pageName = cnnWinLoc;s.eVar1 = cnnWinLoc;}
if (typeof(cnnSectionName) != "undefined") {s.channel=cnnSectionName;s.eVar2=cnnSectionName;} else {s.channel="Nonlabeled";s.eVar2="Nonlabeled";}
if (typeof(cnnSubSectionName) != "undefined") {s.server=cnnSubSectionName;s.eVar3=cnnSubSectionName;} else {s.server="";s.eVar3="";}
if (typeof(cnnSectionFront) != "undefined") {s.prop1=cnnSectionFront;} 
if (typeof(cnnContentType) != "undefined") {s.prop4=cnnContentType;s.prop6=s.pageName;}

/************* DO NOT ALTER ANYTHING BELOW THIS LINE ! **************/
if (typeof(cnnMosaicDetect) == "undefined")
{var s_code=s.t();if(s_code)document.write(s_code)}//--&gt; &lt;/script&gt;
&lt;script language="JavaScript"&gt;&lt;!--
if(navigator.appVersion.indexOf('MSIE')&gt;=0)document.write(unescape('%3C')+'\!-'+'-')
//--&gt;&lt;/script&gt;&lt;noscript&gt;&lt;img
src="http://metrics.cnn.com/b/ss/cnn2global/1/H.1--NS/0?pageName=No%20Javascript"
height="1" width="1" border="0" alt="" /&gt;&lt;/noscript&gt;&lt;!--/DO NOT REMOVE/--&gt;
&lt;!-- End SiteCatalyst code --&gt;

&lt;noscript&gt;
	&lt;style type="text/css"&gt;
		#cnnTxtCmpnt {display:block;}
		#cnnTabNav {display:none;}
 	&lt;/style&gt;
&lt;/noscript&gt;

&lt;/div&gt;&lt;script type="text/javascript"&gt;cnnInitOverlay();&lt;/script&gt;&lt;/body&gt;&lt;/html&gt;</description>

</item>

</channel>
</rss>

```