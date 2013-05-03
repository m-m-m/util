/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

/**
 * This interface gives write access to the {@link #getPrimaryStyle() primary style} of an object.
 * 
 * @see AttributeWriteStyles
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWritePrimaryStyle extends AttributeReadPrimaryStyle {

  /**
   * This method sets the {@link #getPrimaryStyle() primary style}.
   * 
   * @see AttributeReadStyles#getStyles()
   * 
   * @param primaryStyle is the new value for {@link #getPrimaryStyle() primary style}.
   */
  void setPrimaryStyle(String primaryStyle);

}
