/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleGridCell;

/**
 * This is the abstract base implementation of {@link RoleGridCell}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractRoleGridCell extends AbstractRoleWithAttributeExpandedAndSelected implements RoleGridCell {

  /** @see #isRequired() */
  private boolean required;

  /** @see #isReadOnly() */
  private boolean readOnly;

  /**
   * The constructor.
   */
  public AbstractRoleGridCell() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setRequired(boolean required) {

    this.required = required;
    setAttribute(HTML_ATTRIBUTE_ARIA_REQUIRED, required);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isRequired() {

    return this.required;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setReadOnly(boolean readOnly) {

    this.readOnly = readOnly;
    setAttribute(HTML_ATTRIBUTE_ARIA_READ_ONLY, readOnly);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isReadOnly() {

    return this.readOnly;
  }

}
