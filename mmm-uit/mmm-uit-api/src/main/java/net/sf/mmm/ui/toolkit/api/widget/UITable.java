/* $Id$ */
package net.sf.mmm.ui.toolkit.api.widget;

import net.sf.mmm.ui.toolkit.api.model.UITableModel;
import net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionIndex;

/**
 * This is the interface for a table.
 * 
 * @param <C>
 *        is the templated type of the objects in the table cells.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UITable<C> extends UIWidget, UIWriteSelectionIndex {

  /** the type of this object */
  String TYPE = "Table";

  /**
   * This method gets the assigned model.
   * 
   * @return the table model.
   */
  UITableModel<C> getModel();

  /**
   * This method sets the model of this table. If the table already has a model
   * assigned, it will be replaced.
   * 
   * @param newModel
   *        is the new model to set.
   */
  void setModel(UITableModel<C> newModel);
  
}
