/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleGridCell;

/**
 * This is the implementation of {@link RoleGridCell}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class RoleGridCellImpl extends AbstractRoleWithBaseAttributes implements RoleGridCell {

  /**
   * The constructor.
   */
  public RoleGridCellImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_GRID_CELL;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleGridCell> getRoleInterface() {

    return RoleGridCell.class;
  }

}
