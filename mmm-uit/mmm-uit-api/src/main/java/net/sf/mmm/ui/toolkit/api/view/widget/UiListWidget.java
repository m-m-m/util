/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.view.widget;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteSelectionIndex;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteSelectionValue;
import net.sf.mmm.ui.toolkit.api.model.data.UiListMvcModel;

/**
 * This is the abstract interface for a UI list widget. Such a widget presents a
 * list. The way the list is presented is not further specified here.
 * 
 * @see net.sf.mmm.ui.toolkit.api.view.widget.UiList
 * @see net.sf.mmm.ui.toolkit.api.view.widget.UiComboBox
 * @see net.sf.mmm.ui.toolkit.api.view.widget.UiSlideBar
 * 
 * @param <E> is the templated type of the elements that can be selected with
 *        this widget.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface UiListWidget<E> extends UiWidget, AttributeWriteSelectionIndex, AttributeWriteSelectionValue<E> {

  /**
   * This method gets the model of this list.
   * 
   * @return the list model or <code>null</code> if the model is not set.
   */
  UiListMvcModel<E> getModel();

  /**
   * This method sets the model of this list.
   * 
   * @param newModel is the new list model to set.
   */
  void setModel(UiListMvcModel<E> newModel);

}
