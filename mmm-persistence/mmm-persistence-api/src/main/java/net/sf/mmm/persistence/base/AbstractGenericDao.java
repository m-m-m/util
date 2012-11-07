/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.base;

import net.sf.mmm.persistence.api.GenericDao;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.entity.api.GenericEntity;
import net.sf.mmm.util.pojo.api.PojoFactory;
import net.sf.mmm.util.pojo.base.DefaultPojoFactory;
import net.sf.mmm.util.reflect.api.ReflectionException;

/**
 * This is the abstract base-implementation of the
 * {@link GenericDao} interface.
 * 
 * @param <ID> is the type of the {@link GenericEntity#getId() primary key}
 *        of the managed {@link GenericEntity}.
 * @param <ENTITY> is the {@link #getEntityClassImplementation() type} of the
 *        managed entity.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractGenericDao<ID, ENTITY extends GenericEntity<ID>>
    extends AbstractLoggableComponent implements GenericDao<ID, ENTITY> {

  /** @see #getPojoFactory() */
  private PojoFactory pojoFactory;

  /**
   * The constructor.
   */
  public AbstractGenericDao() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    if (this.pojoFactory == null) {
      DefaultPojoFactory factory = new DefaultPojoFactory();
      factory.initialize();
      this.pojoFactory = factory;
    }
    super.doInitialize();
  }

  /**
   * @return the pojoFactory
   */
  protected PojoFactory getPojoFactory() {

    return this.pojoFactory;
  }

  /**
   * @param pojoFactory is the pojoFactory to set
   */
  public void setPojoFactory(PojoFactory pojoFactory) {

    getInitializationState().requireNotInitilized();
    this.pojoFactory = pojoFactory;
  }

  /**
   * {@inheritDoc}
   */
  public ENTITY create() throws ReflectionException {

    return getPojoFactory().newInstance(getEntityClassImplementation());
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public Class<? super ENTITY> getEntityClassReadOnly() {

    return (Class) getEntityClassImplementation();
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public Class<ENTITY> getEntityClassReadWrite() {

    return (Class) getEntityClassImplementation();
  }

}
