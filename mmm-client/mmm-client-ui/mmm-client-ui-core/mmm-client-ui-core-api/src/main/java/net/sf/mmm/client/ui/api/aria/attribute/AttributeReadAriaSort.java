/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.attribute;

import net.sf.mmm.client.ui.api.aria.datatype.AriaSortOrder;
import net.sf.mmm.util.lang.api.concern.Accessibility;

/**
 * This interface gives read access to the {@link #getSort() sort} attribute (property) of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadAriaSort extends Accessibility {

  /** The name of the <code>sort</code> attribute. */
  String HTML_ATTRIBUTE_ARIA_SORT = "aria-sort";

  /**
   * This method gets the <a href="http://www.w3.org/TR/wai-aria/states_and_properties#aria-sort">sort</a>
   * property of this object.
   * 
   * @return the {@link AriaSortOrder}. The default (if NOT set) is {@link AriaSortOrder#NONE}.
   */
  AriaSortOrder getSort();

}
