/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Provides the API for utilities that help to read and write data, process 
 * bytes, etc.
 * <a name="documentation"/><h2>IO-Util API</h2>
 * Dealing with {@link java.io} is NOT an easy task. This package
 * provides utilities that make it easier to deal with 
 * {@link java.io.InputStream}s, {@link java.io.OutputStream}s, 
 * {@link java.io.Reader}s and {@link java.io.Writer}s as well as handling 
 * encodings. If a method specifies that a stream is closed, this is guaranteed 
 * on success as well as in an exceptional state so your server-application 
 * does NOT run out of file-handles.<br>
 */
package net.sf.mmm.util.io.api;

