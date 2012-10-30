/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleTimer;

/**
 * This is the implementation of {@link RoleTimer}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class RoleTimerImpl extends AbstractRoleWithAttributeExpanded implements RoleTimer {

  /**
   * The constructor.
   */
  public RoleTimerImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_TIMER;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleTimer> getRoleInterface() {

    return RoleTimer.class;
  }

}
