/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives read and write access to the {@link #getTooltip() tooltip} of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface AttributeWriteTooltip extends AttributeReadTooltip {

  /**
   * This method sets the tooltip text of this object.
   * 
   * @see #getTooltip()
   * 
   * @param tooltip is the new tooltip text or <code>null</code> to disable the tooltip.
   */
  void setTooltip(String tooltip);

}
