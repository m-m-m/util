/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.base;

import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.persistence.api.GenericDao;
import net.sf.mmm.persistence.api.PersistenceManager;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.entity.api.GenericEntity;
import net.sf.mmm.util.exception.api.DuplicateObjectException;
import net.sf.mmm.util.exception.api.ObjectNotFoundException;

/**
 * This is the abstract base-implementation of the {@link PersistenceManager} interface.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractPersistenceManager extends AbstractLoggableComponent implements PersistenceManager {

  /** @see #getDao(Class) */
  private final Map<Class<?>, GenericDao<?, ?>> class2daoMap;

  /**
   * The constructor.
   */
  public AbstractPersistenceManager() {

    super();
    this.class2daoMap = new HashMap<Class<?>, GenericDao<?, ?>>();
  }

  /**
   * This method registers the given <code>entityManager</code>.
   *
   * @param dao is the {@link GenericDao} to register.
   * @throws DuplicateObjectException if a manager is already registered for the same entity-class (
   *         {@link GenericDao#getEntityClass()} or {@link AbstractGenericDao#getEntityApiClass()}).
   */
  protected void addDao(GenericDao<?, ?> dao) throws DuplicateObjectException {

    getInitializationState().requireNotInitilized();
    Class<?> entityClass = dao.getEntityClass();
    registerDao(entityClass, dao);
    if (dao instanceof AbstractGenericDao) {
      Class<?> entityApiClass = ((AbstractGenericDao<?, ?>) dao).getEntityApiClass();
      if (entityClass != entityApiClass) {
        registerDao(entityApiClass, dao);
      }
    }
  }

  /**
   * This method registers the given {@link GenericDao DAO} for the given <code>entityClass</code>.
   *
   * @param entityClass is the {@link Class} used as key to associate the {@link GenericDao DAO} with.
   * @param dao is the {@link GenericDao} to register.
   * @throws DuplicateObjectException if a DAO is already registered for the same <code>entityClass</code>.
   */
  private void registerDao(Class<?> entityClass, GenericDao<?, ?> dao) throws DuplicateObjectException {

    if (this.class2daoMap.containsKey(entityClass)) {
      throw new DuplicateObjectException(dao, entityClass);
    }
    this.class2daoMap.put(entityClass, dao);
  }

  /**
   * {@inheritDoc}
   */
  public boolean hasDao(Class<? extends GenericEntity<?>> entityClass) {

    return this.class2daoMap.containsKey(entityClass);
  }

  /**
   * {@inheritDoc}
   */
  public <ID, ENTITY extends GenericEntity<ID>> GenericDao<ID, ENTITY> getDao(Class<ENTITY> entityType) {

    @SuppressWarnings("unchecked")
    GenericDao<ID, ENTITY> dao = (GenericDao<ID, ENTITY>) this.class2daoMap.get(entityType);
    if (dao == null) {
      throw new ObjectNotFoundException(GenericDao.class.getSimpleName(), entityType);
    }
    return dao;
  }

  /**
   * {@inheritDoc}
   */
  public <ID, ENTITY extends GenericEntity<ID>> ENTITY find(Class<ENTITY> entityClass, ID id) {

    GenericDao<ID, ENTITY> dao = getDao(entityClass);
    return dao.find(id);
  }

  /**
   * {@inheritDoc}
   */
  public <ID, ENTITY extends GenericEntity<ID>> ENTITY findIfExists(Class<ENTITY> entityClass, ID id) {

    GenericDao<ID, ENTITY> dao = getDao(entityClass);
    return dao.findIfExists(id);
  }

  /**
   * {@inheritDoc}
   */
  public <ID, ENTITY extends GenericEntity<ID>> ENTITY getReference(Class<ENTITY> entityClass, ID id)
      throws ObjectNotFoundException {

    GenericDao<ID, ENTITY> dao = getDao(entityClass);
    return dao.getReference(id);
  }

  /**
   * {@inheritDoc}
   */
  public <ID, ENTITY extends GenericEntity<ID>> ENTITY create(Class<ENTITY> entityClass) {

    GenericDao<ID, ENTITY> dao = getDao(entityClass);
    return dao.create();
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public void save(GenericEntity entity) {

    Class<? extends GenericEntity> entityClass = getEntityClass(entity);
    GenericDao dao = getDao(entityClass);
    dao.save(entity);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("all")
  public void delete(GenericEntity entity) {

    Class<? extends GenericEntity> entityClass = getEntityClass(entity);
    GenericDao dao = getDao(entityClass);
    dao.delete(entity);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public Class<? extends GenericEntity<?>> getEntityClass(GenericEntity<?> entity) {

    // this is a very simple way - it might be wrong...
    Class<?> entityClass = entity.getClass();
    if (!this.class2daoMap.containsKey(entityClass)) {
      // if (entity instanceof HibernateProxy) {
      // entityClass = ((HibernateProxy) proxy).getHibernateLazyInitializer().getImplementation().getClass();
      // return (Class<? extends GenericEntity<?>>) entity.getClass();
      // }
      entityClass = entityClass.getSuperclass();
      if (!this.class2daoMap.containsKey(entityClass)) {
        throw new ObjectNotFoundException(GenericDao.class.getSimpleName(), entity.getClass());
      }
    }
    return (Class<? extends GenericEntity<?>>) entity.getClass();
  }
}
