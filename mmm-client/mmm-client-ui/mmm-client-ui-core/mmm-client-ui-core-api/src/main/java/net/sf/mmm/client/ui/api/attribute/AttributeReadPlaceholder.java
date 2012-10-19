/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

/**
 * This interface gives read access to the {@link #getPlaceholder() placeholder} attribute of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadPlaceholder {

  /**
   * This method gets the <em>placeholder</em> of this object. The placeholder is a text displayed as a hint
   * in the object if its {@link AttributeReadValueAsString#getValueAsString() value} is blank. The
   * placeholder text is typically displayed in gray to distinguish from actual user-input. Typical examples
   * are "Enter your name", or "Search...". The placeholder is a similar concept as the
   * {@link AttributeReadTooltip#getTooltip() tooltip}.
   * 
   * @return the placeholder or the empty string if NOT defined.
   */
  String getPlaceholder();

}
