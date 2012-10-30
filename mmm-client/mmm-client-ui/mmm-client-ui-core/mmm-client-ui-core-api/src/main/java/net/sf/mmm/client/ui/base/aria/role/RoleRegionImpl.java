/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleRegion;

/**
 * This is the implementation of {@link RoleRegion}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class RoleRegionImpl extends AbstractRoleWithAttributeExpanded implements RoleRegion {

  /**
   * The constructor.
   */
  public RoleRegionImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_REGION;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleRegion> getRoleInterface() {

    return RoleRegion.class;
  }

}
