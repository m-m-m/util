/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.base;

import net.sf.mmm.persistence.api.RevisionedPersistenceEntity;
import net.sf.mmm.persistence.api.RevisionedPersistenceEntityManager;
import net.sf.mmm.persistence.api.RevisionedPersistenceManager;
import net.sf.mmm.util.nls.api.NlsUnsupportedOperationException;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;

/**
 * This is the abstract base-implementation of the
 * {@link RevisionedPersistenceManager} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractRevisionedPersistenceManager extends AbstractPersistenceManager
    implements RevisionedPersistenceManager {

  /**
   * The constructor.
   */
  public AbstractRevisionedPersistenceManager() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public <ID, ENTITY extends RevisionedPersistenceEntity<ID>> RevisionedPersistenceEntityManager<ID, ENTITY> getRevisionedManager(
      Class<ENTITY> entityClass) throws ObjectNotFoundException {

    // TODO: handle class cast exception and produce more appropriate error.
    return (RevisionedPersistenceEntityManager<ID, ENTITY>) getManager(entityClass);
  }

  /**
   * {@inheritDoc}
   */
  public <ID, ENTITY extends RevisionedPersistenceEntity<ID>> ENTITY load(
      Class<ENTITY> entityClass, ID id, Number revision) throws ObjectNotFoundException,
      NlsUnsupportedOperationException {

    RevisionedPersistenceEntityManager<ID, ENTITY> manager = getRevisionedManager(entityClass);
    return manager.load(id, revision);
  }

}
