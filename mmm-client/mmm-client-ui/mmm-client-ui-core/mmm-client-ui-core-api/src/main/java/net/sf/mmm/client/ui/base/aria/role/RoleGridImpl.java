/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleGrid;

/**
 * This is the implementation of {@link RoleGrid}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class RoleGridImpl extends AbstractRoleWithCommonAttributesAndMultiSelectable implements RoleGrid {

  /**
   * The constructor.
   */
  public RoleGridImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_GRID;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleGrid> getRoleInterface() {

    return RoleGrid.class;
  }

}
