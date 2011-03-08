/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.gwt;

import net.sf.mmm.ui.toolkit.api.UiDevice;
import net.sf.mmm.ui.toolkit.base.AbstractUiDisplay;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiDisplayImpl extends AbstractUiDisplay {

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   * @param uiDevice is the {@link #getDevice() device} instance.
   */
  public UiDisplayImpl(AbstractUiFactoryGwt uiFactory, UiDevice uiDevice) {

    super(uiFactory, uiDevice);
  }

  /**
   * {@inheritDoc}
   */
  public void dispatch() {

  }

  /**
   * {@inheritDoc}
   */
  public boolean isDispatchThread() {

    return true;
  }

  /**
   * {@inheritDoc}
   */
  public void invokeAsynchron(Runnable task) {

    task.run();
  }

  /**
   * {@inheritDoc}
   */
  public int getWidth() {

    return JavaScriptUtil.getWidthOfScreen();
  }

  /**
   * {@inheritDoc}
   */
  public int getHeight() {

    return JavaScriptUtil.getHeightOfScreen();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInvokeSynchron(Runnable task) {

    task.run();
  }

}
