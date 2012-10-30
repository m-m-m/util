/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleBanner;

/**
 * This is the implementation of {@link RoleBanner}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class RoleBannerImpl extends AbstractRoleWithAttributeExpanded implements RoleBanner {

  /**
   * The constructor.
   */
  public RoleBannerImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_BANNER;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleBanner> getRoleInterface() {

    return RoleBanner.class;
  }

}
