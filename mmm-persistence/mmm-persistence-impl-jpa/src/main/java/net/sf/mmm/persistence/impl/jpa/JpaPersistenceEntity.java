/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;

import net.sf.mmm.persistence.api.PersistenceEntity;

/**
 * This is the abstract base-implementation of a {@link PersistenceEntity} using
 * the {@link javax.persistence JPA} (Java Persistence API).<br>
 * We can NOT extend
 * {@link net.sf.mmm.persistence.base.AbstractPersistenceEntity} because JPA
 * forces super-classes to be annotated and prevents from overriding annotated
 * features such as {@link Transient}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@MappedSuperclass
public abstract class JpaPersistenceEntity implements PersistenceEntity {

  /** @see #getModificationCounter() */
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
  @Version
  public int getModificationCounter() {

    return this.modificationCounter;
  }

  /**
   * @param modificationCounter is the modificationCounter to set
   */
  protected void setModificationCounter(int modificationCounter) {

    this.modificationCounter = modificationCounter;
  }

  /**
   * {@inheritDoc}
   */
  @Transient
  public boolean isPersistent() {

    return (getId() != null);
  }

}
