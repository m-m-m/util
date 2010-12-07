/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.api;

import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.nls.api.NlsIllegalArgumentException;

/**
 * This is a Java-bean for the layout-configuration of a text table.<br>
 * It contains the {@link #getWidth() width}, {@link #getLineSeparator()
 * line-separator}, and various other meta-information for the layout of a
 * textual table.
 * 
 * @see LineWrapper
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class TextTableInfo {

  /**
   * The default {@link #getWidth() width}. This default is suitable for a
   * single-column layout.
   */
  public static final int DEFAULT_WIDTH = 80;

  /** @see #getLineSeparator() */
  private String lineSeparator;

  /** @see #getWidth() */
  private int width;

  /**
   * The constructor.
   */
  public TextTableInfo() {

    super();
    this.lineSeparator = StringUtil.LINE_SEPARATOR;
    this.width = DEFAULT_WIDTH;
  }

  /**
   * This method gets the {@link StringUtil#LINE_SEPARATOR} used to terminate a
   * line of text. Only the line-separator of the
   * {@link LineWrapper#wrap(Appendable, TextTableInfo, TextColumn[]) last
   * column} is used and all others are ignored.
   * 
   * @return the line separator string.
   */
  public String getLineSeparator() {

    return this.lineSeparator;
  }

  /**
   * This method sets the {@link #getLineSeparator() line-separator}.
   * 
   * @see StringUtil#LINE_SEPARATOR
   * @see StringUtil#LINE_SEPARATOR_CR
   * @see StringUtil#LINE_SEPARATOR_CRLF
   * @see StringUtil#LINE_SEPARATOR_LF
   * @see StringUtil#LINE_SEPARATOR_LFCR
   * 
   * @param newline is the new line-separator.
   */
  public void setLineSeparator(String newline) {

    // TODO: extract to StringUtil
    if (!StringUtil.LINE_SEPARATOR_CRLF.equals(newline)
        && !StringUtil.LINE_SEPARATOR_LF.equals(newline)
        && !StringUtil.LINE_SEPARATOR_LFCR.equals(newline)
        && !StringUtil.LINE_SEPARATOR_CR.equals(newline)) {
      throw new NlsIllegalArgumentException(newline);
    }
    this.lineSeparator = newline;
  }

  /**
   * This method gets the width of the text-table in characters.<br>
   * This should be the number of characters per line in your
   * {@link System#console console}. It may be
   * {@link TextColumnInfo#WIDTH_AUTO_ADJUST} if all
   * {@link TextColumnInfo#getWidth() column-widths} are set to actual positive
   * values. Otherwise the value returned by this method has to be positive and
   * greater or equal to the sum of the following term for each
   * {@link TextColumnInfo column}:
   * 
   * <pre>
   * {@link TextColumnInfo#getBorderLeft()}.length() + {@link TextColumnInfo#getBorderRight()}.length() + &lt;column-with&gt; 
   * </pre>
   * 
   * Where <code>&lt;column-with&gt;</code> is {@link TextColumnInfo#getWidth()}
   * or (in case of {@link TextColumnInfo#WIDTH_AUTO_ADJUST}) an auto-calculated
   * value that has to be greater or equal to 1. The default value is
   * {@value #DEFAULT_WIDTH}.
   * 
   * @return the width of the text-table.
   */
  public int getWidth() {

    return this.width;
  }

  /**
   * @param width is the width to set
   */
  public void setWidth(int width) {

    this.width = width;
  }

}
