Spinn3r 3.1 implements a new feature for server-side filtering of API results.

We match results against a boolean expression on our end and only send you the
matching documents.  This can significantly save on bandwidth and indexing costs
as well as making the API easier to work with.

This code requires a version of the Spinn3r reference client greater than `3.1.15`.

# Language #

The language uses a domain specific language similar to Lisp or s-expressions.  All operations use [prefix notation](http://en.wikipedia.org/wiki/Polish_notation).

## Operators ##

We support the operators `and`, `or`, `eq`, `contains`

## and ##

Both expressions must be true

## or ##

Either expressions must be true.

## eq ##

the given variable must equal the given scalar.

## contains ##

the given variable must contain the given scalar.

## not ##

Does not match the following expression.

# Examples #

## Filtering by Language ##

```
(contains dc:lang "en")
```

## Allowing all language other than english ##

```
(not (contains dc:lang "en"))
```


## Advanced ##

Here are some advanced examples:

### Finding Microblog content from Twitter ###

```

(and (eq source:publisher_type "MICROBLOG") (contains source:resource "twitter.com"))

```

## Command Line Usage ##

The command line supports a

` --remote-filter`

option which allows you to pass a filter to be called on our servers.

```

    java \
    -Xmx512M \
    -cp spinn3r-client-3.1.15-rc8.jar \
    com.spinn3r.api.Main \
    --vendor=VENDOR_CODE \
    --api=comment \
    --remote-filter='(eq dc:lang "it")' 

```