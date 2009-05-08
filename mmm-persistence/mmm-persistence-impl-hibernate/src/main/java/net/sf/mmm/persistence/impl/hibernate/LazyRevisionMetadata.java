package net.sf.mmm.persistence.impl.hibernate;

import java.util.Date;

import org.hibernate.envers.AuditReader;

import net.sf.mmm.persistence.api.RevisionMetadata;

/**
 * This inner class is a lazy implementation of the {@link RevisionMetadata}
 * interface.
 */
public class LazyRevisionMetadata implements RevisionMetadata {

  /** The {@link AuditReader} used to fetch metadata. */
  private final AuditReader auditReader;

  /** @see #getRevision() */
  private final Number revision;

  /** @see #getDate() */
  private Date date;

  /**
   * The constructor.
   * 
   * @param auditReader is the {@link AuditReader} used to fetch metadata.
   * @param revision is the {@link #getRevision() revision}.
   */
  public LazyRevisionMetadata(AuditReader auditReader, Number revision) {

    super();
    this.auditReader = auditReader;
    this.revision = revision;
  }

  /**
   * {@inheritDoc}
   */
  public Object getCreator() {

    // TODO Auto-generated method stub
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public Date getDate() {

    if (this.date == null) {
      this.date = this.auditReader.getRevisionDate(getRevision());
    }
    return this.date;
  }

  /**
   * {@inheritDoc}
   */
  public Number getRevision() {

    return this.revision;
  }

}
