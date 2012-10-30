/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleMenuItemRadio;

/**
 * This is the implementation of {@link RoleMenuItemRadio}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class RoleMenuItemRadioImpl extends AbstractRoleOption implements RoleMenuItemRadio {

  /**
   * The constructor.
   */
  public RoleMenuItemRadioImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_MENU_ITEM_RADIO;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleMenuItemRadio> getRoleInterface() {

    return RoleMenuItemRadio.class;
  }

}
