/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

/**
 * This interface gives advanced read and write access to the selected {@link #getSelectedValues() value(s)}
 * of an object.
 * 
 * @param <VALUE> is the generic type of the selectable {@link #getSelectedValues() values}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteSelectedValueAdvanced<VALUE> extends AttributeWriteSelectedValue<VALUE> {

  /**
   * @return <code>true</code> if the first value is selected, <code>false</code> otherwise (if the first
   *         value is not selected or does not exists).
   */
  boolean isFirstValueSelected();

  /**
   * @return <code>true</code> if the last value is selected, <code>false</code> otherwise (if the first value
   *         is not selected or does not exists).
   */
  boolean isLastValueSelected();

  /**
   * @param value is the value to check the selection state of.
   * @return <code>true</code> if the given <code>value</code> is selected, <code>false</code> otherwise (if
   *         <code>value</code> is not selected or is not a value contained in this object).
   */
  boolean isValueSelected(VALUE value);

  /**
   * This method updates the selection state of the first value of this object (e.g. the first entry in a
   * list). Please note that the order of the values contained in this object might changeable so the semantic
   * of the first value may change. If this object is currently empty (contains no values), this method has no
   * effect.
   * 
   * @param selected - <code>true</code> to select the value, <code>false</code> to deselect it.
   * @return <code>true</code> if the selection of the specified value has been changed, <code>false</code>
   *         otherwise (if no such value exists).
   */
  boolean setFirstValueSelected(boolean selected);

  /**
   * This method updates the selection state of the last value of this object (e.g. the last entry in a list).
   * Please note that the order of the values contained in this object might changeable so the semantic of the
   * last value may change. If this object is currently empty (contains no values), this method has no effect.
   * 
   * @param selected - <code>true</code> to select the value, <code>false</code> to deselect it.
   * @return <code>true</code> if the selection of the specified value has been changed, <code>false</code>
   *         otherwise (if no such value exists).
   */
  boolean setLastValueSelected(boolean selected);

  /**
   * This method updates the selection state of the specified <code>value</code>. If the value
   * 
   * @param value is the value to change the selection state of.
   * @param selected - <code>true</code> to select the value, <code>false</code> to deselect it.
   * @return <code>true</code> if the selection of the specified value has been changed, <code>false</code>
   *         otherwise (if no such value exists).
   */
  boolean setValueSelected(VALUE value, boolean selected);

}
