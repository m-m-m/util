/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.api;

/**
 * This is the interface for a provider of data bytes (a <code>byte[]</code> -Buffer) that is
 * {@link ByteIterator iterable} and {@link ByteProcessable processable}. <br>
 * Bytes that are {@link #process(ByteProcessor, long) processed} will be consumed (as if they were
 * {@link #next() iterated}).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public interface ProcessableByteArrayBuffer extends ByteBuffer, ByteProcessable {

  // nothing to add, just a combination
}
