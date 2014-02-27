/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.widget.adapter;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

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
  private Collection<VALUE> selectedValues;

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
  public int getSelectionCount() {

    return this.selectedValues.size();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public VALUE getSelectedValue() {

    verifyNotDisposed();
    if (!this.selectedValues.isEmpty()) {
      return this.selectedValues.iterator().next();
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<VALUE> getSelectedValues() {

    verifyNotDisposed();
    return this.selectedValues;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean setSelectedValue(VALUE selectedValue) {

    verifyNotDisposed();
    this.selectedValues = new LinkedList<VALUE>();
    this.selectedValues.add(selectedValue);
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean setSelectedValues(Collection<VALUE> selection) {

    verifyNotDisposed();
    this.selectedValues = selection;
    return true;
  }

}
