/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.attribute;

import net.sf.mmm.client.ui.api.common.Accessibility;

/**
 * This interface gives read access to the {@link #isExpanded() expanded} attribute (state) of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadAriaExpanded extends Accessibility {

  /** The name of the <code>expanded</code> attribute. */
  String HTML_ATTRIBUTE_ARIA_EXPANDED = "aria-expanded";

  /**
   * This method gets the value of the <a
   * href="http://www.w3.org/TR/wai-aria/states_and_properties#aria-expanded">expanded</a> state of this
   * object.
   * 
   * @return the busy state. The default (if NOT set) is <code>false</code>.
   */
  boolean isExpanded();

}
