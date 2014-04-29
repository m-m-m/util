/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.version.api;

import java.util.Date;

import net.sf.mmm.util.lang.api.SimpleDatatype;

/**
 * A {@link VersionIdentifier} is a version identifying a particular state of an object under version control.
 * Such object can be an atomic asset like a single file but also a complex composition of many input
 * artifacts like an entire software product.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public interface VersionIdentifier extends Comparable<VersionIdentifier>, SimpleDatatype<String> {

  /** The default indicator for a snapshot version. */
  String SNAPSHOT = "SNAPSHOT";

  /**
   * This value indicates that a {@link VersionIdentifier} is a <em>strict successor</em> of another
   * {@link VersionIdentifier}. A strict successor of {@link VersionIdentifier} <code>V1</code> is a
   * {@link VersionIdentifier} <code>V2</code> that identifies a <em>potential</em> next release after
   * <code>V1</code>. <br/>
   * <b>ATTENTION:</b><br/>
   * An implementation can NOT predict the version history of a particular artifact. Therefore this is only an
   * indicator whether a {@link VersionIdentifier} is a legal successor.<br/>
   * E.g. for the version "1.2.3-RC4" strict successors are "1.2.3-RC5", "1.2.3-GA", "1.2.4", etc. while
   * "1.2.3-RC6" or "1.2.4-RC2" are NOT.<br/>
   *
   * @see #compareTo(VersionIdentifier)
   */
  int COMPARE_TO_STRICT_SUCCESSOR = 1;

  /** The inverse relation to {@link #COMPARE_TO_STRICT_SUCCESSOR}. */
  int COMPARE_TO_STRICT_PREDECESSOR = -1;

  /** @see #compareTo(VersionIdentifier) */
  int COMPARE_TO_INCOMPARABLE = Integer.MIN_VALUE;

  /**
   * The {@link #getVersionSegment(int) segment index} of the {@link #getVersionMajorSegment() major} version
   * number.
   */
  int VERSION_SEGMENT_INDEX_MAJOR = 0;

  /**
   * The {@link #getVersionSegment(int) segment index} of the {@link #getVersionMinorSegment() minor} version
   * number.
   */
  int VERSION_SEGMENT_INDEX_MINOR = 1;

  /**
   * The {@link #getVersionSegment(int) segment index} of the {@link #getVersionMilliSegment() milli} version
   * number.
   */
  int VERSION_SEGMENT_INDEX_MILLI = 2;

  /**
   * The {@link #getVersionSegment(int) segment index} of the {@link #getVersionMicroSegment() micro} version
   * number.
   */
  int VERSION_SEGMENT_INDEX_MICRO = 3;

  /**
   * The {@link #getVersionSegment(int) segment index} of the <code>nanno</code> version-number.
   */
  int VERSION_SEGMENT_INDEX_NANNO = 4;

  /**
   * The {@link #getVersionSegment(int) segment index} of the <code>pico</code> version number.
   */
  int VERSION_SEGMENT_INDEX_PICO = 5;

  /**
   * This method gets an arbitrary segment of the <em>version number</em>. The version number is a part of the
   * {@link VersionIdentifier} represented by the concatenation of {@link #getVersionSegmentCount() all}
   * {@link #getVersionSegment(int) segments} separated by a separator (typically the dot sign).<br/>
   * This method will return <code>0</code> if a segment is not {@link #getVersionSegmentCount() available}.
   * However it will not throw an {@link IndexOutOfBoundsException} if the <code>index</code> is non negative.<br/>
   * Here are some examples
   * <table border="1">
   * <tr>
   * <th>version number</th>
   * <th>{@link #getVersionSegmentCount()}</th>
   * <th>index</th>
   * <th>{@link #getVersionSegment(int) getSegment(index)}</th>
   * </tr>
   * <tr>
   * <td>1.2.3.42</td>
   * <td>4</td>
   * <td>0</td>
   * <td>1</td>
   * </tr>
   * <tr>
   * <td>1.2.3.42</td>
   * <td>4</td>
   * <td>3</td>
   * <td>42</td>
   * </tr>
   * <tr>
   * <td>1.2.3.42</td>
   * <td>4</td>
   * <td>4</td>
   * <td>0</td>
   * </tr>
   * <tr>
   * <td>4.0</td>
   * <td>2</td>
   * <td>0</td>
   * <td>4</td>
   * </tr>
   * <tr>
   * <td>4.0</td>
   * <td>2</td>
   * <td>1</td>
   * <td>0</td>
   * </tr>
   * <tr>
   * <td>4.0</td>
   * <td>2</td>
   * <td>2</td>
   * <td>0</td>
   * </tr>
   * <tr>
   * <td>4.0</td>
   * <td>2</td>
   * <td>3</td>
   * <td>0</td>
   * </tr>
   * </table>
   * E.g. if the version number is "1.2.3.42", then <code>{@link #getVersionSegment(int) getSegment}()</code>
   *
   * @see #VERSION_SEGMENT_INDEX_MAJOR
   * @see #VERSION_SEGMENT_INDEX_MINOR
   * @see #VERSION_SEGMENT_INDEX_MILLI
   * @see #VERSION_SEGMENT_INDEX_MICRO
   * @see #VERSION_SEGMENT_INDEX_NANNO
   * @see #VERSION_SEGMENT_INDEX_PICO
   *
   * @param index is the 0-based index of the requested segment. It shall not be negative.
   * @return the requested segment. If not available (in other words if <code>index</code> is greater or equal
   *         to {@link #getVersionSegmentCount()}) the value <code>0</code> is returned.
   * @throws IndexOutOfBoundsException if the given <code>index</code> is negative.
   */
  int getVersionSegment(int index) throws IndexOutOfBoundsException;

  /**
   * This method gets the number of {@link #getVersionSegment(int) segments} of this {@link VersionIdentifier}
   * .
   *
   * @return the number of {@link #getVersionSegment(int) segments}.
   */
  int getVersionSegmentCount();

  /**
   * This method gets the {@link #VERSION_SEGMENT_INDEX_MAJOR major} {@link #getVersionSegment(int) segment}
   * of this {@link VersionIdentifier}. This is the most significant {@link #getVersionSegment(int) segment}
   * of the version. It only gets updated on significant functional and/or structural changes of the versioned
   * artifact. An upgrade can cause appreciable manual effort and will often cause compatibility problems and
   * require migration of data or configuration.
   *
   * @see #VERSION_SEGMENT_INDEX_MAJOR
   *
   * @return the major version segment.
   */
  int getVersionMajorSegment();

  /**
   * This method gets the {@link #VERSION_SEGMENT_INDEX_MINOR minor} {@link #getVersionSegment(int) segment}
   * of this {@link VersionIdentifier}. This is the second most significant {@link #getVersionSegment(int)
   * segment} of the version. It only gets updated on functional and/or structural changes of the versioned
   * artifact. An upgrade should be supported but may cause compatibility problems and require migration of
   * data or configuration.
   *
   * @see #VERSION_SEGMENT_INDEX_MINOR
   *
   * @return the minor version segment.
   */
  int getVersionMinorSegment();

  /**
   * This method gets the {@link #VERSION_SEGMENT_INDEX_MILLI milli} {@link #getVersionSegment(int) segment}
   * of this {@link VersionIdentifier}. This is the third most significant {@link #getVersionSegment(int)
   * segment} of the version. It only gets updated on small functional and/or structural changes of the
   * versioned artifact. An upgrade should not cause problems but needs to be tested.
   *
   * @see #VERSION_SEGMENT_INDEX_MILLI
   *
   * @return the milli version segment.
   */
  int getVersionMilliSegment();

  /**
   * This method gets the {@link #VERSION_SEGMENT_INDEX_MICRO micro} {@link #getVersionSegment(int) segment}
   * of this {@link VersionIdentifier}. This is the fourth most significant {@link #getVersionSegment(int)
   * segment} of the version. It only gets updated on small internal changes (e.g. bugfixes) of the versioned
   * artifact. An upgrade should not cause problems.
   *
   * @see #VERSION_SEGMENT_INDEX_MICRO
   *
   * @return the milli version segment.
   */
  int getVersionMicroSegment();

  /**
   * This method gets the {@link DevelopmentPhase} of this {@link VersionIdentifier}.<br/>
   * <b>ATTENTION:</b><br/>
   * If the {@link #getPhase() phase} is undefined (<code>null</code>), {@link DevelopmentPhase#RELEASE} will
   * be assumed for {@link #compareTo(VersionIdentifier)}.
   *
   * @return the {@link DevelopmentPhase} or <code>null</code> if NOT defined.
   */
  DevelopmentPhase getPhase();

  /**
   * This method gets the string representation of the {@link #getPhase() phase} for this
   * {@link VersionIdentifier}. This is ideally
   * <code>{@link #getPhase()}.{@link DevelopmentPhase#toString() toString()}</code> but may also be any other
   * legal alias for the {@link #getPhase() phase}.<br/>
   * E.g. for {@link DevelopmentPhase#RELEASE} the {@link #getPhaseAlias() phase name} may also be "final",
   * "GA", "RTM", "REL", "gold", or "stable".
   *
   * @return the alias of the {@link #getPhase() phase}.
   */
  String getPhaseAlias();

  /**
   * This method gets the subsequent number of this {@link VersionIdentifier} within a particular
   * {@link #getPhase() phase}. It is typically <code>null</code> and will then be omitted in the
   * {@link #toString() string-representation}. However {@link DevelopmentPhase#RELEASE_CANDIDATE
   * release-candidates} and {@link DevelopmentPhase#UPDATE updates} are often numbered (e.g. like "u1", "u2",
   * ... in Java-releases or "RC1"/"SR1", ... in Eclipse-releases).<br/>
   * If {@link #getPhase()} returns <code>null</code> also this method should return <code>null</code>. Also
   * if {@link #getPhase()} is {@link DevelopmentPhase#RELEASE} this method should return <code>null</code>
   * (there is just one official release - after that there are {@link DevelopmentPhase#UPDATE updates}).<br/>
   * <b>ATTENTION:</b><br/>
   * If the {@link #getPhaseNumber() phase} is undefined (<code>null</code>), <code>0</code> will be assumed
   * for {@link #compareTo(VersionIdentifier)}.
   *
   * @return the phase number or <code>null</code> if NOT defined.
   */
  Integer getPhaseNumber();

  /**
   * This method determines if this {@link VersionIdentifier} indicates a snapshot release. A snapshot is an
   * informal pre-release. Multiple snapshot releases with different content can be published for snapshot
   * versions while this is strictly prohibited for non-snapshot versions. A snapshot version is a
   * {@link #COMPARE_TO_STRICT_PREDECESSOR strict predecessor} of the same {@link VersionIdentifier} without
   * the snapshot.<br/>
   * E.g. multiple "1.0.0-SNAPSHOT" versions can be published for testers. After all tests pass, the version
   * "1.0.0" gets released. This approach is orthogonal to the {@link #getPhase() phase} and can be combined
   * (e.g. "1.0.0-beta2-SNAPSHOT").
   *
   * @return <code>true</code> if this {@link VersionIdentifier} represents a snapshot release,
   *         <code>false</code> in case of an official release.
   */
  boolean isSnapshot();

  /**
   * This method gets the revision of the {@link VersionIdentifier}. It typically has a technical nature and
   * might be derived from the underlying version control system.
   *
   * @return the revision or <code>null</code> if NOT defined.
   */
  Long getRevision();

  /**
   * This method gets the timestamp of the {@link VersionIdentifier}. It indicates the point in time when this
   * {@link VersionIdentifier} was assigned to the associated object. The timestamp may also called
   * <em>release date</em>. The value is optional but is best practice to assign a timestamp to a
   * {@link VersionIdentifier}.
   *
   * @return the timestamp or <code>null</code> if NOT defined.
   */
  Date getTimestamp();

  /**
   * This method gets the optional label of the {@link VersionIdentifier}. A label is a non-technical
   * identifier also called code name (e.g. "Tiger" or "Dolphin").
   *
   * @return the label or <code>null</code> if NOT defined.
   */
  String getLabel();

  /**
   * This method determines a (non-linear) distance from this version to the given <code>otherVersion</code>.
   * It will return
   * <ul>
   * <li><code>{@link #COMPARE_TO_INCOMPARABLE}</code> if both versions are incompatible to each other (e.g.
   * ).</li>
   * <li><code>0</code> if both versions are historically equivalent (but may NOT be {@link #equals(Object)
   * equal})</li>
   * <li>a negative value if this version is a predecessor of <code>otherVersion</code>. The value -1
   * indicates a <em>{@link #COMPARE_TO_STRICT_PREDECESSOR strict predecessor}</em>.</li>
   * <li>a positive value if this version is a successor of <code>otherVersion</code>. The value +1 indicates
   * a <em>{@link #COMPARE_TO_STRICT_SUCCESSOR strict successor}</em>.</li>
   * </ul>
   * The following table gives some examples:
   * <table border="1">
   * <tr>
   * <th>version</th>
   * <th>otherVersion</th>
   * <th>
   * <code>version.{@link #compareTo(VersionIdentifier) compareTo}(otherVersion)</code></th>
   * </tr>
   * <tr>
   * <td>1.0.0-SNAPSHOT</td>
   * <td>1.0.0-SNAPSHOT</td>
   * <td>0</td>
   * </tr>
   * <tr>
   * <td>1.0.0</td>
   * <td>1.0.0-SNAPSHOT</td>
   * <td>1 ({@link #COMPARE_TO_STRICT_SUCCESSOR})</td>
   * </tr>
   * <tr>
   * <td>1.0.1</td>
   * <td>1.0.0</td>
   * <td>1 ({@link #COMPARE_TO_STRICT_SUCCESSOR})</td>
   * </tr>
   * <tr>
   * <td>1.0.1</td>
   * <td>1.0.0-SNAPSHOT</td>
   * <td>&gt;1</td>
   * </tr>
   * <tr>
   * <td>1.6.0u23</td>
   * <td>1.6.0u24</td>
   * <td>-1 ({@link #COMPARE_TO_STRICT_PREDECESSOR})</td>
   * </tr>
   * <tr>
   * <td>3.7-SR1-indigo</td>
   * <td>4.1.1-201109121510</td>
   * <td>&lt;-1</td>
   * </tr>
   * <tr>
   * <td>3.7.1-helios</td>
   * <td>3.7.1-indigo</td>
   * <td>{@link #COMPARE_TO_INCOMPARABLE}</td>
   * </tr>
   * <tr>
   * <td>1.0-20000101T000000Z</td>
   * <td>1.0-19991231T235959Z</td>
   * <td>{@link #COMPARE_TO_INCOMPARABLE}</td>
   * </tr>
   * <tr>
   * <td>1.0-20000101T000000Z</td>
   * <td>1.1-19991231T235959Z</td>
   * <td>{@link #COMPARE_TO_INCOMPARABLE}</td>
   * </tr>
   * </table>
   *
   * {@inheritDoc}
   */
  int compareTo(VersionIdentifier otherVersion);

  /**
   * This method gets a unique string representation of this {@link VersionIdentifier}.
   *
   * {@inheritDoc}
   */
  @Override
  String toString();

}
