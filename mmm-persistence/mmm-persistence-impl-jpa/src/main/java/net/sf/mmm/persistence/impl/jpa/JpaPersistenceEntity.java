/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
 * @param <ID> is the type of the {@link #getId() primary key}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@MappedSuperclass
public abstract class JpaPersistenceEntity<ID> implements PersistenceEntity<ID> {

  // extends AbstractPersistenceEntity<ID>

  /** @see #getModificationCounter() */
  private int modificationCounter;

  /** @see #getModificationTimestamp() */
  private Date modificationTimestamp;

  /** @see #getId() */
  private ID id;

  /**
   * The constructor.
   */
  public JpaPersistenceEntity() {

    super();
  }

  /**
   * <b>ATTENTION:</b><br/>
   * If your ID is not {@link GeneratedValue generated}, you need to override
   * this method. In this case you can also override {@link #setId(Object)} to
   * change the visibility to public.<br/>
   * <br/>
   * 
   * {@inheritDoc}
   */
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  public ID getId() {

    return this.id;
  }

  /**
   * @param id is the id to set
   */
  protected void setId(ID id) {

    this.id = id;
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
  @Version
  public Date getModificationTimestamp() {

    return this.modificationTimestamp;
  }

  /**
   * @param modificationTimestamp is the modificationTimestamp to set
   */
  protected void setModificationTimestamp(Date modificationTimestamp) {

    this.modificationTimestamp = modificationTimestamp;
  }

  /**
   * {@inheritDoc}
   */
  @Transient
  public boolean isPersistent() {

    return (getModificationTimestamp() != null);
  }

}
