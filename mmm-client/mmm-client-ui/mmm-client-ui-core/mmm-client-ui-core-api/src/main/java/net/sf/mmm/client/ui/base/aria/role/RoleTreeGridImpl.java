/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleTreeGrid;

/**
 * This is the implementation of {@link RoleTreeGrid}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class RoleTreeGridImpl extends AbstractRoleWithCommonAttributesAndMultiSelectable implements RoleTreeGrid {

  /**
   * The constructor.
   */
  public RoleTreeGridImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_TREE_GRID;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleTreeGrid> getRoleInterface() {

    return RoleTreeGrid.class;
  }

}
