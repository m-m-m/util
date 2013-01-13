/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Provides the API for utilities that help to deal with date and time.
 * <a name="documentation"/><h2>Date-Util API</h2>
 * This package contains the API for utilities that help to deal with date and
 * time.
 * For regular representation of date there are the common java datatypes
 * {@link java.util.Date} and {@link java.util.Calendar}. However, they are
 * totally awkward. A good alternative is joda time. Rescue will come with JSR 310
 * that unfortunately did not make it into java 7.
 * Please also
 * note the existence of {@link javax.xml.datatype.Duration} and
 * {@link javax.xml.datatype.DatatypeFactory} as well as
 * {@link java.util.concurrent.TimeUnit}.
 * However for additional purposes this package offers further datatypes.
 */
package net.sf.mmm.util.date.api;

