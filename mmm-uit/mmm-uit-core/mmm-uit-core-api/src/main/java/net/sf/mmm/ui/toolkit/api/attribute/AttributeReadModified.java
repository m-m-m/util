/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives read access to the {@link #isModified() modified} attribute of an object. Such object
 * tracks if it gets modifications.
 * 
 * @see AtrributeReadEditable
 * @see AttributeWriteEnabled
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface AttributeReadModified {

  /**
   * This method determines if this object is <em>modified</em>. This means that the object has been modified
   * since it has been saved for the last time. Often this is also called <em>dirty</em>. Depending on the
   * implementation it may even remain modified if changes are done that bring the object back into its
   * initial state so it is {@link Object#equals(Object) equal} to its persistent state. If possible this
   * should be avoided. However a robust and easy solution is often better than a complex and potentially
   * buggy approach. This method may be offered by the data object itself (persistent entity) but also by an
   * object managing the data (e.g. a user interface).
   * 
   * @return <code>true</code> if this object has been modified, <code>false</code> otherwise (if no changes
   *         have been made to this object).
   */
  boolean isModified();

}
