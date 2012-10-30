/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleList;

/**
 * This is the implementation of {@link RoleList}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class RoleListImpl extends AbstractRoleWithAttributeExpanded implements RoleList {

  /**
   * The constructor.
   */
  public RoleListImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_LIST;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleList> getRoleInterface() {

    return RoleList.class;
  }

}
