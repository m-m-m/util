/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import net.sf.mmm.persistence.api.PersistenceEntity;
import net.sf.mmm.persistence.base.AbstractPersistenceEntity;

/**
 * This is the abstract base-implementation of a {@link PersistenceEntity} using
 * the {@link javax.persistence JPA} (Java Persistence API).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@MappedSuperclass
public abstract class JpaPersistenceEntity extends AbstractPersistenceEntity {

  /** @see #getModificationCounter() */
  @Version
  private int modificationCounter;

  /**
   * The constructor.
   */
  public JpaPersistenceEntity() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public int getModificationCounter() {

    return this.modificationCounter;
  }

  /**
   * @param modificationCounter is the modificationCounter to set
   */
  public void setModificationCounter(int modificationCounter) {

    this.modificationCounter = modificationCounter;
  }

  void setPersistent(boolean p) {

    throw new IllegalStateException("Hibernate sucks!");
  }

}
