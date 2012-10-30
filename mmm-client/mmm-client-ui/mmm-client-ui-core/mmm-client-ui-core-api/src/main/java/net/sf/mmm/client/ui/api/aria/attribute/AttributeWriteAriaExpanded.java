/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.attribute;

/**
 * This interface gives read and write access to the {@link #isExpanded() expanded} attribute of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteAriaExpanded extends AttributeReadAriaExpanded {

  /**
   * This method sets the {@link #isExpanded() expanded} state of this object.
   * 
   * @param expanded is the new value of {@link #isExpanded()}.
   */
  void setExpanded(boolean expanded);

}
