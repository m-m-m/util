/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import net.sf.mmm.util.entity.api.RevisionedEntity;

/**
 * This is the abstract base-implementation of a {@link net.sf.mmm.util.entity.api.RevisionedEntity}.<br>
 * <b>ATTENTION:</b><br>
 * JPA does not support <em>auditing</em> (tracking the revision history) of entities. The recommended
 * implementation is Hibernate-Envers ( <code>org.hibernate.envers</code>). In this case your entities need to
 * be annotated with <code>org.hibernate.envers.Audited</code> if you want auditing support.
 * 
 * @param <ID> is the type of the {@link #getId() primary key}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@MappedSuperclass
public abstract class JpaRevisionedEntity<ID> extends JpaEntity<ID> implements
    RevisionedEntity<ID> {

  /** @see #getRevision() */
  private Number revision;

  /**
   * The constructor.
   */
  public JpaRevisionedEntity() {

    super();
    this.revision = LATEST_REVISION;
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
  protected void setRevision(Number revision) {

    this.revision = revision;
  }

}
