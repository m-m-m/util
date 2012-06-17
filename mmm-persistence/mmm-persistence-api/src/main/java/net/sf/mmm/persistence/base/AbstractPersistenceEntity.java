/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.base;

import net.sf.mmm.persistence.api.PersistenceEntity;

/**
 * This is the abstract base-class that implementations of
 * {@link PersistenceEntity} should inherit from (if possible).
 * 
 * @param <ID> is the type of the {@link #getId() primary key}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractPersistenceEntity<ID> implements PersistenceEntity<ID> {

  /**
   * The constructor.
   */
  public AbstractPersistenceEntity() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public boolean isPersistent() {

    return (getModificationTimestamp() != null);
  }

}
