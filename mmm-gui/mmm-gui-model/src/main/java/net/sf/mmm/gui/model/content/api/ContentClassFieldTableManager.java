/* $Id$ */
package net.sf.mmm.gui.model.content.api;

import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.ui.toolkit.api.model.UITableModel;

/**
 * This is the interface for the manager that
 * {@link #getFieldTableModel(ContentClass) provides} a
 * {@link UITableModel table-model} used to view the
 * {@link net.sf.mmm.content.model.api.ContentField fields}
 * {@link ContentClass#getDeclatedFields() declared} by a given
 * {@link ContentClass content-class}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ContentClassFieldTableManager {

  /**
   * This method gets the {@link UITableModel table-model} used to
   * view the {@link net.sf.mmm.content.model.api.ContentField fields}
   * {@link ContentClass#getDeclatedFields() declared} by the given
   * {@link ContentClass content-class}.
   * 
   * @param contentClass
   *        is the class for which the table-model is requested.
   * @return the requested table-model.
   */
  UITableModel<Object> getFieldTableModel(ContentClass contentClass);

}
