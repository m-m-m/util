/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.hibernate;

import java.util.Date;

import net.sf.mmm.persistence.api.PersistenceManager;
import net.sf.mmm.persistence.api.RevisionMetadata;

/**
 * This is a lazy implementation of the {@link RevisionMetadata} interface.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class LazyRevisionMetadata implements RevisionMetadata {

  /** The {@link PersistenceManager} used to read the metadata. */
  private final PersistenceManager persistenceManager;

  /** @see #getRevision() */
  private final Long revision;

  /** @see #getRevisionEntity() */
  private AdvancedRevisionEntity revisionEntity;

  /**
   * The constructor.
   *
   * @param persistenceManager is the {@link PersistenceManager} used to fetch metadata.
   * @param revision is the {@link #getRevision() revision}.
   */
  public LazyRevisionMetadata(PersistenceManager persistenceManager, Long revision) {

    super();
    this.persistenceManager = persistenceManager;
    this.revision = revision;
  }

  /**
   * @return the revisionEntity
   */
  public AdvancedRevisionEntity getRevisionEntity() {

    if (this.revisionEntity == null) {
      this.revisionEntity = this.persistenceManager.find(AdvancedRevisionEntity.class, this.revision);
      assert (this.revisionEntity != null);
    }
    return this.revisionEntity;
  }

  /**
   * {@inheritDoc}
   */
  public Object getCreator() {

    return getRevisionEntity().getUser();
  }

  /**
   * {@inheritDoc}
   */
  public Date getDate() {

    return getRevisionEntity().getDate();
  }

  /**
   * {@inheritDoc}
   */
  public Number getRevision() {

    return this.revision;
  }

}
