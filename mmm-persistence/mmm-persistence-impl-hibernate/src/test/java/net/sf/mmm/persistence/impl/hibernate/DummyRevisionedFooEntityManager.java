/* $Id: $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.hibernate;

import javax.inject.Named;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Named
public class DummyRevisionedFooEntityManager extends
    EnversPersistenceEntityManager<Long, DummyRevisionedFooEntity> {

  /**
   * The constructor.
   */
  public DummyRevisionedFooEntityManager() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public Class<DummyRevisionedFooEntity> getEntityClassImplementation() {

    return DummyRevisionedFooEntity.class;
  }

}
