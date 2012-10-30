/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.attribute;

import net.sf.mmm.client.ui.api.common.Accessibility;

/**
 * This interface gives read access to the {@link #getPosInSet() posInSet} attribute (property) of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadAriaPosInSet extends Accessibility {

  /** The name of the <code>posinset</code> attribute. */
  String HTML_ATTRIBUTE_ARIA_POS_IN_SET = "aria-posinset";

  /**
   * This method gets the <a
   * href="http://www.w3.org/TR/wai-aria/states_and_properties#aria-posinset">posinset</a> property of this
   * object.
   * 
   * @return the number or position in the current set of
   *         {@link net.sf.mmm.client.ui.api.aria.role.RoleListItem list-items} or
   *         {@link net.sf.mmm.client.ui.api.aria.role.RoleTreeItem tree-items} or <code>null</code> if
   *         undefined.
   */
  Integer getPosInSet();

}
