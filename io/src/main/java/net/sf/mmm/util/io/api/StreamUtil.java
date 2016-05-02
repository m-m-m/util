/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.api;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.channels.Channel;
import java.util.Properties;

import net.sf.mmm.util.component.api.ComponentSpecification;

/**
 * This is the interface for a collection of utility functions that help to deal with {@link InputStream}s,
 * {@link OutputStream}s, {@link Reader}s and {@link Writer}s. <br>
 * <b>Information:</b><br>
 * Whenever the javadoc of a method specifies that an object (stream, reader or writer) is closed, then this
 * means that it will be closed on successful return of the method as well as in an exceptional state. If it
 * says that an object is NOT closed then the caller is responsible to ensure that it will be closed properly.
 * 
 * @see net.sf.mmm.util.io.base.StreamUtilImpl
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
@ComponentSpecification
public interface StreamUtil {

  /** The {@link net.sf.mmm.util.component.api.Cdi#CDI_NAME CDI name}. */
  String CDI_NAME = "net.sf.mmm.util.io.api.StreamUtil";

  /**
   * This method reads the contents of the given {@code reader} into a string. <br>
   * <b>ATTENTION:</b><br>
   * Only use this method if you know what you are doing. This method will cause that the complete content of
   * the given {@code reader} is read into memory.
   * 
   * @param reader is where to read the content from. It will be {@link Reader#close() closed} at the end.
   * @return the content of the given {@code reader}.
   * @throws RuntimeIoException if an error occurred with an I/O error.
   */
  String read(Reader reader) throws RuntimeIoException;

  /**
   * This method transfers the contents of the given {@code reader} to the given {@code writer}.
   * 
   * @param reader is where to read the content from. Will be {@link Reader#close() closed} at the end.
   * @param writer is where to write the content to. Will be {@link Reader#close() closed} at the end if
   *        {@code keepWriterOpen} is {@code false}.
   * @param keepWriterOpen if {@code true} the given {@code writer} will remain open so that
   *        additional content can be appended. Else if {@code false}, the {@code writer} will be
   *        {@link Reader#close() closed}.
   * @return the number of bytes that have been transferred.
   * @throws RuntimeIoException if the operation failed. Closing is guaranteed even in exception state.
   */
  long transfer(Reader reader, Writer writer, boolean keepWriterOpen) throws RuntimeIoException;

  /**
   * This method transfers the contents of the given {@code inStream} to the given {@code outStream}
   * using {@link java.nio.channels.Channel NIO-Channels}.
   * 
   * @param inStream is where to read the content from. Will be {@link InputStream#close() closed} at the end.
   * @param outStream is where to write the content to. Will be {@link OutputStream#close() closed} at the end
   *        if {@code keepOutStreamOpen} is {@code false}.
   * @param keepOutStreamOpen if {@code true} the given {@code outStream} will remain open so that
   *        additional content can be appended. Else if {@code false}, the {@code outStream} will be
   *        {@link OutputStream#close() closed}.
   * @return the number of bytes that have been transferred.
   * @throws RuntimeIoException if the operation failed. Closing is guaranteed even in exception state.
   */
  long transfer(FileInputStream inStream, OutputStream outStream, boolean keepOutStreamOpen) throws RuntimeIoException;

  /**
   * This method transfers the contents of the given {@code inStream} to the given {@code outStream}
   * using {@link java.nio.channels.Channel NIO-Channels}.
   * 
   * @param inStream is where to read the content from. Will be {@link InputStream#close() closed} at the end.
   * @param outStream is where to write the content to. Will be {@link OutputStream#close() closed} at the end
   *        if {@code keepOutStreamOpen} is {@code false}.
   * @param keepOutStreamOpen if {@code true} the given {@code outStream} will remain open so that
   *        additional content can be appended. Else if {@code false}, the {@code outStream} will be
   *        {@link OutputStream#close() closed}.
   * @param size is the number of bytes to transfer.
   * @return the number of bytes that have been transferred.
   * @throws RuntimeIoException if the operation failed. Closing is guaranteed even in exception state.
   */
  long transfer(InputStream inStream, FileOutputStream outStream, boolean keepOutStreamOpen, long size)
      throws RuntimeIoException;

  /**
   * This method transfers the contents of the given {@code inStream} to the given {@code outStream}
   * .
   * 
   * @param inStream is where to read the content from. Will be {@link InputStream#close() closed} at the end.
   * @param outStream is where to write the content to. Will be {@link OutputStream#close() closed} at the end
   *        if {@code keepOutStreamOpen} is {@code false}.
   * @param keepOutStreamOpen if {@code true} the given {@code outStream} will remain open so that
   *        additional content can be appended. Else if {@code false}, the {@code outStream} will be
   *        {@link OutputStream#close() closed}.
   * @return the number of bytes that have been transferred.
   * @throws RuntimeIoException if the operation failed. Closing is guaranteed even in exception state.
   */
  long transfer(InputStream inStream, OutputStream outStream, boolean keepOutStreamOpen) throws RuntimeIoException;

  /**
   * This method transfers the contents of the given {@code inStream} to the given {@code outStream}
   * .
   * 
   * @param inStream is where to read the content from. Will be {@link InputStream#close() closed} at the end.
   * @param outStream is where to write the content to. Will be {@link OutputStream#close() closed} at the end
   *        if {@code keepOutStreamOpen} is {@code false}.
   * @param keepOutStreamOpen if {@code true} the given {@code outStream} will remain open so that
   *        additional content can be appended. Else if {@code false}, the {@code outStream} will be
   *        {@link OutputStream#close() closed}.
   * @return the number of bytes that have been transferred.
   */
  AsyncTransferrer transferAsync(InputStream inStream, OutputStream outStream, boolean keepOutStreamOpen);

  /**
   * This method transfers the contents of the given {@code inStream} to the given {@code outStream}
   * .
   * 
   * @param inStream is where to read the content from. Will be {@link InputStream#close() closed} at the end.
   * @param outStream is where to write the content to. Will be {@link OutputStream#close() closed} at the end
   *        if {@code keepOutStreamOpen} is {@code false}.
   * @param keepOutStreamOpen if {@code true} the given {@code outStream} will remain open so that
   *        additional content can be appended. Else if {@code false}, the {@code outStream} will be
   *        {@link OutputStream#close() closed}.
   * @param callback is the callback that is invoked if the transfer is done.
   * @return the number of bytes that have been transferred.
   */
  AsyncTransferrer transferAsync(InputStream inStream, OutputStream outStream, boolean keepOutStreamOpen,
      TransferCallback callback);

  /**
   * This method transfers the contents of the given {@code reader} to the given {@code writer}.
   * 
   * @param reader is where to read the content from. Will be {@link Reader#close() closed} at the end.
   * @param writer is where to write the content to. Will be {@link Reader#close() closed} at the end if
   *        {@code keepWriterOpen} is {@code false}.
   * @param keepWriterOpen if {@code true} the given {@code writer} will remain open so that
   *        additional content can be appended. Else if {@code false}, the {@code writer} will be
   *        {@link Reader#close() closed}.
   * @return the number of bytes that have been transferred.
   */
  AsyncTransferrer transferAsync(Reader reader, Writer writer, boolean keepWriterOpen);

  /**
   * This method transfers the contents of the given {@code reader} to the given {@code writer}.
   * 
   * @param reader is where to read the content from. Will be {@link Reader#close() closed} at the end.
   * @param writer is where to write the content to. Will be {@link Reader#close() closed} at the end if
   *        {@code keepWriterOpen} is {@code false}.
   * @param keepWriterOpen if {@code true} the given {@code writer} will remain open so that
   *        additional content can be appended. Else if {@code false}, the {@code writer} will be
   *        {@link Reader#close() closed}.
   * @param callback is the callback that is invoked if the transfer is done.
   * @return the number of bytes that have been transferred.
   */
  AsyncTransferrer transferAsync(Reader reader, Writer writer, boolean keepWriterOpen, TransferCallback callback);

  /**
   * This method loads the {@link Properties} from the given {@code inStream} and
   * {@link InputStream#close() closes} it. <br>
   * <b>ATTENTION:</b><br>
   * This method loads the properties using the encoding {@code ISO-8859-1} . Use
   * {@link #loadProperties(Reader)} instead to use an explicit encoding (e.g. {@code UTF-8}).
   * 
   * @see Properties#load(InputStream)
   * 
   * @param inStream is the {@link InputStream} to the properties data.
   * @return the properties read from the given {@code inStream}.
   * @throws RuntimeIoException if the operation failed. Closing is guaranteed even in exception state.
   */
  Properties loadProperties(InputStream inStream) throws RuntimeIoException;

  /**
   * This method loads the {@link Properties} from the given {@code reader} and {@link Reader#close()
   * closes} it. <br>
   * 
   * @see Properties#load(Reader)
   * 
   * @param reader is the {@link Reader} to the properties data.
   * @return the properties read from the given {@code reader}.
   * @throws RuntimeIoException if the operation failed. Closing is guaranteed even in exception state.
   */
  Properties loadProperties(Reader reader) throws RuntimeIoException;

  /**
   * This method converts the given {@link Appendable} to a {@link Writer}.
   * 
   * @param appendable is the {@link Appendable} to wrap.
   * @return the adapting {@link Writer}.
   * @since 2.0.0
   */
  Writer toWriter(Appendable appendable);

  /**
   * This method converts the given {@link Appendable} to a {@link PrintWriter}.
   * 
   * @param appendable is the {@link Appendable} to wrap.
   * @return the adapting {@link PrintWriter}.
   * @since 2.0.0
   */
  PrintWriter toPrintWriter(Appendable appendable);

  /**
   * This method closes the given {@code inputStream} without throwing an {@link Exception}. If an
   * exception occurs, it will only be logged.
   * 
   * @param inputStream is the input-stream to close.
   */
  void close(InputStream inputStream);

  /**
   * This method closes the given {@code outputStream} without throwing an {@link java.io.IOException}.
   * 
   * @param outputStream is the output-stream to close.
   * @throws RuntimeIoException if the closing failed.
   */
  void close(OutputStream outputStream) throws RuntimeIoException;

  /**
   * This method closes the given {@code writer} without throwing an {@link java.io.IOException}.
   * 
   * @param writer is the writer to close.
   * @throws RuntimeIoException if the closing failed.
   */
  void close(Writer writer) throws RuntimeIoException;

  /**
   * This method closes the given {@code reader} without throwing an {@link Exception}. If an exception
   * occurs, it will only be logged.
   * 
   * @param reader is the reader to close.
   */
  void close(Reader reader);

  /**
   * This method closes the given {@code channel} without throwing an {@link Exception}.
   * 
   * @param channel is the channel to close.
   */
  void close(Channel channel);

}
