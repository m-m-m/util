/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleComposite;

/**
 * This is the implementation of {@link RoleComposite}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class RoleCompositeImpl extends AbstractRoleComposite {

  /**
   * The constructor.
   */
  public RoleCompositeImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_COMPOSITE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleComposite> getRoleInterface() {

    return RoleComposite.class;
  }
}
