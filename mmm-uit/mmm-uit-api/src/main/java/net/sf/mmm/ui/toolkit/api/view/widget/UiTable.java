/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.view.widget;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteSelectionIndex;
import net.sf.mmm.ui.toolkit.api.model.data.UiTableMvcModel;

/**
 * This is the interface for a table.
 * 
 * @param <C> is the templated type of the objects in the table cells.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiTable<C> extends UiWidget, AttributeWriteSelectionIndex {

  /** the type of this object */
  String TYPE = "Table";

  /**
   * This method gets the assigned model.
   * 
   * @return the table model.
   */
  UiTableMvcModel<C> getModel();

  /**
   * This method sets the model of this table. If the table already has a model assigned, it will be replaced.
   * 
   * @param newModel is the new model to set.
   */
  void setModel(UiTableMvcModel<C> newModel);

}
