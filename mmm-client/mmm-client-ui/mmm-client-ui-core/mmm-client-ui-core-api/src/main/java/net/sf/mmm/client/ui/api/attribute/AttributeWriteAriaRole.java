/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

import net.sf.mmm.client.ui.api.aria.role.Role;

/**
 * This interface gives read and write access to the {@link #getAriaRole() ARIA role} of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteAriaRole extends AttributeReadAriaRole {

  /**
   * This method sets the {@link #getAriaRole() ARIA role} of this object.<br/>
   * <b>NOTE:</b><br/>
   * If you are using the UI-Toolkit (<code>UiWidget</code>) the roles are automatically set for according
   * widgets. There is no need to e.g. manually set {@link net.sf.mmm.client.ui.api.aria.role.RoleCombobox} to
   * a <code>UiWidgetComboBox</code>. Therefore, you only need to set roles explicitly for specific custom
   * features. <b>ATTENTION:</b><br/>
   * This method will fail if an abstract {@link Role} is given.
   * 
   * @see #getAriaRole()
   * @see net.sf.mmm.client.ui.api.common.Accessibility
   * 
   * @param <ROLE> is the generic type of the new {@link Role}.
   * 
   * @param roleInterface is the interface of the new {@link #getAriaRole() ARIA role}. Must not be an
   *        abstract {@link Role} interface.
   * @return the instance of the new {@link Role} that is connected to this object.
   */
  <ROLE extends Role> ROLE setAriaRole(Class<ROLE> roleInterface);

}
