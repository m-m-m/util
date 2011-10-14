/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa.test.impl;

import javax.inject.Named;

import net.sf.mmm.persistence.impl.jpa.JpaPersistenceEntityManager;
import net.sf.mmm.persistence.impl.jpa.test.api.DummyBarEntityManager;

/**
 * This is the implementation of the {@link DummyBarEntityManager} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Named
public class DummyBarEntityManagerImpl extends JpaPersistenceEntityManager<Integer, DummyBarEntityImpl>
    implements DummyBarEntityManager {

  /**
   * {@inheritDoc}
   */
  public Class<DummyBarEntityImpl> getEntityClassImplementation() {

    return DummyBarEntityImpl.class;
  }

}
