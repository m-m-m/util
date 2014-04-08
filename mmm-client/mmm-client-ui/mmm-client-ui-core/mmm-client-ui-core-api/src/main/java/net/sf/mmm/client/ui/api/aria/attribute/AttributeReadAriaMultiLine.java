/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.attribute;

import net.sf.mmm.util.lang.api.concern.Accessibility;

/**
 * This interface gives read access to the {@link #isMultiLine() multiLine} attribute (property) of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadAriaMultiLine extends Accessibility {

  /** The name of the <code>multiline</code> attribute. */
  String HTML_ATTRIBUTE_ARIA_MULTILINE = "aria-multiline";

  /**
   * This method gets the value of the <a
   * href="http://www.w3.org/TR/wai-aria/states_and_properties#aria-multiline">multiline</a> property of this
   * object.
   * 
   * @return <code>true</code> if the object allows multi-line text-input, <code>false</code> if only
   *         single-line text can be entered (default).
   */
  boolean isMultiLine();

}
