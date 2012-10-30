/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.attribute.AttributeWriteAriaSort;
import net.sf.mmm.client.ui.api.common.SortOrder;

/**
 * This class extends {@link AbstractRoleGridCell} with {@link AttributeWriteAriaSort}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractRoleGridCellHeader extends AbstractRoleWithBaseAttributes implements
    AttributeWriteAriaSort {

  /** @see #getSort() */
  private SortOrder sort;

  /**
   * The constructor.
   */
  public AbstractRoleGridCellHeader() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SortOrder getSort() {

    if (this.sort == null) {
      return SortOrder.NONE;
    }
    return this.sort;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSort(SortOrder sort) {

    this.sort = sort;
    setAttribute(HTML_ATTRIBUTE_ARIA_SORT, sort);
  }

}
