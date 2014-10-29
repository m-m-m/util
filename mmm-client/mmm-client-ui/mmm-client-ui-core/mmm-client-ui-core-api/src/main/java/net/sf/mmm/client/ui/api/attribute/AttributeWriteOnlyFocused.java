/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

/**
 * This interface gives write access to the {@link #setFocused() focused} attribute of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteOnlyFocused {

  /**
   * This method sets the {@link AttributeReadFocused#isFocused() focus} to this object. <br>
   * <b>NOTE:</b><br>
   * You can only set the focus. To actually remove it, you need to set it in a different widget. Further, as
   * an API user you can normally ignore the result. This is only relevant for composite widgets such as
   * panels, that delegate the call recursively and may not contain any child that can take the focus.
   * 
   * @return <code>true</code> if the focus has been set successfully, <code>false</code> otherwise.
   */
  boolean setFocused();

}
