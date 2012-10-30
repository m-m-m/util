/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleRowHeader;

/**
 * This is the implementation of {@link RoleRowHeader}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class RoleRowHeaderImpl extends AbstractRoleGridCellHeader implements RoleRowHeader {

  /**
   * The constructor.
   */
  public RoleRowHeaderImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_ROW_HEADER;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleRowHeader> getRoleInterface() {

    return RoleRowHeader.class;
  }

}
