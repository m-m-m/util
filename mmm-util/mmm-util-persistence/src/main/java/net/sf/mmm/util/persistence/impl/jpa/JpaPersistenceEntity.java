/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.persistence.impl.jpa;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import net.sf.mmm.util.persistence.api.PersistenceEntity;
import net.sf.mmm.util.persistence.base.AbstractPersistenceEntity;

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

  /** @see #isTransient() */
  private transient boolean persistent;

  /**
   * The constructor.
   */
  public JpaPersistenceEntity() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getModificationCounter() {

    return this.modificationCounter;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isTransient() {

    return !this.persistent;
  }

}
