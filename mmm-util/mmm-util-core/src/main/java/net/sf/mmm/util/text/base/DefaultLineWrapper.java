/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.base;

import java.io.IOException;
import java.text.BreakIterator;
import java.util.Locale;

import net.sf.mmm.util.io.api.IoMode;
import net.sf.mmm.util.io.api.RuntimeIoException;
import net.sf.mmm.util.lang.api.HorizontalAlignment;
import net.sf.mmm.util.nls.api.IllegalCaseException;
import net.sf.mmm.util.nls.api.NlsIllegalArgumentException;
import net.sf.mmm.util.text.api.TextColumnInfo;
import net.sf.mmm.util.text.api.TextColumnInfo.IndentationMode;

/**
 * This is the default implementation of the
 * {@link net.sf.mmm.util.text.api.LineWrapper} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class DefaultLineWrapper extends AbstractLineWrapper {

  /**
   * The constructor.
   */
  public DefaultLineWrapper() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param lineSeparator is the {@link #getLineSeparator() line-separator}.
   * @param locale is the {@link #getLocale() locale} to use.
   */
  public DefaultLineWrapper(String lineSeparator, Locale locale) {

    super(lineSeparator, locale);
  }

  /**
   * {@inheritDoc}
   */
  public int wrap(Appendable appendable, String[] columns, TextColumnInfo[] columnInfos) {

    try {
      if (columns.length != columnInfos.length) {
        throw new NlsIllegalArgumentException("columns.length != columnInfos.length");
      }
      if (columns.length == 0) {
        throw new NlsIllegalArgumentException(Integer.valueOf(0), "columns.length");
      }
      int newLines = 0;
      boolean todo = true;
      ColumnState[] columnStates = new ColumnState[columns.length];
      Locale locale = getLocale();
      for (int i = 0; i < columns.length; i++) {
        columnStates[i] = new ColumnState(columns[i], columnInfos[i], locale);
      }
      while (todo) {
        todo = false;
        for (int columnIndex = 0; columnIndex < columnStates.length; columnIndex++) {
          ColumnState state = columnStates[columnIndex];
          appendable.append(state.info.getBorderLeft());
          append(appendable, state);
          if (!state.isComplete()) {
            todo = true;
          }
          appendable.append(state.info.getBorderRight());
        }
        if (todo) {
          appendable.append(getLineSeparator());
          newLines++;
        }
      }
      return newLines;
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.WRITE);
    }
  }

  /**
   * This method determines if the given character <code>c</code> is contained
   * in <code>chars</code>.
   * 
   * @param c is the character to check.
   * @param chars is the array with the matching characters.
   * @return <code>true</code> if <code>c</code> is contained in
   *         <code>chars</code>, <code>false</code> otherwise.
   */
  private static boolean isIn(char c, char[] chars) {

    if (chars != null) {
      for (char current : chars) {
        if (current == c) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * 
   * @param appendable
   * @param state
   * @throws IOException if caused by the {@link Appendable}.
   */
  protected void append(Appendable appendable, ColumnState state) throws IOException {

    int start = state.textIndex;
    int length = state.text.length();
    int width = state.info.getWidth();
    boolean doIndentThisLine = state.indent;
    if (doIndentThisLine) {
      width = width - state.info.getIndent().length();
    } else {
      // omit specific characters at beginning of this line...
      while (start < length) {
        char c = state.text.charAt(start);
        if (isIn(c, state.info.getOmitChars())) {
          start++;
        } else {
          break;
        }
      }
      if (start != state.textIndex) {
        state.textIndex = start;

      }
    }
    // determine maximum end...

    int maxEnd = start + width;
    if (maxEnd > length) {
      maxEnd = length;
    }
    int end = maxEnd;
    int result = end;
    // scan text for newlines...
    boolean newline = false;
    for (int i = start; i < end; i++) {
      char c = state.text.charAt(i);
      // newline?
      if ((c == '\r') || (c == '\n')) {
        end = i;
        result = i;
        switch (state.info.getIndentationMode()) {
          case NO_INDENT_AFTER_NEWLINE:
            state.indent = true;
            break;
          case NO_INDENT_AFTER_DOUBLE_NEWLINE:
            if (i == state.textIndex) {
              state.indent = true;
            } else {
              state.indent = false;
            }
            break;
          case INDENT_AFTER_NEWLINE:
            state.indent = false;
            break;
          default :
            throw new IllegalCaseException(IndentationMode.class, state.info.getIndentationMode());
        }
        newline = true;
        i++;
        if (i < end) {
          char next = state.text.charAt(i);
          // newline?
          if (((next == '\r') || (next == '\n')) && (next != c)) {
            result = i;
          }
        }
        break;
      }
    }

    String wordWrap = "";
    if (!newline && (end < length)) {
      // determine wrap position...
      int wrapIndex = -1;
      int reverseStart = end;
      if (end >= length) {
        reverseStart = length - 1;
      }
      for (int i = reverseStart; i > start; i--) {
        char c = state.text.charAt(i);
        if (isIn(c, state.info.getWrapChars())) {
          wrapIndex = i;
          break;
        }
      }
      if (wrapIndex > 0) {
        // if ((end - wrapIndex) > state.info.getMinimumWordWrap()) {
        //
        // }
        end = wrapIndex;
      }
      // TODO: Word-wrap!
      result = end;
    }

    int space = width - (end - start);
    switch (state.info.getAlignment()) {
      case LEFT:
        if (doIndentThisLine) {
          appendable.append(state.info.getIndent());
        }
        appendable.append(state.text, start, end);
        fill(appendable, state.info.getFiller(), space);
        break;
      case RIGHT:
        fill(appendable, state.info.getFiller(), space);
        appendable.append(state.text, start, end);
        if (doIndentThisLine) {
          appendable.append(state.info.getIndent());
        }
        break;
      case CENTER:
        int leftSpace = space / 2;
        int rightSpace = space - leftSpace;
        fill(appendable, state.info.getFiller(), leftSpace);
        String rightIndent = "";
        if (doIndentThisLine) {
          String indent = state.info.getIndent();
          int indentLength = indent.length();
          int rightIndex = indentLength - (indentLength / 2);
          String leftIndent = indent.substring(0, rightIndex);
          rightIndent = indent.substring(rightIndex);
          appendable.append(leftIndent);
        }
        appendable.append(state.text, start, end);
        if (doIndentThisLine) {
          appendable.append(rightIndent);
        }
        fill(appendable, state.info.getFiller(), rightSpace);
        break;
      default :
        throw new IllegalCaseException(HorizontalAlignment.class, state.info.getAlignment());
    }

    state.textIndex = result;
  }

  /**
   * This method fills the {@link Appendable} with the given <code>count</code>
   * of <code>filler</code> characters.
   * 
   * @param appendable is the {@link Appendable} to fill.
   * @param filler is the {@link TextColumnInfo#getFiller() fill-character}.
   * @param count is the number of fill-characters (<code>filler</code>) to add.
   * @throws IOException if caused by the {@link Appendable}.
   */
  protected void fill(Appendable appendable, char filler, int count) throws IOException {

    for (int i = 0; i < count; i++) {
      appendable.append(filler);
    }
  }

  /**
   * This inner class represents the state of a text-column.
   */
  protected static class ColumnState {

    /** @see #getText() */
    private final String text;

    /** @see #getBreakIterator() */
    private final BreakIterator breakIterator;

    /** @see #getInfo() */
    private final TextColumnInfo info;

    /** @see #getTextIndex() */
    private int textIndex;

    /** @see #isIndent() */
    private boolean indent;

    /**
     * The constructor.
     * 
     * @param text is the {@link #getText() text}.
     * @param info is the {@link #getInfo() column-info}.
     * @param locale is the {@link Locale}.
     */
    public ColumnState(String text, TextColumnInfo info, Locale locale) {

      super();
      this.text = text;
      this.breakIterator = BreakIterator.getLineInstance(locale);
      this.breakIterator.setText(text);
      this.info = info;
      this.textIndex = 0;
      this.indent = false;
      if ((info.getIndent() == null) || (info.getIndent().length() >= info.getWidth())) {
        throw new NlsIllegalArgumentException(info.getIndent(), "TextColumnInfo.indent");
      }
    }

    /**
     * @return the text
     */
    public String getText() {

      return this.text;
    }

    /**
     * @return the breakIterator
     */
    public BreakIterator getBreakIterator() {

      return this.breakIterator;
    }

    /**
     * @return the info
     */
    public TextColumnInfo getInfo() {

      return this.info;
    }

    /**
     * @return the textIndex
     */
    public int getTextIndex() {

      return this.textIndex;
    }

    /**
     * @param textIndex is the textIndex to set
     */
    public void setTextIndex(int textIndex) {

      this.textIndex = textIndex;
    }

    /**
     * @return the indent
     */
    public boolean isIndent() {

      return this.indent;
    }

    /**
     * @param indent is the indent to set
     */
    public void setIndent(boolean indent) {

      this.indent = indent;
    }

    /**
     * This method determines if this column is complete (all {@link #getText()
     * text} is appended).
     * 
     * @return <code>true</code> if complete, <code>false</code> otherwise.
     */
    public boolean isComplete() {

      return (this.textIndex >= this.text.length());
    }

  }

}
