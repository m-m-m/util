/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleComplementary;

/**
 * This is the implementation of {@link RoleComplementary}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class RoleComplementaryImpl extends AbstractRoleWithAttributeExpanded implements RoleComplementary {

  /**
   * The constructor.
   */
  public RoleComplementaryImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_COMPLEMENTARY;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleComplementary> getRoleInterface() {

    return RoleComplementary.class;
  }

}
