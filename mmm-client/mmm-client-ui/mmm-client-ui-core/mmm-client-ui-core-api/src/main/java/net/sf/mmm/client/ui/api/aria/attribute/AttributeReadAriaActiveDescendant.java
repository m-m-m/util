/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.attribute;

import net.sf.mmm.client.ui.api.common.Accessibility;

/**
 * This interface gives read access to the {@link #getActiveDescendant() activeDescendant} attribute
 * (property) of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadAriaActiveDescendant extends Accessibility {

  /** The name of the <code>activedescendant</code> attribute. */
  String HTML_ATTRIBUTE_ARIA_ACTIVE_DESCENDANT = "aria-activedescendant";

  /**
   * This method gets the ID of the <a
   * href="http://www.w3.org/TR/wai-aria/states_and_properties#aria-activedescendant">active descendant</a>
   * property of this object.
   * 
   * @return the ID of the active descendant or <code>null</code> if undefined.
   */
  String getActiveDescendant();

}
