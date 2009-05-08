/* $Id: $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.hibernate;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class DummyRevisionedFooEntityManager extends
    EnversPersistenceEntityManager<DummyRevisionedFooEntity> {

  /**
   * The constructor.
   */
  public DummyRevisionedFooEntityManager() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public Class<DummyRevisionedFooEntity> getEntityClass() {

    return DummyRevisionedFooEntity.class;
  }

}
