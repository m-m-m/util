/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleRadio;

/**
 * This is the implementation of {@link RoleRadio}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class RoleRadioImpl extends AbstractRoleOption implements RoleRadio {

  /**
   * The constructor.
   */
  public RoleRadioImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_RADIO;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleRadio> getRoleInterface() {

    return RoleRadio.class;
  }

}
