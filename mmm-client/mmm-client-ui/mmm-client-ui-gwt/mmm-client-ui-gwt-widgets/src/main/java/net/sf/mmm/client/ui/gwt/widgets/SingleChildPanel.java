/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.gwt.widgets;

import com.google.gwt.user.client.ui.Widget;

/**
 * This is the interface for a {@link com.google.gwt.user.client.ui.Panel} that either only supports just a
 * {@link #setChild(Widget) single child} or if it supports multiple children it has a
 * {@link #setChild(Widget) specific child} that will be replaced if updated.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface SingleChildPanel {

  /**
   * @param child is the {@link Widget} to set as child. A potential child previously set via this method will
   *        be removed.
   */
  void setChild(Widget child);

}
