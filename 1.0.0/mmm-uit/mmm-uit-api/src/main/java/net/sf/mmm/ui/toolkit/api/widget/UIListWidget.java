/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.widget;

import net.sf.mmm.ui.toolkit.api.model.UIListModel;
import net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionIndex;
import net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionValue;

/**
 * This is the abstract interface for a UI list widget. Such a widget presents a
 * list. The way the list is presented is not further specified here.
 * 
 * @see net.sf.mmm.ui.toolkit.api.widget.UIList
 * @see net.sf.mmm.ui.toolkit.api.widget.UIComboBox
 * @see net.sf.mmm.ui.toolkit.api.widget.UISlideBar
 * 
 * @param <E> is the templated type of the elements that can be selected with
 *        this widget.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIListWidget<E> extends UIWidget, UIWriteSelectionIndex, UIWriteSelectionValue<E> {

  /**
   * This method gets the model of this list.
   * 
   * @return the list model or <code>null</code> if the model is not set.
   */
  UIListModel<E> getModel();

  /**
   * This method sets the model of this list.
   * 
   * @param newModel is the new list model to set.
   */
  void setModel(UIListModel<E> newModel);

}
