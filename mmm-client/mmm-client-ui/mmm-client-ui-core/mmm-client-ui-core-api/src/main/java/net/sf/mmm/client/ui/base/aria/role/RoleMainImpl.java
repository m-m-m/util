/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleMain;

/**
 * This is the implementation of {@link RoleMain}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class RoleMainImpl extends AbstractRoleWithAttributeExpanded implements RoleMain {

  /**
   * The constructor.
   */
  public RoleMainImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_MAIN;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleMain> getRoleInterface() {

    return RoleMain.class;
  }

}
