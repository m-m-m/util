/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.attribute.AttributeWriteAriaMultiSelectable;

/**
 * This class extends {@link AbstractRoleWithCommonAttributes} with {@link AttributeWriteAriaMultiSelectable}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractRoleWithCommonAttributesAndMultiSelectable extends AbstractRoleWithCommonAttributes
    implements AttributeWriteAriaMultiSelectable {

  /** @see #isMultiSelectable() */
  private boolean multiSelectable;

  /**
   * The constructor.
   */
  public AbstractRoleWithCommonAttributesAndMultiSelectable() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isMultiSelectable() {

    return this.multiSelectable;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMultiSelectable(boolean multiSelectable) {

    this.multiSelectable = multiSelectable;
    setAttribute(HTML_ATTRIBUTE_ARIA_MULTI_SELECTABLE, multiSelectable);
  }

}
