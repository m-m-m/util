/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.api;

/**
 * This is the interface used to
 * {@link #wrap(Appendable, String, TextColumnInfo) wrap} texts if they
 * exceed a specific length. It even allows to do complex
 * {@link #wrap(Appendable, String[], TextColumnInfo[]) multi-column
 * layouts} easily.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public interface LineWrapper {

  /**
   * This method {@link Appendable#append(CharSequence) appends} the given
   * <code>text</code> to the <code>appendable</code>. If the text is longer
   * than the {@link TextColumnInfo#getWidth() number of characters} left in the
   * current line, the <code>text</code> is wrapped. This means the current line
   * is filled with parts of the <code>text</code>, a newline is
   * {@link Appendable#append(CharSequence) added} and the next line is filled
   * with the same procedure until the entire <code>text</code> is processed.
   * The last line is NOT terminated with a newline but ends with the last
   * character of the text.
   * 
   * @param appendable is the target where to
   *        {@link Appendable#append(CharSequence) append} to.
   * @param text is the text to append. The text may contain newline characters
   *        to indicate a line-break is forced at those positions.
   * @param columnInfo holds the configuration like
   *        {@link TextColumnInfo#getWidth() width},
   *        {@link TextColumnInfo#getAlignment() alignment},
   *        {@link TextColumnInfo#getIndent() indent}, etc.
   * @return the number of lines that have been
   *         {@link Appendable#append(CharSequence) appended} excluding the
   *         first line. In other words the number of
   *         {@link net.sf.mmm.util.lang.api.StringUtil#LINE_SEPARATOR line
   *         separators} that have been appended.
   */
  int wrap(Appendable appendable, String text, TextColumnInfo columnInfo);

  /**
   * This method is like {@link #wrap(Appendable, String, TextColumnInfo)}
   * but for a two-column layout.
   * 
   * @see #wrap(Appendable, String[], TextColumnInfo[])
   * 
   * @param appendable is the target where to
   *        {@link Appendable#append(CharSequence) append} to.
   * @param column1 is the text for the first column.
   * @param column1Info is the {@link TextColumnInfo} of the first column.
   * @param column2 is the text for the second column.
   * @param column2Info is the {@link TextColumnInfo} of the second column.
   * @return the number of lines that have been
   *         {@link Appendable#append(CharSequence) appended} excluding the
   *         first line. In other words the number of
   *         {@link net.sf.mmm.util.lang.api.StringUtil#LINE_SEPARATOR line
   *         separators} that have been appended.
   */
  int wrap(Appendable appendable, String column1, TextColumnInfo column1Info,
      String column2, TextColumnInfo column2Info);

  /**
   * This method is like {@link #wrap(Appendable, String, TextColumnInfo)}
   * but for a three-column layout.
   * 
   * @see #wrap(Appendable, String[], TextColumnInfo[])
   * 
   * @param appendable is the target where to
   *        {@link Appendable#append(CharSequence) append} to.
   * @param column1 is the text for the first column.
   * @param column1Info is the {@link TextColumnInfo} of the first column.
   * @param column2 is the text for the second column.
   * @param column2Info is the {@link TextColumnInfo} of the second column.
   * @param column3 is the text for the third column.
   * @param column3Info is the {@link TextColumnInfo} of the third column.
   * @return the number of lines that have been
   *         {@link Appendable#append(CharSequence) appended} excluding the
   *         first line. In other words the number of
   *         {@link net.sf.mmm.util.lang.api.StringUtil#LINE_SEPARATOR line
   *         separators} that have been appended.
   */
  int wrap(Appendable appendable, String column1, TextColumnInfo column1Info,
      String column2, TextColumnInfo column2Info, String column3,
      TextColumnInfo column3Info);

  /**
   * This method is like
   * {@link #wrap(Appendable, String, TextColumnInfo, String, TextColumnInfo, String, TextColumnInfo)}
   * for multi-column layouts.<br>
   * <b>ATTENTION:</b><br>
   * Please use the <code>wrap</code> methods for fixed column-counts in advance
   * to this method. However this method is intended to be used for tables with
   * more than three columns.
   * 
   * @param appendable is the target where to
   *        {@link Appendable#append(CharSequence) append} to.
   * @param columns are the texts to {@link Appendable#append(CharSequence)
   *        append} in multi-column mode.
   * @param columnInfos are the according {@link TextColumnInfo}s.
   * @return the number of lines that have been
   *         {@link Appendable#append(CharSequence) appended} excluding the
   *         first line. In other words the number of
   *         {@link net.sf.mmm.util.lang.api.StringUtil#LINE_SEPARATOR line
   *         separators} that have been appended.
   */
  int wrap(Appendable appendable, String[] columns, TextColumnInfo[] columnInfos);

}
