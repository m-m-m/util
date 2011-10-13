/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * This is the abstract base-implementation of a
 * {@link net.sf.mmm.persistence.api.PersistenceEntity} using the
 * {@link javax.persistence JPA} (Java Persistence API).
 * 
 * @param <ID> is the type of the {@link #getId() primary key}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@MappedSuperclass
public abstract class JpaManagedIdPersistenceEntity<ID> extends JpaPersistenceEntity<ID> {

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
  @Transient
  public boolean isPersistent() {

    return this.persistent;
  }

  /**
   * This method marks this entity as persistent, which means that it is NOT
   * {@link #isPersistent() transient}.<br>
   * <b>ATTENTION:</b><br>
   */
  protected void setPersistent() {

    this.persistent = true;
  }

}
