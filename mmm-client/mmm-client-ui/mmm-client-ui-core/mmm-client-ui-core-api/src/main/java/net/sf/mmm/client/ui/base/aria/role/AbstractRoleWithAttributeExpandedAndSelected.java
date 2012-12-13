/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.attribute.AttributeWriteAriaSelected;

/**
 * This class extends {@link AbstractRoleWithAttributeExpanded} with {@link AttributeWriteAriaSelected}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractRoleWithAttributeExpandedAndSelected extends AbstractRoleWithAttributeExpanded implements
    AttributeWriteAriaSelected {

  /** @see #getSelected() */
  private Boolean selected;

  /**
   * The constructor.
   */
  public AbstractRoleWithAttributeExpandedAndSelected() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSelected(Boolean selected) {

    this.selected = selected;
    setAttribute(HTML_ATTRIBUTE_ARIA_SELECTED, selected);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Boolean getSelected() {

    return this.selected;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void updateDelegate() {

    super.updateDelegate();
    if (this.selected != null) {
      setSelected(this.selected);
    }
  }

}
