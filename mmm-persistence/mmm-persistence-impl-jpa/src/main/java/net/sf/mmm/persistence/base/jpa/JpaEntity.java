/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.base.jpa;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;


/**
 * This is the abstract base-implementation of a {@link net.sf.mmm.util.entity.api.GenericEntity} using the
 * {@link javax.persistence JPA} (Java Persistence API).<br>
 * 
 * @param <ID> is the type of the {@link #getId() primary key}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@MappedSuperclass
public abstract class JpaEntity<ID> extends AbstractJpaEntity<ID> {

  /** @see #getModificationCounter() */
  private int modificationCounter;

  /**
   * The constructor.
   */
  public JpaEntity() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
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

}
