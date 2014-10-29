/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.api;

import net.sf.mmm.util.component.api.ComponentSpecification;
import net.sf.mmm.util.entity.api.GenericEntity;
import net.sf.mmm.util.exception.api.ObjectNotFoundException;
import net.sf.mmm.util.reflect.api.ReflectionException;

/**
 * This is the interface for a <em>Data Access Object</em> (DAO). It acts as a manager responsible for the
 * persistence operations on a {@link #getEntityClass() specific type} of {@link GenericEntity}. It is
 * responsible for {@link #find(Object) loading}, {@link #save(GenericEntity) saving} and
 * {@link net.sf.mmm.util.search.api.SearchQuery searching} {@link GenericEntity entities} of the
 * {@link #getEntityClass() according type}. <br>
 * For each (non-abstract) implementation of {@link GenericEntity} there should exist one instance of this
 * interface. Typically when you create a custom {@link GenericEntity entity} you will also create a custom
 * interface and implementation of an according {@link GenericDao}. If there is no custom implementation of a
 * {@link GenericDao} for some type of {@link GenericEntity}, a generic implementation is used as fallback. <br>
 * The {@link PersistenceManager} can be seen as the manager of all {@link GenericDao} and acts as front-end
 * to them.
 *
 * @see GenericEntity
 * @see PersistenceManager
 *
 * @param <ID> is the type of the {@link GenericEntity#getId() primary key} of the managed
 *        {@link GenericEntity}.
 * @param <ENTITY> is the {@link #getEntityClass() type} of the managed entity. We strongly recommend to
 *        extend {@link net.sf.mmm.util.entity.api.PersistenceEntity}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@ComponentSpecification(plugin = true)
public interface GenericDao<ID, ENTITY extends GenericEntity<ID>> {

  /**
   * This method creates a new, empty, and transient instance of the {@link #getEntityClass() managed} entity.
   * The default implementation is {@link Class#newInstance()}, however more specific implementations are
   * possible. This allows to use interfaces for entities and use the persistence layer as API without knowing
   * the implementation. However you are still free to ignore this method and work with the POJO approach.
   *
   * @return the new instance.
   * @throws ReflectionException is the instantiation failed.
   */
  ENTITY create() throws ReflectionException;

  /**
   * This method gets the {@link Class} reflecting the {@link GenericEntity} managed by this
   * {@link GenericDao}.
   *
   * @return the according entity-class.
   */
  Class<? extends ENTITY> getEntityClass();

  /**
   * This method loads the {@link GenericEntity} with the given <code>id</code> from the persistent store.
   *
   * @see javax.persistence.EntityManager#find(Class, Object)
   *
   * @param id is the {@link GenericEntity#getId() primary key} of the requested {@link GenericEntity entity}.
   * @return the requested {@link GenericEntity entity}.
   * @throws ObjectNotFoundException if the requested {@link GenericEntity entity} could NOT be found. Unlike
   *         the JPA we throw this exception instead of returning <code>null</code> as this is typically an
   *         exceptional situation and it is better to have a precise exception than a NPE. Otherwise use
   *         {@link #findIfExists(Object)}.
   */
  ENTITY find(ID id) throws ObjectNotFoundException;

  /**
   * This method loads the {@link GenericEntity} with the given <code>id</code> from the persistent store.
   *
   * @see javax.persistence.EntityManager#find(Class, Object)
   *
   * @param id is the {@link GenericEntity#getId() primary key} of the requested {@link GenericEntity entity}.
   * @return the requested {@link GenericEntity entity} or <code>null</code> if it does NOT exist in
   *         persistent store.
   */
  ENTITY findIfExists(ID id);

  /**
   * This method creates a lazy reference proxy of the {@link GenericEntity} with the given <code>id</code>
   * from the persistent store.
   *
   * @see javax.persistence.EntityManager#getReference(Class, Object)
   *
   * @param id is the {@link GenericEntity#getId() primary key} of the requested {@link GenericEntity entity}.
   * @return the requested {@link GenericEntity entity}.
   */
  ENTITY getReference(ID id);

  /**
   * This method saves the given <code>entity</code> in the persistent store. If the <code>entity</code> is
   * {@link net.sf.mmm.util.entity.api.PersistenceEntity#STATE_NEW new} it is initially created in the
   * persistent store (after commit). If the <code>entity</code> is configured to have a generated
   * {@link GenericEntity#getId() primary key} a unique ID is generated and assigned. Otherwise, if the
   * <code>entity</code> is already {@link net.sf.mmm.util.entity.api.PersistenceEntity#STATE_MANAGED managed}
   * , the <code>entity</code> is updated in the persistent store. <br>
   * <b>ATTENTION:</b><br>
   * Modifications to a {@link net.sf.mmm.util.entity.api.PersistenceEntity#STATE_MANAGED managed}
   * {@link GenericEntity entity} are automatically saved even if this method has NOT been invoked. However,
   * it is a good practice to always invoke this method after modifying a
   * {@link net.sf.mmm.util.entity.api.PersistenceEntity#STATE_MANAGED managed} {@link GenericEntity entity}
   * to make your code more explicit. This will also guarantee that potential custom-logic of your
   * {@link GenericDao DAO} is invoked.
   *
   * @param entity is the {@link GenericEntity} to save.
   */
  void save(ENTITY entity);

  /**
   * This method deletes the given <code>entity</code> from the persistent store. <br>
   * If the <code>entity</code> is {@link net.sf.mmm.util.entity.api.PersistenceEntity#STATE_NEW transient}
   * this method has no effect. <br>
   *
   * @param entity is the {@link GenericEntity} to save.
   */
  void delete(ENTITY entity);

  /**
   * This method deletes the {@link GenericEntity entity} identified by the given <code>id</code> from the
   * persistent store. <br>
   *
   * @param id is the {@link GenericEntity#getId() primary key} of the {@link GenericEntity entity} to delete.
   */
  void deleteById(ID id);

}
