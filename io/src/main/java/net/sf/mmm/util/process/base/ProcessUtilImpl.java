/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.process.base;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;

import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.concurrent.api.Stoppable;
import net.sf.mmm.util.concurrent.base.SimpleExecutor;
import net.sf.mmm.util.exception.api.NlsIllegalArgumentException;
import net.sf.mmm.util.io.api.AsyncTransferrer;
import net.sf.mmm.util.io.api.StreamUtil;
import net.sf.mmm.util.io.base.StreamUtilImpl;
import net.sf.mmm.util.process.api.AsyncProcessExecutor;
import net.sf.mmm.util.process.api.ProcessContext;
import net.sf.mmm.util.process.api.ProcessUtil;

/**
 * This is the implementation of the {@link ProcessUtil} interface. <br>
 * <b>ATTENTION:</b><br>
 * The {@code execute}-methods spin up multiple {@link Thread threads}, especially when multiple processes are
 * piped (2*n+1[+1] threads). Therefore you should NOT use the {@link #getInstance() singleton} variant of
 * this util except you are writing a simple command-line client that does a simple job and then terminates.
 * When writing a server-application or library, that makes such calls repetitive, you should create your own
 * instance and {@link #setExecutor(Executor) configure} a thread-pool as
 * {@link java.util.concurrent.Executor}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ProcessUtilImpl extends AbstractLoggableComponent implements ProcessUtil {

  private static ProcessUtil instance;

  private StreamUtil streamUtil;

  private Executor executor;

  /**
   * The constructor.
   */
  public ProcessUtilImpl() {

    super();
  }

  /**
   * This method gets the singleton instance of this {@link ProcessUtilImpl}. <br>
   * <b>ATTENTION:</b><br>
   * Please read {@link net.sf.mmm.util.component.api.Cdi#GET_INSTANCE} before using.
   *
   * @return the singleton instance.
   */
  public static ProcessUtil getInstance() {

    if (instance == null) {
      synchronized (ProcessUtilImpl.class) {
        if (instance == null) {
          ProcessUtilImpl util = new ProcessUtilImpl();
          util.initialize();
          instance = util;
        }
      }
    }
    return instance;
  }

  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.executor == null) {
      this.executor = SimpleExecutor.INSTANCE;
    }
    if (this.streamUtil == null) {
      this.streamUtil = StreamUtilImpl.getInstance();
    }
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
   * This method gets the stream-util that is used by this process-util.
   *
   * @return the streamUtil
   */
  protected StreamUtil getStreamUtil() {

    return this.streamUtil;
  }

  /**
   * This method sets the {@link #getStreamUtil() stream-util}.
   *
   * @param streamUtil the streamUtil to set
   */
  @Inject
  public void setStreamUtil(StreamUtil streamUtil) {

    this.streamUtil = streamUtil;
  }

  @Override
  public int execute(ProcessContext context, ProcessBuilder... builders) throws IOException, InterruptedException {

    ProcessExecutor processExecutor = new ProcessExecutor(context, builders);
    return processExecutor.call().intValue();
  }

  @Override
  public int execute(ProcessContext context, long timeout, TimeUnit unit, ProcessBuilder... builders)
      throws IOException, TimeoutException, InterruptedException {

    AsyncProcessExecutor processExecutor = executeAsync(context, builders);
    try {
      return processExecutor.get(timeout, unit).intValue();
    } catch (ExecutionException e) {
      Throwable cause = e.getCause();
      if ((cause != null) && (cause instanceof InterruptedException)) {
        throw (InterruptedException) cause;
      } else {
        // should NOT happen...
        throw new IllegalStateException(e);
      }
    } finally {
      processExecutor.cancel(true);
    }
  }

  @Override
  public AsyncProcessExecutor executeAsync(ProcessContext context, ProcessBuilder... builders) throws IOException {

    ProcessExecutor processExecutor = new ProcessExecutor(context, builders);
    AsyncProcessExecutorImpl asyncExecutor = new AsyncProcessExecutorImpl(processExecutor);
    getExecutor().execute(asyncExecutor);
    return asyncExecutor;
  }

  /**
   * This inner class is the default implementation of the AsyncProcessExecutor.
   */
  protected static class AsyncProcessExecutorImpl extends FutureTask<Integer> implements AsyncProcessExecutor {

    /** The actual task to run. */
    private final ProcessExecutor executor;

    /**
     * The constructor.
     *
     * @param executor is the process-executor to call.
     */
    public AsyncProcessExecutorImpl(ProcessExecutor executor) {

      super(executor);
      this.executor = executor;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {

      this.executor.stop();
      return super.cancel(mayInterruptIfRunning);
    }

  }

  /**
   * This inner class is does the actual execution of the {@link Process}(es).
   */
  protected class ProcessExecutor implements Callable<Integer>, Stoppable {

    /** @see #ProcessUtilImpl.ProcessExecutor(ProcessContext, ProcessBuilder[]) */
    private final ProcessContext context;

    /** @see #ProcessUtilImpl.ProcessExecutor(ProcessContext, ProcessBuilder[]) */
    private final Process[] processes;

    /** @see #ProcessUtilImpl.ProcessExecutor(ProcessContext, ProcessBuilder[]) */
    private final AsyncTransferrer[] transferrers;

    /**
     * The constructor.
     *
     * @param context is the context of the process pipe.
     * @param builders are the configurations of the {@link Process}(es) to execute. The array needs to have a
     *        length greater than zero.
     * @throws IOException if an input/output error occurred.
     */
    public ProcessExecutor(ProcessContext context, ProcessBuilder[] builders) throws IOException {

      super();
      StreamUtil streamUtility = getStreamUtil();
      if (builders.length == 0) {
        streamUtility.close(context.getInStream());
        streamUtility.close(context.getOutStream());
        streamUtility.close(context.getErrStream());
        throw new NlsIllegalArgumentException("builders must NOT be empty!");
      }
      this.context = context;
      this.processes = new Process[builders.length];
      // n*(process.err->context.err), (n+1)*(in->out)
      this.transferrers = new AsyncTransferrer[builders.length + builders.length + 1];
      boolean success = false;
      try {
        InputStream in = context.getInStream();
        OutputStream err = context.getErrStream();
        for (int i = 0; i < builders.length; i++) {
          Process process = builders[i].start();
          AsyncTransferrer inOutTransferrer = streamUtility.transferAsync(in, process.getOutputStream(), false);
          AsyncTransferrer errTransferrer = streamUtility.transferAsync(process.getErrorStream(), err, true);
          this.processes[i] = process;
          in = this.processes[i].getInputStream();
          int transferrersIndex = i + i;
          this.transferrers[transferrersIndex] = inOutTransferrer;
          this.transferrers[transferrersIndex + 1] = errTransferrer;
        }
        this.transferrers[builders.length + builders.length] = streamUtility.transferAsync(in, context.getOutStream(),
            false);
        success = true;
      } finally {
        if (!success) {
          stop();
        }
      }
    }

    /**
     * This method disposes this executor. All processes are {@link Process#destroy() destroyed} and all
     * streams are closed.
     */
    protected void dispose() {

      for (int i = 0; i < this.processes.length; i++) {
        if (this.processes[i] != null) {
          try {
            this.processes[i].destroy();
          } catch (RuntimeException e) {
            getLogger().warn(e.getLocalizedMessage(), e);
          }
          this.processes[i] = null;
        }
      }
      for (int i = 0; i < this.transferrers.length; i++) {
        if (this.transferrers[i] != null) {
          try {
            this.transferrers[i].cancel(true);
          } catch (RuntimeException e) {
            getLogger().warn(e.getLocalizedMessage(), e);
          }
          this.transferrers[i] = null;
        }
      }
      StreamUtil streamUtility = getStreamUtil();
      streamUtility.close(this.context.getInStream());
      streamUtility.close(this.context.getOutStream());
      streamUtility.close(this.context.getErrStream());
    }

    @Override
    public void stop() {

      dispose();
    }

    /**
     * {@inheritDoc}
     *
     * This method executes the {@link Process}(es).
     *
     * @return the return-code of the (last) process.
     * @throws InterruptedException if a process was interrupted.
     */
    @Override
    public Integer call() throws InterruptedException {

      try {
        int returnCode = 0;
        for (int i = 0; i < this.processes.length; i++) {
          returnCode = this.processes[i].waitFor();
        }
        return Integer.valueOf(returnCode);
      } finally {
        dispose();
      }
    }

  }

}
