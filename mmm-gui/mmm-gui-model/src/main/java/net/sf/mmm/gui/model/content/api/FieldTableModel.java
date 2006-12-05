/* $Id$ */
package net.sf.mmm.gui.model.content.api;

import net.sf.mmm.content.model.api.ContentField;
import net.sf.mmm.ui.toolkit.api.model.UITableModel;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface FieldTableModel extends UITableModel<Object> {

  /**
   * This method gets the {@link ContentField field} located at the given
   * <code>rowIndex</code>.
   * 
   * @param rowIndex
   *        is the index of the table row for which the field is requested.
   * @return the field located at the given <code>rowIndex</code>.
   */
  ContentField getField(int rowIndex);

}
