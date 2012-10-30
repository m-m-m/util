/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleRow;

/**
 * This is the implementation of {@link RoleRow}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class RoleRowImpl extends AbstractRoleWithCommonAttributes implements RoleRow {

  /**
   * The constructor.
   */
  public RoleRowImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_ROW;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleRow> getRoleInterface() {

    return RoleRow.class;
  }

}
