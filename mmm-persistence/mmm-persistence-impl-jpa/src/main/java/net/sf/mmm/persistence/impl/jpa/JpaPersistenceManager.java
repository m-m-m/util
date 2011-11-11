/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.metamodel.EntityType;

import net.sf.mmm.persistence.api.PersistenceEntity;
import net.sf.mmm.persistence.api.PersistenceEntityManager;
import net.sf.mmm.persistence.base.AbstractRevisionedPersistenceManager;
import net.sf.mmm.util.component.api.ResourceMissingException;
import net.sf.mmm.util.nls.api.DuplicateObjectException;
import net.sf.mmm.util.pojo.api.PojoFactory;
import net.sf.mmm.util.pojo.base.DefaultPojoFactory;

/**
 * This is the default implementation of the
 * {@link net.sf.mmm.persistence.api.PersistenceManager} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Named
public class JpaPersistenceManager extends AbstractRevisionedPersistenceManager {

  /** @see #getPojoFactory() */
  private PojoFactory pojoFactory;

  /** @see #getEntityManager() */
  private EntityManager entityManager;

  /**
   * The constructor.
   */
  public JpaPersistenceManager() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings({ "unchecked", "rawtypes" })
  protected void doInitialize() {

    super.doInitialize();
    if (this.entityManager == null) {
      throw new ResourceMissingException("entityManager");
    }
    if (this.pojoFactory == null) {
      DefaultPojoFactory factory = new DefaultPojoFactory();
      factory.initialize();
      this.pojoFactory = factory;
    }

    for (EntityType<?> entityType : this.entityManager.getMetamodel().getEntities()) {
      Class<?> entityClass = entityType.getJavaType();
      if (PersistenceEntity.class.isAssignableFrom(entityClass)) {
        if (!hasManager((Class<? extends PersistenceEntity<?>>) entityClass)) {
          JpaGenericPersistenceEntityManager manager = new JpaGenericPersistenceEntityManager(
              entityClass);
          manager.setEntityManager(this.entityManager);
          manager.setPojoFactory(this.pojoFactory);
          manager.initialize();
          addManager(manager);
          getLogger().info("Added generic manager for entity " + entityClass.getName());
        } else {
          getLogger().debug("Found registered manager for entity " + entityClass.getName());
        }
      } else {
        getLogger().warn(
            "Entity " + entityClass.getName() + " does NOT implement "
                + PersistenceEntity.class.getName());
      }
    }
  }

  /**
   * This method gets a thread-safe {@link EntityManager}. It acts as proxy to
   * an {@link EntityManager} associated with the current thread (created when
   * the transaction is opened e.g. via
   * {@link net.sf.mmm.transaction.api.TransactionExecutor}).
   * 
   * @return the according {@link EntityManager}.
   */
  protected EntityManager getEntityManager() {

    return this.entityManager;
  }

  /**
   * This method injects a thread-safe {@link EntityManager} instance that acts
   * as proxy to an {@link EntityManager} associated with the current thread
   * (created when the transaction is opened).
   * 
   * @param entityManager is the entityManager to set
   */
  @PersistenceContext
  public void setEntityManager(EntityManager entityManager) {

    getInitializationState().requireNotInitilized();
    this.entityManager = entityManager;
  }

  /**
   * @return the {@link PojoFactory}.
   */
  protected PojoFactory getPojoFactory() {

    return this.pojoFactory;
  }

  /**
   * @param pojoFactory is the {@link PojoFactory} to set.
   */
  @Inject
  public void setPojoFactory(PojoFactory pojoFactory) {

    getInitializationState().requireNotInitilized();
    this.pojoFactory = pojoFactory;
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

    for (PersistenceEntityManager<?, ?> manager : managerList) {
      addManager(manager);
    }
  }

}
