/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleAlert;

/**
 * This is the implementation of {@link RoleAlert}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class RoleAlertImpl extends AbstractRoleWithAttributeExpanded implements RoleAlert {

  /**
   * The constructor.
   */
  public RoleAlertImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleAlert> getRoleInterface() {

    return RoleAlert.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_ALERT;
  }

}
