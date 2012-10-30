/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.attribute;

import net.sf.mmm.client.ui.api.common.Accessibility;

/**
 * This interface gives read access to the {@link #isReadOnly() readOnly} attribute (property) of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadAriaReadOnly extends Accessibility {

  /** The name of the <code>readonly</code> attribute. */
  String HTML_ATTRIBUTE_ARIA_READ_ONLY = "aria-readonly";

  /**
   * This method gets the value of the <a
   * href="http://www.w3.org/TR/wai-aria/states_and_properties#aria-readonly">readonly</a> property of this
   * object.
   * 
   * @return the read-only property. <code>true</code> if the object is read-only so the user cannot change
   *         its value, <code>false</code> otherwise. The default (if NOT set) is <code>false</code>.
   */
  boolean isReadOnly();

}
