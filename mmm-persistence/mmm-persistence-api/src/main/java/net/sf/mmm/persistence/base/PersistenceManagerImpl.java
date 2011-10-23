/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.base;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import net.sf.mmm.persistence.api.PersistenceEntityManager;
import net.sf.mmm.util.nls.api.DuplicateObjectException;

/**
 * This is the default implementation of the
 * {@link net.sf.mmm.persistence.api.PersistenceManager} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Named
public class PersistenceManagerImpl extends AbstractRevisionedPersistenceManager {

  /**
   * The constructor.
   */
  public PersistenceManagerImpl() {

    super();
  }

  /**
   * This method injects the {@link #getManager(Class) entity-managers}.
   * 
   * @param managerList is the {@link List} of all
   *        {@link PersistenceEntityManager} to register.
   * @throws DuplicateObjectException if two managers use the same entity-class
   *         ( {@link PersistenceEntityManager#getEntityClassImplementation()},
   *         {@link PersistenceEntityManager#getEntityClassReadWrite()}, or
   *         {@link PersistenceEntityManager#getEntityClassReadOnly()}).
   */
  @Inject
  public void setManagers(List<PersistenceEntityManager<?, ?>> managerList)
      throws DuplicateObjectException {

    for (PersistenceEntityManager<?, ?> entityManager : managerList) {
      addManager(entityManager);
    }
  }

}
