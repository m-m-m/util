/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleDialog;

/**
 * This is the implementation of {@link RoleDialog}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class RoleDialogImpl extends AbstractRoleWithAttributeExpanded implements RoleDialog {

  /**
   * The constructor.
   */
  public RoleDialogImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_DIALOG;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleDialog> getRoleInterface() {

    return RoleDialog.class;
  }

}
