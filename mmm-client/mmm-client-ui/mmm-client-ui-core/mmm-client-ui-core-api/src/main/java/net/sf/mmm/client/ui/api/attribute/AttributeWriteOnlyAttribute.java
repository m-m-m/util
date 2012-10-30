/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

/**
 * This interface gives write access to the {@link #setAttribute(String, String) attribute} of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteOnlyAttribute {

  /**
   * This method sets the {@link AttributeReadAttribute#getAttribute(String) attribute} of this object.
   * 
   * @param name is the name of the {@link AttributeReadAttribute#getAttribute(String) attribute}.
   * @param value is the new value of the {@link AttributeReadAttribute#getAttribute(String) attribute}. May
   *        be <code>null</code> to unset (remove attribute).
   */
  void setAttribute(String name, String value);

}
