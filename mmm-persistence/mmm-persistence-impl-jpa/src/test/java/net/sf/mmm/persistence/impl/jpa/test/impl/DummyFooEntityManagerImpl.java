/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa.test.impl;

import javax.inject.Named;

import net.sf.mmm.persistence.impl.jpa.JpaPersistenceEntityManager;
import net.sf.mmm.persistence.impl.jpa.test.api.DummyFooEntityManager;

/**
 * This is the implementation of the {@link DummyFooEntityManager} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Named
public class DummyFooEntityManagerImpl extends JpaPersistenceEntityManager<Integer, DummyFooEntityImpl>
    implements DummyFooEntityManager {

  /**
   * {@inheritDoc}
   */
  public Class<DummyFooEntityImpl> getEntityClassImplementation() {

    return DummyFooEntityImpl.class;
  }

}
