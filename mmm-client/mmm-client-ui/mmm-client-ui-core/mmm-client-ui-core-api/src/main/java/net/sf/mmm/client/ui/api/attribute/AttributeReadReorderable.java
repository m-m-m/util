/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

/**
 * This interface gives read access to the {@link #isReorderable() reorderable} attribute of an object.
 * 
 * @see AttributeWriteReorderable
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadReorderable {

  /**
   * This method determines if this object can be reordered.
   * 
   * @return <code>true</code> if the object can be reordered (by the end-user) - typically via drag and drop,
   *         <code>false</code> otherwise.
   */
  boolean isReorderable();

}
