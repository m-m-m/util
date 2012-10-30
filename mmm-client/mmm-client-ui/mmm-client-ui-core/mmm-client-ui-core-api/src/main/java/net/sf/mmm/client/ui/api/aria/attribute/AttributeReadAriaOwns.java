/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.attribute;

import net.sf.mmm.client.ui.api.aria.datatype.AriaIdList;
import net.sf.mmm.client.ui.api.common.Accessibility;

/**
 * This interface gives read access to the {@link #getOwns() owns} attribute (property) of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadAriaOwns extends Accessibility {

  /** The name of the <code>owns</code> attribute. */
  String HTML_ATTRIBUTE_ARIA_OWNS = "aria-owns";

  /**
   * This method gets the ID of the <a
   * href="http://www.w3.org/TR/wai-aria/states_and_properties#aria-owns">owns</a> property of this object.
   * 
   * @return the {@link AriaIdList} of objects owned by this object. Will be the empty {@link AriaIdList} if
   *         undefined.
   */
  AriaIdList getOwns();

}
