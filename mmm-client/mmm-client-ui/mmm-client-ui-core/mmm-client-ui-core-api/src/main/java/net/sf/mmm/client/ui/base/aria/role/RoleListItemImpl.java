/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleListItem;

/**
 * This is the implementation or {@link RoleListItem}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class RoleListItemImpl extends AbstractRoleOption implements RoleListItem {

  /**
   * The constructor.
   */
  public RoleListItemImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_LIST_ITEM;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleListItem> getRoleInterface() {

    return RoleListItem.class;
  }

}
