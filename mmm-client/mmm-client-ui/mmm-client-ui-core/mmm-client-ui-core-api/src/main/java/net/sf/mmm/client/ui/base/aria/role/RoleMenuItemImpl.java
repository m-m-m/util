/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleMenuItem;

/**
 * This is the implementation of {@link RoleMenuItem}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class RoleMenuItemImpl extends AbstractRole implements RoleMenuItem {

  /**
   * The constructor.
   */
  public RoleMenuItemImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_MENU_ITEM;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleMenuItem> getRoleInterface() {

    return RoleMenuItem.class;
  }

}
