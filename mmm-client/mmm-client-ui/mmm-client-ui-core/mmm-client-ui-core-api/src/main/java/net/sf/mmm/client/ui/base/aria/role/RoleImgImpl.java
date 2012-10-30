/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleImg;

/**
 * This is the implementation of {@link RoleImg}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class RoleImgImpl extends AbstractRoleWithAttributeExpanded implements RoleImg {

  /**
   * The constructor.
   */
  public RoleImgImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_IMG;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleImg> getRoleInterface() {

    return RoleImg.class;
  }

}
