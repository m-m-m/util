/* $Id: $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.base;

import net.sf.mmm.persistence.api.RevisionedPersistenceEntity;
import net.sf.mmm.persistence.api.RevisionedPersistenceEntityManager;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;

/**
 * This is the abstract base implementation of the
 * {@link RevisionedPersistenceEntityManager} interface.
 * 
 * @param <ID> is the type of the
 *        {@link net.sf.mmm.persistence.api.PersistenceEntity#getId() primary
 *        key}.
 * @param <ENTITY> is the {@link #getEntityClassImplementation() type} of the
 *        managed entity.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractRevisionedPersistenceEntityManager<ID, ENTITY extends RevisionedPersistenceEntity<ID>>
    extends AbstractPersistenceEntityManager<ID, ENTITY> implements
    RevisionedPersistenceEntityManager<ID, ENTITY> {

  /**
   * {@inheritDoc}
   */
  public ENTITY load(ID id, Number revision) throws ObjectNotFoundException {

    if (revision == RevisionedPersistenceEntity.LATEST_REVISION) {
      return load(id);
    } else {
      return loadRevision(id, revision);
    }
  }

  /**
   * This method gets a historic revision of the
   * {@link net.sf.mmm.persistence.api.PersistenceEntity} with the given
   * <code>id</code>.
   * 
   * @param id is the
   *        {@link net.sf.mmm.persistence.api.PersistenceEntity#getId() ID} of
   *        the requested {@link net.sf.mmm.persistence.api.PersistenceEntity
   *        entity}.
   * @param revision is the {@link RevisionedPersistenceEntity#getRevision()
   *        revision}
   * @return the requested {@link net.sf.mmm.persistence.api.PersistenceEntity
   *         entity}.
   * @throws ObjectNotFoundException if the requested
   *         {@link net.sf.mmm.persistence.api.PersistenceEntity entity} could
   *         NOT be found.
   */
  protected abstract ENTITY loadRevision(Object id, Number revision) throws ObjectNotFoundException;

}
