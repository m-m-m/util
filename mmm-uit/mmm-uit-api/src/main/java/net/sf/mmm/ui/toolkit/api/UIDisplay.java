/* $Id$ */
package net.sf.mmm.ui.toolkit.api;

import java.util.concurrent.Callable;

import net.sf.mmm.ui.toolkit.api.state.UIReadSize;

/**
 * This is the interface for the display where the UI objects are shown.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIDisplay extends UIObject, UIReadSize {

  /** the type of this object */
  String TYPE = "Display";

  /**
   * This method gets the display device.
   * 
   * @return the device.
   */
  UIDevice getDevice();

  /**
   * Call this method frequently in your main loop if you want to use the UI
   * toolkit in a single-threaded style. This allows using the SWT port
   * implementation in a way intendet by SWT. It does no harm with other
   * implementations but be aware that you do NOT work synchron in this case.<br>
   * If you have a main loop in your foreground thread waiting until the GUI
   * is closed, it is recommendet to call this method from your main loop
   * instead of {@link Thread#sleep(long) sleeping}.<br>
   */
  void dispatch();

  /**
   * This method checks if the {@link Thread#currentThread() "current Thread"}
   * is the dispatcher thread of the underlying UI toolkit.
   * 
   * @return <code>true</code> if this method was invoked within the
   *         dispatcher thread, <code>false</code> otherwise.
   */
  boolean isDispatchThread();

  /**
   * This method {@link Runnable#run() invokes} the given <code>task</code>
   * synchron in the dispatcher thread of the UI. The term "synchron" means
   * that the current thread is suspended and this method will NOT return
   * before the task is completed. If this method is called in the dispatcher
   * thread or there is NO dispatcher thread in the underlying UI
   * implementation, the <code>task</code> may simply be invoked directly in
   * the implementation of this method.
   * 
   * @param task
   *        is the job to {@link Runnable#run() invoke}.
   */
  void invokeSynchron(Runnable task);

  /**
   * This method {@link Runnable#run() invokes} the given <code>task</code>
   * asynchron in the dispatcher thread of the UI. The term "asynchron" means
   * that this method may return before the <code>task</code> is invoked. If
   * there is NO dispatcher thread in the underlying UI implementation, the
   * <code>task</code> may simply be invoked directly in the implementation
   * of this method.
   * 
   * @param task
   *        is the job to {@link Runnable#run() invoke}.
   */
  void invokeAsynchron(Runnable task);

  /**
   * This method {@link Callable#call() invokes} the given <code>task</code>
   * synchron in the dispatcher thread of the UI. The term "synchron" means
   * that the current thread is suspended and this method will NOT return
   * before the task is completed. If this method is called in the dispatcher
   * thread or there is NO dispatcher thread in the underlying UI
   * implementation, the <code>task</code> may simply be invoked directly in
   * the implementation of this method.
   * 
   * @param <T>
   *        is the templated type of the task result.
   * @param task
   *        is the piece of code to be executed synchron.
   * @return is the {@link Callable#call() result} of the task.
   * @throws Exception
   *         if {@link Callable#call()} throws an exception.
   */
  <T> T invokeSynchron(Callable<T> task) throws Exception;

}
