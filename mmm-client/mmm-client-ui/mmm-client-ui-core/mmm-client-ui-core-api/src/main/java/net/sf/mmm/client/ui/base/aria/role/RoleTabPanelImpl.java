/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleTabPanel;

/**
 * This is the implementation of {@link RoleTabPanel}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class RoleTabPanelImpl extends AbstractRoleWithAttributeExpanded implements RoleTabPanel {

  /**
   * The constructor.
   */
  public RoleTabPanelImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_TAB_PANEL;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleTabPanel> getRoleInterface() {

    return RoleTabPanel.class;
  }

}
