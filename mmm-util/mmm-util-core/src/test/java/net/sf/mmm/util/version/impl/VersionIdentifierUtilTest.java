/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.version.impl;

import java.text.SimpleDateFormat;

import net.sf.mmm.util.version.api.DevelopmentPhase;
import net.sf.mmm.util.version.api.VersionIdentifier;
import net.sf.mmm.util.version.api.VersionIdentifierUtil;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the test-case for {@link VersionIdentifierUtilImpl}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class VersionIdentifierUtilTest {

  /**
   * This method gets the {@link VersionIdentifierUtil} instance.
   * 
   * @return the {@link VersionIdentifierUtil} instance.
   */
  public VersionIdentifierUtil getVersionIdentifierUtil() {

    return VersionIdentifierUtilImpl.getInstance();
  }

  /**
   * This method {@link VersionIdentifierUtil#createVersionIdentifier(String)
   * parses} a {@link VersionIdentifier} given by <code>versionString</code>. It
   * also does some sanity checks.
   * 
   * @param versionString is the {@link String} to parse.
   * @return the parsed {@link VersionIdentifier}.
   */
  protected VersionIdentifier parseVersion(String versionString) {

    VersionIdentifier result = getVersionIdentifierUtil().createVersionIdentifier(versionString);

    return result;
  }

  /**
   * This method tests the
   * {@link VersionIdentifierUtil#createVersionIdentifier(String) parsing} of
   * {@link VersionIdentifier}s from {@link String}s.
   * 
   * @throws Exception if something goes wrong.
   */
  @Test
  public void testParse() throws Exception {

    VersionIdentifierUtil versionIdentifierUtil = getVersionIdentifierUtil();
    VersionIdentifier version;

    version = parseVersion("1");
    Assert.assertNotNull(version);
    Assert.assertEquals(1, version.getVersionSegmentCount());
    Assert.assertEquals(1, version.getVersionMajorSegment());
    Assert.assertEquals(0, version.getVersionMinorSegment());
    Assert.assertEquals(0, version.getVersionMilliSegment());
    Assert.assertEquals(0, version.getVersionMicroSegment());
    Assert.assertEquals(0, version.getVersionSegment(42));
    Assert.assertNull(version.getPhase());
    Assert.assertNull(version.getPhaseAlias());
    Assert.assertNull(version.getPhaseNumber());
    Assert.assertFalse(version.isSnapshot());
    Assert.assertNull(version.getLabel());
    Assert.assertNull(version.getRevision());
    Assert.assertNull(version.getTimestamp());

    version = parseVersion("SNAPSHOT");
    Assert.assertNotNull(version);
    Assert.assertEquals(0, version.getVersionSegmentCount());
    Assert.assertEquals(0, version.getVersionMajorSegment());
    Assert.assertEquals(0, version.getVersionMinorSegment());
    Assert.assertEquals(0, version.getVersionMilliSegment());
    Assert.assertEquals(0, version.getVersionMicroSegment());
    Assert.assertEquals(0, version.getVersionSegment(42));
    Assert.assertNull(version.getPhase());
    Assert.assertNull(version.getPhaseAlias());
    Assert.assertNull(version.getPhaseNumber());
    Assert.assertTrue(version.isSnapshot());
    Assert.assertNull(version.getLabel());
    Assert.assertNull(version.getRevision());
    Assert.assertNull(version.getTimestamp());

    version = parseVersion("1.2.3.4");
    Assert.assertNotNull(version);
    Assert.assertEquals(4, version.getVersionSegmentCount());
    Assert.assertEquals(1, version.getVersionMajorSegment());
    Assert.assertEquals(2, version.getVersionMinorSegment());
    Assert.assertEquals(3, version.getVersionMilliSegment());
    Assert.assertEquals(4, version.getVersionMicroSegment());
    Assert.assertEquals(0, version.getVersionSegment(42));
    Assert.assertNull(version.getPhase());
    Assert.assertNull(version.getPhaseAlias());
    Assert.assertNull(version.getPhaseNumber());
    Assert.assertFalse(version.isSnapshot());
    Assert.assertNull(version.getLabel());
    Assert.assertNull(version.getRevision());
    Assert.assertNull(version.getTimestamp());

    version = parseVersion("1.2.3.4u5");
    Assert.assertNotNull(version);
    Assert.assertEquals(4, version.getVersionSegmentCount());
    Assert.assertEquals(1, version.getVersionMajorSegment());
    Assert.assertEquals(2, version.getVersionMinorSegment());
    Assert.assertEquals(3, version.getVersionMilliSegment());
    Assert.assertEquals(4, version.getVersionMicroSegment());
    Assert.assertEquals(0, version.getVersionSegment(42));
    Assert.assertEquals(DevelopmentPhase.UPDATE, version.getPhase());
    Assert.assertEquals("u", version.getPhaseAlias());
    Assert.assertEquals(Integer.valueOf(5), version.getPhaseNumber());
    Assert.assertFalse(version.isSnapshot());
    Assert.assertNull(version.getLabel());
    Assert.assertNull(version.getRevision());
    Assert.assertNull(version.getTimestamp());

    version = parseVersion("987.65.43.021-SNAPSHOT-rev654321");
    Assert.assertNotNull(version);
    Assert.assertEquals(4, version.getVersionSegmentCount());
    Assert.assertEquals(987, version.getVersionMajorSegment());
    Assert.assertEquals(65, version.getVersionMinorSegment());
    Assert.assertEquals(43, version.getVersionMilliSegment());
    Assert.assertEquals(21, version.getVersionMicroSegment());
    Assert.assertEquals(0, version.getVersionSegment(42));
    Assert.assertNull(version.getPhase());
    Assert.assertNull(version.getPhaseAlias());
    Assert.assertNull(version.getPhaseNumber());
    Assert.assertTrue(version.isSnapshot());
    Assert.assertNull(version.getLabel());
    Assert.assertEquals(Long.valueOf(654321), version.getRevision());
    Assert.assertNull(version.getTimestamp());

    version = parseVersion("1.0.0-RC12-snapshot-rev654321-19991231T235959Z");
    Assert.assertNotNull(version);
    Assert.assertEquals(3, version.getVersionSegmentCount());
    Assert.assertEquals(1, version.getVersionMajorSegment());
    Assert.assertEquals(0, version.getVersionMinorSegment());
    Assert.assertEquals(0, version.getVersionMilliSegment());
    Assert.assertEquals(0, version.getVersionMicroSegment());
    Assert.assertEquals(0, version.getVersionSegment(42));
    Assert.assertEquals(DevelopmentPhase.RELEASE_CANDIDATE, version.getPhase());
    Assert.assertEquals("RC", version.getPhaseAlias());
    Assert.assertEquals(Integer.valueOf(12), version.getPhaseNumber());
    Assert.assertTrue(version.isSnapshot());
    Assert.assertNull(version.getLabel());
    Assert.assertEquals(Long.valueOf(654321), version.getRevision());
    Assert.assertEquals(new SimpleDateFormat("yyyyMMdd-HHmmss zzz").parse("19991231-235959 UTC"),
        version.getTimestamp());
  }

  /**
   * This method tests the
   * {@link VersionIdentifierUtil#createVersionIdentifier(String) parsing} of
   * {@link VersionIdentifier}s from {@link String}s.
   */
  @Test
  public void testCompareTo() {

    VersionIdentifierUtil versionIdentifierUtil = getVersionIdentifierUtil();
    VersionIdentifier version;
    VersionIdentifier otherVersion;

    version = versionIdentifierUtil.createVersionIdentifier("1");
    version = versionIdentifierUtil.createVersionIdentifier("1");

  }
}
