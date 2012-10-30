/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleAlertDialog;

/**
 * This is the implementation of {@link RoleAlertDialog}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class RoleAlertDialogImpl extends AbstractRoleWithAttributeExpanded implements RoleAlertDialog {

  /**
   * The constructor.
   */
  public RoleAlertDialogImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_ALERT_DIALOG;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleAlertDialog> getRoleInterface() {

    return RoleAlertDialog.class;
  }

}
