/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleArticle;

/**
 * This is the implementation of {@link RoleArticle}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class RoleArticleImpl extends AbstractRoleWithAttributeExpanded implements RoleArticle {

  /**
   * The constructor.
   */
  public RoleArticleImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_ARTICLE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleArticle> getRoleInterface() {

    return RoleArticle.class;
  }

}
