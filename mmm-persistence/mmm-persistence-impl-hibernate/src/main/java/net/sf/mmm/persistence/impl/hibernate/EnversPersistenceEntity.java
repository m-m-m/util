/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.hibernate;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import net.sf.mmm.persistence.api.RevisionedPersistenceEntity;
import net.sf.mmm.persistence.impl.jpa.JpaPersistenceEntity;

/**
 * This is the abstract base-implementation of a
 * {@link net.sf.mmm.persistence.api.PersistenceEntity} using using
 * {@link org.hibernate.envers Hibernate-Envers} to manage the revision-control.<br>
 * <b>ATTENTION:</b><br>
 * Your {@link RevisionedPersistenceEntity} implementations should NOT only
 * subclass this class but also need to be annotated with
 * {@link org.hibernate.envers.Audited}.
 * 
 * @param <ID> is the type of the {@link #getId() primary key}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@MappedSuperclass
public abstract class EnversPersistenceEntity<ID> extends JpaPersistenceEntity<ID> implements
    RevisionedPersistenceEntity<ID> {

  /** @see #getRevision() */
  private Number revision;

  /**
   * The constructor.
   */
  public EnversPersistenceEntity() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Transient
  public Number getRevision() {

    return this.revision;
  }

  /**
   * @param revision is the revision to set
   */
  void setRevision(Number revision) {

    this.revision = revision;
  }

}
