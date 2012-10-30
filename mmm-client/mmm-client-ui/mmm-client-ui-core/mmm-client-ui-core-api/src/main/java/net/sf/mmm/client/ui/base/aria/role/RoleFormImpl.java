/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleForm;

/**
 * This is the implementation of {@link RoleForm}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class RoleFormImpl extends AbstractRoleWithAttributeExpanded implements RoleForm {

  /**
   * The constructor.
   */
  public RoleFormImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_FORM;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleForm> getRoleInterface() {

    return RoleForm.class;
  }

}
