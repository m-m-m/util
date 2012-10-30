/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleMenuItemCheckbox;

/**
 * This is the implementation of {@link RoleMenuItemCheckbox}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class RoleMenuItemCheckboxImpl extends AbstractRoleWithAttributeChecked implements RoleMenuItemCheckbox {

  /**
   * The constructor.
   */
  public RoleMenuItemCheckboxImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_MENU_ITEM_CHECKBOX;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleMenuItemCheckbox> getRoleInterface() {

    return RoleMenuItemCheckbox.class;
  }

}
