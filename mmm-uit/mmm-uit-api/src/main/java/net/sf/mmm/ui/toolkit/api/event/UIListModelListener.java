/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.event;

import java.util.EventListener;

/**
 * This is the interface of a list model listener. Such a listener gets notified
 * about any change of list items from the list model.
 * 
 * @see net.sf.mmm.ui.toolkit.api.model.data.UiListMvcModel
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIListModelListener extends EventListener {

  /**
   * This method is called by the list model if the list has changed (items have
   * been updated, inserted or removed).
   * 
   * @param event notifies about the change of the list.
   */
  void listModelChanged(UIListModelEvent event);

}
