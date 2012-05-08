/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.api;

import java.util.Map;

/**
 * This is the abstract base interface for a container of a wrapped stream together with metadata that is
 * detected while streaming the data.<br>
 * If a reasonable application transfers data from a source to a sink it does NOT load the entire data into
 * memory but uses streams ( {@link java.io.InputStream} and {@link java.io.OutputStream}). A typical example
 * is a file that is uploaded via HTTP and written into a database. In many situations it is desirable to get
 * metadata (e.g. mimetype, md5sum, etc.) about the actual data. Most solutions to detect metadata need random
 * access to seek in the data. Therefore you would need to save the data to a temporary file, analyze it and
 * then transfer the file to the database.<br>
 * The {@link DetectorStream} allows you to get the metadata on the fly while streaming that data. All you
 * need to do is to create a wrapper on your stream and perform your actual transfer on the wrapper stream
 * instead. This interface is the container for the wrapper stream.<br>
 * This approach requires that you read/write your data completely (at least until the detection is
 * {@link #isDone() done}).<br>
 * A {@link DetectorStream} is typically used to get the actual wrapper stream, read/write the stream data and
 * then {@link #getMetadata() get the metadata}. After this, the object is useless and can be disposed by the
 * garbage collector.
 * 
 * @see DetectorStreamProvider
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public abstract interface DetectorStream {

  /**
   * This method gets the context with the detected metadata. It is immutable and should NOT be modified. This
   * method should NOT be called, before this detector stream is {@link #isDone() done}.<br>
   * For the keys to use in the metadata {@link Map} have a look at
   * <code>net.sf.mmm.util.metakey.api.MetakeyCore</code>.
   * 
   * @return the metadata.
   */
  Map<String, Object> getMetadata();

  /**
   * This method determines if the detection is done. If the stream has been processed to the end this method
   * will always return <code>true</code>. However the detection may be done before the end of the stream was
   * reached (e.g. because all {@link #getMetadata() metadata} comes from the header of the data).
   * 
   * @return <code>true</code> if the metadata has been completely been collected, <code>false</code>
   *         otherwise.
   */
  boolean isDone();

}
