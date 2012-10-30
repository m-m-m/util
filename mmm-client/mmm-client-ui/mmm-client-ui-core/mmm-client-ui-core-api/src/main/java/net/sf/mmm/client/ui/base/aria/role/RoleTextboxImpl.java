/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleTextbox;

/**
 * This is the implementation of {@link RoleTextbox}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class RoleTextboxImpl extends AbstractRoleWithCommonAttributesAndAutocomplete implements RoleTextbox {

  /** @see #isMultiLine() */
  private boolean multiLine;

  /**
   * The constructor.
   */
  public RoleTextboxImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_TEXTBOX;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleTextbox> getRoleInterface() {

    return RoleTextbox.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMultiLine(boolean multiLine) {

    this.multiLine = multiLine;
    setAttribute(HTML_ATTRIBUTE_ARIA_MULTILINE, multiLine);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isMultiLine() {

    return this.multiLine;
  }

}
