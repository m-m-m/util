/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.version.base;

import java.util.Date;

import net.sf.mmm.util.exception.api.NlsNullPointerException;
import net.sf.mmm.util.version.api.DevelopmentPhase;
import net.sf.mmm.util.version.api.VersionIdentifier;
import net.sf.mmm.util.version.impl.VersionUtilImpl;

/**
 * This is the abstract base implementation of {@link VersionIdentifier}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public abstract class AbstractVersionIdentifier implements VersionIdentifier {

  /** UID for serialization. */
  private static final long serialVersionUID = 681216451154445513L;

  /** @see #hashCode() */
  private transient int hash;

  /** @see #toString() */
  private transient String stringRepresentation;

  /**
   * The constructor.
   *
   * @param stringRepresentation is the {@link String} returned by {@link #toString()}.
   */
  public AbstractVersionIdentifier(String stringRepresentation) {

    super();
    this.hash = 0;
    this.stringRepresentation = stringRepresentation;
  }

  @Override
  public int getVersionMajorSegment() {

    return getVersionSegment(VERSION_SEGMENT_INDEX_MAJOR);
  }

  @Override
  public int getVersionMinorSegment() {

    return getVersionSegment(VERSION_SEGMENT_INDEX_MINOR);
  }

  @Override
  public int getVersionMilliSegment() {

    return getVersionSegment(VERSION_SEGMENT_INDEX_MILLI);
  }

  @Override
  public int getVersionMicroSegment() {

    return getVersionSegment(VERSION_SEGMENT_INDEX_MICRO);
  }

  /**
   * @return the stringRepresentation
   */
  public String getStringRepresentation() {

    return this.stringRepresentation;
  }

  /**
   * @param stringRepresentation is the stringRepresentation to set
   */
  protected void setStringRepresentation(String stringRepresentation) {

    this.stringRepresentation = stringRepresentation;
  }

  /**
   * This method performs the part of {@link #compareTo(VersionIdentifier)} for the
   * {@link #getVersionSegment(int) version number}.
   *
   * @param otherVersion is the {@link VersionIdentifier} to compare to.
   * @return the result of comparison.
   */
  private int compareToVersionNumber(VersionIdentifier otherVersion) {

    // Example version: 1.2.3.4
    // Direct successors: 1.2.3.5 / 1.2.4[.0] / 1.3.[.0[.0]] / 2.[.0[.0[.0]]]
    // Direct predecessors: 1.2.3.3[.*]
    boolean equivalent = true;
    int result = 0;
    int maxSegmentCount = StrictMath.max(getVersionSegmentCount(), otherVersion.getVersionSegmentCount());
    for (int i = 0; i < maxSegmentCount; i++) {
      int segment = getVersionSegment(i);
      int otherSegment = otherVersion.getVersionSegment(i);
      if (segment != otherSegment) {
        int delta = segment - otherSegment;
        if (equivalent) {
          result = delta;
        } else {
          if (result == COMPARE_TO_STRICT_SUCCESSOR) {
            if (segment != 0) {
              result++;
              break;
            }
          } else if (result == COMPARE_TO_STRICT_PREDECESSOR) {
            if (otherSegment != 0) {
              result--;
              break;
            }
          }
        }
      }
    }
    return result;
  }

  /**
   * This method gets {@link #getPhaseNumber()} as {@code int}.
   *
   * @param version is the {@link VersionIdentifier}.
   * @return the {@link #getPhaseNumber()} as {@code int}, using {@code 0} for {@code null}.
   */
  private static int getPhaseNumberAsInt(VersionIdentifier version) {

    Integer phaseNumberInteger = version.getPhaseNumber();
    if (phaseNumberInteger == null) {
      return 0;
    } else {
      return phaseNumberInteger.intValue();
    }
  }

  /**
   * This method performs the part of {@link #compareTo(VersionIdentifier)} for the {@link #getTimestamp()
   * timestamp}.
   *
   * @param currentResult is the current result so far.
   * @param otherVersion is the {@link VersionIdentifier} to compare to.
   * @return the result of comparison.
   */
  private int compareToPhase(int currentResult, VersionIdentifier otherVersion) {

    if (currentResult == COMPARE_TO_INCOMPARABLE) {
      return COMPARE_TO_INCOMPARABLE;
    }
    int result = currentResult;
    DevelopmentPhase phase = getPhase();
    if (phase == null) {
      phase = DevelopmentPhase.RELEASE;
    }
    DevelopmentPhase otherPhase = otherVersion.getPhase();
    if (otherPhase == null) {
      otherPhase = DevelopmentPhase.RELEASE;
    }
    int deltaPhase = phase.compareTo(otherPhase);
    if (result == 0) {
      result = deltaPhase;
    } else if (result == COMPARE_TO_STRICT_SUCCESSOR) {
      // strict successors:
      // 1.2-alpha -> 1.2-beta
      // 1.2-RC1 -> 1.2-RC2
      // 1.2-release -> 1.2-update
      // 1.2-release -> 1.3-pre-alpha
      // 1.2-release -> 1.3-alpha
      // 1.2-release -> 1.3-beta
      // 1.2-release -> 1.3-release-candidate
      // 1.2-release -> 1.3-release
      // BUT NOT:
      // 1.2-release -> 1.3-update
      // 1.2-alpha1 -> 1.2-alpha3
      // 1.2-alpha -> 1.3-alpha (release expected, maybe 1.2 was never
      // published as release but however)

      if ((otherPhase != DevelopmentPhase.RELEASE) || (phase == DevelopmentPhase.UPDATE)) {
        result++;
      }
    } else if (result == COMPARE_TO_STRICT_PREDECESSOR) {
      // see above
      if ((otherPhase != DevelopmentPhase.UPDATE) || (phase == DevelopmentPhase.RELEASE)) {
        result--;
      }
    } else {
      if (((result < 0) && (deltaPhase < 0)) || ((result > 0) && (deltaPhase > 0))) {
        result = result + deltaPhase;
      }
    }
    int phaseNumber = getPhaseNumberAsInt(this);
    int otherPhaseNumber = getPhaseNumberAsInt(otherVersion);
    int deltaPhaseNumber = phaseNumber - otherPhaseNumber;
    if (result == 0) {
      result = deltaPhaseNumber;
    } else if (((result < 0) && (deltaPhaseNumber < 0)) || ((result > 0) && (deltaPhaseNumber > 0))) {
      result = result + deltaPhaseNumber;
    }
    return result;
  }

  /**
   * This method performs the part of {@link #compareTo(VersionIdentifier)} for the {@link #getTimestamp()
   * timestamp}.
   *
   * @param currentResult is the current result so far.
   * @param otherVersion is the {@link VersionIdentifier} to compare to.
   * @return the result of comparison.
   */
  private int compareToTimestamp(int currentResult, VersionIdentifier otherVersion) {

    return compareToLinear(currentResult, getTimestamp(), otherVersion.getTimestamp(), otherVersion);
  }

  /**
   * This method performs the part of {@link #compareTo(VersionIdentifier)} for linear and optional attributes
   * like {@link #getTimestamp()} or {@link #getRevision()}.
   *
   * @param <T> is the generic type of the {@link Comparable} value.
   * @param currentResult is the current result so far.
   * @param thisValue is the value of this {@link VersionIdentifier}.
   * @param otherValue is the value of the other {@link VersionIdentifier}.
   * @param otherVersion is the {@link VersionIdentifier} to compare to.
   * @return the result of comparison.
   */
  private <T extends Comparable<T>> int compareToLinear(int currentResult, T thisValue, T otherValue,
      VersionIdentifier otherVersion) {

    if (currentResult == COMPARE_TO_INCOMPARABLE) {
      return COMPARE_TO_INCOMPARABLE;
    }
    int result = currentResult;
    if (thisValue != null) {
      if (otherValue != null) {
        int diff = thisValue.compareTo(otherValue);
        if (result == 0) {
          if ((diff != 0) && (!isSnapshot()) && (!otherVersion.isSnapshot())) {
            return COMPARE_TO_INCOMPARABLE;
          }
          if (diff < 0) {
            result = COMPARE_TO_STRICT_PREDECESSOR;
          } else {
            result = COMPARE_TO_STRICT_SUCCESSOR;
          }
        } else if (result < 0) {
          // this.timestamp < otherVersion.timestamp
          if (diff > 0) {
            return COMPARE_TO_INCOMPARABLE;
          }
        } else {
          // this.timestamp > otherVersion.timestamp
          if (diff < 0) {
            return COMPARE_TO_INCOMPARABLE;
          }
        }
      }
    }
    return result;
  }

  /**
   * This method performs the part of {@link #compareTo(VersionIdentifier)} for the {@link #getRevision()
   * revision}.
   *
   * @param currentResult is the current result so far.
   * @param otherVersion is the {@link VersionIdentifier} to compare to.
   * @return the result of comparison.
   */
  private int compareToRevision(int currentResult, VersionIdentifier otherVersion) {

    return compareToLinear(currentResult, getRevision(), otherVersion.getRevision(), otherVersion);
  }

  /**
   * This method performs the part of {@link #compareTo(VersionIdentifier)} for the {@link #getLabel() label}.
   *
   * @param currentResult is the current result so far.
   * @param otherVersion is the {@link VersionIdentifier} to compare to.
   * @return the result of comparison.
   */
  private int compareToLabel(int currentResult, VersionIdentifier otherVersion) {

    if (currentResult == COMPARE_TO_INCOMPARABLE) {
      return COMPARE_TO_INCOMPARABLE;
    }
    int result = currentResult;
    if (result == 0) {
      String label = getLabel();
      if (label != null) {
        String otherLabel = otherVersion.getLabel();
        if (otherLabel != null) {
          if (!label.equalsIgnoreCase(otherLabel)) {
            // 2 versions identical except for label
            return COMPARE_TO_INCOMPARABLE;
          }
        }
      }
    }
    return result;
  }

  @Override
  public final int compareTo(VersionIdentifier otherVersion) {

    NlsNullPointerException.checkNotNull(VersionIdentifier.class, otherVersion);
    int result = compareToVersionNumber(otherVersion);
    result = compareToPhase(result, otherVersion);
    result = compareToRevision(result, otherVersion);
    result = compareToTimestamp(result, otherVersion);
    result = compareToLabel(result, otherVersion);
    return result;
  }

  @Override
  public final boolean equals(Object other) {

    if ((other == null) || (!(other instanceof AbstractVersionIdentifier))) {
      return false;
    }
    VersionIdentifier otherVersion = (VersionIdentifier) other;
    int segmentCount = getVersionSegmentCount();
    if (segmentCount != otherVersion.getVersionSegmentCount()) {
      return false;
    }
    for (int i = 0; i < segmentCount; i++) {
      int segment = getVersionSegment(i);
      int otherSegment = otherVersion.getVersionSegment(i);
      if (segment != otherSegment) {
        return false;
      }
    }
    Long revision = getRevision();
    if (revision == null) {
      if (otherVersion.getRevision() != null) {
        return false;
      }
    } else if (!revision.equals(otherVersion.getRevision())) {
      return false;
    }
    Date timestamp = getTimestamp();
    if (timestamp == null) {
      if (otherVersion.getTimestamp() != null) {
        return false;
      }
    } else if (!timestamp.equals(otherVersion.getTimestamp())) {
      return false;
    }
    if (getPhase() != otherVersion.getPhase()) {
      return false;
    }
    Integer phaseNumber = getPhaseNumber();
    if (phaseNumber == null) {
      if (otherVersion.getPhaseNumber() != null) {
        return false;
      }
    } else if (!phaseNumber.equals(otherVersion.getPhaseNumber())) {
      return false;
    }
    String label = getLabel();
    if (label == null) {
      if (otherVersion.getLabel() != null) {
        return false;
      }
    } else if (!label.equals(otherVersion.getLabel())) {
      return false;
    }
    return true;
  }

  @Override
  public final int hashCode() {

    if (this.hash != 0) {
      int hashCode = 0;
      int segmentCount = getVersionSegmentCount();
      for (int i = 0; i < segmentCount; i++) {
        hashCode = hashCode * 31 + getVersionSegment(i);
      }
      DevelopmentPhase phase = getPhase();
      if (phase != null) {
        hashCode = hashCode * 5 + phase.ordinal();
      }
      Integer phaseNumber = getPhaseNumber();
      if (phaseNumber != null) {
        hashCode = hashCode * 31 + phaseNumber.intValue();
      }
      Long revision = getRevision();
      if (revision != null) {
        hashCode = hashCode * 31 + revision.intValue();
      }
      Date timestamp = getTimestamp();
      if (timestamp != null) {
        hashCode = hashCode * 31 + timestamp.hashCode();
      }
      String label = getLabel();
      if (label != null) {
        hashCode = hashCode * 31 + label.hashCode();
      }
      if (hashCode == 0) {
        hashCode = -1;
      }
      this.hash = hashCode;
    }
    return this.hash;
  }

  @Override
  public String getValue() {

    if (this.stringRepresentation == null) {
      return VersionUtilImpl.getInstance().getDefaultFormatter().format(this);
    } else {
      return this.stringRepresentation;
    }
  }

  @Override
  public String toString() {

    return getValue();
  }

}
