/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.persistence.api;

import java.util.List;

import net.sf.mmm.util.nls.api.ObjectNotFoundException;

/**
 * This is the interface for a {@link PersistenceEntityManager} with the ability
 * of revision-control.
 * 
 * @see RevisionedPersistenceEntity
 * 
 * @param <ENTITY> is the {@link #getEntityClass() type} of the managed entity.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface RevisionedPersistenceEntityManager<ENTITY extends RevisionedPersistenceEntity>
    extends PersistenceEntityManager<ENTITY> {

  /**
   * This method will get the {@link List} of historic
   * {@link RevisionedPersistenceEntity#getRevision() revisions} before the
   * given <code>entity</code>. If the given <code>entity</code> is the
   * {@link RevisionedPersistenceEntity#LATEST_REVISION latest revision} If the
   * <code>entity</code> is NOT revision controlled, an
   * {@link java.util.Collections#emptyList() empty list} is returned.
   * 
   * @param entity is the according {@link PersistenceEntity}.
   * @return the {@link List} of historic
   *         {@link RevisionedPersistenceEntity#getRevision() revisions}.
   */
  List<Integer> getRevisionHistory(ENTITY entity);

  /**
   * This method loads a historic
   * {@link RevisionedPersistenceEntity#getRevision() revision} of the
   * {@link PersistenceEntity} with the given <code>id</code> from the
   * persistent store.<br>
   * However if the given <code>revision</code> is
   * {@link RevisionedPersistenceEntity#LATEST_REVISION} the
   * {@link #load(Object) latest revision will be loaded}.<br>
   * 
   * @param id is the {@link PersistenceEntity#getId() primary key} of the
   *        requested {@link PersistenceEntity entity}.
   * @param revision is the {@link RevisionedPersistenceEntity#getRevision()
   *        revision} of the requested entity or
   *        {@link RevisionedPersistenceEntity#LATEST_REVISION} to get the
   *        {@link #load(Object) latest} revision.
   * @return the requested {@link PersistenceEntity entity} or <code>null</code>
   *         if no {@link PersistenceEntity} of the type
   *         {@link #getEntityClass() ENTITY} exists with the given ID.
   * @throws ObjectNotFoundException if the requested {@link PersistenceEntity
   *         entity} could NOT be found.
   */
  ENTITY load(Object id, int revision) throws ObjectNotFoundException;

}
