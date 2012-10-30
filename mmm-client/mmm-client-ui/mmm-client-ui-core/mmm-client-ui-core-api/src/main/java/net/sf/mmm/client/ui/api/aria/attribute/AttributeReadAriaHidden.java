/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.attribute;

import net.sf.mmm.client.ui.api.common.Accessibility;

/**
 * This interface gives read access to the {@link #isHidden() hidden} attribute (state) of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadAriaHidden extends Accessibility {

  /** The name of the <code>hidden</code> attribute. */
  String HTML_ATTRIBUTE_ARIA_HIDDEN = "aria-hidden";

  /**
   * 
   * This method gets the <a href="http://www.w3.org/TR/wai-aria/states_and_properties#aria-hidden">hidden</a>
   * state of this object. It indicates whether the object and all of its descendants are not visible or
   * perceivable to any user.
   * 
   * @see net.sf.mmm.client.ui.api.attribute.AttributeReadVisible#isVisible()
   * 
   * @return <code>true</code> if the object is hidden, <code>false</code> if it is visible for assistive
   *         technologies.
   */
  boolean isHidden();

}
