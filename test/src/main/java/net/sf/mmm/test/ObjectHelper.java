/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.test;

import org.assertj.core.api.Assertions;

/**
 * This is a utility-class for testing implementations of {@link #equals(Object)} and {@link #hashCode()}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ObjectHelper extends Assertions {

  /**
   * Generic checks of {@link #equals(Object)} and {@link #hashCode()}.
   *
   * @param <T> the generic type of the object to test.
   * @param x the first instance of the object to test.
   * @param y the second instance of the object to test.
   * @param equal - {@code true} if {@code x.equals(y)} is expected, {@code false} otherwise.
   */
  public static <T> void checkEqualsAndHashCode(T x, T y, boolean equal) {

    checkEquals(x, y, equal);
    checkHashCode(x, y);
  }

  /**
   * Generic checks of {@link #equals(Object)}.
   *
   * @param <T> the generic type of the object to test.
   * @param x the first instance of the object to test.
   * @param y the second instance of the object to test.
   * @param equal - {@code true} if {@code x.equals(y)} is expected, {@code false} otherwise.
   */
  public static <T> void checkEquals(T x, T y, boolean equal) {

    // equals has to be reflexive...
    assertThat(x).isNotNull().isNotEqualTo(null).isEqualTo(x);
    assertThat(y).isNotNull().isNotEqualTo(null).isEqualTo(y);
    // equals has to be symmetric...
    assertThat(x).isNotSameAs(y);
    assertThat(x.equals(y)).isEqualTo(y.equals(x)).isEqualTo(equal);
    // equals has to consider type
    assertThat(x).isNotEqualTo(new Object());
  }

  /**
   * Generic checks of {@link #hashCode()}.
   *
   * @param <T> the generic type of the object to test.
   * @param x the first instance of the object to test.
   * @param y the second instance of the object to test.
   */
  public static <T> void checkHashCode(T x, T y) {

    assertThat(x).isNotNull();
    assertThat(y).isNotNull();
    // hashCode() has to be self consistent
    assertThat(x.hashCode()).isEqualTo(x.hashCode());
    assertThat(y.hashCode()).isEqualTo(y.hashCode());
    // hashCode() has to be consistent with equals
    assertThat(x).isNotSameAs(y);
    if (x.equals(y)) {
      assertThat(x.hashCode()).isEqualTo(y.hashCode());
    } else {
      // this one is maybe a little dangerous as there can be a hash collision
      // However, users are requested to provide reasonable arguments to avoid this...
      assertThat(x.hashCode()).isNotEqualTo(y.hashCode());
    }
  }

}
