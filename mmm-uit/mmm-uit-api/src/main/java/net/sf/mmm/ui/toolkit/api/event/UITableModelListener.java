/* $Id$ */
package net.sf.mmm.ui.toolkit.api.event;

import java.util.EventListener;

/**
 * This is the interface of a table model listener. Such a listener gets
 * notified about any change of table cells from the table model.
 * 
 * @see net.sf.mmm.ui.toolkit.api.model.UITableModel
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UITableModelListener extends EventListener {

  /**
   * This method is called by the
   * {@link net.sf.mmm.ui.toolkit.api.model.UITableModel table-model} if it
   * changed (cells have been updated, inserted or removed).
   * 
   * @param event
   *        notifies about the change.
   */
  void tableModelChanged(UITableModelEvent event);

}
