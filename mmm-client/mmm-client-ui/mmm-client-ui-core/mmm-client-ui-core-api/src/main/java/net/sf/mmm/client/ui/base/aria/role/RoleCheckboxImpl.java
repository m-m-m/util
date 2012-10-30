/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleCheckbox;

/**
 * This is the implementation of {@link RoleCheckboxImpl}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class RoleCheckboxImpl extends AbstractRoleWithAttributeChecked implements RoleCheckbox {

  /**
   * The constructor.
   */
  public RoleCheckboxImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_CHECKBOX;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleCheckbox> getRoleInterface() {

    return RoleCheckbox.class;
  }

}
