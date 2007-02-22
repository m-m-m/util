/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.awt;

import java.awt.EventQueue;
import java.awt.GraphicsConfiguration;

import net.sf.mmm.ui.toolkit.api.UIFactory;
import net.sf.mmm.ui.toolkit.base.AbstractUIDisplay;

/**
 * This class is the implementation of the UIDisplay interface using AWT as
 * the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIDisplayImpl extends AbstractUIDisplay {

  /** the AWT graphics configuration */
  private GraphicsConfiguration gc;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is the UIFactorySwing instance.
   * @param uiDevice
   *        is the device the display belongs to.
   * @param graphicConfiguration
   *        is the graphics configuration for the diplay to represent.
   */
  public UIDisplayImpl(UIFactory uiFactory, UIDeviceImpl uiDevice,
      GraphicsConfiguration graphicConfiguration) {

    super(uiFactory, uiDevice);
    // GraphicsEnvironment.getLocalGraphicsEnvironment()
    // .getDefaultScreenDevice().getDefaultConfiguration()
    this.gc = graphicConfiguration;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadSize#getWidth()
   */
  public int getWidth() {

    return this.gc.getBounds().width;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadSize#getHeight()
   */
  public int getHeight() {

    return this.gc.getBounds().height;
  }

  /**
   * This method gets the graphics configuration. The method is only for
   * internal usage - do NOT access from outside (never leave the API).
   * 
   * @return the AWT graphics configuration object.
   */
  public GraphicsConfiguration getGraphicsConfiguration() {

    return this.gc;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIDisplay#dispatch()
   */
  public void dispatch() {

  // Sleep some time?
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIDisplay#isDispatchThread()
   */
  public boolean isDispatchThread() {

    return EventQueue.isDispatchThread();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIDisplay#invokeAsynchron(java.lang.Runnable)
   */
  public void invokeAsynchron(Runnable task) {

    EventQueue.invokeLater(task);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.base.AbstractUIDisplay#doInvokeSynchron(java.lang.Runnable)
   */
  @Override
  protected void doInvokeSynchron(Runnable task) {

    try {
      EventQueue.invokeAndWait(task);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
