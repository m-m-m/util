/* $Id$ */
package net.sf.mmm.streamdetect.api;

import java.io.InputStream;
import java.io.OutputStream;

import net.sf.mmm.context.api.ContextIF;

/**
 * This is the interface of a service that provides
 * {@link DetectorStreamIF detector-streams}. <br>
 * Typical usage might look like this (the cast is a little stupid but just to
 * make it clear):
 * 
 * <pre>
 *   {@link StreamDetectorServiceIF} service = ...
 *   {@link StreamDetectorIF} detector = ...
 *   OutputStream outStream = ...
 * 
 *   //writeData(outStream);
 *   //outStream.close();
 * 
 *   OutputStream wrapperOutStream = service.wrap(outStream, detector);
 *   writeData(wrapperOutStream);
 *   wrapperOutStream.close();
 * 
 *   long bytesWritten = ((SizeStreamDetectorIF) detector).getSize();
 * </pre>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface DetectorStreamProviderIF {

    /**
     * This method gets a {@link DetectorInputStreamIF} that wrapps the given
     * <code>stream</code>.
     * 
     * @param stream
     *        is the stream to wrap. This stream must be a fresh stream that is
     *        untouched since it was opened.
     * @return the wrapped input stream.
     */
    DetectorInputStreamIF wrapInputStream(InputStream stream);

    /**
     * This method gets a {@link DetectorInputStreamIF} that wrapps the given
     * <code>stream</code>. In addition to
     * {@link #wrapInputStream(InputStream)} an additional parameter
     * <code>metadata</code> is supplied. If this context contains metadata
     * values, that are mutable, the stream wrapper manipulates the data such
     * that the given values correspond to the data.<br>
     * E.g. if the metadata contains a title and a genre and the stream points
     * to the data of an mp3 song, the given title and genre are "written" to
     * the ID3 tag of the song (if supported by the implementation).<br>
     * If the metadata contains values that are immutable and NOT compatible
     * with the detected values (e.g. mimetype=text/plain is supplied, but
     * mimetype is audio/midi) then the value will simple be overriden in the
     * detected {@link DetectorStreamIF#getMetadata() metadata}.<br>
     * If the metadata contains values that are unknown to the detector
     * implementation (e.g. foo=bar), these values will also be untouched and
     * are also available in the detected
     * {@link DetectorStreamIF#getMetadata() metadata}.
     * 
     * @param stream
     *        is the stream to wrap. This stream must be a fresh stream that is
     *        untouched since it was opened.
     * @param metadata
     *        is the existing metadata already available.
     * @return the wrapped input stream.
     */
    DetectorInputStreamIF wrapInputStream(InputStream stream, ContextIF metadata);

    /**
     * This method gets a {@link DetectorOutputStreamIF} that wrapps the given
     * <code>stream</code>.
     * 
     * @param stream
     *        is the stream to wrap. This stream must be a fresh stream that is
     *        untouched since it was opened.
     * @return the wrapped output stream.
     */
    DetectorOutputStreamIF wrapOutputStream(OutputStream stream);

    /**
     * This method gets a {@link DetectorOutputStreamIF} that wrapps the given
     * <code>stream</code>. In addition to
     * {@link #wrapOutputStream(OutputStream)} an additional parameter
     * <code>metadata</code> is supplied. If this context contains metadata
     * values, that are mutable, the stream wrapper manipulates the data such
     * that the given values correspond to the data.<br>
     * E.g. if the metadata contains a title and a genre and the stream points
     * to the data of an mp3 song, the given title and genre are written to the
     * ID3 tag of the song (if supported by the implementation).<br>
     * If the metadata contains values that are immutable and NOT compatible
     * with the detected values (e.g. mimetype=text/plain is supplied, but
     * mimetype is audio/midi) then the value will simple be overriden in the
     * detected {@link DetectorStreamIF#getMetadata() metadata}.<br>
     * If the metadata contains values that are unknown to the detector
     * implementation (e.g. foo=bar), these values will also be untouched and
     * are also available in the detected
     * {@link DetectorStreamIF#getMetadata() metadata}.
     * 
     * @param stream
     *        is the stream to wrap. This stream must be a fresh stream that is
     *        untouched since it was opened.
     * @param metadata
     *        is the existing metadata already available.
     * @return the wrapped output stream.
     */
    DetectorOutputStreamIF wrapOutputStream(OutputStream stream, ContextIF metadata);

}
