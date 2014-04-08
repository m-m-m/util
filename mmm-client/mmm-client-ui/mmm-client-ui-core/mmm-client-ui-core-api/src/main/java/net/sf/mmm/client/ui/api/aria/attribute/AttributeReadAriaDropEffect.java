/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.attribute;

import net.sf.mmm.client.ui.api.aria.datatype.AriaDropEffect;
import net.sf.mmm.util.lang.api.concern.Accessibility;

/**
 * This interface gives read access to the {@link #getDropEffect() dropEffect} attribute (property) of an
 * object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadAriaDropEffect extends Accessibility {

  /** The name of the <code>dropeffect</code> attribute. */
  String HTML_ATTRIBUTE_ARIA_DROP_EFFECT = "aria-dropeffect";

  /**
   * This method gets the <a
   * href="http://www.w3.org/TR/wai-aria/states_and_properties#aria-dropeffect">dropeffect</a> property of
   * this object. This property is specified as a list of tokens but many combinations such as copy and move
   * do not make sense. Therefore we defined combinations in {@link AriaDropEffect} rather than returning a
   * list of {@link AriaDropEffect}s.
   * 
   * @return the {@link AriaDropEffect} value. The default (if NOT set) is {@link AriaDropEffect#NONE}.
   */
  AriaDropEffect getDropEffect();

}
