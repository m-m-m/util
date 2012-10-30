/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleSeparator;

/**
 * This is the implementation of {@link RoleSeparator}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class RoleSeparatorImpl extends AbstractRoleWithCommonAttributes implements RoleSeparator {

  /**
   * The constructor.
   */
  public RoleSeparatorImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_SEPARATOR;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleSeparator> getRoleInterface() {

    return RoleSeparator.class;
  }

}
