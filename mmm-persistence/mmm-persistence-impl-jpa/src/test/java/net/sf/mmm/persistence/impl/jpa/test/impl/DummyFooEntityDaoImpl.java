/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa.test.impl;

import javax.inject.Named;

import net.sf.mmm.persistence.base.jpa.AbstractJpaGenericDao;
import net.sf.mmm.persistence.impl.jpa.test.api.DummyFooEntity;
import net.sf.mmm.persistence.impl.jpa.test.api.DummyFooEntityDao;
import net.sf.mmm.persistence.impl.jpa.test.api.DummyFooEntityView;

/**
 * This is the implementation of the {@link DummyFooEntityDao} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Named
public class DummyFooEntityDaoImpl extends AbstractJpaGenericDao<Integer, DummyFooEntity>
    implements DummyFooEntityDao {

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<DummyFooEntityView> getEntityClassReadOnly() {

    return DummyFooEntityView.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<DummyFooEntity> getEntityClassReadWrite() {

    return DummyFooEntity.class;
  }

  /**
   * {@inheritDoc}
   */
  public Class<DummyFooEntityImpl> getEntityClassImplementation() {

    return DummyFooEntityImpl.class;
  }

}
