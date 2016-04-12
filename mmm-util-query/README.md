# Mature Modular Meta-framework (mmm)

![logo](https://raw.github.com/m-m-m/mmm/master/src/site/resources/images/logo.png)

**Welcome to the wonderful world of [mmm](http://m-m-m.sourceforge.net/index.html)!**

## mmm-util-query

This module is part of the [mmm-util project](../README.md) and allows to build query statements in a type-safe and secure way with a fluent API
* [Generic Query API](http://m-m-m.github.io/maven/apidocs/net/sf/mmm/util/query/api/package-summary.html#package.description) with the basic features of all SQL dialects.
* [Feature API](http://m-m-m.github.io/maven/apidocs/net/sf/mmm/util/query/api/feature/package-summary.html#package.description) for ultimate flexibility, extension and reuse.
* Support for many databases and [SQL dialects](http://m-m-m.github.io/maven/apidocs/net/sf/mmm/util/query/base/SqlDialect.html) such as [JPQL](http://m-m-m.github.io/maven/apidocs/net/sf/mmm/util/query/api/jpql/package-summary.html#package.description), [OrientDB](http://m-m-m.github.io/maven/apidocs/net/sf/mmm/util/query/api/orientdb/package-summary.html#package.description), etc.
* and much more...

In general you might want to use [QueryDSL](http://www.querydsl.com/).
However, if you are using [mmm-util-property](../mmm-util-property/README.md) then mmm-util-query will make your life easier as you neither need code-generation nor dynamic asm code using Alias.$.