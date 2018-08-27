/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.scanner.base;

import org.junit.Test;

import net.sf.mmm.util.scanner.api.CharStreamScanner;

/**
 * Test of {@link CharSequenceScanner}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class CharSequenceScannerTest extends AbstractCharStreamScannerTest {

  @Override
  protected CharStreamScanner scanner(String string, boolean lookahead) {

    return new CharSequenceScanner(string);
  }

  /**
   * Tests {@link CharSequenceScanner#readUntil(char, boolean)}.
   */
  @Test
  public void testReadUntilWithSetCurrentIndex() {

    CharSequenceScanner scanner;
    // not escaped
    scanner = new CharSequenceScanner("string");
    assertThat(scanner.readUntil('n', false)).isEqualTo("stri");
    assertThat(scanner.getCurrentIndex()).isEqualTo(5);
    assertThat(scanner.next()).isEqualTo('g');

    // no EOF
    scanner.setCurrentIndex(0); // also test reset
    assertThat(scanner.readUntil('x', false)).isNull();
    assertThat(scanner.hasNext()).isFalse();

    // EOF
    scanner.setCurrentIndex(0); // also test reset
    assertThat(scanner.readUntil('x', true)).isEqualTo("string");
    assertThat(scanner.hasNext()).isFalse();

  }

  @Test
  public void testBasic() {

    String string = "string";
    CharSequenceScanner parser = new CharSequenceScanner(string);
    assertThat(parser.getCurrentIndex()).isEqualTo(0);
    assertThat(parser.getOriginalString()).isEqualTo(string);
    assertThat(parser.toString()).isEqualTo(string);
    assertThat(parser.getLength()).isEqualTo(string.length());
    assertThat(parser.hasNext()).isTrue();
    assertThat(parser.peek()).isEqualTo('s');
    assertThat(parser.getCurrentIndex()).isEqualTo(0);
    assertThat(parser.next()).isEqualTo('s');
    assertThat(parser.getCurrentIndex()).isEqualTo(1);
    assertThat(parser.toString()).isEqualTo("tring");
    assertThat(parser.read(Integer.MAX_VALUE)).isEqualTo("tring");
    assertThat(parser.toString()).isEmpty();
  }

  @Test
  public void testSubstring() {

    String string = "string";
    CharSequenceScanner parser = new CharSequenceScanner(string);
    for (int i = 0; i <= string.length(); i++) {
      for (int j = i; j <= string.length(); j++) {
        assertThat(parser.substring(i, j)).isEqualTo(string.substring(i, j));
      }
    }
  }

  @Test
  public void testGetReplaced() {

    String start = "hello ";
    String middle = "world";
    String end = " this is cool!";
    CharSequenceScanner parser = new CharSequenceScanner(start + middle + end);
    String middleNew = "universe";
    String replaced = parser.getReplaced(middleNew, start.length(), start.length() + middle.length());
    assertThat(replaced).isEqualTo(start + middleNew + end);
  }

}
