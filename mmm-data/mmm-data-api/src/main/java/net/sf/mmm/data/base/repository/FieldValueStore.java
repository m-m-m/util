/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.base.repository;

import net.sf.mmm.data.api.DataException;
import net.sf.mmm.data.api.reflection.DataField;
import net.sf.mmm.data.base.AbstractContentObject;

/**
 * This is the interface for a store that persists (parts of) a
 * {@link net.sf.mmm.data.api.DataObject content-object}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface FieldValueStore {

  /**
   * This method determines if the given <code>field</code> is <em>handled</em>
   * by this store. Here handled means that this store will
   * {@link #loadFields(AbstractContentObject) load} and
   * {@link #saveFields(AbstractContentObject) save} it.
   * 
   * @param field is the field to check.
   * @return true if the given <code>field</code> is handled by this store.
   */
  boolean isFieldHandled(DataField field);

  /**
   * This method will load all {@link DataField field} that are
   * {@link #isFieldHandled(DataField) handled} by this store.
   * 
   * @param contentObject is the object whose fields should be loaded.
   * @throws DataException if the operation fails.
   */
  void loadFields(AbstractContentObject contentObject) throws DataException;

  /**
   * This method will save all {@link DataField field} that are
   * {@link #isFieldHandled(DataField) handled} by this store.
   * 
   * @param contentObject is the object whose fields should be saved.
   * @throws DataException if the operation fails.
   */
  void saveFields(AbstractContentObject contentObject) throws DataException;

}
