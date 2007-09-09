/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * This class is a collection of utility functions to deal with
 * {@link InputStream}s, {@link OutputStream}s, {@link Reader}s and
 * {@link Writer}s.<br>
 * <b>Information:</b><br>
 * Whenever the javadoc of a method specifies that an object (stream, reader or
 * writer) is closed, then this means that it will be closed on successful
 * return of the method as well as in an exceptional state. If it says that an
 * object is NOT closed then the caller is responsible to ensure that it will be
 * closed properly.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class StreamUtil {

  /** the size used for char buffers. */
  private static final int CHAR_BUFFER_SIZE = 4096;

  /**
   * The constructor.
   */
  private StreamUtil() {

    super();
  }

  /**
   * This method reads the contents of the given <code>reader</code> into a
   * string.
   * 
   * @param reader is where to read the content from. It will be
   *        {@link Reader#close() closed} at the end.
   * @return the content of the given <code>reader</code>.
   * @throws IOException if an error occurred.
   */
  public static String read(Reader reader) throws IOException {

    StringWriter writer = new StringWriter();
    stream(reader, writer, false);
    return writer.toString();
  }

  /**
   * This method streams the contents of the given <code>reader</code> to the
   * given <code>writer</code>.
   * 
   * @param reader is where to read the content from. Will be
   *        {@link Reader#close() closed} at the end.
   * @param writer is where to write the content to. Will be
   *        {@link Reader#close() closed} at the end if
   *        <code>keepWriterOpen</code> is <code>false</code>.
   * @param keepWriterOpen if <code>true</code> the given <code>writer</code>
   *        will remain open so that additional content can be appended. Else if
   *        <code>false</code>, the <code>writer</code> will be
   *        {@link Reader#close() closed}.
   * @throws IOException if the operation failed.
   */
  public static void stream(Reader reader, Writer writer, boolean keepWriterOpen)
      throws IOException {

    char[] buffer = new char[CHAR_BUFFER_SIZE];
    try {
      int count = reader.read(buffer);
      while (count >= 0) {
        writer.write(buffer, 0, count);
        count = reader.read(buffer);
      }
    } finally {
      try {
        reader.close();
      } finally {
        if (!keepWriterOpen) {
          writer.close();
        }
      }
    }
  }

}
