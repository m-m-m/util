/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.base;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.channels.Channel;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.concurrent.api.Stoppable;
import net.sf.mmm.util.concurrent.base.SimpleExecutor;
import net.sf.mmm.util.exception.api.NlsNullPointerException;
import net.sf.mmm.util.io.api.AsyncTransferrer;
import net.sf.mmm.util.io.api.IoMode;
import net.sf.mmm.util.io.api.RuntimeIoException;
import net.sf.mmm.util.io.api.StreamUtil;
import net.sf.mmm.util.io.api.TransferCallback;
import net.sf.mmm.util.pool.api.Pool;
import net.sf.mmm.util.pool.base.NoByteArrayPool;
import net.sf.mmm.util.pool.base.NoCharArrayPool;

/**
 * This is the implementation of the {@link StreamUtil} interface.
 * 
 * @see #getInstance()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Singleton
@Named(StreamUtil.CDI_NAME)
public class StreamUtilImpl extends AbstractLoggableComponent implements StreamUtil {

  /** @see #getInstance() */
  private static StreamUtil instance;

  /** @see #getExecutor() */
  private Executor executor;

  /** @see #getByteArrayPool() */
  private Pool<byte[]> byteArrayPool;

  /** @see #getCharArrayPool() */
  private Pool<char[]> charArrayPool;

  /**
   * The constructor.
   */
  public StreamUtilImpl() {

    super();
    this.executor = null;
    this.byteArrayPool = null;
    this.charArrayPool = null;
  }

  /**
   * This method gets the singleton instance of this {@link StreamUtilImpl}.<br/>
   * <b>ATTENTION:</b><br/>
   * Please read {@link net.sf.mmm.util.component.api.Cdi#GET_INSTANCE} before using.
   * 
   * @return the singleton instance.
   */
  public static StreamUtil getInstance() {

    if (instance == null) {
      synchronized (StreamUtilImpl.class) {
        if (instance == null) {
          StreamUtilImpl util = new StreamUtilImpl();
          util.initialize();
          instance = util;
        }
      }
    }
    return instance;
  }

  /**
   * This method gets the {@link Executor} used to run asynchronous tasks. It may use a thread-pool.
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
  @Inject
  public void setExecutor(Executor executor) {

    getInitializationState().requireNotInitilized();
    this.executor = executor;
  }

  /**
   * This method gets the byte-array {@link Pool} used to transfer streams.<br>
   * The implementation should create byte-arrays with a suitable length (at least 512, suggested is 4096).
   * Override this method to use a real pool implementation.
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
  // @Resource
  public void setByteArrayPool(Pool<byte[]> byteArrayPool) {

    getInitializationState().requireNotInitilized();
    this.byteArrayPool = byteArrayPool;
  }

  /**
   * This method gets the char-array {@link Pool} used to transfer {@link Reader}s and {@link Writer}s. The
   * implementation should create char-arrays with a suitable length (at least 512, suggested is 2048).<br>
   * Override this method to use a real pool implementation.
   * 
   * @return the {@link Pool} instance.
   */
  protected Pool<char[]> getCharArrayPool() {

    return this.charArrayPool;
  }

  /**
   * This method sets the {@link #getCharArrayPool() char-array-pool}.
   * 
   * @param charArrayPool the charArrayPool to set
   */
  // @Resource
  public void setCharArrayPool(Pool<char[]> charArrayPool) {

    getInitializationState().requireNotInitilized();
    this.charArrayPool = charArrayPool;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.executor == null) {
      this.executor = SimpleExecutor.INSTANCE;
    }
    if (this.byteArrayPool == null) {
      this.byteArrayPool = NoByteArrayPool.INSTANCE;
    }
    if (this.charArrayPool == null) {
      this.charArrayPool = NoCharArrayPool.INSTANCE;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String read(Reader reader) throws RuntimeIoException {

    StringWriter writer = new StringWriter();
    transfer(reader, writer, false);
    return writer.toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long transfer(Reader reader, Writer writer, boolean keepWriterOpen) throws RuntimeIoException {

    ReaderTransferrer transferrer = new ReaderTransferrer(reader, writer, keepWriterOpen, null);
    long bytes = transferrer.transfer();
    return bytes;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long transfer(FileInputStream inStream, OutputStream outStream, boolean keepOutStreamOpen)
      throws RuntimeIoException {

    RuntimeIoException t = null;
    try (FileInputStream is = inStream; FileChannel inChannel = is.getChannel()) {
      WritableByteChannel outChannel = Channels.newChannel(outStream);
      return inChannel.transferTo(0, inChannel.size(), outChannel);
    } catch (Exception e) {
      t = new RuntimeIoException(e, IoMode.COPY);
      throw t;
    } finally {
      if (!keepOutStreamOpen) {
        try {
          outStream.close();
        } catch (IOException e) {
          if (t != null) {
            t.addSuppressed(e);
          } else {
            throw new RuntimeIoException(e, IoMode.CLOSE);
          }
        }
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long transfer(InputStream inStream, FileOutputStream outStream, boolean keepOutStreamOpen, long size)
      throws RuntimeIoException {

    RuntimeIoException t = null;
    try (InputStream is = inStream; ReadableByteChannel inChannel = Channels.newChannel(is)) {

      FileChannel outChannel = outStream.getChannel();
      return outChannel.transferFrom(inChannel, 0, size);

    } catch (Exception e) {
      t = new RuntimeIoException(e, IoMode.COPY);
      throw t;
    } finally {
      if (!keepOutStreamOpen) {
        try {
          outStream.close();
        } catch (IOException e) {
          if (t != null) {
            t.addSuppressed(e);
          } else {
            throw new RuntimeIoException(e, IoMode.CLOSE);
          }
        }
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long transfer(InputStream inStream, OutputStream outStream, boolean keepOutStreamOpen)
      throws RuntimeIoException {

    StreamTransferrer transferrer = new StreamTransferrer(inStream, outStream, keepOutStreamOpen, null);
    long bytes = transferrer.transfer();
    return bytes;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AsyncTransferrer transferAsync(InputStream inStream, OutputStream outStream, boolean keepOutStreamOpen) {

    return transferAsync(inStream, outStream, keepOutStreamOpen, null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AsyncTransferrer transferAsync(InputStream inStream, OutputStream outStream, boolean keepOutStreamOpen,
      TransferCallback callback) {

    StreamTransferrer transferrer = new StreamTransferrer(inStream, outStream, keepOutStreamOpen, callback);
    AsyncTransferrerImpl task = new AsyncTransferrerImpl(transferrer);
    this.executor.execute(task);
    return task;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AsyncTransferrer transferAsync(Reader reader, Writer writer, boolean keepWriterOpen) {

    return transferAsync(reader, writer, keepWriterOpen, null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AsyncTransferrer transferAsync(Reader reader, Writer writer, boolean keepWriterOpen, TransferCallback callback) {

    ReaderTransferrer transferrer = new ReaderTransferrer(reader, writer, keepWriterOpen, callback);
    AsyncTransferrerImpl task = new AsyncTransferrerImpl(transferrer);
    this.executor.execute(task);
    return task;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Properties loadProperties(InputStream inStream) throws RuntimeIoException {

    try (InputStream is = inStream) {
      Properties properties = new Properties();
      properties.load(is);
      return properties;
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.READ);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Properties loadProperties(Reader reader) throws RuntimeIoException {

    try (Reader r = reader) {
      Properties properties = new Properties();
      properties.load(r);
      return properties;
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.READ);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PrintWriter toPrintWriter(Appendable appendable) {

    NlsNullPointerException.checkNotNull(Appendable.class, appendable);
    if (appendable instanceof PrintWriter) {
      return (PrintWriter) appendable;
    } else {
      return new PrintWriter(toWriter(appendable));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Writer toWriter(Appendable appendable) {

    NlsNullPointerException.checkNotNull(Appendable.class, appendable);
    if (appendable instanceof Writer) {
      return (Writer) appendable;
    } else {
      return new AppendableWriter(appendable);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void close(InputStream inputStream) {

    try {
      inputStream.close();
    } catch (Exception e) {
      getLogger().warn("Failed to close stream!", e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void close(OutputStream outputStream) {

    try {
      outputStream.close();
    } catch (Exception e) {
      throw new RuntimeIoException(e, IoMode.CLOSE);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void close(Writer writer) {

    try {
      writer.close();
    } catch (Exception e) {
      throw new RuntimeIoException(e, IoMode.CLOSE);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void close(Reader reader) {

    try {
      reader.close();
    } catch (Exception e) {
      getLogger().warn("Failed to close reader!", e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void close(Channel channel) {

    try {
      channel.close();
    } catch (Exception e) {
      if (channel instanceof WritableByteChannel) {
        throw new RuntimeIoException(e, IoMode.CLOSE);
      }
      getLogger().warn("Failed to close channel!", e);
    }
  }

  /**
   * This is the default implementation of the {@link AsyncTransferrer} interface.
   */
  protected static class AsyncTransferrerImpl extends FutureTask<Long> implements AsyncTransferrer {

    /** the actual task. */
    private final BaseTransferrer<?> transferrer;

    /**
     * The constructor.
     * 
     * @param transferrer is the actual transferrer task.
     */
    public AsyncTransferrerImpl(BaseTransferrer<?> transferrer) {

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
   * This is the abstract base class for the {@link Callable} that transfers data of streams or
   * readers/writers.
   */
  protected abstract static class AbstractAsyncTransferrer implements Callable<Long>, Stoppable {

    /** @see #stop() */
    private volatile boolean stopped;

    /** @see #isCompleted() */
    private volatile boolean completed;

    /**
     * {@inheritDoc}
     */
    @Override
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
     * @return <code>true</code> if successfully completed, <code>false</code> if still running,
     *         {@link #isStopped() stopped} or an exception occurred.
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
   * This is an abstract implementation of the {@link AsyncTransferrer} interface, that implements
   * {@link Runnable} defining the main flow.
   * 
   * @param <BUFFER> is the generic type of the buffers provided by {@link BaseTransferrer#getPool()}.
   */
  protected abstract class BaseTransferrer<BUFFER> extends AbstractAsyncTransferrer {

    /** The callback or <code>null</code>. */
    private final TransferCallback callback;

    /**
     * <code>true</code> if {@link #getDestination() destination} should be closed.
     */
    private final boolean keepDestinationOpen;

    /**
     * The constructor.
     * 
     * @param callback is the callback or <code>null</code>.
     * @param keepDestinationOpen <code>true</code> if the {@link #getDestination() destination} should be
     *        closed, <code>false</code> otherwise.
     */
    public BaseTransferrer(TransferCallback callback, boolean keepDestinationOpen) {

      super();
      this.callback = callback;
      this.keepDestinationOpen = keepDestinationOpen;
    }

    /**
     * This method gets the source to transfer.
     * 
     * @return the source (e.g. {@link InputStream} or {@link Reader}).
     */
    protected abstract Closeable getSource();

    /**
     * This method gets the destination to transfer to.
     * 
     * @return the destination (e.g. {@link OutputStream} or {@link Writer}).
     */
    protected abstract Closeable getDestination();

    /**
     * This method gets the {@link Pool} to retrieve buffers.
     * 
     * @return the {@link Pool}.
     */
    protected abstract Pool<BUFFER> getPool();

    /**
     * This method performs the actual transfer.
     * 
     * @param buffer is the buffer used for the transfer.
     * @return the number of bytes that have been transferred.
     * @throws IOException if the transfer failed.
     */
    protected abstract long transfer(BUFFER buffer) throws IOException;

    /**
     * This method performs the actual transfer.
     * 
     * @return the number of bytes that have been transferred.
     * @throws RuntimeIoException if the transfer failed.
     */
    protected long transfer() throws RuntimeIoException {

      BUFFER buffer = getPool().borrow();
      RuntimeIoException t = null;
      try {
        return transfer(buffer);
      } catch (Exception e) {
        t = new RuntimeIoException(e, IoMode.COPY);
        throw t;
      } finally {
        boolean doThrow = (t == null);
        try {
          getSource().close();
        } catch (Exception e) {
          RuntimeIoException ex = new RuntimeIoException(e, IoMode.CLOSE);
          if (t != null) {
            t.addSuppressed(ex);
          } else {
            t = ex;
          }
        }
        if (!this.keepDestinationOpen) {
          try {
            getDestination().close();
          } catch (Exception e) {
            RuntimeIoException ex = new RuntimeIoException(e, IoMode.CLOSE);
            if (t != null) {
              t.addSuppressed(ex);
            } else {
              t = ex;
            }
          }
        }
        RuntimeException rte = null;
        try {
          getPool().release(buffer);
        } catch (RuntimeException e) {
          rte = e;
        } finally {
          if (rte != null) {
            if (t == null) {
              throw rte;
            } else {
              t.addSuppressed(rte);
            }
          }
          if ((t != null) && doThrow) {
            throw t;
          }
        }
      }
    }

    /**
     * {@inheritDoc}
     */
    @Override
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
   * This inner class is used to transfer an {@link InputStream} to an {@link OutputStream}.
   */
  protected class StreamTransferrer extends BaseTransferrer<byte[]> {

    /** The source to read from (to copy). */
    private final InputStream source;

    /** The destination to write to. */
    private final OutputStream destination;

    /**
     * The constructor.
     * 
     * @see StreamUtilImpl#transfer(InputStream, OutputStream, boolean)
     * 
     * @param source is {@link InputStream} to read from.
     * @param destination the {@link OutputStream} to write to.
     * @param keepDestinationOpen <code>true</code> if the <code>destination</code> should be closed.
     * @param callback is the callback or <code>null</code>.
     */
    public StreamTransferrer(InputStream source, OutputStream destination, boolean keepDestinationOpen,
        TransferCallback callback) {

      super(callback, keepDestinationOpen);
      this.source = source;
      this.destination = destination;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected InputStream getSource() {

      return this.source;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected OutputStream getDestination() {

      return this.destination;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Pool<byte[]> getPool() {

      return getByteArrayPool();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long transfer(byte[] buffer) throws IOException {

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
    }
  }

  /**
   * This inner class is used to transfer a {@link Reader} to a {@link Writer}.
   */
  protected class ReaderTransferrer extends BaseTransferrer<char[]> {

    /** The source to read from (to copy). */
    private final Reader source;

    /** The destination to write to. */
    private final Writer destination;

    /**
     * The constructor.
     * 
     * @see StreamUtilImpl#transfer(Reader, Writer, boolean)
     * 
     * @param source is {@link Reader} to read from.
     * @param destination the {@link Writer} to write to.
     * @param keepDestinationOpen <code>true</code> if the <code>destination</code> should be closed.
     * @param callback is the callback or <code>null</code>.
     */
    public ReaderTransferrer(Reader source, Writer destination, boolean keepDestinationOpen, TransferCallback callback) {

      super(callback, keepDestinationOpen);
      this.source = source;
      this.destination = destination;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Reader getSource() {

      return this.source;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Writer getDestination() {

      return this.destination;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Pool<char[]> getPool() {

      return getCharArrayPool();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long transfer(char[] buffer) throws IOException {

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
    }
  }

}
