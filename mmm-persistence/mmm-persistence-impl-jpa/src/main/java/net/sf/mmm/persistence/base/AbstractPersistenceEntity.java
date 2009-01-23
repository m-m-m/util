/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.base;

import javax.persistence.MappedSuperclass;

import net.sf.mmm.persistence.api.PersistenceEntity;

/**
 * This is the abstract base-class that implementations of
 * {@link PersistenceEntity} should inherit from (if possible).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@MappedSuperclass
public abstract class AbstractPersistenceEntity implements PersistenceEntity {

  /**
   * The constructor.
   */
  public AbstractPersistenceEntity() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public int getModificationCounter() {

    return 0;
  }

  /**
   * {@inheritDoc}
   */
  public int getRevision() {

    return 0;
  }

}
