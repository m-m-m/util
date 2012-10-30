/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleSpinButton;

/**
 * This is the implementation of {@link RoleSpinButton}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class RoleSpinButtonImpl extends AbstractRoleRange implements RoleSpinButton {

  /**
   * The constructor.
   */
  public RoleSpinButtonImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_SPIN_BUTTON;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleSpinButton> getRoleInterface() {

    return RoleSpinButton.class;
  }

}
