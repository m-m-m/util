/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleTabList;

/**
 * This is the implementation of {@link RoleTabList}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class RoleTabListImpl extends AbstractRoleWithCommonAttributes implements RoleTabList {

  /**
   * The constructor.
   */
  public RoleTabListImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_TAB_LIST;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleTabList> getRoleInterface() {

    return RoleTabList.class;
  }

}
