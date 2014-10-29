/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

import java.util.Collection;

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
   * @return <code>true</code> if the operation was successful, <code>false</code> otherwise (the given
   *         <code>selectedValue</code> is not contained in this object and cannot be selected).
   */
  boolean setSelectedValue(VALUE selectedValue);

  /**
   * This method sets the {@link #getSelectedValues() values currently selected}. All values that are NOT
   * available, will be ignored. <br>
   * <b>ATTENTION:</b><br>
   * In case of {@link net.sf.mmm.client.ui.api.common.SelectionMode#SINGLE_SELECTION} you shall not provide a
   * {@link Collection} with more than one element. So you should then use {@link #setSelectedValue(Object)}
   * instead. <br>
   * Further, if you call {@link #getSelectedValues()} after this method the items may be in a different
   * order. Additionally the result of {@link #getSelectedValue()} will contain elements that are actually
   * items available in this object. So e.g. if you have a list of items and call this method to select two
   * items but only one of them is actually available in the list, then only that one is selected and
   * therefore returned by {@link #getSelectedValues()}.
   * 
   * @param selectedValues is the {@link Collection} with the {@link #getSelectedValues() values to select}.
   * @return <code>true</code> if the operation was successful and all <code>selectedValues</code> have been
   *         selected, <code>false</code> otherwise (at least one item out of <code>selectedValues</code> is
   *         not contained in this object and cannot be selected).
   */
  boolean setSelectedValues(Collection<VALUE> selectedValues);

}
