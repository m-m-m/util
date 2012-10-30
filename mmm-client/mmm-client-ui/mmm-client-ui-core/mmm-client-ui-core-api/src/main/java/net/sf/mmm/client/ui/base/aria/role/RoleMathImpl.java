/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleMath;

/**
 * This is the implementation of {@link RoleMath}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class RoleMathImpl extends AbstractRoleWithAttributeExpanded implements RoleMath {

  /**
   * The constructor.
   */
  public RoleMathImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_MATH;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleMath> getRoleInterface() {

    return RoleMath.class;
  }

}
