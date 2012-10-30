/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleRadioGroup;

/**
 * This is the implementation of {@link RoleRadioGroup}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class RoleRadioGroupImpl extends AbstractRoleWithCommonAttributes implements RoleRadioGroup {

  /**
   * The constructor.
   */
  public RoleRadioGroupImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_RADIO_GROUP;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleRadioGroup> getRoleInterface() {

    return RoleRadioGroup.class;
  }

}
