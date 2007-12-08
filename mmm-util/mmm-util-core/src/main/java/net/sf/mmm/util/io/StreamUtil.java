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
import java.nio.channels.Channel;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.Jdk14Logger;

import net.sf.mmm.util.pool.api.Pool;
import net.sf.mmm.util.pool.base.NoByteArrayPool;
import net.sf.mmm.util.pool.base.NoCharArrayPool;

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
public class StreamUtil {

  /**
   * This is the singleton instance of this {@link StreamUtil}. Instead of
   * declaring the methods static, we declare this static instance what gives
   * the same way of access while still allowing a design for extension by
   * inheriting from this class.
   */
  public static final StreamUtil INSTANCE = new StreamUtil();

  /** @see #getLogger() */
  private final Log logger;

  /**
   * The constructor.
   */
  protected StreamUtil() {

    this(new Jdk14Logger(StreamUtil.class.getName()));
  }

  /**
   * The constructor.
   * 
   * @param logger the logger instance.
   */
  protected StreamUtil(Log logger) {

    super();
    this.logger = logger;
  }

  /**
   * This method gets the logger to be used.
   * 
   * @return the logger.
   */
  protected final Log getLogger() {

    return this.logger;
  }

  /**
   * This method gets the byte-array {@link Pool} used to transfer streams.<br>
   * The implementation should create byte-arrays with a suitable length (at
   * least 512, suggested is 4096). Override this method to use a real pool
   * implementation.
   * 
   * @return the {@link Pool} instance.
   */
  protected Pool<byte[]> getByteArrayPool() {

    return NoByteArrayPool.INSTANCE;
  }

  /**
   * This method gets the char-array {@link Pool} used to transfer
   * {@link Reader}s and {@link Writer}s. The implementation should create
   * char-arrays with a suitable length (at least 512, suggested is 2048).<br>
   * Override this method to use a real pool implementation.
   * 
   * @return the {@link Pool} instance.
   */
  protected Pool<char[]> getCharArrayPool() {

    return NoCharArrayPool.INSTANCE;
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
  public String read(Reader reader) throws IOException {

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
  public long transfer(Reader reader, Writer writer, boolean keepWriterOpen) throws IOException {

    char[] buffer = getCharArrayPool().borrow();
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
        getCharArrayPool().release(buffer);
      } finally {
        close(reader);
        if (!keepWriterOpen) {
          close(writer);
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
  public long transfer(FileInputStream inStream, OutputStream outStream, boolean keepOutStreamOpen)
      throws IOException {

    FileChannel inChannel = inStream.getChannel();
    WritableByteChannel outChannel = Channels.newChannel(outStream);
    try {
      return inChannel.transferTo(0, inChannel.size(), outChannel);
    } finally {
      // close(inStream);
      close(inChannel);
      if (!keepOutStreamOpen) {
        // close(outStream);
        close(outChannel);
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
  public long transfer(InputStream inStream, FileOutputStream outStream, boolean keepOutStreamOpen,
      long size) throws IOException {

    ReadableByteChannel inChannel = Channels.newChannel(inStream);
    FileChannel outChannel = outStream.getChannel();
    try {
      return outChannel.transferFrom(inChannel, 0, size);
    } finally {
      // close (inStream);
      close(inChannel);
      if (!keepOutStreamOpen) {
        // close(outStream);
        close(outChannel);
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
  public long transfer(InputStream inStream, OutputStream outStream, boolean keepOutStreamOpen)
      throws IOException {

    byte[] buffer = getByteArrayPool().borrow();
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
        getByteArrayPool().release(buffer);
      } finally {
        close(inStream);
        if (!keepOutStreamOpen) {
          close(outStream);
        }
      }
    }
  }

  /**
   * This method closes the given <code>inputStream</code> without throwing an
   * {@link Exception}.
   * 
   * @param inputStream is the input-stream to close.
   */
  public void close(InputStream inputStream) {

    try {
      inputStream.close();
    } catch (Exception e) {
      this.logger.warn("Failed to close stream!", e);
    }
  }

  /**
   * This method closes the given <code>outputStream</code> without throwing
   * an {@link Exception}.
   * 
   * @param outputStream is the output-stream to close.
   */
  public void close(OutputStream outputStream) {

    try {
      outputStream.close();
    } catch (Exception e) {
      this.logger.warn("Failed to close stream!", e);
    }
  }

  /**
   * This method closes the given <code>writer</code> without throwing an
   * {@link Exception}.
   * 
   * @param writer is the writer to close.
   */
  public void close(Writer writer) {

    try {
      writer.close();
    } catch (Exception e) {
      this.logger.warn("Failed to close writer!", e);
    }
  }

  /**
   * This method closes the given <code>reader</code> without throwing an
   * {@link Exception}.
   * 
   * @param reader is the reader to close.
   */
  public void close(Reader reader) {

    try {
      reader.close();
    } catch (Exception e) {
      this.logger.warn("Failed to close writer!", e);
    }
  }

  /**
   * This method closes the given <code>channel</code> without throwing an
   * {@link Exception}.
   * 
   * @param channel is the channel to close.
   */
  public void close(Channel channel) {

    try {
      channel.close();
    } catch (Exception e) {
      this.logger.warn("Failed to close writer!", e);
    }
  }

}
