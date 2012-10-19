/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

import java.util.List;

/**
 * This interface gives read and write access to the selected {@link #getSelectedValues() value(s)} (items) of
 * an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the selectable {@link #getSelectedValues() values}.
 */
public abstract interface AttributeWriteSelectedValue<VALUE> extends AttributeReadSelectedValue<VALUE> {

  /**
   * This method sets the value currently selected. If the given value is NOT available, the method will have
   * NO effect. Otherwise this method will deselect all {@link #getSelectedValues() currently selected values}
   * and select the given value.
   * 
   * @param selectedValue is the {@link #getSelectedValue() value to select}.
   * @return <code>true</code> if the <code>selectedValue</code> was found and selected, <code>false</code>
   *         otherwise (if it does NOT exist in this object and could therefore NOT be selected).
   */
  boolean setSelectedValue(VALUE selectedValue);

  /**
   * This method sets the {@link #getSelectedValues() values currently selected}. All values that are NOT
   * available, will be ignored. This method does NOT make sense for
   * {@link net.sf.mmm.ui.toolkit.api.common.SelectionMode#SINGLE_SELECTION}.
   * 
   * @param selectedValues is the {@link List} with the {@link #getSelectedValues() values to select}.
   * @return <code>true</code> if all <code>selectedValues</code> have been found and selected,
   *         <code>false</code> otherwise (at least one value does NOT exist in this object).
   */
  boolean setSelectedValues(List<VALUE> selectedValues);

}
