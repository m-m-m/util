/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.persistence.base;

import net.sf.mmm.util.component.base.AbstractComponent;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;
import net.sf.mmm.util.persistence.api.PersistenceEntity;
import net.sf.mmm.util.persistence.api.PersistenceEntityManager;
import net.sf.mmm.util.persistence.api.RevisionedPersistenceEntity;

/**
 * This is the abstract base-implementation of the
 * {@link PersistenceEntityManager} interface.
 * 
 * @param <ENTITY> is the {@link #getEntityClass() type} of the managed entity.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractPersistenceEntityManager<ENTITY extends PersistenceEntity> extends
    AbstractComponent implements PersistenceEntityManager<ENTITY> {

  /**
   * The constructor.
   */
  public AbstractPersistenceEntityManager() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public ENTITY load(Object id, int revision) throws ObjectNotFoundException {

    if (revision == RevisionedPersistenceEntity.LATEST_REVISION) {
      return load(id);
    } else {
      return loadRevision(id, revision);
    }
  }

  /**
   * This method gets a historic revision of the {@link PersistenceEntity} with
   * the given <code>id</code>.
   * 
   * @param id is the {@link PersistenceEntity#getId() ID} of the requested
   *        {@link PersistenceEntity entity}.
   * @param revision is the {@link PersistenceEntity#getRevision()
   *        revision}
   * @return
   * @throws ObjectNotFoundException
   */
  protected abstract ENTITY loadRevision(Object id, int revision) throws ObjectNotFoundException;

}
