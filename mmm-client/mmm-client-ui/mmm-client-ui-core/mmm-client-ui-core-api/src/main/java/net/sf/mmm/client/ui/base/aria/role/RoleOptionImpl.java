/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleOption;

/**
 * This interface represents the <a href="http://www.w3.org/TR/wai-aria/roles#option">option</a> role.
 * 
 * @see #ARIA_ROLE_OPTION
 * @see #WAI_ARIA
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class RoleOptionImpl extends AbstractRoleOption {

  /**
   * The constructor.
   */
  public RoleOptionImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_OPTION;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleOption> getRoleInterface() {

    return RoleOption.class;
  }

}
