/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

/**
 * This interface gives read and write access to the {@link #isModified() modified} attribute of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteModified extends AttributeReadModified {

  /**
   * This method sets the {@link #isModified() modified} attribute programatically. <br>
   * <b>ATTENTION:</b><br>
   * This interface and this method is NOT intended to be used by API users. The strategy how to determine if
   * something is {@link #isModified() modified} should be an implementation secret (another strategy may be
   * to get the value and check if it is equal to the original value what is however not as robust). Instead
   * methods such as {@link AttributeWriteValueAdvanced#setValueForUser(Object)} should be provided for API
   * users.
   * 
   * @see #isModified()
   * 
   * @param modified is the new value of {@link #isModified()}.
   */
  // TODO hohwille: For discussion... I currently think setModified should never be recursive...
  // * <b>NOTE:</b><br>
  // * Setting an object to {@link #isModified() modified} is a local operation in the object while resetting
  // * {@link #isModified() modified} back to <code>false</code> is a recursive operation that will be
  // * propagated to all descendants. <br>
  void setModified(boolean modified);

}
