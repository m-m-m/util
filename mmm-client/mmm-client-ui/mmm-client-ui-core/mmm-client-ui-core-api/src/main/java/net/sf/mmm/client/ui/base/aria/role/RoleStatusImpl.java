/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleStatus;

/**
 * This is the implementation of {@link RoleStatus}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class RoleStatusImpl extends AbstractRoleWithAttributeExpanded implements RoleStatus {

  /**
   * The constructor.
   */
  public RoleStatusImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_STATUS;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleStatus> getRoleInterface() {

    return RoleStatus.class;
  }

}
