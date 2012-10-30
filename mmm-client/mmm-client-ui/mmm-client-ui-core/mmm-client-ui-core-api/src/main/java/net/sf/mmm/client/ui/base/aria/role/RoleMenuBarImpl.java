/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleMenuBar;

/**
 * This is the implementation of {@link RoleMenuBar}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class RoleMenuBarImpl extends AbstractRoleGroup implements RoleMenuBar {

  /**
   * The constructor.
   */
  public RoleMenuBarImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_MENU_BAR;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleMenuBar> getRoleInterface() {

    return RoleMenuBar.class;
  }

}
