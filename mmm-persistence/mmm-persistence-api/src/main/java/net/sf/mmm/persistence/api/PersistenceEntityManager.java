/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.api;

import net.sf.mmm.util.component.base.ComponentSpecification;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;
import net.sf.mmm.util.reflect.api.ReflectionException;

/**
 * This is the interface for a manager of a
 * {@link #getEntityClassImplementation() specific type} of
 * {@link PersistenceEntity}. In other contexts this is often called a DAO (Data
 * Access Object). It is responsible for {@link #load(Object) loading} ,
 * {@link #save(PersistenceEntity) saving} and
 * {@link net.sf.mmm.persistence.api.search.PersistenceSearcher searching} all
 * {@link PersistenceEntity entities} of the
 * {@link #getEntityClassImplementation() according type}.<br>
 * For each (non-abstract) implementation of {@link PersistenceEntity} there
 * should exists one instance of this interface. Typically when you create a
 * custom {@link PersistenceEntity entity} you will also create a custom
 * interface and implementation of an according {@link PersistenceEntityManager}
 * . If there is no custom implementation of a {@link PersistenceEntityManager}
 * for some type of {@link PersistenceEntity}, a generic implementation is used
 * as fallback.<br>
 * The {@link PersistenceManager} can be seen as the manager of all
 * {@link PersistenceEntityManager} and acts as front-end to them.
 * 
 * @see PersistenceEntity
 * @see PersistenceManager
 * 
 * @param <ID> is the type of the {@link PersistenceEntity#getId() primary key}
 *        of the managed {@link PersistenceEntity}.
 * @param <ENTITY> is the {@link #getEntityClassImplementation() type} of the
 *        managed entity.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@ComponentSpecification(plugin = true)
public interface PersistenceEntityManager<ID, ENTITY extends PersistenceEntity<ID>> {

  /**
   * This method creates a new, empty, and transient instance of the
   * {@link #getEntityClassImplementation() managed} entity. The default
   * implementation is {@link Class#newInstance()}, however more specific
   * implementations are possible. This allows to use interfaces for entities
   * and use the persistence layer as API without knowing the implementation.
   * However you are still free to ignore this method and work with the POJO
   * approach.
   * 
   * @return the new instance.
   * @throws ReflectionException is the instantiation failed.
   */
  ENTITY create() throws ReflectionException;

  /**
   * This method gets the implementation class reflecting the
   * {@link PersistenceEntity} managed by this {@link PersistenceEntityManager}.
   * 
   * @return the according entity-class.
   */
  Class<? extends ENTITY> getEntityClassImplementation();

  /**
   * This method gets the {@link #getEntityClassImplementation() entity-class}
   * with the view for reading the entity. This may be the same as
   * {@link #getEntityClassImplementation()} but can also be an interface with
   * the getters of the entity.
   * 
   * @return the according entity-class.
   */
  Class<? super ENTITY> getEntityClassReadOnly();

  /**
   * This method gets the {@link #getEntityClassImplementation() entity-class}
   * with the view for reading and writing the entity. This may be the same as
   * {@link #getEntityClassImplementation()} but can also be an interface with
   * the getters and setters (typically extending
   * {@link #getEntityClassReadOnly()}) of the entity.
   * 
   * @return the according entity-class.
   */
  Class<ENTITY> getEntityClassReadWrite();

  /**
   * This method loads the {@link PersistenceEntity} with the given
   * <code>id</code> from the persistent store.
   * 
   * @see javax.persistence.EntityManager#find(Class, Object)
   * 
   * @param id is the {@link PersistenceEntity#getId() primary key} of the
   *        requested {@link PersistenceEntity entity}.
   * @return the requested {@link PersistenceEntity entity}.
   * @throws ObjectNotFoundException if the requested {@link PersistenceEntity
   *         entity} could NOT be found. Unlike the JPA we throw this exception
   *         instead of returning <code>null</code> as this is typically an
   *         exceptional situation and it is better to have a precise exception
   *         than a NPE.
   */
  ENTITY load(ID id) throws ObjectNotFoundException;

  /**
   * This method creates a lazy reference proxy of the {@link PersistenceEntity}
   * with the given <code>id</code> from the persistent store.
   * 
   * @see javax.persistence.EntityManager#getReference(Class, Object)
   * 
   * @param id is the {@link PersistenceEntity#getId() primary key} of the
   *        requested {@link PersistenceEntity entity}.
   * @return the requested {@link PersistenceEntity entity}.
   */
  ENTITY getReference(ID id);

  /**
   * This method saves the given <code>entity</code> in the persistent store. If
   * the <code>entity</code> is {@link PersistenceEntity#isPersistent()
   * transient} it is initially created in the persistent store. If the
   * <code>entity</code> is configured to have a generated
   * {@link PersistenceEntity#getId() primary key} a unique ID is generated and
   * assigned. Otherwise, if the <code>entity</code> is already persistent, the
   * <code>entity</code> is updated in the persistent store.<br>
   * <b>ATTENTION:</b><br>
   * Depending on the underlying implementation the modification of a
   * {@link PersistenceEntity#isPersistent() persistent}
   * {@link PersistenceEntity entity} is automatically saved even if this method
   * has NOT been invoked. However you should always invoke this method after
   * modifying a {@link PersistenceEntity#isPersistent() persistent}
   * {@link PersistenceEntity entity} instead of relying on some implementation
   * specific behaviour. This will also guarantee that the custom-logic of your
   * {@link PersistenceEntityManager} is invoked.
   * 
   * @param entity is the {@link PersistenceEntity} to save.
   */
  void save(ENTITY entity);

  /**
   * This method deletes the given <code>entity</code> from the persistent
   * store.<br>
   * If the <code>entity</code> is {@link PersistenceEntity#isPersistent()
   * transient} this method has no effect.<br>
   * 
   * @param entity is the {@link PersistenceEntity} to save.
   */
  void delete(ENTITY entity);

}
