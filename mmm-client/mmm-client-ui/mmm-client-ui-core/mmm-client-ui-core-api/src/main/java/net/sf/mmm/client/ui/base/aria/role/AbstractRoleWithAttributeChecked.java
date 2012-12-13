/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.attribute.AttributeWriteAriaChecked;
import net.sf.mmm.client.ui.api.aria.datatype.AriaTristate;

/**
 * This is the implementation of {@link AbstractRoleWithAttributeChecked}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractRoleWithAttributeChecked extends AbstractRole implements AttributeWriteAriaChecked {

  /** @see #getChecked() */
  private AriaTristate checked;

  /**
   * The constructor.
   */
  public AbstractRoleWithAttributeChecked() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setChecked(AriaTristate checked) {

    this.checked = checked;
    setAttribute(HTML_ATTRIBUTE_ARIA_CHECKED, checked);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AriaTristate getChecked() {

    return this.checked;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void updateDelegate() {

    super.updateDelegate();
    if (this.checked != null) {
      setChecked(this.checked);
    }
  }

}
