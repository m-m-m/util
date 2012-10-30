/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

import net.sf.mmm.client.ui.api.common.Accessibility;

/**
 * This interface gives read access to the {@link #getAttribute(String) attribute} attribute of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadAttribute extends Accessibility {

  /**
   * This method gets the <em>attribute</em> with the given <code>name</code> of this object. This is generic
   * and not further specified. It can e.g. allow to get DOM attributes of an element.
   * 
   * @param name is the name of the requested attribute.
   * @return the value of the attribute with the given <code>name</code> or <code>null</code> if NOT set.
   */
  String getAttribute(String name);

}
