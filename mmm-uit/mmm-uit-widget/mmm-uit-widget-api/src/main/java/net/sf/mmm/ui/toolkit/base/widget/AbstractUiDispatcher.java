/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.widget;

import java.util.concurrent.Callable;

import net.sf.mmm.ui.toolkit.api.widget.UiDispatcher;

/**
 * This is the abstract base implementation of {@link UiDispatcher}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiDispatcher implements UiDispatcher {

  /**
   * The constructor.
   */
  public AbstractUiDispatcher() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean dispatch() {

    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isDispatchThread() {

    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void invokeSynchron(Runnable task) {

    task.run();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> T invokeSynchron(Callable<T> task) throws Exception {

    return task.call();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return getClass().getSimpleName();
  }

}
