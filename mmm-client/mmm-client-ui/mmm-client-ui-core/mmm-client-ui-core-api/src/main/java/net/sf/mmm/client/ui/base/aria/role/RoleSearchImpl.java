/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleSearch;

/**
 * This is the implementation of {@link RoleSearch}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class RoleSearchImpl extends AbstractRoleWithAttributeExpanded implements RoleSearch {

  /**
   * The constructor.
   */
  public RoleSearchImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_SEARCH;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleSearch> getRoleInterface() {

    return RoleSearch.class;
  }

}
