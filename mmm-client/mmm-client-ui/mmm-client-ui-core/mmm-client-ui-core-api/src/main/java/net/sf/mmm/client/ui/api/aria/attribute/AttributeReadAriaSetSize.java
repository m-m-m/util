/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.attribute;

import net.sf.mmm.util.lang.api.concern.Accessibility;

/**
 * This interface gives read access to the {@link #getSetSize() setSize} attribute (property) of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadAriaSetSize extends Accessibility {

  /** The name of the <code>setsize</code> attribute. */
  String HTML_ATTRIBUTE_ARIA_SET_SIZE = "aria-setsize";

  /**
   * This method gets the <a
   * href="http://www.w3.org/TR/wai-aria/states_and_properties#aria-setsize">setsize</a> property of this
   * object.
   * 
   * @return the number of items in the current set of {@link net.sf.mmm.client.ui.api.aria.role.RoleListItem
   *         list-items} or {@link net.sf.mmm.client.ui.api.aria.role.RoleTreeItem tree-items} or
   *         <code>null</code> if undefined.
   */
  Integer getSetSize();

}
