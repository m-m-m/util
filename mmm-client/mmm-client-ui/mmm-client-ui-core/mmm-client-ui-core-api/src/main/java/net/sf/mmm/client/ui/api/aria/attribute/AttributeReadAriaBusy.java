/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.attribute;

import net.sf.mmm.client.ui.api.common.Accessibility;

/**
 * This interface gives read access to the {@link #isBusy() busy} attribute (state) of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadAriaBusy extends Accessibility {

  /** The name of the <code>busy</code> attribute. */
  String HTML_ATTRIBUTE_ARIA_BUSY = "aria-busy";

  /**
   * This method gets the value of the <a
   * href="http://www.w3.org/TR/wai-aria/states_and_properties#aria-busy">busy</a> state of this object.
   * 
   * @return the busy state. The default (if NOT set) is <code>false</code>.
   */
  boolean isBusy();

}
