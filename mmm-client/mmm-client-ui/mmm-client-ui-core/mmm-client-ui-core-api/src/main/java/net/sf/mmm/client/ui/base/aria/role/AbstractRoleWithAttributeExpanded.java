/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.attribute.AttributeWriteAriaExpanded;

/**
 * This class extends {@link AbstractRole} with {@link AttributeWriteAriaExpanded}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractRoleWithAttributeExpanded extends AbstractRole implements AttributeWriteAriaExpanded {

  /** @see #isExpanded() */
  private boolean expanded;

  /**
   * The constructor.
   */
  public AbstractRoleWithAttributeExpanded() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setExpanded(boolean expanded) {

    this.expanded = expanded;
    setAttribute(HTML_ATTRIBUTE_ARIA_EXPANDED, expanded);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isExpanded() {

    return this.expanded;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void updateDelegate() {

    super.updateDelegate();
    if (this.expanded) {
      setExpanded(true);
    }
  }

}
