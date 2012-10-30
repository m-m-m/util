/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleApplication;

/**
 * This is the implementation of {@link RoleApplication}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class RoleApplicationImpl extends AbstractRoleWithAttributeExpanded implements RoleApplication {

  /**
   * The constructor.
   */
  public RoleApplicationImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_APPLICATION;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleApplication> getRoleInterface() {

    return RoleApplication.class;
  }

}
