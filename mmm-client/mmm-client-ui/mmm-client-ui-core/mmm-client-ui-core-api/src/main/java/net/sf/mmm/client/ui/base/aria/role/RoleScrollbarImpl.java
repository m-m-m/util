/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleScrollbar;

/**
 * This is the implementation of {@link RoleScrollbar}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class RoleScrollbarImpl extends AbstractRoleRange implements RoleScrollbar {

  /**
   * The constructor.
   */
  public RoleScrollbarImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_SCROLLBAR;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleScrollbar> getRoleInterface() {

    return RoleScrollbar.class;
  }

}
