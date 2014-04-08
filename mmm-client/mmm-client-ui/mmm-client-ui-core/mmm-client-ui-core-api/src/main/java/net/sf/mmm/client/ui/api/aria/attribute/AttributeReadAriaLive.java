/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.attribute;

import net.sf.mmm.client.ui.api.aria.datatype.AriaLiveLevel;
import net.sf.mmm.util.lang.api.concern.Accessibility;

/**
 * This interface gives read access to the {@link #getLive() live} attribute (property) of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadAriaLive extends Accessibility {

  /** The name of the <code>live</code> attribute. */
  String HTML_ATTRIBUTE_ARIA_LIVE = "aria-live";

  /**
   * This method gets the <a href="http://www.w3.org/TR/wai-aria/states_and_properties#aria-live">live</a>
   * property of this object.
   * 
   * @return the {@link AriaLiveLevel}. The default (if NOT set) is {@link AriaLiveLevel#OFF}.
   */
  AriaLiveLevel getLive();

}
