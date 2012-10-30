/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleMarquee;

/**
 * This is the implementation of {@link RoleMarquee}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class RoleMarqueeImpl extends AbstractRoleWithAttributeExpanded implements RoleMarquee {

  /**
   * The constructor.
   */
  public RoleMarqueeImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_MARQUEE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleMarquee> getRoleInterface() {

    return RoleMarquee.class;
  }

}
