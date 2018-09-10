/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.datatype.api;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import net.sf.mmm.util.lang.api.AbstractSimpleDatatypeBase;

/**
 * Test of {@link Password}.
 */
public class PasswordTest extends Assertions {

  /** Test {@link Password} with {@code null} value. */
  @Test
  public void testNull() {

    assertThat(Password.valueOf(null)).isNull();
    assertThat(Password.valueOf("")).isNull();
    assertThat(AbstractSimpleDatatypeBase.getValue(null)).isNull();
  }

  /** Test {@link Password} with empty value. */
  @Test
  public void testEmpty() {

    // passwords should actually not be empty but we do not want to prevent this (in development you might
    // have empty passwords e.g. for h2)
    String value = "";
    Password empty = new Password(value);
    assertThat(empty).isEqualTo(new Password(value));
    assertThat(empty.getValue()).isEqualTo(value);
    assertThat(AbstractSimpleDatatypeBase.getValue(empty)).isEqualTo(value);
    assertThat(empty.hashCode()).isEqualTo(value.hashCode());
    assertThat(empty.toString()).isEqualTo("********");
  }

  /** Test {@link Password} with an example value. */
  @Test
  public void testExample() {

    String value = "example";
    Password example = Password.valueOf(value);
    assertThat(example).isEqualTo(new Password(value));
    assertThat(example.getValue()).isEqualTo(value);
    assertThat(AbstractSimpleDatatypeBase.getValue(example)).isEqualTo(value);
    assertThat(example.hashCode()).isEqualTo(value.hashCode());
    assertThat(example.toString()).isEqualTo("********");
  }
}
