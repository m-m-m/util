/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa;

import javax.inject.Named;

/**
 * This is the implementation of the {@link DummyFooEntityManager} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Named
public class DummyFooEntityManagerImpl extends JpaPersistenceEntityManager<Integer, DummyFooEntity>
    implements DummyFooEntityManager {

  /**
   * {@inheritDoc}
   */
  public Class<DummyFooEntity> getEntityClass() {

    return DummyFooEntity.class;
  }

}
