/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

import net.sf.mmm.client.ui.api.common.Accessibility;

/**
 * This interface gives read access to the {@link #isAriaHidden() aria-hidden} attribute of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadAriaHidden extends Accessibility {

  /**
   * This method gets the <em>ARIA hidden</em> attribute of this object. It indicates that the element and all
   * of its descendants are not visible or perceivable to any user.
   * 
   * @see AttributeReadVisible#isVisible()
   * 
   * @return <code>true</code> if the object is hidden, <code>false</code> if it is visible for assistive
   *         technologies.
   */
  boolean isAriaHidden();

}
