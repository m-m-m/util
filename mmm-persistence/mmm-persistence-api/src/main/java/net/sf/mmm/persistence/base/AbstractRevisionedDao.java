/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.base;

import net.sf.mmm.persistence.api.RevisionedDao;
import net.sf.mmm.util.entity.api.RevisionedEntity;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;

/**
 * This is the abstract base implementation of the {@link RevisionedDao} interface.
 * 
 * @param <ID> is the type of the {@link net.sf.mmm.util.entity.api.GenericEntity#getId() primary key}.
 * @param <ENTITY> is the {@link #getEntityClassImplementation() type} of the managed entity.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractRevisionedDao<ID, ENTITY extends RevisionedEntity<ID>> extends
    AbstractGenericDao<ID, ENTITY> implements RevisionedDao<ID, ENTITY> {

  /**
   * {@inheritDoc}
   */
  @Override
  public ENTITY load(ID id, Number revision) throws ObjectNotFoundException {

    if (revision == RevisionedEntity.LATEST_REVISION) {
      return find(id);
    } else {
      return loadRevision(id, revision);
    }
  }

  /**
   * This method gets a historic revision of the {@link net.sf.mmm.util.entity.api.GenericEntity} with the
   * given <code>id</code>.
   * 
   * @param id is the {@link net.sf.mmm.util.entity.api.GenericEntity#getId() ID} of the requested
   *        {@link net.sf.mmm.util.entity.api.GenericEntity entity}.
   * @param revision is the {@link RevisionedEntity#getRevision() revision}
   * @return the requested {@link net.sf.mmm.util.entity.api.GenericEntity entity}.
   * @throws ObjectNotFoundException if the requested {@link net.sf.mmm.util.entity.api.GenericEntity entity}
   *         could NOT be found.
   */
  protected abstract ENTITY loadRevision(Object id, Number revision) throws ObjectNotFoundException;

}
