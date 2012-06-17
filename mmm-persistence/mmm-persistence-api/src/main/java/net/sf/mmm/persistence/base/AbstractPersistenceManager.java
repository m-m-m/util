/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.base;

import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.persistence.api.PersistenceEntity;
import net.sf.mmm.persistence.api.PersistenceEntityManager;
import net.sf.mmm.persistence.api.PersistenceManager;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.nls.api.DuplicateObjectException;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;

/**
 * This is the abstract base-implementation of the {@link PersistenceManager}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractPersistenceManager extends AbstractLoggableComponent implements
    PersistenceManager {

  /** @see #getManager(Class) */
  private final Map<Class<?>, PersistenceEntityManager<?, ?>> class2managerMap;

  /**
   * The constructor.
   */
  public AbstractPersistenceManager() {

    super();
    this.class2managerMap = new HashMap<Class<?>, PersistenceEntityManager<?, ?>>();
  }

  /**
   * This method registers the given <code>entityManager</code>.
   * 
   * @param entityManager is the {@link PersistenceEntityManager} to register.
   * @throws DuplicateObjectException if a manager is already registered for the
   *         same entity-class (
   *         {@link PersistenceEntityManager#getEntityClassImplementation()},
   *         {@link PersistenceEntityManager#getEntityClassReadWrite()}, or
   *         {@link PersistenceEntityManager#getEntityClassReadOnly()}).
   */
  protected void addManager(PersistenceEntityManager<?, ?> entityManager)
      throws DuplicateObjectException {

    getInitializationState().requireNotInitilized();
    Class<?> entityClass = entityManager.getEntityClassImplementation();
    registerManager(entityClass, entityManager);
    Class<?> entityClassReadOnly = entityManager.getEntityClassReadOnly();
    if (entityClass != entityClassReadOnly) {
      registerManager(entityClassReadOnly, entityManager);
    }
    Class<?> entityClassReadWrite = entityManager.getEntityClassReadWrite();
    if ((entityClass != entityClassReadWrite) && (entityClassReadOnly != entityClassReadWrite)) {
      registerManager(entityClassReadWrite, entityManager);
    }
  }

  /**
   * This method registers the given <code>entityManager</code> for the given
   * <code>entityClass</code>.
   * 
   * @param entityClass is the {@link Class} used as key to associate the
   *        <code>entityManager</code> with.
   * @param entityManager is the {@link PersistenceEntityManager} to register.
   * @throws DuplicateObjectException if a manager is already registered for the
   *         same <code>entityClass</code>.
   */
  private void registerManager(Class<?> entityClass, PersistenceEntityManager<?, ?> entityManager)
      throws DuplicateObjectException {

    if (this.class2managerMap.containsKey(entityClass)) {
      throw new DuplicateObjectException(entityManager, entityClass);
    }
    this.class2managerMap.put(entityClass, entityManager);
  }

  /**
   * {@inheritDoc}
   */
  public boolean hasManager(Class<? extends PersistenceEntity<?>> entityClass) {

    return this.class2managerMap.containsKey(entityClass);
  }

  /**
   * {@inheritDoc}
   */
  public <ID, ENTITY extends PersistenceEntity<ID>> PersistenceEntityManager<ID, ENTITY> getManager(
      Class<ENTITY> entityType) {

    PersistenceEntityManager<ID, ENTITY> manager = (PersistenceEntityManager<ID, ENTITY>) this.class2managerMap
        .get(entityType);
    if (manager == null) {
      throw new ObjectNotFoundException(PersistenceEntityManager.class.getSimpleName(), entityType);
    }
    return manager;
  }

  /**
   * {@inheritDoc}
   */
  public <ID, ENTITY extends PersistenceEntity<ID>> ENTITY load(Class<ENTITY> entityClass, ID id) {

    PersistenceEntityManager<ID, ENTITY> manager = getManager(entityClass);
    return manager.load(id);
  }

  /**
   * {@inheritDoc}
   */
  public <ID, ENTITY extends PersistenceEntity<ID>> ENTITY loadIfExists(Class<ENTITY> entityClass,
      ID id) {

    PersistenceEntityManager<ID, ENTITY> manager = getManager(entityClass);
    return manager.loadIfExists(id);
  }

  /**
   * {@inheritDoc}
   */
  public <ID, ENTITY extends PersistenceEntity<ID>> ENTITY getReference(Class<ENTITY> entityClass,
      ID id) throws ObjectNotFoundException {

    PersistenceEntityManager<ID, ENTITY> manager = getManager(entityClass);
    return manager.getReference(id);
  }

  /**
   * {@inheritDoc}
   */
  public <ID, ENTITY extends PersistenceEntity<ID>> ENTITY create(Class<ENTITY> entityClass) {

    PersistenceEntityManager<ID, ENTITY> manager = getManager(entityClass);
    return manager.create();
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public void save(PersistenceEntity entity) {

    Class<? extends PersistenceEntity> entityClass = getEntityClass(entity);
    PersistenceEntityManager manager = getManager(entityClass);
    manager.save(entity);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("all")
  public void delete(PersistenceEntity entity) {

    Class<? extends PersistenceEntity> entityClass = getEntityClass(entity);
    PersistenceEntityManager manager = getManager(entityClass);
    manager.delete(entity);
  }

  /**
   * {@inheritDoc}
   */
  public Class<? extends PersistenceEntity<?>> getEntityClass(PersistenceEntity<?> entity) {

    // this is a very simple way - it might be wrong...
    Class<?> entityClass = entity.getClass();
    if (!this.class2managerMap.containsKey(entityClass)) {
      entityClass = entityClass.getSuperclass();
      if (!this.class2managerMap.containsKey(entityClass)) {
        throw new ObjectNotFoundException(PersistenceEntityManager.class.getSimpleName(),
            entity.getClass());
      }
    }
    return (Class<? extends PersistenceEntity<?>>) entity.getClass();
  }
}
