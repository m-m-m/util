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
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.mmm.util.component.AlreadyInitializedException;
import net.sf.mmm.util.concurrent.SimpleExecutor;
import net.sf.mmm.util.pool.api.Pool;
import net.sf.mmm.util.pool.base.NoByteArrayPool;
import net.sf.mmm.util.pool.base.NoCharArrayPool;
import net.sf.mmm.util.state.Stoppable;

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

  /** @see #getInstance() */
  private static StreamUtil instance;

  /** @see #getLogger() */
  private Log logger;

  /** @see #getExecutor() */
  private Executor executor;

  /** @see #getByteArrayPool() */
  private Pool<byte[]> byteArrayPool;

  /** @see #getCharArrayPool() */
  private Pool<char[]> charArrayPool;

  /**
   * The constructor.
   */
  public StreamUtil() {

    super();
  }

  /**
   * This method gets the singleton instance of this {@link StreamUtil}.<br>
   * This design is the best compromise between easy access (via this
   * indirection you have direct, static access to all offered functionality)
   * and IoC-style design which allows extension and customization.<br>
   * For IoC usage, simply ignore all static {@link #getInstance()} methods and
   * construct new instances via the container-framework of your choice (like
   * plexus, pico, springframework, etc.). To wire up the dependent components
   * everything is properly annotated using common-annotations (JSR-250). If
   * your container does NOT support this, you should consider using a better
   * one.
   * 
   * @return the singleton instance.
   */
  public static StreamUtil getInstance() {

    if (instance == null) {
      synchronized (StreamUtil.class) {
        if (instance == null) {
          instance = new StreamUtil();
          instance.setExecutor(SimpleExecutor.INSTANCE);
          // INSTANCE.setLogger(new Jdk14Logger(StreamUtil.class.getName()));
          // even more ugly...
          instance.setLogger(LogFactory.getLog(StreamUtil.class));
          instance.setByteArrayPool(NoByteArrayPool.INSTANCE);
          instance.setCharArrayPool(NoCharArrayPool.INSTANCE);
        }
      }
    }
    return instance;
  }

  /**
   * This method gets the logger to be used.
   * 
   * @return the logger.
   */
  protected Log getLogger() {

    return this.logger;
  }

  /**
   * This method sets the {@link #getLogger() logger}.
   * 
   * @param logger the logger to set
   */
  @Resource
  public void setLogger(Log logger) {

    if (this.logger != null) {
      throw new AlreadyInitializedException();
    }
    this.logger = logger;
  }

  /**
   * This method gets the {@link Executor} used to run asynchronous tasks. It
   * may use a thread-pool.
   * 
   * @return the executor.
   */
  protected Executor getExecutor() {

    return this.executor;
  }

  /**
   * This method sets the {@link #getExecutor() executor}.
   * 
   * @param executor the executor to set.
   */
  @Resource
  public void setExecutor(Executor executor) {

    if (this.executor != null) {
      throw new AlreadyInitializedException();
    }
    this.executor = executor;
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

    return this.byteArrayPool;
  }

  /**
   * This method sets the {@link #getByteArrayPool() byte-array-pool}.
   * 
   * @param byteArrayPool the byteArrayPool to set
   */
  @Resource
  public void setByteArrayPool(Pool<byte[]> byteArrayPool) {

    if (this.byteArrayPool != null) {
      throw new AlreadyInitializedException();
    }
    this.byteArrayPool = byteArrayPool;
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
   * This method sets the {@link #getCharArrayPool() char-array-pool}.
   * 
   * @param charArrayPool the charArrayPool to set
   */
  @Resource
  public void setCharArrayPool(Pool<char[]> charArrayPool) {

    if (this.charArrayPool != null) {
      throw new AlreadyInitializedException();
    }
    this.charArrayPool = charArrayPool;
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

    ReaderTransferrer transferrer = new ReaderTransferrer(reader, writer, keepWriterOpen, null);
    long bytes = transferrer.transfer();
    return bytes;
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

    StreamTransferrer transferrer = new StreamTransferrer(inStream, outStream, keepOutStreamOpen,
        null);
    long bytes = transferrer.transfer();
    return bytes;
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
   */
  public AsyncTransferrer transferAsync(InputStream inStream, OutputStream outStream,
      boolean keepOutStreamOpen) {

    return transferAsync(inStream, outStream, keepOutStreamOpen, null);
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
   * @param callback is the callback that is invoked if the transfer is done.
   * @return the number of bytes that have been transferred.
   */
  public AsyncTransferrer transferAsync(InputStream inStream, OutputStream outStream,
      boolean keepOutStreamOpen, TransferCallback callback) {

    StreamTransferrer transferrer = new StreamTransferrer(inStream, outStream, keepOutStreamOpen,
        callback);
    AsyncTransferrerImpl task = new AsyncTransferrerImpl(transferrer);
    this.executor.execute(task);
    return task;
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
   */
  public AsyncTransferrer transferAsync(Reader reader, Writer writer, boolean keepWriterOpen) {

    return transferAsync(reader, writer, keepWriterOpen, null);
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
   * @param callback is the callback that is invoked if the transfer is done.
   * @return the number of bytes that have been transferred.
   */
  public AsyncTransferrer transferAsync(Reader reader, Writer writer, boolean keepWriterOpen,
      TransferCallback callback) {

    ReaderTransferrer transferrer = new ReaderTransferrer(reader, writer, keepWriterOpen, callback);
    AsyncTransferrerImpl task = new AsyncTransferrerImpl(transferrer);
    this.executor.execute(task);
    return task;
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

  /**
   * This is the default implementation of the {@link AsyncTransferrer}
   * interface.
   */
  protected static class AsyncTransferrerImpl extends FutureTask<Long> implements AsyncTransferrer {

    /** the actual task. */
    private final BaseTransferrer transferrer;

    /**
     * The constructor.
     * 
     * @param transferrer is the actual transferrer task.
     */
    public AsyncTransferrerImpl(BaseTransferrer transferrer) {

      super(transferrer);
      this.transferrer = transferrer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {

      this.transferrer.stop();
      return super.cancel(mayInterruptIfRunning);
    }

  }

  /**
   * This is the abstract base class for the {@link Callable} that transfers
   * data of streams or readers/writers.
   */
  protected abstract static class AbstractAsyncTransferrer implements Callable<Long>, Stoppable {

    /** @see #stop() */
    private volatile boolean stopped;

    /** @see #isCompleted() */
    private volatile boolean completed;

    /**
     * {@inheritDoc}
     */
    public void stop() {

      this.stopped = true;
    }

    /**
     * This method determines if this transferrer was {@link #stop() stopped}.
     * 
     * @return <code>true</code> if stopped, <code>false</code> otherwise.
     */
    public final boolean isStopped() {

      return this.stopped;
    }

    /**
     * This method determines if the transfer has been completed successfully.
     * 
     * @return <code>true</code> if successfully completed, <code>false</code>
     *         if still running, {@link #isStopped() stopped} or an exception
     *         occurred.
     */
    public final boolean isCompleted() {

      return this.completed;
    }

    /**
     * This method sets the {@link #isCompleted() completed-flag}.
     */
    protected void setCompleted() {

      this.completed = true;
    }

  }

  /**
   * This is an abstract implementation of the {@link AsyncTransferrer}
   * interface, that implements {@link Runnable} defining the main flow.
   */
  protected abstract class BaseTransferrer extends AbstractAsyncTransferrer {

    /** The callback or <code>null</code>. */
    private final TransferCallback callback;

    /**
     * The constructor.
     * 
     * @param callback is the callback or <code>null</code>.
     */
    public BaseTransferrer(TransferCallback callback) {

      super();
      this.callback = callback;
    }

    /**
     * This method performs the actual transfer.
     * 
     * @return the number of bytes that have been transferred.
     * @throws IOException if the transfer failed.
     */
    protected abstract long transfer() throws IOException;

    /**
     * {@inheritDoc}
     */
    public Long call() throws Exception {

      try {
        long bytes = transfer();
        if (this.callback != null) {
          if (isCompleted()) {
            this.callback.transferCompleted(bytes);
          } else {
            this.callback.transferStopped(bytes);
          }
        }
        return Long.valueOf(bytes);
      } catch (Exception e) {
        getLogger().error("Error during async transfer!", e);
        if (this.callback != null) {
          this.callback.transferFailed(e);
        }
        throw e;
      }
    }

  }

  /**
   * This inner class is used to transfer an {@link InputStream} to an
   * {@link OutputStream}.
   */
  protected class StreamTransferrer extends BaseTransferrer {

    /** The source to read from (to copy). */
    private final InputStream source;

    /** The destination to write to. */
    private final OutputStream destination;

    /** <code>true</code> if {@link #destination} should be closed. */
    private final boolean keepDestinationOpen;

    /**
     * The constructor.
     * 
     * @see StreamUtil#transfer(InputStream, OutputStream, boolean)
     * 
     * @param source is {@link InputStream} to read from.
     * @param destination the {@link OutputStream} to write to.
     * @param keepDestinationOpen <code>true</code> if the
     *        <code>destination</code> should be closed.
     * @param callback is the callback or <code>null</code>.
     */
    public StreamTransferrer(InputStream source, OutputStream destination,
        boolean keepDestinationOpen, TransferCallback callback) {

      super(callback);
      this.source = source;
      this.destination = destination;
      this.keepDestinationOpen = keepDestinationOpen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long transfer() throws IOException {

      byte[] buffer = getByteArrayPool().borrow();
      try {
        long bytesTransferred = 0;
        int count = this.source.read(buffer);
        while ((count > 0) && !isStopped()) {
          this.destination.write(buffer, 0, count);
          bytesTransferred += count;
          count = this.source.read(buffer);
        }
        if (count == -1) {
          setCompleted();
        }
        return bytesTransferred;
      } finally {
        try {
          getByteArrayPool().release(buffer);
        } finally {
          close(this.source);
          if (!this.keepDestinationOpen) {
            close(this.destination);
          }
        }
      }
    }
  }

  /**
   * This inner class is used to transfer a {@link Reader} to a {@link Writer}.
   */
  protected class ReaderTransferrer extends BaseTransferrer {

    /** The source to read from (to copy). */
    private final Reader source;

    /** The destination to write to. */
    private final Writer destination;

    /** <code>true</code> if {@link #destination} should be closed. */
    private final boolean keepDestinationOpen;

    /**
     * The constructor.
     * 
     * @see StreamUtil#transfer(Reader, Writer, boolean)
     * 
     * @param source is {@link Reader} to read from.
     * @param destination the {@link Writer} to write to.
     * @param keepDestinationOpen <code>true</code> if the
     *        <code>destination</code> should be closed.
     * @param callback is the callback or <code>null</code>.
     */
    public ReaderTransferrer(Reader source, Writer destination, boolean keepDestinationOpen,
        TransferCallback callback) {

      super(callback);
      this.source = source;
      this.destination = destination;
      this.keepDestinationOpen = keepDestinationOpen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long transfer() throws IOException {

      char[] buffer = getCharArrayPool().borrow();
      try {
        long bytesTransferred = 0;
        int count = this.source.read(buffer);
        while ((count > 0) && !isStopped()) {
          this.destination.write(buffer, 0, count);
          bytesTransferred += count;
          count = this.source.read(buffer);
        }
        if (count == -1) {
          setCompleted();
        }
        return bytesTransferred;
      } finally {
        try {
          getCharArrayPool().release(buffer);
        } finally {
          close(this.source);
          if (!this.keepDestinationOpen) {
            close(this.destination);
          }
        }
      }
    }
  }

}
