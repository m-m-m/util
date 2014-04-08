/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.attribute;

import net.sf.mmm.util.lang.api.concern.Accessibility;

/**
 * This interface gives read access to the {@link #getGrabbed() grabbed} attribute (state) of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadAriaGrabbed extends Accessibility {

  /** The name of the <code>grabbed</code> attribute. */
  String HTML_ATTRIBUTE_ARIA_GRABBED = "aria-grabbed";

  /**
   * This method gets the value of the <a
   * href="http://www.w3.org/TR/wai-aria/states_and_properties#aria-grabbed">grabbed</a> state of this object.
   * 
   * @return <code>true</code> if the object has been "grabbed" for dragging, <code>false</code> if the object
   *         supports being dragged (but is not currently grabbed), <code>null</code> if the object does not
   *         support being dragged (default).
   */
  Boolean getGrabbed();

}
