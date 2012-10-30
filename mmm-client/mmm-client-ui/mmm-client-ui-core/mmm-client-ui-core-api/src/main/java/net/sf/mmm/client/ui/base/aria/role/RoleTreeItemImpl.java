/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleTreeItem;

/**
 * This is the implementation of {@link RoleTreeItem}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class RoleTreeItemImpl extends AbstractRoleOption implements RoleTreeItem {

  /**
   * The constructor.
   */
  public RoleTreeItemImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_TREE_ITEM;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleTreeItem> getRoleInterface() {

    return RoleTreeItem.class;
  }

}
