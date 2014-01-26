/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

import java.util.List;

/**
 * This interface gives read and write access to the selected {@link #getSelectedValues() value(s)} (items) of
 * an object.
 * 
 * @param <VALUE> is the generic type of the selectable {@link #getSelectedValues() values}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteSelectedValue<VALUE> extends AttributeReadSelectedValue<VALUE> {

  /**
   * This method sets the value currently selected. If the given value is NOT available, the method will have
   * NO effect. Otherwise this method will deselect all {@link #getSelectedValues() currently selected values}
   * and select the given value.
   * 
   * @param selectedValue is the {@link #getSelectedValue() value to select}.
   */
  void setSelectedValue(VALUE selectedValue);

  /**
   * This method sets the {@link #getSelectedValues() values currently selected}. All values that are NOT
   * available, will be ignored. This method does NOT make sense for
   * {@link net.sf.mmm.client.ui.api.common.SelectionMode#SINGLE_SELECTION}.
   * 
   * @param selectedValues is the {@link List} with the {@link #getSelectedValues() values to select}.
   */
  void setSelectedValues(List<VALUE> selectedValues);

}
