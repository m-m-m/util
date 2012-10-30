/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleNote;

/**
 * This is the implementation of {@link RoleNote}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class RoleNoteImpl extends AbstractRoleWithAttributeExpanded implements RoleNote {

  /**
   * The constructor.
   */
  public RoleNoteImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_NOTE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleNote> getRoleInterface() {

    return RoleNote.class;
  }

}
