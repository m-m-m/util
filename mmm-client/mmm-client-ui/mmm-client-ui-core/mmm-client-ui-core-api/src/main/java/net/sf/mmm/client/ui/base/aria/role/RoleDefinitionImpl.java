/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleDefinition;

/**
 * This is the implementation of {@link RoleDefinition}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class RoleDefinitionImpl extends AbstractRoleWithAttributeExpanded implements RoleDefinition {

  /**
   * The constructor.
   */
  public RoleDefinitionImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_DEFINITION;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleDefinition> getRoleInterface() {

    return RoleDefinition.class;
  }

}
