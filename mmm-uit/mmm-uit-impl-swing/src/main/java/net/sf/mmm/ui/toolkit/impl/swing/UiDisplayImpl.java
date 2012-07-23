/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing;

import java.awt.EventQueue;
import java.awt.GraphicsConfiguration;

import net.sf.mmm.ui.toolkit.base.AbstractUiDisplay;
import net.sf.mmm.ui.toolkit.base.AbstractUiFactory;

/**
 * This class is the implementation {@link net.sf.mmm.ui.toolkit.api.UiDisplay}
 * using AWT/Swing as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UiDisplayImpl extends AbstractUiDisplay {

  /** the AWT graphics configuration */
  private GraphicsConfiguration gc;

  /**
   * The constructor.
   * 
   * @param uiFactory is the UIFactorySwing instance.
   * @param uiDevice is the device the display belongs to.
   * @param graphicConfiguration is the graphics configuration for the display
   *        to represent.
   */
  public UiDisplayImpl(AbstractUiFactory uiFactory, UiDeviceImpl uiDevice,
      GraphicsConfiguration graphicConfiguration) {

    super(uiFactory, uiDevice);
    // GraphicsEnvironment.getLocalGraphicsEnvironment()
    // .getDefaultScreenDevice().getDefaultConfiguration()
    this.gc = graphicConfiguration;
  }

  /**
   * {@inheritDoc}
   */
  public int getWidthInPixel() {

    return this.gc.getBounds().width;
  }

  /**
   * {@inheritDoc}
   */
  public int getHeightInPixel() {

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
   * {@inheritDoc}
   */
  public void dispatch() {

    // Sleep some time?
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      // ignore...
    }
  }

  /**
   * {@inheritDoc}
   */
  public boolean isDispatchThread() {

    return EventQueue.isDispatchThread();
  }

  /**
   * {@inheritDoc}
   */
  public void invokeAsynchron(Runnable task) {

    EventQueue.invokeLater(task);
  }

  /**
   * {@inheritDoc}
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
