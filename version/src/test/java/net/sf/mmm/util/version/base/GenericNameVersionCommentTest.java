/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.version.base;

import org.junit.Test;

import net.sf.mmm.test.ObjectHelper;

/**
 * The test of {@link GenericNameVersionComment}.
 *
 * @author hohwille
 */
public class GenericNameVersionCommentTest extends AbstractNameVersionTest {

  // given

  private static final GenericNameVersionComment MOZILLA = new GenericNameVersionComment(MOZILLA_NAME, MOZILLA_VERSION, MOZILLA_COMMENT);

  private static final GenericNameVersionComment WEBKIT = new GenericNameVersionComment(WEBKIT_NAME, WEBKIT_VERSION, WEBKIT_COMMENT);

  private static final GenericNameVersionComment VERSION = new GenericNameVersionComment(VERSION_NAME, VERSION_VERSION, VERSION_COMMENT);

  private static final GenericNameVersionComment SAFARI = new GenericNameVersionComment(SAFARI_NAME, SAFARI_VERSION, SAFARI_COMMENT);

  private static final GenericNameVersionComment DEBIAN = new GenericNameVersionComment(DEBIAN_NAME, DEBIAN_VERSION, DEBIAN_COMMENT);

  private static final GenericNameVersionComment EPIPHANY = new GenericNameVersionComment(EPIPHANY_NAME, EPIPHANY_VERSION, EPIPHANY_COMMENT);

  /** Test of {@link GenericNameVersionComment#equals(Object)} and {@link GenericNameVersionComment#hashCode()}. */
  @Test
  public void testEqualsAndHashCode() {

    ObjectHelper.checkEqualsAndHashCode(MOZILLA, DEBIAN, false);
    ObjectHelper.checkEqualsAndHashCode(MOZILLA,
        new GenericNameVersionComment(new String(MOZILLA_NAME), new String(MOZILLA_VERSION), new String(MOZILLA_COMMENT)), true);
  }

  /** Test of {@link GenericNameVersionComment#ofAll(CharSequence)}. */
  @Test
  public void testToString() {

    assertThat(MOZILLA.toString()).isEqualTo(MOZILLA_NVC);
    assertThat(WEBKIT.toString()).isEqualTo(WEBKIT_NVC);
    assertThat(VERSION.toString()).isEqualTo(VERSION_NV);
    assertThat(SAFARI.toString()).isEqualTo(SAFARI_NV);
    assertThat(DEBIAN.toString()).isEqualTo(DEBIAN_NVC);
    assertThat(EPIPHANY.toString()).isEqualTo(EPIPHANY_NV);
  }

  /** Test of {@link GenericNameVersionComment#of(CharSequence)}. */
  @Test
  public void testOf() {

    assertThat(GenericNameVersionComment.of(MOZILLA_NVC)).isEqualTo(MOZILLA);
    assertThat(GenericNameVersionComment.of(WEBKIT_NVC)).isEqualTo(WEBKIT);
    assertThat(GenericNameVersionComment.of(VERSION_NV)).isEqualTo(VERSION);
    assertThat(GenericNameVersionComment.of(SAFARI_NV)).isEqualTo(SAFARI);
    assertThat(GenericNameVersionComment.of(DEBIAN_NVC)).isEqualTo(DEBIAN);
    assertThat(GenericNameVersionComment.of(EPIPHANY_NV)).isEqualTo(EPIPHANY);
    assertThat(GenericNameVersionComment.of("")).isNull();
    assertThat(GenericNameVersionComment.of("hello world")).isNull();
  }

  /** Test of {@link GenericNameVersionComment#ofFirst(CharSequence)}. */
  @Test
  public void testOfFirst() {

    assertThat(GenericNameVersionComment.ofFirst(MOZILLA_NVC)).isEqualTo(MOZILLA);
    assertThat(GenericNameVersionComment.ofFirst(WEBKIT_NVC)).isEqualTo(WEBKIT);
    assertThat(GenericNameVersionComment.ofFirst(VERSION_NV)).isEqualTo(VERSION);
    assertThat(GenericNameVersionComment.ofFirst(SAFARI_NV)).isEqualTo(SAFARI);
    assertThat(GenericNameVersionComment.ofFirst(DEBIAN_NVC)).isEqualTo(DEBIAN);
    assertThat(GenericNameVersionComment.ofFirst(EPIPHANY_NV)).isEqualTo(EPIPHANY);
    assertThat(GenericNameVersionComment.ofFirst(ALL)).isEqualTo(MOZILLA);
    assertThat(GenericNameVersionComment.ofFirst(DEBIAN_NVC + " " + ALL)).isEqualTo(DEBIAN);
    assertThat(GenericNameVersionComment.ofFirst("")).isNull();
    assertThat(GenericNameVersionComment.ofFirst("hello world")).isNull();
  }

  /** Test of {@link GenericNameVersionComment#ofAll(CharSequence)}. */
  @Test
  public void testOfAll() {

    assertThat(GenericNameVersionComment.ofAll(ALL)).containsExactly(MOZILLA, WEBKIT, VERSION, SAFARI, DEBIAN, EPIPHANY);
    assertThat(GenericNameVersionComment.ofAll("")).isEmpty();
    assertThat(GenericNameVersionComment.ofAll("hello world (this is not a version name comment)")).isEmpty();
  }

}
