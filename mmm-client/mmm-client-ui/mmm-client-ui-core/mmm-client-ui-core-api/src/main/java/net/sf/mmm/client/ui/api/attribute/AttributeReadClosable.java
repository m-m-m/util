/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

/**
 * This interface gives read access to the {@link #isClosable() closable} attribute of an object.
 * 
 * @see AttributeWriteClosable
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadClosable {

  /**
   * This method determines if this object (e.g. a window) can be closed by the end-user.
   * 
   * @return <code>true</code> if the object can be closed by the end-user, <code>false</code> otherwise.
   */
  boolean isClosable();

}
