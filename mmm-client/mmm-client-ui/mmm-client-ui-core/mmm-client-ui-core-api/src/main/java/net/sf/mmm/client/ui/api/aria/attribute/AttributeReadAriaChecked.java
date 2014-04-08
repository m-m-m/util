/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.attribute;

import net.sf.mmm.client.ui.api.aria.datatype.AriaTristate;
import net.sf.mmm.util.lang.api.concern.Accessibility;

/**
 * This interface gives read access to the {@link #getChecked() checked} attribute (state) of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadAriaChecked extends Accessibility {

  /** The name of the <code>checked</code> attribute. */
  String HTML_ATTRIBUTE_ARIA_CHECKED = "aria-checked";

  /**
   * This method gets the <a
   * href="http://www.w3.org/TR/wai-aria/states_and_properties#aria-checked">checked</a> state of this object.
   * 
   * @return the {@link AriaTristate} indicating if this object is checked or <code>null</code> if undefined.
   */
  AriaTristate getChecked();

}
