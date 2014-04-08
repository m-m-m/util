/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.attribute;

import net.sf.mmm.util.lang.api.concern.Accessibility;

/**
 * This interface gives read access to the {@link #isAtomic() atomic} attribute (property) of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadAriaAtomic extends Accessibility {

  /** The name of the <code>atomic</code> attribute. */
  String HTML_ATTRIBUTE_ARIA_ATOMIC = "aria-atomic";

  /**
   * This method gets the value of the <a
   * href="http://www.w3.org/TR/wai-aria/states_and_properties#aria-atomic">atomic</a> property of this
   * object.
   * 
   * @return the atomic state. <code>true</code> if assistive technologies should present the entire region as
   *         a whole, <code>false</code> if a change within the region may be processed by the assistive
   *         technologies on its own. The default (if NOT set) is <code>false</code>.
   */
  boolean isAtomic();

}
