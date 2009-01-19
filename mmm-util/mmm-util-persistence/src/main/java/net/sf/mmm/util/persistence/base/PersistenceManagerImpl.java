/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.persistence.base;

import java.util.List;

import javax.annotation.Resource;

import net.sf.mmm.util.persistence.api.PersistenceEntityManager;

/**
 * This is the default implementation of the
 * {@link net.sf.mmm.util.persistence.api.PersistenceManager} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PersistenceManagerImpl extends AbstractPersistenceManager {

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
   */
  @Resource
  public void setManagers(List<PersistenceEntityManager<?>> managerList) {

    for (PersistenceEntityManager<?> entityManager : managerList) {
      addManager(entityManager);
    }
  }

}
