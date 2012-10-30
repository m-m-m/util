/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleDirectory;

/**
 * This is the implementation of {@link RoleDirectory}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class RoleDirectoryImpl extends AbstractRoleWithAttributeExpanded implements RoleDirectory {

  /**
   * The constructor.
   */
  public RoleDirectoryImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_DIRECTORY;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleDirectory> getRoleInterface() {

    return RoleDirectory.class;
  }

}
