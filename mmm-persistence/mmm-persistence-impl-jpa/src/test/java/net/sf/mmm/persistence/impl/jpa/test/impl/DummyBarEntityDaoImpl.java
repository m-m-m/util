/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa.test.impl;

import javax.inject.Named;

import net.sf.mmm.persistence.base.jpa.AbstractJpaGenericDao;
import net.sf.mmm.persistence.impl.jpa.test.api.DummyBarEntity;
import net.sf.mmm.persistence.impl.jpa.test.api.DummyBarEntityDao;
import net.sf.mmm.persistence.impl.jpa.test.api.DummyBarEntityView;

/**
 * This is the implementation of the {@link DummyBarEntityDao} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Named
public class DummyBarEntityDaoImpl extends AbstractJpaGenericDao<Long, DummyBarEntity> implements DummyBarEntityDao {

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<DummyBarEntity> getEntityClassReadWrite() {

    return DummyBarEntity.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<? super DummyBarEntity> getEntityClassReadOnly() {

    return DummyBarEntityView.class;
  }

  /**
   * {@inheritDoc}
   */
  public Class<DummyBarEntityImpl> getEntityClassImplementation() {

    return DummyBarEntityImpl.class;
  }

}
