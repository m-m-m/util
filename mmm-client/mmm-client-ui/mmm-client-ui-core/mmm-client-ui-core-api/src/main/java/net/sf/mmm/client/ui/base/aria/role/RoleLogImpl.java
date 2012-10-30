/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleLog;

/**
 * This is the implementation of {@link RoleLog}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class RoleLogImpl extends AbstractRoleWithAttributeExpanded implements RoleLog {

  /**
   * The constructor.
   */
  public RoleLogImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_LOG;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleLog> getRoleInterface() {

    return RoleLog.class;
  }

}
