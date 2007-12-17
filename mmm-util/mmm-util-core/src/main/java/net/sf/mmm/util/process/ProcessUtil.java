/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.process;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.mmm.util.component.AlreadyInitializedException;
import net.sf.mmm.util.concurrent.SimpleExecutor;
import net.sf.mmm.util.io.AsyncTransferrer;
import net.sf.mmm.util.io.StreamUtil;
import net.sf.mmm.util.nls.base.NlsIllegalArgumentException;
import net.sf.mmm.util.state.Stoppable;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ProcessUtil {

  /**
   * This is the singleton instance of this {@link ProcessUtil}. Instead of
   * declaring the methods static, we declare this static instance what gives
   * the same way of access while still allowing a design for extension by
   * inheriting from this class.
   */
  public static final ProcessUtil INSTANCE = new ProcessUtil();

  static {
    // INSTANCE.setLogger(new Jdk14Logger(ProcessUtil.class.getName()));
    // even more ugly...
    INSTANCE.setLogger(LogFactory.getLog(ProcessUtil.class));
    INSTANCE.setExecutor(SimpleExecutor.INSTANCE);
    INSTANCE.setStreamUtil(StreamUtil.INSTANCE);
  }

  /** @see #getStreamUtil() */
  private StreamUtil streamUtil;

  /** @see #getLogger() */
  private Log logger;

  /** @see #getExecutor() */
  private Executor executor;

  /**
   * The constructor.
   * 
   */
  public ProcessUtil() {

    super();
  }

  /**
   * @return the logger
   */
  protected Log getLogger() {

    return this.logger;
  }

  /**
   * @param logger the logger to set
   */
  @Resource
  public void setLogger(Log logger) {

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
  @Resource
  public void setStreamUtil(StreamUtil streamUtil) {

    this.streamUtil = streamUtil;
  }

  /**
   * This method executes the external {@link Process}es configured by the
   * given <code>builders</code>. If more than one
   * {@link ProcessBuilder builder} is given, the according processes are piped.<br>
   * <b>ATTENTION:</b><br>
   * This method spins up multiple {@link Thread threads}, especially when
   * multiple processes are piped (2*n+1[+1] threads). Therefore you should NOT
   * use the {@link #INSTANCE singleton} variant of this util except you are
   * writing a simple command-line client that does a simple job and then
   * terminates. When writing a server-application or library, that makes such
   * calls repetitive, you should NOT use the {@link #INSTANCE singleton} and
   * configure a thread-pool as {@link java.util.concurrent.Executor}.
   * 
   * @param context is the context of the process pipe (fist <code>stdin</code>,
   *        last <code>stdout</code> and <code>stderr</code> for all
   *        processes as well as a potential timeout).
   * @param builders are the configurations of the {@link Process}(es) to
   *        execute. The array needs to have a length greater than zero.
   * @return the {@link Process#waitFor() exit-code} of the {@link Process}-pipe
   *         configured by the given <code>builders</code>.
   * @throws IOException if an input/output-error occurred.
   * @throws InterruptedException if the calling {@link Thread} was interrupted
   *         while {@link Process#waitFor() waiting for} a {@link Process} to
   *         complete.
   */
  public int execute(ProcessContext context, ProcessBuilder... builders) throws IOException,
      InterruptedException {

    ProcessExecutor processExecutor = new ProcessExecutor(context, builders);
    return processExecutor.call().intValue();
  }

  /**
   * This method executes the external {@link Process}es configured by the
   * given <code>builders</code>. If more than one
   * {@link ProcessBuilder builder} is given, the according processes are piped.<br>
   * <b>ATTENTION:</b><br>
   * This method spins up multiple {@link Thread threads}, especially when
   * multiple processes are piped (2*n+1[+1] threads). Therefore you should NOT
   * use the {@link #INSTANCE singleton} variant of this util except you are
   * writing a simple command-line client that does a simple job and then
   * terminates. When writing a server-application or library, that makes such
   * calls repetitive, you should NOT use the {@link #INSTANCE singleton} and
   * configure a thread-pool as {@link java.util.concurrent.Executor}.
   * 
   * @param context is the context of the process pipe (fist <code>stdin</code>,
   *        last <code>stdout</code> and <code>stderr</code> for all
   *        processes as well as a potential timeout).
   * @param timeout is the maximum amount of time to wait for the
   *        {@link Process}-pipe to finish.
   * @param unit is the {@link TimeUnit} of the given <code>timeout</code>
   *        argument.
   * @param builders are the configurations of the {@link Process}(es) to
   *        execute. The array needs to have a length greater than zero.
   * @return the {@link Process#waitFor() exit-code} of the {@link Process}-pipe
   *         configured by the given <code>builders</code>.
   * @throws IOException if an input/output-error occurred.
   * @throws TimeoutException if the {@link Process}-pipe did NOT complete
   *         before the given <code>timeout</code> (according to
   *         <code>unit</code>).
   * @throws InterruptedException if the calling {@link Thread} was interrupted
   *         while waiting for the {@link Process}-pipe to complete and before
   *         the <code>timeout</code> occurred.
   */
  public int execute(ProcessContext context, long timeout, TimeUnit unit,
      ProcessBuilder... builders) throws IOException, TimeoutException, InterruptedException {

    try {
      AsyncProcessExecutor processExecutor = executeAsync(context, builders);
      return processExecutor.get(timeout, unit).intValue();
    } catch (ExecutionException e) {
      Throwable cause = e.getCause();
      if ((cause != null) && (cause instanceof InterruptedException)) {
        throw (InterruptedException) cause;
      } else {
        // should NOT happen...
        throw new IllegalStateException(e);
      }
    }
  }

  /**
   * This method executes the external {@link Process}es configured by the
   * given <code>builders</code> as async task. If more than one
   * {@link ProcessBuilder builder} is given, the according processes are piped.<br>
   * <b>ATTENTION:</b><br>
   * This method spins up multiple {@link Thread threads}, especially when
   * multiple processes are piped (2*n+1[+1] threads). Therefore you should NOT
   * use the {@link #INSTANCE singleton} variant of this util except you are
   * writing a simple command-line client that does a simple job and then
   * terminates. When writing a server-application or library, that makes such
   * calls repetitive, you should NOT use the {@link #INSTANCE singleton} and
   * configure a thread-pool as {@link java.util.concurrent.Executor}.
   * 
   * @param context is the context of the process pipe (fist <code>stdin</code>,
   *        last <code>stdout</code> and <code>stderr</code> for all
   *        processes as well as a potential timeout).
   * @param builders are the configurations of the
   * @return the {@link Process#waitFor() exit-code} of the {@link Process}-pipe
   *         configured by the given <code>builders</code>.
   * @throws IOException if an input/output-error occurred while setting up the
   *         {@link Process}(es).
   */
  public AsyncProcessExecutor executeAsync(ProcessContext context, ProcessBuilder... builders)
      throws IOException {

    ProcessExecutor processExecutor = new ProcessExecutor(context, builders);
    AsyncProcessExecutorImpl asyncExecutor = new AsyncProcessExecutorImpl(processExecutor);
    getExecutor().execute(asyncExecutor);
    return asyncExecutor;
  }

  /**
   * This inner class is the default implementation of the AsyncProcessExecutor.
   */
  protected static class AsyncProcessExecutorImpl extends FutureTask<Integer> implements
      AsyncProcessExecutor {

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

    /**
     * {@inheritDoc}
     */
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

    /** */
    private final ProcessContext context;

    /** */
    private final Process[] processes;

    /** */
    private final AsyncTransferrer[] transferrers;

    /**
     * The constructor.
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
          AsyncTransferrer inOutTransferrer = streamUtility.transferAsync(in, this.processes[i]
              .getOutputStream(), false);
          AsyncTransferrer errTransferrer = streamUtility.transferAsync(this.processes[i]
              .getErrorStream(), err, true);
          this.processes[i] = process;
          in = this.processes[i].getInputStream();
          int transferrersIndex = i + i;
          this.transferrers[transferrersIndex] = inOutTransferrer;
          this.transferrers[transferrersIndex + 1] = errTransferrer;
        }
        this.transferrers[builders.length + builders.length] = streamUtility.transferAsync(in,
            context.getOutStream(), false);
        success = true;
        // if (this.mainThread != null) {
        // this.mainThread.interrupt();
        // }
      } finally {
        if (!success) {
          stop();
        }
      }
    }

    /**
     * This method disposes this executor. All processes are
     * {@link Process#destroy() destroyed} and all streams are closed.
     */
    protected void dispose() {

      for (int i = 0; i < this.processes.length; i++) {
        if (this.processes[i] != null) {
          this.processes[i].destroy();
          this.processes[i] = null;
        }
      }
      for (int i = 0; i < this.transferrers.length; i++) {
        if (this.transferrers[i] != null) {
          this.transferrers[i].cancel(true);
          this.transferrers[i] = null;
        }
      }
      StreamUtil streamUtility = getStreamUtil();
      streamUtility.close(this.context.getInStream());
      streamUtility.close(this.context.getOutStream());
      streamUtility.close(this.context.getErrStream());
    }

    /**
     * {@inheritDoc}
     */
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
