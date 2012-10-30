/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleProgressBar;

/**
 * This is the implementation of {@link RoleProgressBar}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class RoleProgressBarImpl extends AbstractRoleRange implements RoleProgressBar {

  /**
   * The constructor.
   */
  public RoleProgressBarImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_PROGRESS_BAR;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleProgressBar> getRoleInterface() {

    return RoleProgressBar.class;
  }

}
