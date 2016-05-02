/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Provides the API for utilities that help to deal with date and time.
 * <a name="documentation"></a><h2>Util Date API</h2>
 * This package contains the API for utilities that help to deal with date and time.
 * The JDK comes with rich support for date and time. On the other side the APIs like {@link java.util.Date} and
 * {@link java.util.Calendar} are very poor. This problem has been solved with Java 8 and JSR310. Where ever possible we
 * strongly encourage you to use JSR310 ({@code java.time.*}).
 * However until then and for some other gaps you might find helpful types in this package. <br>
 * E.g. representation of date and time there is a common standard with ISO 8601.
 * Therefore {@link net.sf.mmm.util.date.api.Iso8601Util} comes with support to format and parse date and time
 * according to this standard in a stateless and thread-safe way. Further for for simplifying performance measurement
 * there is {@link net.sf.mmm.util.date.api.TimeMeasure} that makes your life easier.
 */
package net.sf.mmm.util.date.api;

