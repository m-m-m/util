/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RolePresentation;

/**
 * This is the implementation of {@link RolePresentation}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class RolePresentationImpl extends AbstractRole implements RolePresentation {

  /**
   * The constructor.
   */
  public RolePresentationImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_PRESENTATION;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RolePresentation> getRoleInterface() {

    return RolePresentation.class;
  }

}
