/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.attribute;

import net.sf.mmm.client.ui.api.aria.datatype.AriaAutocomplete;
import net.sf.mmm.client.ui.api.common.Accessibility;

/**
 * This interface gives read access to the {@link #getAutocomplete() autocomplete} attribute (property) of an
 * object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadAriaAutocomplete extends Accessibility {

  /** The name of the <code>autocomplete</code> attribute. */
  String HTML_ATTRIBUTE_ARIA_AUTOCOMPLETE = "aria-autocomplete";

  /**
   * This method gets the <a
   * href="http://www.w3.org/TR/wai-aria/states_and_properties#aria-autocomplete">autocomplete</a> property of
   * this object.
   * 
   * @return the {@link AriaAutocomplete} value. The default (if NOT set) is {@link AriaAutocomplete#NONE}.
   */
  AriaAutocomplete getAutocomplete();

}
