/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.api;

import java.util.Locale;

import net.sf.mmm.util.lang.api.HorizontalAlignment;

/**
 * This is a Java-bean for the layout-configuration of a text column.<br>
 * It contains the {@link TextColumnInfo#getWidth() width}, {@link TextColumnInfo#getAlignment() alignment},
 * {@link TextColumnInfo#getIndent() indent}, and various other meta-information for the layout of a textual
 * column.
 * 
 * @see LineWrapper
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class TextColumnInfo {

  /**
   * The minimum {@link #getWidth() width} of a {@link TextColumnInfo column} in order that
   * {@link #getIndent() indentation} and {@link Hyphenator hyphenation} will be active. The value is
   * {@value #MINIMUM_WIDTH_FOR_INDENT_AND_HYPHEN}.
   */
  public static final int MINIMUM_WIDTH_FOR_INDENT_AND_HYPHEN = 4;

  /**
   * The value used for {@link #getWidth() width} of a {@link TextColumnInfo column} in order to activate
   * automatic adjustment of the width. The value is {@value #WIDTH_AUTO_ADJUST}. Please note that automatic
   * adjustment for multiple columns needs to pre-process the entire {@link TextColumn#getText() text} of
   * these columns what costs a little bit of additional performance.
   */
  public static final int WIDTH_AUTO_ADJUST = -1;

  /** @see #getWidth() */
  private int width;

  /** @see #getBorderLeft() */
  private String borderLeft;

  /** @see #getBorderRight() */
  private String borderRight;

  /** @see #getFiller() */
  private char filler;

  /** @see #getIndent() */
  private String indent;

  /** @see #getAlignment() */
  private HorizontalAlignment alignment;

  /** @see #getWrapChars() */
  private char[] wrapChars;

  /** @see #getOmitChars() */
  private char[] omitChars;

  /** @see #getIndentationMode() */
  private IndentationMode indentationMode;

  /** @see #getLocale() */
  private Locale locale;

  /**
   * The constructor.
   */
  public TextColumnInfo() {

    super();
    this.borderLeft = "";
    this.borderRight = "";
    this.width = WIDTH_AUTO_ADJUST;
    this.indent = "";
    this.alignment = HorizontalAlignment.LEFT;
    this.filler = ' ';
    this.wrapChars = new char[] { ' ', '-', '\t' };
    this.omitChars = new char[] { ' ' };
    this.indentationMode = IndentationMode.NO_INDENT_AFTER_DOUBLE_NEWLINE;
    this.locale = Locale.getDefault();
  }

  /**
   * This method gets the width of this column in characters excluding the {@link #getBorderLeft() left} and
   * {@link #getBorderRight() right border}.<br>
   * The value has to be positive or may have the value of {@link #WIDTH_AUTO_ADJUST} (the default). A
   * reasonable value is at least 5, typically more than 10. However you may use a very low values (&lt;5) for
   * rendering a small column of a large table, but then do NOT expect nice results if text is really wrapped.
   * 
   * @see #MINIMUM_WIDTH_FOR_INDENT_AND_HYPHEN
   * 
   * @return the width of this column or {@link #WIDTH_AUTO_ADJUST} for automatic calculation of the width.
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

  /**
   * This method gets the auto calculated width of the {@link #getBorderLeft() left} and
   * {@link #getBorderRight() right border}.<br>
   * 
   * @return {@link #getBorderLeft()}.length() + {@link #getBorderRight()} .length()
   */
  public int getBorderWidth() {

    return this.borderLeft.length() + this.borderRight.length();
  }

  /**
   * This method gets the string for the right border of this column. It is appended to the end of the column
   * text in each line. The default value is the empty string. For multi-column-layouts this should typically
   * be {@link #setBorderRight(String) changed} for all columns except the last one.
   * 
   * @return the right border separator.
   */
  public String getBorderRight() {

    return this.borderRight;
  }

  /**
   * This method sets the {@link #getBorderRight() right border string}. This is typically a single character
   * (e.g. ' ' or '|'). However it is also possible to use the empty string or a sequence of characters.
   * 
   * @param separator is the right border.
   */
  public void setBorderRight(String separator) {

    this.borderRight = separator;
  }

  /**
   * This method gets the string for the left border of this column. It is appended before the column text in
   * each line. The default value is the empty string.
   * 
   * @return the left border separator.
   */
  public String getBorderLeft() {

    return this.borderLeft;
  }

  /**
   * This method sets the {@link #getBorderLeft() left border string}. This is typically a single character
   * (e.g. '|'). However it is also possible to use a sequence of characters.
   * 
   * @param separator is the right border.
   */
  public void setBorderLeft(String separator) {

    this.borderLeft = separator;
  }

  /**
   * This method gets the indent. This string is added after the {@link #getBorderLeft() left border} and
   * before the text of the column in each line except the first one. The exact behavior depends on
   * {@link #getIndentationMode()}. The default is the empty string.<br>
   * <b>ATTENTION:</b><br>
   * <ul>
   * <li>Indentation is ignored if {@link #getWidth() width} is less than
   * {@link #MINIMUM_WIDTH_FOR_INDENT_AND_HYPHEN}.</li>
   * <li>Be aware that indentation also works with an {@link #getAlignment() alignment} other than
   * {@link HorizontalAlignment#LEFT left}. However this might cause confusing results.</li>
   * </ul>
   * 
   * @see #getIndentationMode()
   * 
   * @return the indent
   */
  public String getIndent() {

    return this.indent;
  }

  /**
   * This method sets the {@link #getIndent() indent}.
   * 
   * @param indent is the indent to set
   */
  public void setIndent(String indent) {

    this.indent = indent;
  }

  /**
   * This method gets the {@link HorizontalAlignment alignment}.
   * 
   * @return the alignment.
   */
  public HorizontalAlignment getAlignment() {

    return this.alignment;
  }

  /**
   * This method sets the {@link #getAlignment() alignment}.
   * 
   * @param alignment is the {@link HorizontalAlignment} to use.
   */
  public void setAlignment(HorizontalAlignment alignment) {

    this.alignment = alignment;
  }

  /**
   * This method gets the character used to fill up the column. The default value is a whitespace (' ').
   * 
   * @return the fill-character.
   */
  public char getFiller() {

    return this.filler;
  }

  /**
   * This method sets the {@link #getFiller() filler}.
   * 
   * @param filler is character used to fill up the column.
   */
  public void setFiller(char filler) {

    this.filler = filler;
  }

  /**
   * This method gets the characters where a wrap of the line is preferred. Typical characters are space (' ')
   * or hyphen ('-').
   * 
   * @return the characters where to wrap the text.
   */
  public char[] getWrapChars() {

    return this.wrapChars;
  }

  /**
   * This method sets the {@link #getWrapChars() wrap characters}.
   * 
   * @param wrapChars are the {@link #getWrapChars() wrap characters}.
   */
  public void setWrapChars(char... wrapChars) {

    this.wrapChars = wrapChars;
  }

  /**
   * This method gets the characters to omit at the beginning of each {@link #getIndent() indented} line. E.g.
   * if space (' ') is contained in {@link #getWrapChars()} you can add it to omit chars to prevent the space
   * to occur at the beginning of the next line.
   * 
   * @return the omitChars
   */
  public char[] getOmitChars() {

    return this.omitChars;
  }

  /**
   * This method sets the {@link #getOmitChars() characters to omit}.
   * 
   * @param omitChars are the characters that shall be omitted from the column text.
   */
  public void setOmitChars(char... omitChars) {

    this.omitChars = omitChars;
  }

  /**
   * This method gets the {@link IndentationMode} that defines when to add an {@link #getIndent() indentation}
   * . To turn {@link #getIndent() indentation} off, simply set {@link #getIndent() indent} to the empty
   * string.<br>
   * The default is {@link IndentationMode#NO_INDENT_AFTER_DOUBLE_NEWLINE}.
   * 
   * @see #getIndent()
   * 
   * @return the indentationMode
   */
  public IndentationMode getIndentationMode() {

    return this.indentationMode;
  }

  /**
   * This method sets the {@link #getIndentationMode() indentation-mode}.
   * 
   * @param indentationMode is the indentationMode to set
   */
  public void setIndentationMode(IndentationMode indentationMode) {

    this.indentationMode = indentationMode;
  }

  /**
   * This method gets the {@link Locale} to use for this column. It is used for line wrapping including
   * hyphenation.
   * 
   * @return the columns locale.
   */
  public Locale getLocale() {

    return this.locale;
  }

  /**
   * This method sets the {@link #getLocale() locale}.
   * 
   * @param locale is the {@link Locale} to set.
   */
  public void setLocale(Locale locale) {

    this.locale = locale;
  }

  /**
   * This enum contains the available modes how to deal with {@link TextColumnInfo#getIndent() indentation}
   * after {@link net.sf.mmm.util.lang.api.StringUtil#LINE_SEPARATOR newlines}.
   */
  public enum IndentationMode {

    /**
     * Do NOT add {@link TextColumnInfo#getIndent() indent} for the first line, but for each following lines.
     * After {@link net.sf.mmm.util.lang.api.StringUtil#LINE_SEPARATOR newline} in the text, no
     * {@link TextColumnInfo#getIndent() indent} is added for the following line of the column.
     */
    NO_INDENT_AFTER_NEWLINE,

    /**
     * Do NOT add {@link TextColumnInfo#getIndent() indent} for the first line, but for each following lines,
     * even after a single {@link net.sf.mmm.util.lang.api.StringUtil#LINE_SEPARATOR newline} in the text. If
     * a column-line starts with a {@link net.sf.mmm.util.lang.api.StringUtil#LINE_SEPARATOR newline}
     * (duplicate newlines), this indicates a new paragraph. So after an empty line in the column, no
     * {@link TextColumnInfo#getIndent() indent} is added for the next line of the column.
     */
    NO_INDENT_AFTER_DOUBLE_NEWLINE,

    /**
     * Do NOT add {@link TextColumnInfo#getIndent() indent} for the first line, but for each following lines,
     * even after a {@link net.sf.mmm.util.lang.api.StringUtil#LINE_SEPARATOR newline} in the text.
     */
    INDENT_AFTER_NEWLINE

  }

}
