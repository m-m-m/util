/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives read access to the {@link #isVisible() visible} property of an object. Such object can
 * be displayed to or hidden from the UI.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadVisible {

  /**
   * This method determines if this object is displayed to the user.
   * 
   * @see AttributeReadVisibleState#getVisibleState()
   * 
   * @return the value of {@link net.sf.mmm.ui.toolkit.api.common.VisibleState#isVisible()}.
   */
  boolean isVisible();

}
