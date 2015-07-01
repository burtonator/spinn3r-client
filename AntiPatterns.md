# Introduction #

Over the years we have seen customers make a lot of mistakes.  These seem to happen often and we want to codify that these are not supported.

99% of these are solved by the Spinn3r client but some of these we can't enforce in the client.

# Network Attached Storage #

Don't use any form of network attached storage.  The latencies can be very high and can impact the throughput of the client.

# Crontab #

Don't put the client in a crontab entry starting and stopping.  It will eventually have problems recovering.  Make sure the client is connected 24/7 and up to date.

# Reading stale data #

Make sure to constantly fetch the most recent data.  If you're an hour or two behind.  You're eventually going to fall behind entirely and start breaking due to the inability to catch up.

# Plenty of bandwidth #

10Mbit is probably sufficient bandwidth.  However, it's best to make sure you have a LOT more available.  50-100Mbit is more appropriate.  This way if there is a network issue and you need to catch up, you can do so quickly.