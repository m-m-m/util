/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.base;

import net.sf.mmm.persistence.api.GenericDao;
import net.sf.mmm.persistence.api.RevisionedDao;
import net.sf.mmm.persistence.api.RevisionedPersistenceManager;
import net.sf.mmm.util.entity.api.RevisionedEntity;
import net.sf.mmm.util.nls.api.NlsUnsupportedOperationException;
import net.sf.mmm.util.nls.api.ObjectMismatchException;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;

/**
 * This is the abstract base-implementation of the {@link RevisionedPersistenceManager} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractRevisionedPersistenceManager extends AbstractPersistenceManager implements
    RevisionedPersistenceManager {

  /**
   * The constructor.
   */
  public AbstractRevisionedPersistenceManager() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <ID, ENTITY extends RevisionedEntity<ID>> RevisionedDao<ID, ENTITY> getRevisionedManager(
      Class<ENTITY> entityClass) throws ObjectNotFoundException {

    GenericDao<ID, ENTITY> manager = getDao(entityClass);
    try {
      return (RevisionedDao<ID, ENTITY>) manager;
    } catch (ClassCastException e) {
      throw new ObjectMismatchException(manager, RevisionedDao.class, entityClass);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <ID, ENTITY extends RevisionedEntity<ID>> ENTITY load(Class<ENTITY> entityClass, ID id, Number revision)
      throws ObjectNotFoundException, NlsUnsupportedOperationException {

    RevisionedDao<ID, ENTITY> manager = getRevisionedManager(entityClass);
    return manager.load(id, revision);
  }

}
