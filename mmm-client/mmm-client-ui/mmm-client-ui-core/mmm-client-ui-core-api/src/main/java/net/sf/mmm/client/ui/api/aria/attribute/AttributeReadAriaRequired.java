/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.attribute;

import net.sf.mmm.util.lang.api.concern.Accessibility;

/**
 * This interface gives read access to the {@link #isRequired() required} attribute (property) of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadAriaRequired extends Accessibility {

  /** The name of the <code>required</code> attribute. */
  String HTML_ATTRIBUTE_ARIA_REQUIRED = "aria-required";

  /**
   * This method gets the value of the <a
   * href="http://www.w3.org/TR/wai-aria/states_and_properties#aria-required">required</a> property of this
   * object.
   * 
   * @return the required state. <code>true</code> if the user needs to provide input for this object in order
   *         to submit the data, <code>false</code> otherwise. The default (if NOT set) is <code>false</code>.
   */
  boolean isRequired();

}
