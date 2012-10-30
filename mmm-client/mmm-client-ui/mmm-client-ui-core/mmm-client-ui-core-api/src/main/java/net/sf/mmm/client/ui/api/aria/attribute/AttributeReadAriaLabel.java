/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.attribute;

import net.sf.mmm.client.ui.api.common.Accessibility;

/**
 * This interface gives read access to the {@link #getLabel() label} attribute (property) of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadAriaLabel extends Accessibility {

  /** The name of the <code>label</code> attribute. */
  String HTML_ATTRIBUTE_ARIA_LABEL = "aria-label";

  /**
   * This method gets the <a href="http://www.w3.org/TR/wai-aria/states_and_properties#aria-label">label</a>
   * property of this object.
   * 
   * @see net.sf.mmm.client.ui.api.attribute.AttributeReadLabel#getLabel()
   * 
   * @return the label or <code>null</code> if undefined.
   */
  String getLabel();

}
