/* $Id$ */
package net.sf.mmm.streamdetect.api;

import java.io.OutputStream;

/**
 * This is the interface for a {@link DetectorStream} that wrapps an
 * {@link OutputStream}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface DetectorOutputStream extends DetectorStream {

    /**
     * This method gets the wrapped stream. After this stream is completely
     * written and closed, the {@link #getMetadata() metadata} can be retrieved.
     * This method is a simple getter - it will always return the same stream
     * object.
     * 
     * @return the wrapper stream.
     */
    OutputStream getStream();

}
