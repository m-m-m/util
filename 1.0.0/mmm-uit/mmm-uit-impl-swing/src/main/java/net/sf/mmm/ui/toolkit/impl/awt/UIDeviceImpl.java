/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.awt;

import java.awt.GraphicsDevice;

import net.sf.mmm.ui.toolkit.api.UIDevice;

/**
 * This class is the implementation of the UIDevice interface using AWT as the
 * UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIDeviceImpl implements UIDevice {

  /** the actual device */
  private GraphicsDevice device;

  /**
   * The constructor.
   * 
   * @param graphicsDevice is the AWT device to represent.
   */
  public UIDeviceImpl(GraphicsDevice graphicsDevice) {

    super();
    this.device = graphicsDevice;
  }

  /**
   * This method gets the graphics device. The method is only for internal usage -
   * do NOT access from outside (never leave the API).
   * 
   * @return the AWT graphics device object.
   */
  public GraphicsDevice getGraphicsDevice() {

    return this.device;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return this.device.getIDstring();
  }
}
