/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives read access to the {@link #isFocused() enabled-flag} of an object. Such object allows
 * interactive behavior that can be enabled or disabled. If an object is disabled, the user can NOT interact
 * with the object. This is typically visualized by greying out the object.
 * 
 * @see AtrributeReadEditable
 * @see AttributeWriteEnabled
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadFocused {

  /**
   * This method determines if this object currently has the focus. In this case the cursor is placed in the
   * object (e.g. a text input) and it will receive keyboard events.
   * 
   * @return <code>true</code> if this object is focused, <code>false</code> otherwise.
   */
  boolean isFocused();

}
