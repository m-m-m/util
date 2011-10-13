/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api;

import net.sf.mmm.data.api.datatype.DataId;
import net.sf.mmm.util.component.base.ComponentSpecification;

/**
 * This is the interface for the manager of {@link DataId} instances.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@ComponentSpecification
public interface DataIdManager {

  /**
   * This method gets the {@link DataId} of the
   * {@link net.sf.mmm.data.api.reflection.DataClass ContentClass} reflecting
   * {@link net.sf.mmm.data.api.DataObject ContentObject}.
   * 
   * @return the ID of the root class.
   */
  DataId getRootClassId();

  /**
   * This method gets the {@link DataId} of the
   * {@link net.sf.mmm.data.api.reflection.DataClass ContentClass} reflecting
   * itself.
   * 
   * @return the ID of the classes class.
   */
  DataId getClassClassId();

  /**
   * This method gets the {@link DataId} of the
   * {@link net.sf.mmm.data.api.reflection.DataClass ContentClass} reflecting
   * {@link net.sf.mmm.data.api.reflection.DataField ContentField}.
   * 
   * @return the ID of the field class.
   */
  DataId getFieldClassId();

  /**
   * This method gets the {@link DataId} for a
   * {@link net.sf.mmm.data.api.reflection.DataClass ContentClass} with the
   * given <code>{@link DataId#getObjectId() classId}</code>.
   * 
   * @param classId is the ID of the requested class-ID.
   * @return the requested ID.
   */
  DataId getClassId(int classId);

  /**
   * This method gets the {@link DataId} for a
   * {@link net.sf.mmm.data.api.reflection.DataField ContentField} with the
   * given <code>{@link DataId#getObjectId() fieldId}</code>.
   * 
   * @param fieldId is the ID of the requested field-ID.
   * @return the requested ID.
   */
  DataId getFieldId(int fieldId);

  /**
   * This method parses the given String <code>idAsString</code> as a
   * {@link DataId}. It is the inverse operation of
   * {@link DataId#toString()}.
   * 
   * @param idAsString is the {@link #toString() string representation} of a
   *        {@link DataId}.
   * @return the parsed ID.
   */
  DataId getId(String idAsString);

  /**
   * This method gets the {@link DataId} with the given parameters.
   * 
   * @param objectId is the {@link DataId#getObjectId() object-ID}.
   * @param classId is the {@link DataId#getObjectId() class-ID}.
   * @return the requested ID.
   */
  DataId getId(long objectId, int classId);

  /**
   * This method gets the {@link DataId} with the given parameters.
   * 
   * @param objectId is the {@link DataId#getObjectId() object-ID}.
   * @param classId is the {@link DataId#getObjectId() class-ID}.
   * @param revision is the {@link DataId#getRevision() revision}.
   * @return the requested ID.
   */
  DataId getId(long objectId, int classId, int revision);

  /**
   * This method gets the {@link DataId} with the given parameters.
   * 
   * @param objectId is the {@link DataId#getObjectId() object-ID}.
   * @param classId is the {@link DataId#getObjectId() class-ID}.
   * @param revision is the {@link DataId#getRevision() revision}.
   * @param storeId is the {@link DataId#getStoreId() store-ID}.
   * @return the requested ID.
   */
  DataId getId(long objectId, int classId, int revision, int storeId);

  // /**
  // * This method creates a new, unique {@link ContentId ID} for an
  // * {@link net.sf.mmm.content.api.ContentObject object} of the
  // * {@link net.sf.mmm.content.api.ContentObject#getContentClass() type}
  // * identified by the given <code>{@link ContentId#getClassId()
  // classId}</code>
  // * .
  // *
  // * @param classId is the {@link ContentId#getClassId() classId}.
  // * @return the created ID.
  // */
  // ContentId createUniqueId(int classId);
  //
  // /**
  // * This method creates a new, unique {@link ContentId ID} for a
  // * {@link net.sf.mmm.content.model.api.ContentClass class}.
  // *
  // * @return the created ID.
  // */
  // ContentId createUniqueClassId();
  //
  // /**
  // * This method creates a new, unique {@link ContentId ID} for a
  // * {@link net.sf.mmm.content.model.api.ContentField field}.
  // *
  // * @return the created ID.
  // */
  // ContentId createUniqueFieldId();

}
