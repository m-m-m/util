/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Contains utilities for reading and writing data typically using streams.
 * <h2>Input/Output Utilities</h2>
 * Dealing with {@link java.io.InputStream input-} and 
 * {@link java.io.OutputStream output-streams} is NOT an easy task. This package
 * provides {@link net.sf.mmm.util.io.StreamUtil utilities} that make it easier 
 * to deal with streams, readers and writers. If a method specifies that a 
 * stream is closed, this is guaranteed on success as well as in an exceptional 
 * state so your server-application does NOT run out of file-handles.<br>
 */
package net.sf.mmm.util.io;

