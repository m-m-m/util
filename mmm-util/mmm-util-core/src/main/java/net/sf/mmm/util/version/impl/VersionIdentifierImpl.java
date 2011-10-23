/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.version.impl;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import net.sf.mmm.util.nls.api.NlsIllegalArgumentException;
import net.sf.mmm.util.version.api.DevelopmentPhase;
import net.sf.mmm.util.version.base.AbstractVersionIdentifier;

/**
 * This is the abstract base implementation of
 * {@link net.sf.mmm.util.version.api.VersionIdentifier}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.2
 */
@XmlRootElement(name = "version-identifier")
@XmlAccessorType(XmlAccessType.FIELD)
public class VersionIdentifierImpl extends AbstractVersionIdentifier {

  /** @see #getVersionSegment(int) */
  @XmlElementWrapper(name = "version-number")
  @XmlElement(name = "segment")
  private int[] segments;

  /** @see #getPhase() */
  @XmlElement(name = "phase")
  private DevelopmentPhase phase;

  /** @see #getPhaseNumber() */
  @XmlElement(name = "phase-number")
  private Integer phaseNumber;

  /** @see #getPhaseAlias() */
  @XmlElement(name = "phase-alias")
  private String phaseAlias;

  /** @see #isSnapshot() */
  @XmlElement(name = "snapshot")
  private boolean snapshot;

  /** @see #getRevision() */
  @XmlElement(name = "revision")
  private Long revision;

  /** @see #getTimestamp() */
  @XmlElement(name = "timestamp")
  private Date timestamp;

  /** @see #getLabel() */
  @XmlElement(name = "label")
  private String label;

  /**
   * The non-arg constructor. Required for JAXB - not suitable for end-users.
   */
  public VersionIdentifierImpl() {

    // this is not nice!
    super(null);
  }

  /**
   * The constructor.
   * 
   * @param stringRepresentation - see {@link #toString()}.
   * @param label - see {@link #getLabel()}. May be <code>null</code>.
   * @param timestamp - see {@link #getTimestamp()}. May be <code>null</code>.
   * @param revision - see {@link #getRevision()}. May be <code>null</code>.
   * @param phase - see {@link #getPhase()}. May be <code>null</code>.
   * @param phaseAlias - see {@link #getPhase()}. May be <code>null</code> (only
   *        if <code>phase</code> is <code>null</code>).
   * @param phaseNumber - see {@link #getPhaseNumber()}. May be
   *        <code>null</code> (only if <code>phase</code> is <code>null</code>).
   * @param snapshot - see {@link #isSnapshot()}.
   * @param versionSegments - see {@link #getVersionSegment(int)}. At least one
   *        segment is required if no {@link #isSnapshot() snapshot}.
   */
  // CHECKSTYLE:OFF (only internal constructor)
  public VersionIdentifierImpl(String stringRepresentation, String label, Date timestamp,
      Long revision, DevelopmentPhase phase, String phaseAlias, Integer phaseNumber,
      boolean snapshot, int... versionSegments) {

    // CHECKSTYLE:ON
    super(stringRepresentation);
    this.label = label;
    this.timestamp = timestamp;
    this.revision = revision;
    this.phase = phase;
    this.phaseAlias = phaseAlias;
    this.phaseNumber = phaseNumber;
    this.snapshot = snapshot;
    this.segments = new int[versionSegments.length];
    System.arraycopy(versionSegments, 0, this.segments, 0, this.segments.length);
    validate();
  }

  /**
   * This method validates the consistency of this version identifier.
   */
  private void validate() {

    if ((this.segments.length < 0) && (!this.snapshot)) {
      throw new NlsIllegalArgumentException(Integer.valueOf(this.segments.length),
          "segments.length");
    }
    for (int i = 0; i < this.segments.length; i++) {
      if (this.segments[i] < 0) {
        throw new NlsIllegalArgumentException(Integer.valueOf(this.segments[i]), "segments[" + i
            + "]");
      }
    }
    if (this.phaseNumber != null) {
      if (this.phaseNumber.intValue() < 0) {
        throw new NlsIllegalArgumentException(this.phaseNumber, "phaseNumber");
      }
      if (this.phase == null) {
        throw new NlsIllegalArgumentException(this.phaseNumber, "phaseNumber (phase==null)");
      }
      if ((this.phase == DevelopmentPhase.RELEASE) && (this.phaseNumber.intValue() != 0)) {
        throw new NlsIllegalArgumentException(this.phaseNumber, "phaseNumber (phase==RELEASE)");
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void setStringRepresentation(String stringRepresentation) {

    // make visible in this package
    super.setStringRepresentation(stringRepresentation);
  }

  /**
   * {@inheritDoc}
   */
  public int getVersionSegment(int index) throws IndexOutOfBoundsException {

    if (index < 0) {
      throw new IndexOutOfBoundsException(Integer.toString(index));
    } else if (index < this.segments.length) {
      return this.segments[index];
    }
    return 0;
  }

  /**
   * {@inheritDoc}
   */
  public int getVersionSegmentCount() {

    return this.segments.length;
  }

  /**
   * {@inheritDoc}
   */
  public DevelopmentPhase getPhase() {

    return this.phase;
  }

  /**
   * {@inheritDoc}
   */
  public Integer getPhaseNumber() {

    return this.phaseNumber;
  }

  /**
   * {@inheritDoc}
   */
  public String getPhaseAlias() {

    return this.phaseAlias;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isSnapshot() {

    return this.snapshot;
  }

  /**
   * {@inheritDoc}
   */
  public Long getRevision() {

    return this.revision;
  }

  /**
   * {@inheritDoc}
   */
  public Date getTimestamp() {

    return this.timestamp;
  }

  /**
   * {@inheritDoc}
   */
  public String getLabel() {

    return this.label;
  }

}
