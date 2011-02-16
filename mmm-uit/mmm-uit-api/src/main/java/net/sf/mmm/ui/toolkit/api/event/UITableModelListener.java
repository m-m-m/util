/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.event;

import java.util.EventListener;

import net.sf.mmm.ui.toolkit.base.event.UITableModelEvent;

/**
 * This is the interface of a table model listener. Such a listener gets
 * notified about any change of table cells from the table model.
 * 
 * @see net.sf.mmm.ui.toolkit.api.model.data.UiTableMvcModel
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UITableModelListener extends EventListener {

  /**
   * This method is called by the
   * {@link net.sf.mmm.ui.toolkit.api.model.data.UiTableMvcModel table-model} if it
   * changed (cells have been updated, inserted or removed).
   * 
   * @param event notifies about the change.
   */
  void tableModelChanged(UITableModelEvent event);

}
