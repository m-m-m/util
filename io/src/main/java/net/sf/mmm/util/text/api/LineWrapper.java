/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.api;

/**
 * This is the interface used to {@link #wrap(Appendable, TextTableInfo, String, TextColumnInfo) wrap} texts
 * if they exceed a specific length. It even allows to do complex
 * {@link #wrap(Appendable, TextTableInfo, TextColumn[]) multi-column layouts} easily.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public interface LineWrapper {

  /**
   * This method {@link Appendable#append(CharSequence) appends} the given {@code text} to the
   * {@code appendable}. If the text is longer than the {@link TextColumnInfo#getWidth() number of characters}
   * left in the current line, the {@code text} is wrapped. This means the current line is filled with parts
   * of the {@code text}, a newline is {@link Appendable#append(CharSequence) added} and the next line is
   * filled with the same procedure until the entire {@code text} is processed. The last line is NOT
   * terminated with a newline but ends with the last character of the text.
   *
   * @param appendable is the target where to {@link Appendable#append(CharSequence) append} to.
   * @param tableInfo is the {@link TextTableInfo} for the general layout.
   * @param columnText is the text to append. The text may contain newline characters to indicate a line-break
   *        is forced at those positions.
   * @param columnInfo holds the configuration like {@link TextColumnInfo#getWidth() width},
   *        {@link TextColumnInfo#getAlignment() alignment}, {@link TextColumnInfo#getIndent() indent}, etc.
   * @return the number of lines that have been {@link Appendable#append(CharSequence) appended} excluding the
   *         first line. In other words the number of
   *         {@link net.sf.mmm.util.lang.api.StringUtil#getLineSeparator() line separators} that have been
   *         appended.
   */
  int wrap(Appendable appendable, TextTableInfo tableInfo, String columnText, TextColumnInfo columnInfo);

  /**
   * This method is like {@link #wrap(Appendable, TextTableInfo, String, TextColumnInfo)} but for a two-column
   * layout.
   *
   * @see #wrap(Appendable, TextTableInfo, TextColumn[])
   *
   * @param appendable is the target where to {@link Appendable#append(CharSequence) append} to.
   * @param tableInfo is the {@link TextTableInfo} for the general layout.
   * @param column1Text is the text for the first column.
   * @param column1Info is the {@link TextColumnInfo} of the first column.
   * @param column2Text is the text for the second column.
   * @param column2Info is the {@link TextColumnInfo} of the second column.
   * @return the number of lines that have been {@link Appendable#append(CharSequence) appended} excluding the
   *         first line. In other words the number of
   *         {@link net.sf.mmm.util.lang.api.StringUtil#getLineSeparator() line separators} that have been
   *         appended.
   */
  int wrap(Appendable appendable, TextTableInfo tableInfo, String column1Text, TextColumnInfo column1Info, String column2Text, TextColumnInfo column2Info);

  /**
   * This method is like {@link #wrap(Appendable, TextTableInfo, String, TextColumnInfo)} but for a
   * three-column layout.
   *
   * @see #wrap(Appendable, TextTableInfo, TextColumn[])
   *
   * @param appendable is the target where to {@link Appendable#append(CharSequence) append} to.
   * @param tableInfo is the {@link TextTableInfo} for the general layout.
   * @param column1Text is the text for the first column.
   * @param column1Info is the {@link TextColumnInfo} of the first column.
   * @param column2Text is the text for the second column.
   * @param column2Info is the {@link TextColumnInfo} of the second column.
   * @param column3Text is the text for the third column.
   * @param column3Info is the {@link TextColumnInfo} of the third column.
   * @return the number of lines that have been {@link Appendable#append(CharSequence) appended} excluding the
   *         first line. In other words the number of
   *         {@link net.sf.mmm.util.lang.api.StringUtil#getLineSeparator() line separators} that have been
   *         appended.
   */
  // CHECKSTYLE:OFF (more than 7 parameters required here)
  int wrap(Appendable appendable, TextTableInfo tableInfo, String column1Text, TextColumnInfo column1Info, String column2Text, TextColumnInfo column2Info,
      String column3Text, TextColumnInfo column3Info);

  // CHECKSTYLE:ON

  /**
   * This method is like
   * {@link #wrap(Appendable, TextTableInfo, String, TextColumnInfo, String, TextColumnInfo, String, TextColumnInfo)}
   * for any number of columns. <br>
   *
   * @param appendable is the target where to {@link Appendable#append(CharSequence) append} to.
   * @param tableInfo is the {@link TextTableInfo} for the general layout.
   * @param columns are the columns to {@link Appendable#append(CharSequence) append}.
   * @return the number of lines that have been {@link Appendable#append(CharSequence) appended} excluding the
   *         first line. In other words the number of
   *         {@link net.sf.mmm.util.lang.api.StringUtil#getLineSeparator() line separators} that have been
   *         appended.
   */
  int wrap(Appendable appendable, TextTableInfo tableInfo, TextColumn... columns);

}
