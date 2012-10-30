/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleTab;

/**
 * This is the implementation of {@link RoleTab}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class RoleTabImpl extends AbstractRoleWithAttributeExpandedAndSelected implements RoleTab {

  /**
   * The constructor.
   */
  public RoleTabImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_TAB;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleTab> getRoleInterface() {

    return RoleTab.class;
  }

}
