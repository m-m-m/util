/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Contains utilities for dealing with date and time.
 * <h2>Date/Time Utilities</h2>
 * The JDK comes with rich support for date and time. On the other side the
 * APIs to do so are very poor. This package contains utilities to help you 
 * dealing with date and time.<br>
 * The {@link java.text.SimpleDateFormat} is nice but unfortunately NOT 
 * thread-safe. For representation of date and time there is a common 
 * standard with ISO 8601. Therefore {@link net.sf.mmm.util.date.Iso8601Util}
 * comes with support to format and parse date and time according to this standard. 
 * It is very fast and thread-safe.
 */
package net.sf.mmm.util.date;

