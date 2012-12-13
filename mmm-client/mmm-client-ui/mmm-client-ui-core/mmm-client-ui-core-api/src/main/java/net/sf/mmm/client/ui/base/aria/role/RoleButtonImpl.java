/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.datatype.AriaTristate;
import net.sf.mmm.client.ui.api.aria.role.RoleButton;

/**
 * This is the implementation of {@link RoleButton}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class RoleButtonImpl extends AbstractRoleWithAttributeExpanded implements RoleButton {

  /** @see #getPressed() */
  private AriaTristate pressed;

  /**
   * The constructor.
   */
  public RoleButtonImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setPressed(AriaTristate pressed) {

    this.pressed = pressed;
    setAttribute(HTML_ATTRIBUTE_ARIA_PRESSED, pressed);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AriaTristate getPressed() {

    return this.pressed;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_BUTTON;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleButton> getRoleInterface() {

    return RoleButton.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void updateDelegate() {

    super.updateDelegate();
    if (this.pressed != null) {
      setPressed(this.pressed);
    }
  }

}
