/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.attribute;

import net.sf.mmm.client.ui.api.aria.datatype.AriaChangeNotifications;
import net.sf.mmm.client.ui.api.common.Accessibility;

/**
 * This interface gives read access to the {@link #getRelevant() relevant} attribute (property) of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadAriaRelevant extends Accessibility {

  /** The name of the <code>relevant</code> attribute. */
  String HTML_ATTRIBUTE_ARIA_RELEVANT = "aria-relevant";

  /**
   * This method gets the <a
   * href="http://www.w3.org/TR/wai-aria/states_and_properties#aria-relevant">relevant</a> property of this
   * object. As this property is specified by a list of tokens but some tokens themselves represent
   * combinations we mapped this to a single enum {@link AriaChangeNotifications} that covers all combinations
   * rather than a list of the enum.
   * 
   * @return the {@link AriaChangeNotifications} value or <code>null</code> if undefined.
   */
  AriaChangeNotifications getRelevant();

}
