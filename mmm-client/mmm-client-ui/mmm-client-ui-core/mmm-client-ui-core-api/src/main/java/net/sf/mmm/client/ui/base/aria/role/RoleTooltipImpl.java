/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleTooltip;

/**
 * This is the implementation of {@link RoleTooltip}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class RoleTooltipImpl extends AbstractRoleWithAttributeExpanded implements RoleTooltip {

  /**
   * The constructor.
   */
  public RoleTooltipImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_TOOLTIP;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleTooltip> getRoleInterface() {

    return RoleTooltip.class;
  }

}
