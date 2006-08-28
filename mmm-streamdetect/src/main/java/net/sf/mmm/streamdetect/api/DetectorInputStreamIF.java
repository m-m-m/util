/* $Id$ */
package net.sf.mmm.streamdetect.api;

import java.io.InputStream;

/**
 * This is the interface for a {@link DetectorStreamIF} that wrapps an
 * {@link InputStream}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface DetectorInputStreamIF extends DetectorStreamIF {

    /**
     * This method gets the wrapped stream. After this stream is read to the
     * end, the {@link #getMetadata() metadata} can be retrieved. This method is
     * a simple getter - it will always return the same stream object.<br>
     * This returned wrapper stream has the following limitations:
     * <ul>
     * <li>{@link java.io.InputStream#skip(long) skipping} is NOT permitted.</li>
     * <li>{@link java.io.InputStream#mark(int) mark} is NOT
     * {@link java.io.InputStream#markSupported() supported}.</li>
     * <li>You need to read/write your data completely (at least until the
     * detectiton is {@link #isDone() done}) in order to get the complete
     * {@link #getMetadata() metadata}.</li>
     * </ul>
     * 
     * @return the wrapper stream.
     */
    InputStream getStream();

}
