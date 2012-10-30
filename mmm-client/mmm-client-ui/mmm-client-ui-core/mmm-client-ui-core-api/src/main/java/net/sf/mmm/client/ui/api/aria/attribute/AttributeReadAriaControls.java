/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.attribute;

import net.sf.mmm.client.ui.api.aria.datatype.AriaIdList;
import net.sf.mmm.client.ui.api.common.Accessibility;

/**
 * This interface gives read access to the {@link #getControls() controls} attribute (property) of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadAriaControls extends Accessibility {

  /** The name of the <code>controls</code> attribute. */
  String HTML_ATTRIBUTE_ARIA_CONTROLS = "aria-controls";

  /**
   * This method gets the ID of the <a
   * href="http://www.w3.org/TR/wai-aria/states_and_properties#aria-controls">controls</a> property of this
   * object.
   * 
   * @return the {@link AriaIdList} of the controls. Will be the an empty {@link AriaIdList} if undefined.
   */
  AriaIdList getControls();

}
