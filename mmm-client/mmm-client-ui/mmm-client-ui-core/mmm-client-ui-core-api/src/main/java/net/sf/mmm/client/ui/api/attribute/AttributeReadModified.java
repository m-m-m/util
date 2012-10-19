/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

/**
 * This interface gives read access to the {@link #isModified() modified} attribute of an object. Such object
 * tracks if it gets modifications.
 * 
 * @see AttributeReadEditable
 * @see AttributeWriteEnabled
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadModified {

  /**
   * This method determines if this object (or more precisely its value) is <em>modified</em>. This means that
   * the object has been modified (via a the user interface) since it's value was programatically
   * {@link net.sf.mmm.util.lang.api.attribute.AttributeWriteValue#setValue(Object) set} for the last time.
   * Often this is also called <em>dirty</em>. A composite object is also modified if one of its children are
   * modified.<br/>
   * <b>NOTE:</b><br/>
   * If the object's value is changed by the end-user to something else but then changed back to its original
   * value it may still remain {@link #isModified() modified}.
   * 
   * @return <code>true</code> if this object has been modified, <code>false</code> otherwise (if no changes
   *         have been made to this object).
   */
  boolean isModified();

}
