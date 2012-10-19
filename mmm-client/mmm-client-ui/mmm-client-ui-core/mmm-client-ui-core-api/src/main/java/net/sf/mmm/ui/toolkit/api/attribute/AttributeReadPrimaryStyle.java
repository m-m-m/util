/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives read access to the {@link #getPrimaryStyle() primary style} of an object.
 * 
 * @see AttributeReadStyles
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadPrimaryStyle {

  /**
   * This method gets the primary {@link AttributeReadStyles#getStyles() style} of this object.
   * 
   * @return the primary style of this object. Will be <code>null</code> if NOT defined.
   */
  String getPrimaryStyle();

}
