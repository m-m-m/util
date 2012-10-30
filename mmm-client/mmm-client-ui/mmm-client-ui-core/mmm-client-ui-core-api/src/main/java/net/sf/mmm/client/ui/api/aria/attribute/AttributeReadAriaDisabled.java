/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.attribute;

import net.sf.mmm.client.ui.api.common.Accessibility;

/**
 * This interface gives read access to the {@link #isDisabled() disabled} attribute (state) of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadAriaDisabled extends Accessibility {

  /** The name of the <code>disabled</code> attribute. */
  String HTML_ATTRIBUTE_ARIA_DISABLED = "aria-disabled";

  /**
   * This method gets the value of the <a
   * href="http://www.w3.org/TR/wai-aria/states_and_properties#aria-disabled">disabled</a> state of this
   * object.
   * 
   * @return the disabled state. <code>true</code> if this object and all its focusable descendants are
   *         disabled so the end-user cannot change their value, <code>false</code> otherwise (default).
   */
  boolean isDisabled();

}
