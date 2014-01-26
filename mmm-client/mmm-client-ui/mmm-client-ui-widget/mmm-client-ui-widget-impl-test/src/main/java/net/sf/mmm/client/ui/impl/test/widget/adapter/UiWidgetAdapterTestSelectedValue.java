/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.widget.adapter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteSelectedValue;

/**
 * This class extends {@link UiWidgetAdapterTest} and implements {@link AttributeWriteSelectedValue} for
 * testing without a native toolkit.
 * 
 * @param <VALUE> is the generic type of the #getSelectedValue() selected value}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterTestSelectedValue<VALUE> extends UiWidgetAdapterTest implements
    AttributeWriteSelectedValue<VALUE> {

  /** @see #getSelectedValues() */
  private List<VALUE> selectedValues;

  /**
   * The constructor.
   */
  public UiWidgetAdapterTestSelectedValue() {

    super();
    this.selectedValues = Collections.emptyList();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasSelectedValue() {

    verifyNotDisposed();
    return !this.selectedValues.isEmpty();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public VALUE getSelectedValue() {

    verifyNotDisposed();
    if (!this.selectedValues.isEmpty()) {
      return this.selectedValues.get(0);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<VALUE> getSelectedValues() {

    verifyNotDisposed();
    return this.selectedValues;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSelectedValue(VALUE selectedValue) {

    verifyNotDisposed();
    this.selectedValues = new LinkedList<VALUE>();
    this.selectedValues.add(selectedValue);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSelectedValues(List<VALUE> selection) {

    verifyNotDisposed();
    this.selectedValues = selection;
  }

}
