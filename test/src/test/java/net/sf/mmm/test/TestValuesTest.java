/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.test;

import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * Test of {@link TestValues}.
 */
public class TestValuesTest extends Assertions implements TestValues {

  /** Test of {@link TestValues#UNICODE_DIGITS}. */
  @Test
  public void testUnicodeDigits() {

    for (char digit : UNICODE_DIGITS.toCharArray()) {
      assertThat(Character.isDigit(digit)).as("Character.isDigit(" + digit + "/0x0" + Integer.toHexString(digit) + ")").isTrue();
    }
  }

  /** Test of {@link TestValues#UNICODE_LETTERS}. */
  @Test
  public void testUnicodeLetters() {

    for (char letter : UNICODE_LETTERS.toCharArray()) {
      assertThat(Character.isLetter(letter)).as("Character.isLetter(" + letter + "/0x0" + Integer.toHexString(letter) + ")").isTrue();
    }
  }

  /** Test of {@link TestValues#UNICODE_NON_ALPHANUMERIC_SYMBOLS}. */
  @Test
  public void testUnicodeNonAlphanumericSymbols() {

    for (char symbol : UNICODE_NON_ALPHANUMERIC_SYMBOLS.toCharArray()) {
      assertThat(Character.isLetterOrDigit(symbol)).as("Character.isLetterOrDigit(" + symbol + "/0x0" + Integer.toHexString(symbol) + ")").isFalse();
    }
  }

}
