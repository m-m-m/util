/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleMenu;

/**
 * This is the implementation of {@link RoleMenu}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class RoleMenuImpl extends AbstractRoleGroup implements RoleMenu {

  /**
   * The constructor.
   */
  public RoleMenuImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_MENU;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleMenu> getRoleInterface() {

    return RoleMenu.class;
  }

}
