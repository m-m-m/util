/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa;

import net.sf.mmm.persistence.api.PersistenceEntity;

/**
 * This is a generic implementation of {@link JpaPersistenceEntityManager}.
 * 
 * @param <ID> is the type of the {@link PersistenceEntity#getId() primary key}
 *        of the managed {@link PersistenceEntity}.
 * @param <ENTITY> is the {@link #getEntityClassImplementation() type} of the
 *        managed entity.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class JpaGenericPersistenceEntityManager<ID, ENTITY extends PersistenceEntity<ID>> extends
    JpaPersistenceEntityManager<ID, ENTITY> {

  /** @see #getEntityClassImplementation() */
  private Class<? extends ENTITY> entityClassImplementation;

  /**
   * The constructor.
   * 
   * @param entityClassImplementation is the
   *        {@link #getEntityClassImplementation()}.
   */
  public JpaGenericPersistenceEntityManager(Class<? extends ENTITY> entityClassImplementation) {

    super();
    this.entityClassImplementation = entityClassImplementation;
  }

  /**
   * {@inheritDoc}
   */
  public Class<? extends ENTITY> getEntityClassImplementation() {

    return this.entityClassImplementation;
  }

}
