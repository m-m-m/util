/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleListbox;

/**
 * This is the implementation of {@link RoleListbox}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class RoleListboxImpl extends AbstractRoleWithCommonAttributesAndMultiSelectable implements RoleListbox {

  /**
   * The constructor.
   */
  public RoleListboxImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_LISTBOX;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleListbox> getRoleInterface() {

    return RoleListbox.class;
  }

}
