/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.api;

import net.sf.mmm.util.entity.api.RevisionedEntity;
import net.sf.mmm.util.exception.api.NlsUnsupportedOperationException;
import net.sf.mmm.util.exception.api.ObjectNotFoundException;

/**
 * This is the interface for a {@link PersistenceManager} with the ability of revision-control (auditing),
 * e.g. provided by hibernate-envers.
 * 
 * @see RevisionedEntity
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface RevisionedPersistenceManager extends PersistenceManager {

  /**
   * This method gets the individual {@link RevisionedDao} {@link GenericDao#getEntityClass()
   * responsible} for the given <code>entityClass</code>.
   * 
   * @param <ENTITY> is the generic entity-type.
   * @param <ID> is the type of the {@link RevisionedEntity#getId() primary key}.
   * @param entityClass is the type of the {@link RevisionedEntity} for which the according {@link GenericDao}
   *        is requested.
   * @return the {@link GenericDao} responsible for the given <code>entityClass</code>.
   * @throws ObjectNotFoundException if the requested {@link GenericDao manager} could NOT be found.
   */
  <ID, ENTITY extends RevisionedEntity<ID>> RevisionedDao<ID, ENTITY> getRevisionedManager(Class<ENTITY> entityClass)
      throws ObjectNotFoundException;

  /**
   * This method loads the specified {@link RevisionedEntity#getRevision() revision} of the
   * {@link RevisionedEntity} with the given <code>entityClass</code> and <code>id</code> from the persistent
   * store.
   * 
   * @see RevisionedDao#load(Object, Number)
   * 
   * @param <ENTITY> is the generic type of the <code>entityClass</code>.
   * @param <ID> is the type of the {@link RevisionedEntity#getId() primary key}.
   * @param entityClass is the class reflecting the type of the requested entity.
   * @param id is the {@link RevisionedEntity#getId() primary key} of the requested entity.
   * @param revision is the {@link RevisionedEntity#getRevision() revision} of the requested entity or
   *        {@link RevisionedEntity#LATEST_REVISION} to get the {@link #find(Class, Object) latest} revision.
   * @return the requested entity. It is immutable if NOT the {@link #find(Class, Object) latest} revision.
   * @throws ObjectNotFoundException if the requested {@link RevisionedEntity entity} could NOT be found.
   * @throws NlsUnsupportedOperationException if the given <code>entityClass</code> reflects a
   *         {@link RevisionedEntity} that is NOT revision-controlled.
   */
  <ID, ENTITY extends RevisionedEntity<ID>> ENTITY load(Class<ENTITY> entityClass, ID id, Number revision)
      throws ObjectNotFoundException, NlsUnsupportedOperationException;

}
