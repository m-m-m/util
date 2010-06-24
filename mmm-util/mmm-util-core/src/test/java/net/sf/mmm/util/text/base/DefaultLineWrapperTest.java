/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.base;

import java.text.BreakIterator;
import java.util.Locale;

import net.sf.mmm.util.text.api.LineWrapper;
import net.sf.mmm.util.text.api.TextColumn;
import net.sf.mmm.util.text.api.TextColumnInfo;
import net.sf.mmm.util.text.api.TextTableInfo;

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

    DefaultLineWrapper wrapper = new DefaultLineWrapper();
    wrapper.initialize();
    return wrapper;
  }

  /**
   * Tests
   * {@link LineWrapper#wrap(Appendable, TextTableInfo, String, net.sf.mmm.util.text.api.TextColumnInfo)}
   * .
   */
  @Test
  public void testWordIterator() {

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
  }

  /**
   * Tests the method
   * {@link LineWrapper#wrap(Appendable, TextTableInfo, String, TextColumnInfo)}
   * .
   */
  @Test
  public void testSingleColumn() {

    LineWrapper wrapper = getLineWrapper();
    TextColumnInfo columnInfo = new TextColumnInfo();
    columnInfo.setLocale(Locale.US);
    columnInfo.setBorderLeft("left|");
    columnInfo.setBorderRight("|right");
    String indent = ">";
    columnInfo.setIndent(indent);
    TextTableInfo tableInfo = new TextTableInfo();
    int borderWidth = columnInfo.getBorderWidth();
    // ............000000000011111111112222222222333333333344444444445555555555
    // ............012345678901234567890123456789012345678901234567890123456789
    String text = "Hello world! This is wrapped text. It wraps perfectly well!\n" //
        + "Indentation is '" + indent + "' but no indentation after double\n\n" // 
        + "newline.";
    for (int i = 12; i > 0; i--) {
      tableInfo.setWidth(i + borderWidth);
      StringBuilder buffer = new StringBuilder();
      int lines = wrapper.wrap(buffer, tableInfo, text, columnInfo);
      System.out.println("width=" + i + ",line:" + lines);
      System.out.println(buffer.toString());
    }
  }

  /**
   * @param index is the column index
   * @return the {@link TextColumnInfo}.
   */
  protected TextColumnInfo createColumnInfo(int index) {

    TextColumnInfo columnInfo = new TextColumnInfo();
    columnInfo.setLocale(Locale.US);
    columnInfo.setBorderLeft(index + ">");
    columnInfo.setBorderRight("<|");
    return columnInfo;
  }

  /**
   * Tests the method
   * {@link LineWrapper#wrap(Appendable, TextTableInfo, TextColumn...)} .
   */
  @Test
  public void testMultiColumn() {

    LineWrapper wrapper = getLineWrapper();
    TextTableInfo tableInfo = new TextTableInfo();
    // ............000000000011111111112222222222333333333344444444445555555555
    // ............012345678901234567890123456789012345678901234567890123456789
    String text;

    TextColumn[] columns = new TextColumn[4];
    text = "Hello world! This is wrapped text. It wraps perfectly well!\n" //
        + "Indentation is not active at all.\n\nText after doulbe newline.";
    columns[0] = new TextColumn(text, createColumnInfo(0));
    text = "1\n2\n3\n4\n5\n6\n7\n8\n9\n10";
    columns[1] = new TextColumn(text, createColumnInfo(1));
    text = "This is a very long sentence without any newlines at all and with the "
        + "extraordinary long, strange and undefined and therefore un-hyphenate-able "
        + "word hushgigloobulathifagomasiwashutumiginatorgobonodile.";
    columns[2] = new TextColumn(text, createColumnInfo(2));
    text = "";
    columns[3] = new TextColumn(text, createColumnInfo(3));
    for (int i = 80; i > 60; i--) {
      tableInfo.setWidth(i);
      StringBuilder buffer = new StringBuilder();
      int lines = wrapper.wrap(buffer, tableInfo, columns);
      System.out.println("width=" + i + ",line:" + lines);
      System.out.println(buffer.toString());
    }
  }
}
