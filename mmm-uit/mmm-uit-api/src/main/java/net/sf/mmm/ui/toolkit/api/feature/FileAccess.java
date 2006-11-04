/* $Id$ */
package net.sf.mmm.ui.toolkit.api.feature;

import java.io.IOException;
import java.io.InputStream;

/**
 * This is the interface to access a file (or any arbitrary data).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface FileAccess {

  /**
   * This method gets an input stream used to read the data.
   * 
   * @return a new input stream to the data. The stream should be
   *         {@link InputStream#close() closed} after usage.
   * @throws IOException
   *         if the file (data-source) could not be opened for reading.
   */
  InputStream getFile() throws IOException;

  /**
   * This method gets the name of the data-file.
   * 
   * @return the name of the data-file. This does NOT include a path (only
   *         basename).
   */
  String getFilename();

  /**
   * This method gets the size of the data in bytes or <code>-1</code> if
   * the size is unknown.
   * 
   * @return the size in bytes or <code>-1</code> if unknown.
   */
  long getSize();

}
