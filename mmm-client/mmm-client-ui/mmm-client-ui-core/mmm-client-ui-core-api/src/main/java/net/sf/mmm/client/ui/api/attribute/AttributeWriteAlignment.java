/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

import net.sf.mmm.util.lang.api.Alignment;

/**
 * This interface gives read and write access to the {@link #getAlignment() alignment} attribute of an object.
 * 
 * @see AttributeReadAlignment
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteAlignment extends AttributeReadAlignment {

  /**
   * This method set the {@link #getAlignment() alignment}.
   * 
   * @param alignment is the new value of {@link #getAlignment()}.
   */
  void setAlignment(Alignment alignment);

}
