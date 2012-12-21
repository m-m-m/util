/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api;

import java.util.concurrent.Callable;


/**
 * This is the interface for the dispatcher of UI events.
 * 
 * @see UiContext#getDispatcher()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiDispatcher {

  /**
   * If you want to support SWT in its intended way (this method delegates to
   * <code>Display.readAndDispatch</code>), you need to call this method frequently from a main loop of your
   * application client. For other toolkit implementations this method will simply do nothing.<br>
   * If you have a main loop in your foreground thread waiting until the GUI is closed, it is recommended to
   * call this method from your main loop followed by {@link #sleep()}.<br>
   * 
   * @return <code>true</code> if there is more work to do, <code>false</code> if it is safe to
   *         {@link #sleep() sleep} for a short while.
   */
  boolean dispatch();

  /**
   * This method puts the UI thread to <em>sleep</em> until a event to {@link #dispatch() dispatch} is
   * received or it is awakened.
   * 
   * @return <code>true</code> if an event to {@link #dispatch() dispatch} was received, <code>false</code>
   *         otherwise.
   */
  boolean sleep();

  /**
   * This method checks if the {@link Thread#currentThread() "current thread"} is the dispatcher thread of the
   * underlying UI toolkit.
   * 
   * @return <code>true</code> if this method was invoked within the dispatcher thread, <code>false</code>
   *         otherwise.
   */
  boolean isDispatchThread();

  /**
   * This method {@link Runnable#run() invokes} the given <code>task</code> synchronous in the dispatcher
   * thread of the UI. The term "synchronous" means that the current thread is suspended and this method will
   * NOT return before the task is completed. If this method is called in the dispatcher thread or there is NO
   * dispatcher thread in the underlying UI implementation, the <code>task</code> may simply be invoked
   * directly in the implementation of this method.
   * 
   * @param task is the job to {@link Runnable#run() invoke}.
   */
  void invokeSynchron(Runnable task);

  /**
   * This method {@link Runnable#run() invokes} the given <code>task</code> asynchronous in the dispatcher
   * thread of the UI. The term "asynchronous" means that this method may return before the <code>task</code>
   * is invoked. If there is NO dispatcher thread in the underlying UI implementation, the <code>task</code>
   * may simply be invoked directly in the implementation of this method.
   * 
   * @param task is the job to {@link Runnable#run() invoke}.
   */
  void invokeAsynchron(Runnable task);

  /**
   * This method {@link Runnable#run() invokes} the given <code>task</code> asynchronous after the given
   * dalay.
   * 
   * @param task is the job to {@link Runnable#run() invoke}.
   * @param delayMilliseconds is the delay in milliseconds when the <code>task</code> shall be executed.
   */
  void invokeTimer(Runnable task, int delayMilliseconds);

  /**
   * This method {@link Callable#call() invokes} the given <code>task</code> periodically. The first
   * {@link Callable#call() invocation} is performed after the given delay. Every time the <code>task</code>
   * returns {@link Boolean#TRUE true}, another {@link Callable#call() invocation} is performed after the same
   * delay until {@link Boolean#FALSE false} is returned.
   * 
   * @param task is the job to {@link Callable#call() invoke}.
   * @param delayMilliseconds is the periodically delay in milliseconds when the <code>task</code> shall be
   *        executed.
   */
  void invokeTimer(Callable<Boolean> task, int delayMilliseconds);

  /**
   * This method {@link Callable#call() invokes} the given <code>task</code> synchronous in the dispatcher
   * thread of the UI. The term "synchronous" means that the current thread is suspended and this method will
   * NOT return before the task is completed. If this method is called in the dispatcher thread or there is NO
   * dispatcher thread in the underlying UI implementation, the <code>task</code> may simply be invoked
   * directly in the implementation of this method.
   * 
   * @param <T> is the templated type of the task result.
   * @param task is the piece of code to be executed synchronous.
   * @return is the {@link Callable#call() result} of the task.
   * @throws Exception if {@link Callable#call()} throws an exception.
   */
  <T> T invokeSynchron(Callable<T> task) throws Exception;

}
