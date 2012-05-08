/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.api;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * This is the interface of a service that provides {@link DetectorStream detector-streams}. <br>
 * Typical usage might look like this (the cast is a little stupid but just to make it clear):
 * 
 * <pre>
 *   {@link DetectorStreamProvider} provider = ...
 *   OutputStream outStream = ...
 * 
 *   //writeData(outStream);
 *   //outStream.close();
 * 
 *   {@link DetectorOutputStream} detectorOutStream = provider.wrap(outStream, detector);
 *   {@link OutputStream} wrappedOutStream = detectorOutStream.{@link DetectorOutputStream#getStream() getStream()};
 *   writeData(wrappedOutStream);
 *   wrappedOutStream.close();
 * 
 *   {@link Map}&lt;String, Object&gt; metadata = detectorOutStream.{@link DetectorStream#getMetadata() getMetadata()}; 
 *   Long bytesWritten = metadata.{@link Map#get(Object) get}("filesize");
 * </pre>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public interface DetectorStreamProvider {

  /**
   * This method gets a {@link DetectorInputStream} that wrapps the given <code>stream</code>.
   * 
   * @param stream is the stream to wrap. This stream must be a fresh stream that is untouched since it was
   *        opened.
   * @return the wrapped input stream.
   */
  DetectorInputStream wrapInputStream(InputStream stream);

  /**
   * This method gets a {@link DetectorInputStream} that wraps the given <code>stream</code>. In addition to
   * {@link #wrapInputStream(InputStream)} an additional parameter <code>metadata</code> is supplied. If this
   * context contains metadata values, that are mutable, the stream wrapper manipulates the data such that the
   * given values correspond to the data.<br>
   * E.g. if the metadata contains a title and a genre and the stream points to the data of an mp3 song, the
   * given title and genre are "written" to the ID3 tag of the song (if supported by the implementation).<br>
   * If the metadata contains values that are immutable and NOT compatible with the detected values (e.g.
   * mimetype=text/plain is supplied, but mimetype is audio/midi) then the value will simple be overridden in
   * the detected {@link DetectorStream#getMetadata() metadata}.<br>
   * If the metadata contains values that are unknown to the detector implementation (e.g. foo=bar), these
   * values will also be untouched and are also available in the detected {@link DetectorStream#getMetadata()
   * metadata}.
   * 
   * @param stream is the stream to wrap. This stream must be a fresh stream that is untouched since it was
   *        opened.
   * @param metadata is the existing metadata to apply.
   * @return the wrapped input stream.
   */
  DetectorInputStream wrapInputStream(InputStream stream, Map<String, Object> metadata);

  /**
   * This method gets a {@link DetectorOutputStream} that wraps the given <code>stream</code>.
   * 
   * @param stream is the stream to wrap. This stream must be a fresh stream that is untouched since it was
   *        opened.
   * @return the wrapped output stream.
   */
  DetectorOutputStream wrapOutputStream(OutputStream stream);

  /**
   * This method gets a {@link DetectorOutputStream} that wraps the given <code>stream</code>. In addition to
   * {@link #wrapOutputStream(OutputStream)} an additional parameter <code>metadata</code> is supplied. If
   * this context contains metadata values, that are mutable, the stream wrapper manipulates the data such
   * that the given values correspond to the data.<br>
   * E.g. if the metadata contains a title and a genre and the stream points to the data of an mp3 song, the
   * given title and genre are written to the ID3 tag of the song (if supported by the implementation).<br>
   * If the metadata contains values that are immutable and NOT compatible with the detected values (e.g.
   * mimetype=text/plain is supplied, but mimetype is audio/midi) then the value will simple be overridden in
   * the detected {@link DetectorStream#getMetadata() metadata}.<br>
   * If the metadata contains values that are unknown to the detector implementation (e.g. foo=bar), these
   * values will also be untouched and are also available in the detected {@link DetectorStream#getMetadata()
   * metadata}.
   * 
   * @param stream is the stream to wrap. This stream must be a fresh stream that is untouched since it was
   *        opened.
   * @param metadata is the existing metadata to apply.
   * @return the wrapped output stream.
   */
  DetectorOutputStream wrapOutputStream(OutputStream stream, Map<String, Object> metadata);

}
