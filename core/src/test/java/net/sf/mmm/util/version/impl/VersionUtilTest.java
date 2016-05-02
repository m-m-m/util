/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.version.impl;

import java.text.SimpleDateFormat;

import net.sf.mmm.util.version.api.DevelopmentPhase;
import net.sf.mmm.util.version.api.VersionIdentifier;
import net.sf.mmm.util.version.api.VersionUtil;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the test-case for {@link VersionUtilImpl}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class VersionUtilTest extends Assert {

  /**
   * This method gets the {@link VersionUtil} instance.
   *
   * @return the {@link VersionUtil} instance.
   */
  public VersionUtil getVersionUtil() {

    return VersionUtilImpl.getInstance();
  }

  /**
   * This method {@link VersionUtil#createVersionIdentifier(String) parses} a {@link VersionIdentifier} given
   * by {@code versionString}. It also does some sanity checks.
   *
   * @param versionString is the {@link String} to parse.
   * @return the parsed {@link VersionIdentifier}.
   */
  protected VersionIdentifier parseVersion(String versionString) {

    VersionIdentifier result = getVersionUtil().createVersionIdentifier(versionString);
    assertEquals(versionString, result.getValue());
    return result;
  }

  /**
   * This method tests the {@link VersionUtil#createVersionIdentifier(String) parsing} of
   * {@link VersionIdentifier} with only a major version.
   */
  @Test
  public void testParseVersionMajor() {

    VersionIdentifier version = parseVersion("1");
    assertNotNull(version);
    assertEquals(1, version.getVersionSegmentCount());
    assertEquals(1, version.getVersionMajorSegment());
    assertEquals(0, version.getVersionMinorSegment());
    assertEquals(0, version.getVersionMilliSegment());
    assertEquals(0, version.getVersionMicroSegment());
    assertEquals(0, version.getVersionSegment(42));
    assertNull(version.getPhase());
    assertNull(version.getPhaseAlias());
    assertNull(version.getPhaseNumber());
    assertFalse(version.isSnapshot());
    assertNull(version.getLabel());
    assertNull(version.getRevision());
    assertNull(version.getTimestamp());

  }

  /**
   * This method tests the {@link VersionUtil#createVersionIdentifier(String) parsing} of
   * {@link VersionIdentifier} "SNAPSHOT".
   */
  @Test
  public void testParseVersionSnapshot() {

    VersionIdentifier version = parseVersion("SNAPSHOT");
    assertNotNull(version);
    assertEquals(0, version.getVersionSegmentCount());
    assertEquals(0, version.getVersionMajorSegment());
    assertEquals(0, version.getVersionMinorSegment());
    assertEquals(0, version.getVersionMilliSegment());
    assertEquals(0, version.getVersionMicroSegment());
    assertEquals(0, version.getVersionSegment(42));
    assertNull(version.getPhase());
    assertNull(version.getPhaseAlias());
    assertNull(version.getPhaseNumber());
    assertTrue(version.isSnapshot());
    assertNull(version.getLabel());
    assertNull(version.getRevision());
    assertNull(version.getTimestamp());
  }

  /**
   * This method tests the {@link VersionUtil#createVersionIdentifier(String) parsing} of
   * {@link VersionIdentifier} with 4 version segments.
   */
  @Test
  public void testParseVersion4Segments() {

    VersionIdentifier version = parseVersion("1.2.3.4");
    assertNotNull(version);
    assertEquals(4, version.getVersionSegmentCount());
    assertEquals(1, version.getVersionMajorSegment());
    assertEquals(2, version.getVersionMinorSegment());
    assertEquals(3, version.getVersionMilliSegment());
    assertEquals(4, version.getVersionMicroSegment());
    assertEquals(0, version.getVersionSegment(42));
    assertNull(version.getPhase());
    assertNull(version.getPhaseAlias());
    assertNull(version.getPhaseNumber());
    assertFalse(version.isSnapshot());
    assertNull(version.getLabel());
    assertNull(version.getRevision());
    assertNull(version.getTimestamp());
  }

  /**
   * This method tests the {@link VersionUtil#createVersionIdentifier(String) parsing} of
   * {@link VersionIdentifier} with 4 version segments and phase update number 5.
   */
  @Test
  public void testParseVersion4SegmentsUpdate5() {

    VersionIdentifier version = parseVersion("1.2.3.4u5");
    assertNotNull(version);
    assertEquals(4, version.getVersionSegmentCount());
    assertEquals(1, version.getVersionMajorSegment());
    assertEquals(2, version.getVersionMinorSegment());
    assertEquals(3, version.getVersionMilliSegment());
    assertEquals(4, version.getVersionMicroSegment());
    assertEquals(0, version.getVersionSegment(42));
    assertEquals(DevelopmentPhase.UPDATE, version.getPhase());
    assertEquals("u", version.getPhaseAlias());
    assertEquals(Integer.valueOf(5), version.getPhaseNumber());
    assertFalse(version.isSnapshot());
    assertNull(version.getLabel());
    assertNull(version.getRevision());
    assertNull(version.getTimestamp());
  }

  /**
   * This method tests the {@link VersionUtil#createVersionIdentifier(String) parsing} of
   * {@link VersionIdentifier} with 4 version segments, Snapshot and revision number.
   */
  @Test
  public void testParseVersion4SegmentsSnapshotRevision() {

    VersionIdentifier version = parseVersion("987.65.43.021-SNAPSHOT-rev654321");
    assertNotNull(version);
    assertEquals(4, version.getVersionSegmentCount());
    assertEquals(987, version.getVersionMajorSegment());
    assertEquals(65, version.getVersionMinorSegment());
    assertEquals(43, version.getVersionMilliSegment());
    assertEquals(21, version.getVersionMicroSegment());
    assertEquals(0, version.getVersionSegment(42));
    assertNull(version.getPhase());
    assertNull(version.getPhaseAlias());
    assertNull(version.getPhaseNumber());
    assertTrue(version.isSnapshot());
    assertNull(version.getLabel());
    assertEquals(Long.valueOf(654321), version.getRevision());
    assertNull(version.getTimestamp());
  }

  /**
   * This method tests the {@link VersionUtil#createVersionIdentifier(String) parsing} of
   * {@link VersionIdentifier} with 4 version segments, Snapshot, revision number and timestamp.
   *
   * @throws Exception if something went wrong.
   */
  @Test
  public void testParseVersion4SegmentsSnapshotRevisionTimestamp() throws Exception {

    VersionIdentifier version = parseVersion("1.0.0-RC12-snapshot-rev654321-19991231T235959Z");
    assertNotNull(version);
    assertEquals(3, version.getVersionSegmentCount());
    assertEquals(1, version.getVersionMajorSegment());
    assertEquals(0, version.getVersionMinorSegment());
    assertEquals(0, version.getVersionMilliSegment());
    assertEquals(0, version.getVersionMicroSegment());
    assertEquals(0, version.getVersionSegment(42));
    assertEquals(DevelopmentPhase.RELEASE_CANDIDATE, version.getPhase());
    assertEquals("RC", version.getPhaseAlias());
    assertEquals(Integer.valueOf(12), version.getPhaseNumber());
    assertTrue(version.isSnapshot());
    assertNull(version.getLabel());
    assertEquals(Long.valueOf(654321), version.getRevision());
    assertEquals(new SimpleDateFormat("yyyyMMdd-HHmmss zzz").parse("19991231-235959 UTC"), version.getTimestamp());
  }

  /**
   * This method tests the {@link VersionUtil#createVersionIdentifier(String) parsing} of
   * {@link VersionIdentifier} with 2 segments and phase release candidate number 5.
   */
  @Test
  public void testParseVersion2SegmentsRc5() {

    VersionIdentifier version = parseVersion("1.0-release-candidate-5");
    assertNotNull(version);
    assertEquals(2, version.getVersionSegmentCount());
    assertEquals(1, version.getVersionMajorSegment());
    assertEquals(0, version.getVersionMinorSegment());
    assertEquals(DevelopmentPhase.RELEASE_CANDIDATE, version.getPhase());
    assertEquals("release-candidate", version.getPhaseAlias());
    assertEquals(Integer.valueOf(5), version.getPhaseNumber());
    assertFalse(version.isSnapshot());
    assertNull(version.getLabel());
    assertNull(version.getRevision());
    assertNull(version.getTimestamp());
  }

  /**
   * This method tests the {@link VersionUtil#createVersionIdentifier(String) parsing} of
   * {@link VersionIdentifier}s from {@link String}s.
   */
  @Test
  public void testCompareTo() {

    VersionIdentifier version;
    VersionIdentifier otherVersion;

    version = parseVersion("1");
    otherVersion = parseVersion("1.0");
    assertEquals(0, version.compareTo(otherVersion));
  }
}
