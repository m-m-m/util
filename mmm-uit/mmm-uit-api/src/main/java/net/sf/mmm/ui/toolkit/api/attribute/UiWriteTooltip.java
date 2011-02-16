/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This is the interface for an object of the UI framework that has a tooltip.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWriteTooltip {

  /**
   * This method gets the tooltip text of this object.
   * 
   * @return the tooltip text or <code>null</code> if tooltip is disabled.
   */
  String getTooltipText();

  /**
   * This method sets the tooltip text of this object.
   * 
   * @param tooltip is the new tooltip text or <code>null</code> to disable
   *        the tooltip.
   */
  void setTooltipText(String tooltip);

}
