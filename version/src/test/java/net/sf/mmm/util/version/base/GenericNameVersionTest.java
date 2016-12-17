/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.version.base;

import java.util.List;

import org.junit.Test;

import net.sf.mmm.test.ObjectHelper;

/**
 * The test of {@link GenericNameVersionComment}.
 *
 * @author hohwille
 */
public class GenericNameVersionTest extends AbstractNameVersionTest {

  // given

  private static final GenericNameVersion MOZILLA = new GenericNameVersion(MOZILLA_NAME, MOZILLA_VERSION);

  private static final GenericNameVersion WEBKIT = new GenericNameVersion(WEBKIT_NAME, WEBKIT_VERSION);

  private static final GenericNameVersion VERSION = new GenericNameVersion(VERSION_NAME, VERSION_VERSION);

  private static final GenericNameVersion SAFARI = new GenericNameVersion(SAFARI_NAME, SAFARI_VERSION);

  private static final GenericNameVersion DEBIAN = new GenericNameVersion(DEBIAN_NAME, DEBIAN_VERSION);

  private static final GenericNameVersion EPIPHANY = new GenericNameVersion(EPIPHANY_NAME, EPIPHANY_VERSION);

  /** Test of {@link GenericNameVersionComment#equals(Object)} and {@link GenericNameVersionComment#hashCode()}. */
  @Test
  public void testEqualsAndHashCode() {

    ObjectHelper.checkEqualsAndHashCode(MOZILLA, DEBIAN, false);
    ObjectHelper.checkEqualsAndHashCode(MOZILLA, new GenericNameVersion(new String(MOZILLA_NAME), new String(MOZILLA_VERSION)), true);
  }

  /** Test of {@link GenericNameVersionComment#ofAll(CharSequence)}. */
  @Test
  public void testToString() {

    assertThat(MOZILLA.toString()).isEqualTo(MOZILLA_NV);
    assertThat(WEBKIT.toString()).isEqualTo(WEBKIT_NV);
    assertThat(VERSION.toString()).isEqualTo(VERSION_NV);
    assertThat(SAFARI.toString()).isEqualTo(SAFARI_NV);
    assertThat(DEBIAN.toString()).isEqualTo(DEBIAN_NV);
    assertThat(EPIPHANY.toString()).isEqualTo(EPIPHANY_NV);
  }

  /** Test of {@link GenericNameVersionComment#of(CharSequence)}. */
  @Test
  public void testOf() {

    assertThat(GenericNameVersion.of(MOZILLA_NV)).isEqualTo(MOZILLA);
    assertThat(GenericNameVersion.of(WEBKIT_NV)).isEqualTo(WEBKIT);
    assertThat(GenericNameVersion.of(VERSION_NV)).isEqualTo(VERSION);
    assertThat(GenericNameVersion.of(SAFARI_NV)).isEqualTo(SAFARI);
    assertThat(GenericNameVersion.of(DEBIAN_NV)).isEqualTo(DEBIAN);
    assertThat(GenericNameVersion.of(EPIPHANY_NV)).isEqualTo(EPIPHANY);
    assertThat(GenericNameVersion.of("")).isNull();
    assertThat(GenericNameVersion.of("hello world")).isNull();
  }

  /** Test of {@link GenericNameVersionComment#ofFirst(CharSequence)}. */
  @Test
  public void testOfFirst() {

    assertThat(GenericNameVersion.ofFirst(MOZILLA_NVC)).isEqualTo(MOZILLA);
    assertThat(GenericNameVersion.ofFirst(WEBKIT_NVC)).isEqualTo(WEBKIT);
    assertThat(GenericNameVersion.ofFirst(VERSION_NV)).isEqualTo(VERSION);
    assertThat(GenericNameVersion.ofFirst(SAFARI_NV)).isEqualTo(SAFARI);
    assertThat(GenericNameVersion.ofFirst(DEBIAN_NVC)).isEqualTo(DEBIAN);
    assertThat(GenericNameVersion.ofFirst(EPIPHANY_NV)).isEqualTo(EPIPHANY);
    assertThat(GenericNameVersion.ofFirst(ALL)).isEqualTo(MOZILLA);
    assertThat(GenericNameVersion.ofFirst(DEBIAN_NVC + " " + ALL)).isEqualTo(DEBIAN);
    assertThat(GenericNameVersion.ofFirst("")).isNull();
    assertThat(GenericNameVersion.ofFirst("hello world")).isNull();
  }

  /** Test of {@link GenericNameVersionComment#ofAll(CharSequence)}. */
  @Test
  public void testOfAll() {

    List<GenericNameVersion> list = (List<GenericNameVersion>) GenericNameVersion.ofAll(ALL);
    assertThat(list).containsExactly(MOZILLA, WEBKIT, VERSION, SAFARI, DEBIAN, EPIPHANY);
    assertThat(GenericNameVersion.ofAll("")).isEmpty();
    assertThat(GenericNameVersion.ofAll("hello world (this is not a version name comment)")).isEmpty();
  }

}
