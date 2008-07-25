/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.process.api;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import net.sf.mmm.util.process.base.ProcessUtilImpl;

/**
 * This is the interface for a collection of utility functions to deal with
 * {@link Process}es.
 * 
 * @see net.sf.mmm.util.process.base.ProcessUtilImpl
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ProcessUtil {

  /**
   * This method executes the external {@link Process}es configured by the given
   * <code>builders</code>. If more than one {@link ProcessBuilder builder} is
   * given, the according processes are piped.<br>
   * 
   * @param context is the context of the process pipe (fist <code>stdin</code>,
   *        last <code>stdout</code> and <code>stderr</code> for all processes
   *        as well as a potential timeout).
   * @param builders are the configurations of the {@link Process}(es) to
   *        execute. The array needs to have a length greater than zero.
   * @return the {@link Process#waitFor() exit-code} of the {@link Process}-pipe
   *         configured by the given <code>builders</code>.
   * @throws IOException if an input/output-error occurred.
   * @throws InterruptedException if the calling {@link Thread} was interrupted
   *         while {@link Process#waitFor() waiting for} a {@link Process} to
   *         complete.
   */
  int execute(ProcessContext context, ProcessBuilder... builders) throws IOException,
      InterruptedException;

  /**
   * This method executes the external {@link Process}es configured by the given
   * <code>builders</code>. If more than one {@link ProcessBuilder builder} is
   * given, the according processes are piped.<br>
   * <b>ATTENTION:</b><br>
   * This method spins up multiple {@link Thread threads}, especially when
   * multiple processes are piped (2*n+1[+1] threads). Therefore you should NOT
   * use the {@link #getInstance() singleton} variant of this util except you
   * are writing a simple command-line client that does a simple job and then
   * terminates. When writing a server-application or library, that makes such
   * calls repetitive, you should create your own instance of
   * {@link ProcessUtilImpl} and configure a thread-pool as
   * {@link java.util.concurrent.Executor}.
   * 
   * @param context is the context of the process pipe (fist <code>stdin</code>,
   *        last <code>stdout</code> and <code>stderr</code> for all processes
   *        as well as a potential timeout).
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
  int execute(ProcessContext context, long timeout, TimeUnit unit, ProcessBuilder... builders)
      throws IOException, TimeoutException, InterruptedException;

  /**
   * This method executes the external {@link Process}es configured by the given
   * <code>builders</code> as async task. If more than one
   * {@link ProcessBuilder builder} is given, the according processes are piped.<br>
   * <b>ATTENTION:</b><br>
   * This method spins up multiple {@link Thread threads}, especially when
   * multiple processes are piped (2*n+1[+1] threads). Therefore you should NOT
   * use the {@link #getInstance() singleton} variant of this util except you
   * are writing a simple command-line client that does a simple job and then
   * terminates. When writing a server-application or library, that makes such
   * calls repetitive, you should create your own instance of
   * {@link ProcessUtilImpl} and configure a thread-pool as
   * {@link java.util.concurrent.Executor}.
   * 
   * @param context is the context of the process pipe (fist <code>stdin</code>,
   *        last <code>stdout</code> and <code>stderr</code> for all processes
   *        as well as a potential timeout).
   * @param builders are the configurations of the {@link Process}(es) to
   *        execute. The array needs to have a length greater than zero.
   * @return the {@link Process#waitFor() exit-code} of the {@link Process}-pipe
   *         configured by the given <code>builders</code>.
   * @throws IOException if an input/output-error occurred while setting up the
   *         {@link Process}(es).
   */
  AsyncProcessExecutor executeAsync(ProcessContext context, ProcessBuilder... builders)
      throws IOException;

}
