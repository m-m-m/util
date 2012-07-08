/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This is the interface for an object of the UI framework that has a selection mode. The mode can eighter
 * allow selection of a single item or selection of multiple items.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadMultiSelection {

  /**
   * This methods determines the selection mode.
   * 
   * @return <code>true</code> if the user can select multiple items, <code>false</code> if only a single item
   *         can be selected.
   */
  boolean isMultiSelection();

}
