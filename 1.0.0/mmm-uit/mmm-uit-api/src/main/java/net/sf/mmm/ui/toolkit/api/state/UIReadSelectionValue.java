/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.state;

/**
 * This interface gives read access to the selected
 * {@link #getSelectedValue() value} of an object.
 * 
 * @param <V> is the templated type of the selectable value.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIReadSelectionValue<V> {

  /**
   * This method gets the currently selected value (item).
   * 
   * @return the selected value or <code>null</code> if no value is selected.
   */
  V getSelectedValue();

}
