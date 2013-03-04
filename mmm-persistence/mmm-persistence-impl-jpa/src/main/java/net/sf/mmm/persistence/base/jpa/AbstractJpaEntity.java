/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.base.jpa;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;

import net.sf.mmm.persistence.base.AbstractGenericEntity;
import net.sf.mmm.util.entity.api.MutableRevisionedEntity;

/**
 * This is the abstract base-implementation of a {@link net.sf.mmm.util.entity.api.GenericEntity} using the
 * {@link javax.persistence JPA} (Java Persistence API).<br>
 * 
 * @param <ID> is the type of the {@link #getId() primary key}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@MappedSuperclass
public abstract class AbstractJpaEntity<ID> extends AbstractGenericEntity<ID> implements MutableRevisionedEntity<ID> {

  /** @see #getId() */
  private ID id;

  /** @see #getModificationCounter() */
  private int modificationCounter;

  /** @see #getRevision() */
  private Number revision;

  /**
   * The constructor.
   */
  public AbstractJpaEntity() {

    super();
    this.revision = LATEST_REVISION;
  }

  /**
   * <b>ATTENTION:</b><br/>
   * If your ID is not {@link GeneratedValue generated}, you need to override this method. In this case you
   * can also override {@link #setId(Object)} to change the visibility to public.<br/>
   * <br/>
   * 
   * {@inheritDoc}
   */
  @Override
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  public ID getId() {

    return this.id;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setId(ID id) {

    this.id = id;
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
   * {@inheritDoc}
   */
  @Override
  public void setModificationCounter(int modificationCounter) {

    this.modificationCounter = modificationCounter;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transient
  public Number getRevision() {

    return this.revision;
  }

  /**
   * This method sets the {@link #getRevision() revision}.
   * 
   * @param revision is the revision to set
   */
  @Override
  public void setRevision(Number revision) {

    this.revision = revision;
  }

}
