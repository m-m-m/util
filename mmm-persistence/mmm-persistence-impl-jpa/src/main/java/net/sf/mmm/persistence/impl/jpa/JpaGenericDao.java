/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa;

import net.sf.mmm.persistence.base.jpa.AbstractJpaGenericDao;
import net.sf.mmm.util.entity.api.GenericEntity;

/**
 * This is a generic implementation of {@link AbstractJpaGenericDao}. It is automatically used as default if no
 * custom {@link net.sf.mmm.persistence.api.GenericDao DAO} exists.
 * 
 * @param <ID> is the type of the {@link GenericEntity#getId() primary key} of the managed
 *        {@link GenericEntity}.
 * @param <ENTITY> is the {@link #getEntityClass() type} of the managed entity.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 0.9.0
 */
public class JpaGenericDao<ID, ENTITY extends GenericEntity<ID>> extends AbstractJpaGenericDao<ID, ENTITY> {

  /** @see #getEntityClass() */
  private Class<? extends ENTITY> entityClassImplementation;

  /**
   * The constructor.
   * 
   * @param entityClassImplementation is the {@link #getEntityClass()}.
   */
  public JpaGenericDao(Class<? extends ENTITY> entityClassImplementation) {

    super();
    this.entityClassImplementation = entityClassImplementation;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<? extends ENTITY> getEntityClass() {

    return this.entityClassImplementation;
  }

}
