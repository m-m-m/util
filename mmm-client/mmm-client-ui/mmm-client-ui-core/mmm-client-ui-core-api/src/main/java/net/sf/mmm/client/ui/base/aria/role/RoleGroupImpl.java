/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleGroup;

/**
 * This is the implementation of {@link RoleGroup}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class RoleGroupImpl extends AbstractRoleGroup {

  /**
   * The constructor.
   */
  public RoleGroupImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_GROUP;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleGroup> getRoleInterface() {

    return RoleGroup.class;
  }

}
