/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.attribute;

import net.sf.mmm.client.ui.api.aria.datatype.AriaIdList;
import net.sf.mmm.client.ui.api.common.Accessibility;

/**
 * This interface gives read access to the {@link #getLabelledBy() labelledBy} attribute (property) of an
 * object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadAriaLabelledBy extends Accessibility {

  /** The name of the <code>labelledby</code> attribute. */
  String HTML_ATTRIBUTE_ARIA_LABELLED_BY = "aria-labelledby";

  /**
   * This method gets the ID of the <a
   * href="http://www.w3.org/TR/wai-aria/states_and_properties#aria-labelledby">labelledby</a> property of
   * this object.
   * 
   * @return the {@link AriaIdList} of IDs of objects that label this object. Will be the empty
   *         {@link AriaIdList} if undefined.
   */
  AriaIdList getLabelledBy();

}
