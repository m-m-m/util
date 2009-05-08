/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa;

/**
 * This is the implementation of the {@link DummyBarEntityManager} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class DummyBarEntityManagerImpl extends JpaPersistenceEntityManager<DummyBarEntity> implements
    DummyBarEntityManager {

  /**
   * {@inheritDoc}
   */
  public Class<DummyBarEntity> getEntityClass() {

    return DummyBarEntity.class;
  }

}
