/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

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
  private static final int CHAR_BUFFER_SIZE = 2048;

  /** the size used for char buffers. */
  private static final int BYTE_BUFFER_SIZE = 4096;

  /**
   * The constructor.
   */
  private StreamUtil() {

    super();
  }

  /**
   * This method reads the contents of the given <code>reader</code> into a
   * string.<br>
   * <b>ATTENTION:</b><br>
   * Only use this method if you know what you are doing. This method will cause
   * that the complete content of the given <code>reader</code> is read into
   * memory.
   * 
   * @param reader is where to read the content from. It will be
   *        {@link Reader#close() closed} at the end.
   * @return the content of the given <code>reader</code>.
   * @throws IOException if an error occurred with an I/O error.
   */
  public static String read(Reader reader) throws IOException {

    StringWriter writer = new StringWriter();
    transfer(reader, writer, false);
    return writer.toString();
  }

  /**
   * This method transfers the contents of the given <code>reader</code> to
   * the given <code>writer</code>.
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
   * @return the number of bytes that have been transferred.
   * @throws IOException if the operation failed. Closing is guaranteed even in
   *         exception state.
   */
  public static long transfer(Reader reader, Writer writer, boolean keepWriterOpen)
      throws IOException {

    char[] buffer = new char[CHAR_BUFFER_SIZE];
    try {
      long bytes = 0;
      int count = reader.read(buffer);
      while (count >= 0) {
        bytes = bytes + count;
        writer.write(buffer, 0, count);
        count = reader.read(buffer);
      }
      return bytes;
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

  /**
   * This method transfers the contents of the given <code>inStream</code> to
   * the given <code>outStream</code> using
   * {@link java.nio.channels.Channel NIO-Channels}.
   * 
   * @param inStream is where to read the content from. Will be
   *        {@link InputStream#close() closed} at the end.
   * @param outStream is where to write the content to. Will be
   *        {@link OutputStream#close() closed} at the end if
   *        <code>keepOutStreamOpen</code> is <code>false</code>.
   * @param keepOutStreamOpen if <code>true</code> the given
   *        <code>outStream</code> will remain open so that additional content
   *        can be appended. Else if <code>false</code>, the
   *        <code>outStream</code> will be {@link OutputStream#close() closed}.
   * @return the number of bytes that have been transferred.
   * @throws IOException if the operation failed. Closing is guaranteed even in
   *         exception state.
   */
  public static long transfer(FileInputStream inStream, OutputStream outStream,
      boolean keepOutStreamOpen) throws IOException {

    FileChannel inChannel = inStream.getChannel();
    WritableByteChannel outChannel = Channels.newChannel(outStream);
    try {
      return inChannel.transferTo(0, inChannel.size(), outChannel);
    } finally {
      try {
        // inStream.close();
        inChannel.close();
      } finally {
        if (!keepOutStreamOpen) {
          // outStream.close();
          outChannel.close();
        }
      }
    }
  }

  /**
   * This method transfers the contents of the given <code>inStream</code> to
   * the given <code>outStream</code> using
   * {@link java.nio.channels.Channel NIO-Channels}.
   * 
   * @param inStream is where to read the content from. Will be
   *        {@link InputStream#close() closed} at the end.
   * @param outStream is where to write the content to. Will be
   *        {@link OutputStream#close() closed} at the end if
   *        <code>keepOutStreamOpen</code> is <code>false</code>.
   * @param keepOutStreamOpen if <code>true</code> the given
   *        <code>outStream</code> will remain open so that additional content
   *        can be appended. Else if <code>false</code>, the
   *        <code>outStream</code> will be {@link OutputStream#close() closed}.
   * @param size is the number of bytes to transfer.
   * @return the number of bytes that have been transferred.
   * @throws IOException if the operation failed. Closing is guaranteed even in
   *         exception state.
   */
  public static long transfer(InputStream inStream, FileOutputStream outStream,
      boolean keepOutStreamOpen, long size) throws IOException {

    ReadableByteChannel inChannel = Channels.newChannel(inStream);
    FileChannel outChannel = outStream.getChannel();
    try {
      return outChannel.transferFrom(inChannel, 0, size);
    } finally {
      try {
        // inStream.close();
        inChannel.close();
      } finally {
        if (!keepOutStreamOpen) {
          // outStream.close();
          outChannel.close();
        }
      }
    }
  }

  /**
   * This method transfers the contents of the given <code>inStream</code> to
   * the given <code>outStream</code>.
   * 
   * @param inStream is where to read the content from. Will be
   *        {@link InputStream#close() closed} at the end.
   * @param outStream is where to write the content to. Will be
   *        {@link OutputStream#close() closed} at the end if
   *        <code>keepOutStreamOpen</code> is <code>false</code>.
   * @param keepOutStreamOpen if <code>true</code> the given
   *        <code>outStream</code> will remain open so that additional content
   *        can be appended. Else if <code>false</code>, the
   *        <code>outStream</code> will be {@link OutputStream#close() closed}.
   * @return the number of bytes that have been transferred.
   * @throws IOException if the operation failed. Closing is guaranteed even in
   *         exception state.
   */
  public static long transfer(InputStream inStream, OutputStream outStream,
      boolean keepOutStreamOpen) throws IOException {

    if (inStream instanceof FileInputStream) {
      return transfer((FileInputStream) inStream, outStream, keepOutStreamOpen);
    }
    byte[] buffer = new byte[BYTE_BUFFER_SIZE];
    try {
      long size = 0;
      int count = inStream.read(buffer);
      while (count >= 0) {
        size = size + count;
        outStream.write(buffer, 0, count);
        count = inStream.read(buffer);
      }
      return size;
    } finally {
      try {
        inStream.close();
      } finally {
        if (!keepOutStreamOpen) {
          outStream.close();
        }
      }
    }
  }
}
