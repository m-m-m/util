/* $Id: UIDeviceImpl.java 304 2007-06-13 23:58:29Z hohwille $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt;

import net.sf.mmm.ui.toolkit.api.UiDevice;

import org.eclipse.swt.widgets.Monitor;

/**
 * This class is the implementation of the UIDevice interface using SWT as the
 * UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UiDeviceImpl implements UiDevice {

  /** the SWT graphics device */
  private Monitor device;

  /**
   * The constructor.
   * 
   * @param monitor is the SWT graphics device to represent.
   */
  public UiDeviceImpl(Monitor monitor) {

    super();
    this.device = monitor;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return this.device.toString();
  }
}
