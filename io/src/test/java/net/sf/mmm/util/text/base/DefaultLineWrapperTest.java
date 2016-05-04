/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.base;

import java.text.BreakIterator;
import java.util.Locale;

import org.assertj.core.api.Assertions;
import org.junit.Ignore;
import org.junit.Test;

import net.sf.mmm.util.exception.api.IllegalCaseException;
import net.sf.mmm.util.scanner.base.CharSequenceScanner;
import net.sf.mmm.util.text.api.LineWrapper;
import net.sf.mmm.util.text.api.TextColumn;
import net.sf.mmm.util.text.api.TextColumnInfo;
import net.sf.mmm.util.text.api.TextColumnInfo.IndentationMode;
import net.sf.mmm.util.text.api.TextTableInfo;

/**
 * This is the test-case for {@link DefaultLineWrapper}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class DefaultLineWrapperTest extends Assertions {

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
   * Some experiment with Thai-script.
   */
  @Ignore
  public void testWordIterator() {

    BreakIterator breakIterator = BreakIterator.getWordInstance(new Locale("th"));
    String text = "เด็กที่มีปัญหาทางการเรียนรู้่บางคนสามารถเรียนร่วมกับเด็กปกติได้";
    breakIterator.setText(text);
    int start = breakIterator.first();
    for (int end = breakIterator.next(); end != BreakIterator.DONE; start = end, end = breakIterator.next()) {
      String substring = text.substring(start, end);
      if (substring.length() == 1) {
        System.out.println(((int) substring.charAt(0)));
      } else {
        System.out.println(substring);
      }
    }
  }

  /**
   * Tests the method {@link LineWrapper#wrap(Appendable, TextTableInfo, String, TextColumnInfo)} .
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
    for (int i = 30; i > 0; i--) {
      tableInfo.setWidth(i + borderWidth);
      StringBuilder buffer = new StringBuilder();
      int lines = wrapper.wrap(buffer, tableInfo, text, columnInfo);
      if (lines > 1) {
        double lineRation = (text.length() / i);
        double ratio = lineRation / lines;
        assertThat(ratio).as("ratio").isGreaterThanOrEqualTo(0.5d);
      }
      // System.out.println("width=" + i + ",line:" + lines);
      // System.out.println(buffer.toString());
      checkWrappedColumnText(buffer, tableInfo, new TextColumn(text, columnInfo));
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
   * This method counts the number of newlines by scanning the given text in reverse order starting at the given
   * {@code index}.
   *
   * @param text is the text to scan for newlines.
   * @param index is the index where to start from in reverse direction (to the start of the text).
   * @return the number of newlines.
   */
  private int countReverseNewlines(CharSequence text, int index) {

    char oldNewline = 0;
    int i = index;
    int newlines = 0;
    while (i > 0) {
      char c = text.charAt(i);
      if ((c == '\n') || (c == '\r')) {
        if (oldNewline == 0) {
          oldNewline = c;
          newlines++;
        } else if (oldNewline == c) {
          newlines++;
        } else {
          oldNewline = 0;
        }
        i--;
      } else {
        break;
      }
    }
    return newlines;
  }

  /**
   * This method verifies the result of a {@link LineWrapper} according to the input that was given.
   *
   * @param wrappedText is the result of {@link LineWrapper#wrap(Appendable, TextTableInfo, TextColumn...)
   *        line-wrapping} that should be checked.
   * @param tableInfo is the {@link TextTableInfo} that was given to the {@link LineWrapper}.
   * @param columns are the {@link TextColumn}s that have been given to the {@link LineWrapper}.
   * @return the number of lines produced by the {@link LineWrapper}. May be used to check that an expected maximum of
   *         lines is not exceeded.
   */
  protected int checkWrappedColumnText(CharSequence wrappedText, TextTableInfo tableInfo, TextColumn... columns) {

    int lineCount = 0;
    ColumnState[] columnStates = new ColumnState[columns.length];
    int currentWidth = 0;
    int tableWidth = tableInfo.getWidth();
    for (int i = 0; i < columnStates.length; i++) {
      columnStates[i] = new ColumnState();
      TextColumnInfo columnInfo = columns[i].getColumnInfo();
      columnStates[i].indent = false;
      columnStates[i].textIndex = 0;
      columnStates[i].width = columnInfo.getWidth();
      if (TextColumnInfo.WIDTH_AUTO_ADJUST == columnStates[i].width) {
        if (columns.length == 1) {
          assertThat(tableWidth).as("tableWidth").isNotEqualTo(TextColumnInfo.WIDTH_AUTO_ADJUST);
          columnStates[i].width = tableWidth - columnInfo.getBorderWidth();
        } else {
          // Assert.fail("column-width-auto-adjust not reimplemented here!");
          // this is just some magic hack to determine the column width
          int indexBorderRight = wrappedText.subSequence(currentWidth, tableWidth).toString()
              .indexOf(columnInfo.getBorderRight());
          assertThat(indexBorderRight).as("indexBorderRight").isGreaterThan(0);
          columnStates[i].width = indexBorderRight - columnInfo.getBorderLeft().length();
        }
      }
      currentWidth = currentWidth + columnStates[i].width + columnInfo.getBorderWidth();
    }
    CharSequenceScanner scanner = new CharSequenceScanner(wrappedText);
    while (scanner.hasNext()) {
      int startIndex = scanner.getCurrentIndex();
      for (int columnIndex = 0; columnIndex < columns.length; columnIndex++) {
        TextColumnInfo columnInfo = columns[columnIndex].getColumnInfo();
        ColumnState state = columnStates[columnIndex];
        // left border
        assertThat(scanner.expect(columnInfo.getBorderLeft(), false)).isTrue();

        // text-context
        int width = state.width;
        if ((state.indent) && (width >= TextColumnInfo.MINIMUM_WIDTH_FOR_INDENT_AND_HYPHEN)) {
          String indent = columnInfo.getIndent();
          assertThat(scanner.expect(indent, false)).isTrue();
          width = width - indent.length();
        }
        String columnContent = scanner.read(width);
        int contentLength = columnContent.length();
        assertThat(contentLength).isEqualTo(width);
        String columnText = columns[columnIndex].getText();
        int textLength = columnText.length();
        // start with columnContent...
        // this code assumes that indent and/or filler can not clash with
        // content of the column-text
        int cellStartIndex = 0;
        // indent, TODO: indent is only allowed according to specific state of
        // the column that should be checked here...
        // filler
        char filler = columnInfo.getFiller();
        // System.out.println(columnContent + "|");
        while ((cellStartIndex < contentLength) && (columnContent.charAt(cellStartIndex) == filler)) {
          cellStartIndex++;
        }
        int cellEndIndex = contentLength - 1;
        while ((cellEndIndex >= 0) && (columnContent.charAt(cellEndIndex) == filler)) {
          cellEndIndex--;
        }
        // Assert.assertTrue(textIndices[columnIndex] + contentLength <=
        // textLength);
        for (int i = cellStartIndex; i <= cellEndIndex; i++) {
          char c = columnContent.charAt(i);
          char textChar = columnText.charAt(state.textIndex);
          if (textChar != c) {
            if ((c == '-') && (i == cellEndIndex)) {
              // text was hyphenated...
            } else {
              assertThat(c).as(columnContent).isEqualTo(textChar);
            }
          } else {
            state.textIndex++;
          }
        }
        // omit whitespaces for wrap position...
        // TODO: respect columnInfo.getOmitChars() and columnInfo.getWrapChars()
        char newline = 0;
        while (state.textIndex < textLength) {
          char c = columnText.charAt(state.textIndex);
          if (((c == '\n') || (c == '\r')) && (newline != c)) {
            newline = c;
            state.textIndex++;
          } else if (c == ' ') {
            state.textIndex++;
          } else {
            break;
          }
        }
        boolean indent;
        if (newline == 0) {
          indent = true;
        } else {
          switch (columnInfo.getIndentationMode()) {
            case INDENT_AFTER_NEWLINE:
              indent = true;
              break;
            case NO_INDENT_AFTER_NEWLINE:
              indent = true;
              break;
            case NO_INDENT_AFTER_DOUBLE_NEWLINE:
              int newlines = countReverseNewlines(columnText, state.textIndex - 1);
              if (newlines >= 2) {
                indent = false;
              } else {
                indent = true;
              }
              break;
            default:
              throw new IllegalCaseException(IndentationMode.class, columnInfo.getIndentationMode());
          }
        }
        state.indent = indent;
        // right border
        assertThat(scanner.expect(columnInfo.getBorderRight(), false)).isTrue();
      }
      int endIndex = scanner.getCurrentIndex();
      if (tableWidth != TextColumnInfo.WIDTH_AUTO_ADJUST) {
        // in this text-case we expect equality.
        // however in general this is not correct.
        assertThat(endIndex - startIndex).isEqualTo(tableWidth);
      }
      assertThat(scanner.expect(tableInfo.getLineSeparator(), false)).isTrue();
      lineCount++;
    }
    // check that all texts are completed...
    for (int columnIndex = 0; columnIndex < columns.length; columnIndex++) {
      ColumnState state = columnStates[columnIndex];
      assertThat(state.textIndex).isEqualTo(columns[columnIndex].getText().length());
    }
    return lineCount;
  }

  /**
   * Tests the method {@link LineWrapper#wrap(Appendable, TextTableInfo, TextColumn...)} .
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
      assertThat(lines).as("lines").isEqualTo(10);
      // System.out.println("width=" + i + ",lines:" + lines);
      // System.out.println(buffer.toString());
      checkWrappedColumnText(buffer, tableInfo, columns);
    }
  }

  /**
   * Inner class for the state of a text column.
   */
  private static class ColumnState {

    /** The width of the column. */
    private int width;

    /** The current index in the column-text. */
    private int textIndex;

    /**
     * {@code true} if line is expected to be indented, {@code false} otherwise.
     */
    private boolean indent;

  }
}
