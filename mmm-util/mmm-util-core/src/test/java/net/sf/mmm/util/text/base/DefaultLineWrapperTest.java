/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.base;

import java.text.BreakIterator;
import java.util.Locale;

import net.sf.mmm.util.text.api.LineWrapper;
import net.sf.mmm.util.text.api.TextColumnInfo;

import org.junit.Test;

/**
 * This is the test-case for {@link DefaultLineWrapper}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class DefaultLineWrapperTest {

  /**
   * This method gets the {@link LineWrapper} instance to test.
   * 
   * @return the {@link LineWrapper}.
   */
  protected LineWrapper getLineWrapper() {

    return new DefaultLineWrapper();
  }

  /**
   * Tests
   * {@link LineWrapper#wrap(Appendable, String, net.sf.mmm.util.text.api.TextColumnInfo)}
   * .
   */
  @Test
  public void testSingleColumn() {

    BreakIterator breakIterator = BreakIterator.getWordInstance(new Locale("th"));
    String text2 = "เด็กที่มีปัญหาทางการเรียนรู้่บางคนสามารถเรียนร่วมกับเด็กปกติได้";
    breakIterator.setText(text2);
    int start = breakIterator.first();
    for (int end = breakIterator.next(); end != BreakIterator.DONE; start = end, end = breakIterator
        .next()) {
      String substring = text2.substring(start, end);
      if (substring.length() == 1) {
        System.out.println(((int) substring.charAt(0)));
      } else {
        System.out.println(substring);
      }
    }

    LineWrapper wrapper = getLineWrapper();
    StringBuilder buffer = new StringBuilder();
    TextColumnInfo columnInfo = new TextColumnInfo();
    columnInfo.setWidth(20);
    columnInfo.setBorderLeft("left|");
    columnInfo.setBorderRight("|right");
    // .............000000000011111111112222222222333333333344444444445555555555
    // .............012345678901234567890123456789012345678901234567890123456789
    String text = "Hello world! This is wrapped text. It wraps perfectly well!";
    int lines = wrapper.wrap(buffer, text, columnInfo);
    System.out.println(lines);
    System.out.println(buffer.toString());
  }
}
