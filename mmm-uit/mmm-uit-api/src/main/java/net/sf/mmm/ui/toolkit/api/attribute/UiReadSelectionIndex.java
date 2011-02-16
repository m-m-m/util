/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives read access to the
 * {@link #getSelectedIndex() selection-index} of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UiReadSelectionIndex {

  /**
   * This method gets the index of the selected item.
   * 
   * @return the index of the selected item or <code>-1</code> if no item is
   *         selected.
   */
  int getSelectedIndex();

}
