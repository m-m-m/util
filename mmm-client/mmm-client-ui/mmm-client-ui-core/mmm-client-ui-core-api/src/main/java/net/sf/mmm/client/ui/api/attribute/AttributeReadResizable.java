/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

/**
 * This interface gives read access to the {@link #isResizable() resizable} attribute of an object.
 * 
 * @see AttributeWriteResizable
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadResizable {

  /**
   * This method determines if this object is resizable.
   * 
   * @return <code>true</code> if the object can be
   *         {@link AttributeWriteSizeAdvanced#setSize(net.sf.mmm.client.ui.api.common.Length, net.sf.mmm.client.ui.api.common.Length)
   *         resized} (by the end-user), <code>false</code> otherwise.
   */
  boolean isResizable();

}
