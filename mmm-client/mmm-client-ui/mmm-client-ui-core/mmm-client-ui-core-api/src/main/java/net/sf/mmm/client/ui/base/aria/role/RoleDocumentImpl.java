/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleDocument;

/**
 * This is the implementation of {@link RoleDocument}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class RoleDocumentImpl extends AbstractRoleWithAttributeExpanded implements RoleDocument {

  /**
   * The constructor.
   */
  public RoleDocumentImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_DOCUMENT;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleDocument> getRoleInterface() {

    return RoleDocument.class;
  }

}
