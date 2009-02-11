/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import net.sf.mmm.persistence.api.PersistenceEntity;

/**
 * This is the abstract base-implementation of a {@link PersistenceEntity} using
 * the {@link javax.persistence JPA} (Java Persistence API).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@MappedSuperclass
public abstract class JpaManagedIdPersistenceEntity extends JpaPersistenceEntity {

  /** @see #isPersistent() */
  private transient boolean persistent;

  /**
   * The constructor.
   */
  public JpaManagedIdPersistenceEntity() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isPersistent() {

    return this.persistent;
  }

  /**
   * This method marks this entity as persistent, which means that it is NOT
   * {@link #isPersistent() transient}.<br>
   * <b>ATTENTION:</b><br>
   */
  public void setPersistent() {

    this.persistent = true;
  }

  /**
   * {@inheritDoc}
   */
  @Id
  public abstract Object getId();

}
