/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

/**
 * This interface gives read and write access to the {@link #isClosable() closable} attribute of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteClosable extends AttributeReadClosable {

  /**
   * This method sets the {@link #isClosable() closable} attribute.
   * 
   * @param closable is the new value of {@link #isClosable()}.
   */
  void setClosable(boolean closable);

}
