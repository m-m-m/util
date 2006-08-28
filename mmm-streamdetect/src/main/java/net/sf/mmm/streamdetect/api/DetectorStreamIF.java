/* $Id$ */
package net.sf.mmm.streamdetect.api;

import net.sf.mmm.context.api.ContextIF;

/**
 * This is the abstract base interface for a container of a wrapped stream
 * together with metadata that was detected while streaming the data.<br>
 * If a reasonable application transfers data from a source to a sink it does
 * NOT load the entire data into memory but uses streams ({@link java.io.InputStream}
 * and {@link java.io.OutputStream}). A typical example is a file that is
 * uploaded via http and written into a database. In many situations it is
 * desirebale to get metadata (e.g. mimetype, md5sum, etc.) about the actual
 * data. Most solutions to detect metadata need random access to seek in the
 * data. Therefore you would need to save the data to a temporary file, analyse
 * it and then transfer the file to the database. The stream-detect library
 * allows you to get the metadata on the fly while streaming that data. All you
 * need to do is to create a wrapper on your stream and perform your actual
 * transfer on the wrapper stream instead. This interface is the container for
 * the wrapper stream.<br>
 * This approach has the following limitations:
 * <ul>
 * <li>You need to read/write your data completely (at least until the
 * detectiton is {@link #isDone() done}).</li>
 * <li>{@link java.io.InputStream#skip(long) skipping} is NOT permitted.</li>
 * <li>{@link java.io.InputStream#mark(int) mark} is NOT
 * {@link java.io.InputStream#markSupported() supported}.</li>
 * </ul>
 * of A {@link DetectorStreamIF} is typically used to get the actual wrapper
 * stream, read/write the stream data and then
 * {@link #getMetadata() get the metadata}. After this, the object is useless
 * and can be eaten up by the garbarge collector.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface DetectorStreamIF {

    /**
     * This method gets the context with the detected metadata. It should NOT be
     * called, before this detector stream is {@link #isDone() done}.
     * 
     * @return the metadata.
     */
    ContextIF getMetadata();

    /**
     * This method determines if the detection is done. This should be the case
     * at least if the stream has completely been read (in case of
     * {@link java.io.InputStream}) or written (in case of
     * {@link java.io.OutputStream}). Depending on the type of the data and
     * this implementation all metadata might be collected before (e.g. because
     * all metadata comes from the header of the file).
     * 
     * @return <code>true</code> if the metadata has been completely been
     *         collected, <code>false</code> otherwise.
     */
    boolean isDone();

}
