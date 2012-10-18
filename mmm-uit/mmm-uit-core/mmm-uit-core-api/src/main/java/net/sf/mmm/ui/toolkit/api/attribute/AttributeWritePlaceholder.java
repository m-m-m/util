/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives read and write access to the {@link #getPlaceholder() placeholder} attribute of an
 * object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWritePlaceholder extends AttributeReadPlaceholder {

  /**
   * This method sets the {@link #getPlaceholder() placeholder} of this object.
   * 
   * @param placeholder is the new {@link #getPlaceholder() placeholder}. Use the empty string to remove.
   */
  void setPlaceholder(String placeholder);

}
