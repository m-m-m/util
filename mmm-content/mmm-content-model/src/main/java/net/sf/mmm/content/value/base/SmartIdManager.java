/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.value.base;

import net.sf.mmm.content.value.api.ContentIdManager;

/**
 * This is the interface for the manager of {@link SmartId} instances.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface SmartIdManager extends ContentIdManager {

  /**
   * This method gets the {@link SmartId} of the
   * {@link net.sf.mmm.content.model.api.ContentClass ContentClass} reflecting
   * {@link net.sf.mmm.content.api.ContentObject ContentObject}.
   * 
   * @return the ID of the root class.
   */
  SmartId getRootClassId();

  /**
   * This method gets the {@link SmartId} of the
   * {@link net.sf.mmm.content.model.api.ContentClass ContentClass} reflecting
   * itself.
   * 
   * @return the ID of the classes class.
   */
  SmartId getClassClassId();

  /**
   * This method gets the {@link SmartId} of the
   * {@link net.sf.mmm.content.model.api.ContentClass ContentClass} reflecting
   * {@link net.sf.mmm.content.model.api.ContentField ContentField}.
   * 
   * @return the ID of the field class.
   */
  SmartId getFieldClassId();

  /**
   * This method gets the {@link SmartId} for a
   * {@link net.sf.mmm.content.model.api.ContentClass ContentClass} with the
   * given <code>{@link SmartId#getClassId() classId}</code>.
   * 
   * @param classId is the ID of the requested class-ID.
   * @return the requested ID.
   */
  SmartId getClassId(int classId);

  /**
   * This method gets the {@link SmartId} for a
   * {@link net.sf.mmm.content.model.api.ContentField ContentField} with the
   * given <code>{@link SmartId#getClassId() fieldId}</code>.
   * 
   * @param fieldId is the ID of the requested field-ID.
   * @return the requested ID.
   */
  SmartId getFieldId(int fieldId);

  /**
   * {@inheritDoc}
   */
  SmartId getId(String idAsString);

  /**
   * This method gets the {@link SmartId} with the given parameters.
   * 
   * @param objectId is the {@link SmartId#getObjectId() object-ID}.
   * @param classId is the {@link SmartId#getClassId() class-ID}.
   * @return the requested ID.
   */
  SmartId getId(long objectId, int classId);

  /**
   * This method gets the {@link SmartId} with the given parameters.
   * 
   * @param objectId is the {@link SmartId#getObjectId() object-ID}.
   * @param classId is the {@link SmartId#getClassId() class-ID}.
   * @param revision is the {@link SmartId#getRevision() revision}.
   * @return the requested ID.
   */
  SmartId getId(long objectId, int classId, int revision);

  /**
   * This method gets the {@link SmartId} with the given parameters.
   * 
   * @param objectId is the {@link SmartId#getObjectId() object-ID}.
   * @param classId is the {@link SmartId#getClassId() class-ID}.
   * @param revision is the {@link SmartId#getRevision() revision}.
   * @param storeId is the {@link SmartId#getStoreId() store-ID}.
   * @return the requested ID.
   */
  SmartId getId(long objectId, int classId, int revision, int storeId);

}
