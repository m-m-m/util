/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.persistence.base;

import net.sf.mmm.util.nls.api.NlsUnsupportedOperationException;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;
import net.sf.mmm.util.persistence.api.RevisionedPersistenceEntity;
import net.sf.mmm.util.persistence.api.RevisionedPersistenceEntityManager;
import net.sf.mmm.util.persistence.api.RevisionedPersistenceManager;

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
  public <ENTITY extends RevisionedPersistenceEntity> RevisionedPersistenceEntityManager<ENTITY> getRevisionedManager(
      Class<ENTITY> entityClass) throws ObjectNotFoundException {

    return (RevisionedPersistenceEntityManager<ENTITY>) getManager(entityClass);
  }

  /**
   * {@inheritDoc}
   */
  public <ENTITY extends RevisionedPersistenceEntity> ENTITY load(Class<ENTITY> entityClass,
      Object id, int revision) throws ObjectNotFoundException, NlsUnsupportedOperationException {

    RevisionedPersistenceEntityManager<ENTITY> manager = getRevisionedManager(entityClass);
    return manager.load(id, revision);
  }

}
