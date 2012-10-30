/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleRowGroup;

/**
 * This is the implementation of {@link RoleRowGroup}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class RoleRowGroupImpl extends AbstractRoleGroup implements RoleRowGroup {

  /**
   * The constructor.
   */
  public RoleRowGroupImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_ROW_GROUP;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleRowGroup> getRoleInterface() {

    return RoleRowGroup.class;
  }

}
