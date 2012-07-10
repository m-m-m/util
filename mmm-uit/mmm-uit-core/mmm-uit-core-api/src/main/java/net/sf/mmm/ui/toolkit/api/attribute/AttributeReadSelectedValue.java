/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

import java.util.List;

/**
 * This interface gives read access to the {@link #getSelectedValues() selected value(s)} (items) of an
 * object.
 * 
 * @see net.sf.mmm.ui.toolkit.api.common.SelectionMode
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the selectable {@link #getSelectedValues() values}.
 */
public abstract interface AttributeReadSelectedValue<VALUE> {

  /**
   * @return <code>true</code> if at least one value is {@link #getSelectedValue() selected},
   *         <code>false</code> otherwise.
   */
  boolean hasSelectedValue();

  /**
   * This method gets the currently selected value (item).
   * 
   * @return the selected value or <code>null</code> if no value is selected.
   */
  VALUE getSelectedValue();

  /**
   * This method gets the currently selected values.
   * 
   * @see #getSelectedValue()
   * 
   * @return the {@link List} with all selected values. Will be empty if nothing is selected.
   */
  List<VALUE> getSelectedValues();

}
