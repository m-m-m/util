/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.attribute;

import net.sf.mmm.util.lang.api.concern.Accessibility;

/**
 * This interface gives read access to the {@link #isMultiSelectable() multi-selectable} attribute (property)
 * of an object.
 * 
 * @see net.sf.mmm.client.ui.api.attribute.AttributeReadSelectionMode
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadAriaMultiSelectable extends Accessibility {

  /** The name of the <code>multiselectable</code> attribute. */
  String HTML_ATTRIBUTE_ARIA_MULTI_SELECTABLE = "aria-multiselectable";

  /**
   * This method gets the value of the <a
   * href="http://www.w3.org/TR/wai-aria/states_and_properties#aria-multiselectable">multiselectable</a>
   * property of this object.
   * 
   * @return the multi-selectable property. <code>true</code> if multiples items can be selected at a time,
   *         <code>false</code> otherwise. The default (if NOT set) is <code>false</code>.
   */
  boolean isMultiSelectable();

}
