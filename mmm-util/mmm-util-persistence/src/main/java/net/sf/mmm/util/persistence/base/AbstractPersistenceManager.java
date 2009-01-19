/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.persistence.base;

import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.util.nls.api.DuplicateObjectException;
import net.sf.mmm.util.nls.api.NlsIllegalStateException;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;
import net.sf.mmm.util.persistence.api.PersistenceEntity;
import net.sf.mmm.util.persistence.api.PersistenceEntityManager;
import net.sf.mmm.util.persistence.api.PersistenceManager;

/**
 * This is the abstract base-implementation of the {@link PersistenceManager}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractPersistenceManager implements PersistenceManager {

  /** @see #getManager(Class) */
  private final Map<Class<?>, PersistenceEntityManager<?>> class2managerMap;

  /**
   * The constructor.
   */
  public AbstractPersistenceManager() {

    super();
    this.class2managerMap = new HashMap<Class<?>, PersistenceEntityManager<?>>();
  }

  /**
   * This method registers the given <code>entityManager</code>.
   * 
   * @param entityManager is the {@link PersistenceEntityManager} to register.
   */
  protected void addManager(PersistenceEntityManager<?> entityManager) {

    Class<?> entityClass = entityManager.getEntityClass();
    if (this.class2managerMap.containsKey(entityClass)) {
      throw new DuplicateObjectException(entityManager, entityClass);
    }
    this.class2managerMap.put(entityClass, entityManager);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public <ENTITY extends PersistenceEntity> PersistenceEntityManager<ENTITY> getManager(
      Class<ENTITY> entityType) {

    PersistenceEntityManager<ENTITY> manager = (PersistenceEntityManager<ENTITY>) this.class2managerMap
        .get(entityType);
    if (manager == null) {
      throw new ObjectNotFoundException(PersistenceEntityManager.class.getSimpleName(), entityType);
    }
    return manager;
  }

  /**
   * {@inheritDoc}
   */
  public <ENTITY extends PersistenceEntity> ENTITY load(Class<ENTITY> entityClass, Object id) {

    PersistenceEntityManager<ENTITY> manager = getManager(entityClass);
    return manager.load(id);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public void save(PersistenceEntity entity) {

    Class<? extends PersistenceEntity> entityClass = getEntityClass(entity);
    PersistenceEntityManager manager = getManager(entityClass);
    manager.save(entity);
  }

  /**
   * {@inheritDoc}
   */
  public Class<? extends PersistenceEntity> getEntityClass(PersistenceEntity entity) {

    // this is a very simple way - it might be wrong...
    Class<?> entityClass = entity.getClass();
    if (!this.class2managerMap.containsKey(entityClass)) {
      entityClass = entityClass.getSuperclass();
      if (!this.class2managerMap.containsKey(entityClass)) {
        throw new NlsIllegalStateException();
      }
    }
    return entity.getClass();
  }

}
