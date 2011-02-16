/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base;

import java.util.concurrent.Callable;

import net.sf.mmm.ui.toolkit.api.UIDevice;
import net.sf.mmm.ui.toolkit.api.UIDisplay;
import net.sf.mmm.ui.toolkit.api.UIFactoryRenamed;

/**
 * This is the abstract base implementation of the UIDisplay interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractUIDisplay extends AbstractUIObject implements UIDisplay {

  /** the UI device this display belongs to */
  private UIDevice device;

  /**
   * The constructor.
   * 
   * @param uiFactory is the UI factory instance.
   * @param uiDevice is the device the display belongs to.
   */
  public AbstractUIDisplay(AbstractUIFactory uiFactory, UIDevice uiDevice) {

    super(uiFactory);
    this.device = uiDevice;
  }

  /**
   * {@inheritDoc}
   */
  public String getType() {

    return TYPE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return getDevice().toString() + "[" + getWidth() + "*" + getHeight() + "]";
  }

  /**
   * {@inheritDoc}
   */
  public UIDevice getDevice() {

    return this.device;
  }

  /**
   * {@inheritDoc}
   */
  public <T> T invokeSynchron(Callable<T> task) throws Exception {

    if (isDispatchThread()) {
      return task.call();
    } else {
      CallableRunner<T> runner = new CallableRunner<T>(task);
      doInvokeSynchron(runner);
      return runner.getResult();
    }
  }

  /**
   * {@inheritDoc}
   */
  public void invokeSynchron(Runnable task) {

    if (isDispatchThread()) {
      task.run();
    } else {
      doInvokeSynchron(task);
    }
  }

  /**
   * This method handles the {@link #invokeSynchron(Runnable)} if called from
   * another thread.
   * 
   * @param task
   */
  protected abstract void doInvokeSynchron(Runnable task);

}
