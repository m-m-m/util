/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleTree;

/**
 * This is the implementation of {@link RoleTree}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class RoleTreeImpl extends AbstractRoleWithCommonAttributesAndMultiSelectable implements RoleTree {

  /**
   * The constructor.
   */
  public RoleTreeImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_TREE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleTree> getRoleInterface() {

    return RoleTree.class;
  }

}
