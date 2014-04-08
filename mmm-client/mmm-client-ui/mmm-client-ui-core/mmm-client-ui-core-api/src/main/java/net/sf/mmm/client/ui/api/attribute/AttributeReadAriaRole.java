/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

import net.sf.mmm.client.ui.api.aria.role.Role;
import net.sf.mmm.util.lang.api.concern.Accessibility;

/**
 * This interface gives read access to the {@link #getAriaRole() ARIA role} attribute of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadAriaRole extends Accessibility {

  /**
   * This method gets the {@link #WAI_ARIA} {@link Role role} of this object.<br/>
   * <b>ATTENTION:</b><br/>
   * A reasonable UI framework (e.g. when using <code>UiWidget</code>) is supposed to provide high-level
   * widgets and support setting the according {@link Role} and their attributes automatically. Therefore
   * users should only use this for special situations e.g. for setting {@link Role#setHidden(boolean)
   * aria-hidden} intentionally for a visible object.
   * 
   * @see net.sf.mmm.util.lang.api.concern.Accessibility
   * 
   * @return the {@link Role} or <code>null</code> if NOT set.
   */
  Role getAriaRole();

}
