/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.gui.model.content.api;

import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.ui.toolkit.api.model.UIListModel;
import net.sf.mmm.ui.toolkit.api.model.UITreeModel;

/**
 * This is the interface for the manager that
 * {@link #getFieldTableModel(ContentClass) provides} a
 * {@link FieldTableModel table-model} used to view the
 * {@link net.sf.mmm.content.model.api.ContentField fields}
 * {@link ContentClass#getDeclaredFields() declared} by a given
 * {@link ContentClass content-class}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ContentReflectionModelManager {

  /**
   * This method gets the {@link UITreeModel tree-model} containing the 
   * {@link ContentClass}es of the content-model.
   * 
   * @return the class tree-model.
   */
  UITreeModel<ContentClass> getContentClassTreeModel();
  
  /**
   * This method gets the {@link UIListModel list-model} containing the
   * {@link ContentClass}es of the content-model.
   * 
   * @return the class list-model.
   */
  UIListModel<ContentClass> getContentClassListModel();

  /**
   * This method gets the {@link FieldTableModel table-model} used to view the
   * {@link net.sf.mmm.content.model.api.ContentField fields}
   * {@link ContentClass#getDeclaredFields() declared} by the given
   * {@link ContentClass content-class}.
   * 
   * @param contentClass is the class for which the table-model is requested.
   * @return the requested table-model.
   */
  FieldTableModel getFieldTableModel(ContentClass contentClass);

}
