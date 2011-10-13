/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api;

import net.sf.mmm.data.datatype.api.ContentId;
import net.sf.mmm.util.component.base.ComponentSpecification;

/**
 * This is the interface for the manager of {@link ContentId} instances.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@ComponentSpecification
public interface ContentIdManager {

  /**
   * This method gets the {@link ContentId} of the
   * {@link net.sf.mmm.data.reflection.api.ContentClass ContentClass} reflecting
   * {@link net.sf.mmm.data.api.ContentObject ContentObject}.
   * 
   * @return the ID of the root class.
   */
  ContentId getRootClassId();

  /**
   * This method gets the {@link ContentId} of the
   * {@link net.sf.mmm.data.reflection.api.ContentClass ContentClass} reflecting
   * itself.
   * 
   * @return the ID of the classes class.
   */
  ContentId getClassClassId();

  /**
   * This method gets the {@link ContentId} of the
   * {@link net.sf.mmm.data.reflection.api.ContentClass ContentClass} reflecting
   * {@link net.sf.mmm.data.reflection.api.ContentField ContentField}.
   * 
   * @return the ID of the field class.
   */
  ContentId getFieldClassId();

  /**
   * This method gets the {@link ContentId} for a
   * {@link net.sf.mmm.data.reflection.api.ContentClass ContentClass} with the
   * given <code>{@link ContentId#getObjectId() classId}</code>.
   * 
   * @param classId is the ID of the requested class-ID.
   * @return the requested ID.
   */
  ContentId getClassId(int classId);

  /**
   * This method gets the {@link ContentId} for a
   * {@link net.sf.mmm.data.reflection.api.ContentField ContentField} with the
   * given <code>{@link ContentId#getObjectId() fieldId}</code>.
   * 
   * @param fieldId is the ID of the requested field-ID.
   * @return the requested ID.
   */
  ContentId getFieldId(int fieldId);

  /**
   * This method parses the given String <code>idAsString</code> as a
   * {@link ContentId}. It is the inverse operation of
   * {@link ContentId#toString()}.
   * 
   * @param idAsString is the {@link #toString() string representation} of a
   *        {@link ContentId}.
   * @return the parsed ID.
   */
  ContentId getId(String idAsString);

  /**
   * This method gets the {@link ContentId} with the given parameters.
   * 
   * @param objectId is the {@link ContentId#getObjectId() object-ID}.
   * @param classId is the {@link ContentId#getObjectId() class-ID}.
   * @return the requested ID.
   */
  ContentId getId(long objectId, int classId);

  /**
   * This method gets the {@link ContentId} with the given parameters.
   * 
   * @param objectId is the {@link ContentId#getObjectId() object-ID}.
   * @param classId is the {@link ContentId#getObjectId() class-ID}.
   * @param revision is the {@link ContentId#getRevision() revision}.
   * @return the requested ID.
   */
  ContentId getId(long objectId, int classId, int revision);

  /**
   * This method gets the {@link ContentId} with the given parameters.
   * 
   * @param objectId is the {@link ContentId#getObjectId() object-ID}.
   * @param classId is the {@link ContentId#getObjectId() class-ID}.
   * @param revision is the {@link ContentId#getRevision() revision}.
   * @param storeId is the {@link ContentId#getStoreId() store-ID}.
   * @return the requested ID.
   */
  ContentId getId(long objectId, int classId, int revision, int storeId);

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
