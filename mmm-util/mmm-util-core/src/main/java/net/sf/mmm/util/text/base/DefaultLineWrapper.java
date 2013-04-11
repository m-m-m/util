/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.base;

import java.io.IOException;
import java.text.BreakIterator;
import java.util.Iterator;
import java.util.Locale;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.io.api.IoMode;
import net.sf.mmm.util.io.api.RuntimeIoException;
import net.sf.mmm.util.lang.api.HorizontalAlignment;
import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.nls.api.IllegalCaseException;
import net.sf.mmm.util.nls.api.NlsIllegalArgumentException;
import net.sf.mmm.util.nls.api.NlsIllegalStateException;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.text.api.Hyphenation;
import net.sf.mmm.util.text.api.Hyphenator;
import net.sf.mmm.util.text.api.HyphenatorBuilder;
import net.sf.mmm.util.text.api.LineWrapper;
import net.sf.mmm.util.text.api.TextColumn;
import net.sf.mmm.util.text.api.TextColumnInfo;
import net.sf.mmm.util.text.api.TextColumnInfo.IndentationMode;
import net.sf.mmm.util.text.api.TextTableInfo;
import net.sf.mmm.util.text.api.UnicodeUtil;
import net.sf.mmm.util.value.api.ValueOutOfRangeException;

/**
 * This is the default implementation of {@link LineWrapper}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
@Singleton
@Named(LineWrapper.CDI_NAME)
public class DefaultLineWrapper extends AbstractLineWrapper {

  /** @see #getHyphenatorBuilder() */
  private HyphenatorBuilder hyphenatorBuilder;

  /**
   * The constructor.
   */
  public DefaultLineWrapper() {

    super();
  }

  /**
   * @return the hyphenatorBuilder
   */
  protected HyphenatorBuilder getHyphenatorBuilder() {

    return this.hyphenatorBuilder;
  }

  /**
   * @param hyphenatorBuilder is the hyphenatorBuilder to set
   */
  @Inject
  public void setHyphenatorBuilder(HyphenatorBuilder hyphenatorBuilder) {

    getInitializationState().requireNotInitilized();
    this.hyphenatorBuilder = hyphenatorBuilder;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.hyphenatorBuilder == null) {
      this.hyphenatorBuilder = HyphenatorBuilderImpl.getInstance();
    }
  }

  /**
   * This method calculates the {@link ColumnState#getWidth() width} of the columns to
   * {@link TextColumnInfo#WIDTH_AUTO_ADJUST auto-adjust}.
   * 
   * @param columnStates are the {@link ColumnState}s.
   * @param tableInfo is the {@link TextTableInfo} containing the available {@link TextTableInfo#getWidth()
   *        width}.
   */
  @SuppressWarnings("null")
  protected void autoAdjustWidthOfColumns(ColumnState[] columnStates, TextTableInfo tableInfo) {

    int tableWidth = tableInfo.getWidth();
    if (tableWidth == TextColumnInfo.WIDTH_AUTO_ADJUST) {
      // if table width is not set (auto adjust) then the width of all columns
      // have to be set...
      for (int i = 0; i < columnStates.length; i++) {
        if (columnStates[i].width == TextColumnInfo.WIDTH_AUTO_ADJUST) {
          // better exception message?
          throw new NlsIllegalArgumentException(Integer.valueOf(tableWidth), "tableInfo.width && columnInfo[" + i
              + "].width");
        }
      }
      return;
    }
    if (tableWidth <= 0) {
      throw new NlsIllegalArgumentException(Integer.valueOf(tableWidth), "tableInfo.width");
    }
    int autoAdjustColumnCount = 0;
    int staticTableWidth = 0;
    ColumnState lastAutoAdjustColumn = null;
    long autoAdjustColumnTotalTextLength = 0;
    // find columns that need auto adjustment and determine static width
    for (int i = 0; i < columnStates.length; i++) {
      TextColumnInfo columnInfo = columnStates[i].getColumnInfo();
      staticTableWidth = staticTableWidth + columnInfo.getBorderWidth();
      int width = columnStates[i].width;
      if (width == TextColumnInfo.WIDTH_AUTO_ADJUST) {
        autoAdjustColumnCount++;
        lastAutoAdjustColumn = columnStates[i];
        autoAdjustColumnTotalTextLength = autoAdjustColumnTotalTextLength + columnStates[i].getText().length();
      } else {
        staticTableWidth = staticTableWidth + width;
      }
    }

    // remaining width to be divided over the auto-adjust columns
    int widthRemaining = tableWidth - staticTableWidth;
    if (widthRemaining < autoAdjustColumnCount) {
      // there is less than 1 character left for each auto-adjust columns (or
      // less than 0 for no such column).
      throw new ValueOutOfRangeException(Integer.valueOf(tableWidth), Integer.valueOf(staticTableWidth
          + autoAdjustColumnCount), Integer.valueOf(Integer.MAX_VALUE), "tableInfo.width");
    }

    // perform auto adjustment...
    if (autoAdjustColumnCount > 0) {
      if (autoAdjustColumnCount == 1) {
        // easy case: single column gets the remaining space
        lastAutoAdjustColumn.width = widthRemaining;
      } else {
        // actual adjustment algorithm ...
        AutoAdjustInfo[] autoAdjustInfoArray = new AutoAdjustInfo[autoAdjustColumnCount];
        int autoAdjustInfoIndex = 0;
        // pass 1
        int widthUsed = 0;
        for (int i = 0; i < columnStates.length; i++) {
          if (columnStates[i].getColumnInfo().getWidth() == TextColumnInfo.WIDTH_AUTO_ADJUST) {
            AutoAdjustInfo autoAdjustInfo = new AutoAdjustInfo(columnStates[i]);
            autoAdjustInfoArray[autoAdjustInfoIndex] = autoAdjustInfo;
            double ratio = autoAdjustInfo.getTextLengthRation(autoAdjustColumnTotalTextLength);
            int calculatedWidth = (int) (widthRemaining * ratio);
            if ((calculatedWidth < 1) && (ratio > 0)) {
              // 0 is simply too small :)
              calculatedWidth = 1;
            }
            if (calculatedWidth > autoAdjustInfo.lineLengthMax) {
              // width of column should never be greater than maximum line
              calculatedWidth = autoAdjustInfo.lineLengthMax;
            }
            autoAdjustInfo.columnState.setWidth(calculatedWidth);
            widthUsed = widthUsed + calculatedWidth;
            autoAdjustInfoIndex++;
          }
        }
        int delta = widthUsed - widthRemaining;
        if (delta != 0) {
          SortedSet<AutoAdjustInfo> autoAdjustInfoSet = new TreeSet<DefaultLineWrapper.AutoAdjustInfo>();
          if (delta > 0) {
            // width is too high because of rounding errors,
            // delta should typically be 1
            for (AutoAdjustInfo autoAdjustInfo : autoAdjustInfoArray) {
              if (autoAdjustInfo.columnState.width >= 2) {
                autoAdjustInfoSet.add(autoAdjustInfo);
              }
            }
            while (delta > 0) {
              Iterator<AutoAdjustInfo> iterator = autoAdjustInfoSet.iterator();
              if (!iterator.hasNext()) {
                throw new NlsIllegalStateException();
              }
              while (iterator.hasNext()) {
                AutoAdjustInfo autoAdjustInfo = iterator.next();
                autoAdjustInfo.columnState.width--;
                delta--;
                if (delta == 0) {
                  return;
                }
                if (autoAdjustInfo.columnState.width < 2) {
                  iterator.remove();
                }
              }
            }
          } else {
            // we have chars left to divide ...
            for (AutoAdjustInfo autoAdjustInfo : autoAdjustInfoArray) {
              if (autoAdjustInfo.columnState.width < autoAdjustInfo.lineLengthMax) {
                autoAdjustInfoSet.add(autoAdjustInfo);
              }
            }
            while (delta < 0) {
              Iterator<AutoAdjustInfo> iterator = autoAdjustInfoSet.iterator();
              if (!iterator.hasNext()) {
                return;
              }
              while (iterator.hasNext()) {
                AutoAdjustInfo autoAdjustInfo = iterator.next();
                autoAdjustInfo.columnState.width++;
                delta++;
                if (delta == 0) {
                  return;
                }
                if (autoAdjustInfo.columnState.width >= autoAdjustInfo.lineLengthMax) {
                  iterator.remove();
                }
              }
            }
          }
        }
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int wrap(Appendable appendable, TextTableInfo tableInfo, TextColumn... columns) {

    try {
      NlsNullPointerException.checkNotNull(Appendable.class, appendable);
      NlsNullPointerException.checkNotNull(TextTableInfo.class, tableInfo);
      NlsNullPointerException.checkNotNull(TextColumn[].class, columns);
      if (columns.length == 0) {
        throw new NlsIllegalArgumentException(Integer.valueOf(0), "columns.length");
      }
      int newLines = 0;
      boolean todo = true;
      ColumnState[] columnStates = new ColumnState[columns.length];
      for (int i = 0; i < columns.length; i++) {
        columnStates[i] = new ColumnState(columns[i].getText(), columns[i].getColumnInfo(), this.hyphenatorBuilder);
      }
      autoAdjustWidthOfColumns(columnStates, tableInfo);
      assert (verifyWithOfColumns(columnStates, tableInfo));

      CellBuffer textFragmentBuilder = new CellBuffer();
      while (todo) {
        todo = false;
        for (int columnIndex = 0; columnIndex < columnStates.length; columnIndex++) {
          ColumnState state = columnStates[columnIndex];
          appendable.append(state.getColumnInfo().getBorderLeft());
          append(appendable, state, textFragmentBuilder);
          if (!state.isComplete()) {
            // if at least one column is not complete, we are not done...
            todo = true;
          }
          appendable.append(state.getColumnInfo().getBorderRight());
        }
        appendable.append(tableInfo.getLineSeparator());
        newLines++;
      }
      return newLines;
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.WRITE);
    }
  }

  /**
   * This method verifies that the width of the columns are sane.
   * 
   * @param columnStates are the {@link ColumnState}s.
   * @param tableInfo is the {@link TextTableInfo}.
   * @return <code>true</code> if the width is sane, <code>false</code> otherwise.
   */
  private boolean verifyWithOfColumns(ColumnState[] columnStates, TextTableInfo tableInfo) {

    int tableWidth = tableInfo.getWidth();
    if (tableWidth != TextColumnInfo.WIDTH_AUTO_ADJUST) {
      int calculatedWidth = 0;
      for (ColumnState columnState : columnStates) {
        if (columnState.width < 0) {
          throw new AssertionError("columnWidth=" + columnState.width);
          // return false;
        }
        calculatedWidth = calculatedWidth + columnState.width + columnState.getColumnInfo().getBorderWidth();
      }
      if (calculatedWidth != tableWidth) {
        throw new AssertionError("with=" + tableWidth + ", sum-of-columns=" + calculatedWidth);
        // return false;
      }
    }
    return true;
  }

  /**
   * This method determines if the given character <code>c</code> is contained in <code>chars</code>.
   * 
   * @param c is the character to check.
   * @param chars is the array with the matching characters.
   * @return <code>true</code> if <code>c</code> is contained in <code>chars</code>, <code>false</code>
   *         otherwise.
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
   * This method fills the {@link CellBuffer} with the payload for the current cell.
   * 
   * @param state is the {@link ColumnState}.
   * @param buffer is the {@link CellBuffer}.
   */
  protected void appendCellBuffer(ColumnState state, CellBuffer buffer) {

    int bufferRest = buffer.getRest();

    boolean hyphenationActive = (buffer.maxLength >= TextColumnInfo.MINIMUM_WIDTH_FOR_INDENT_AND_HYPHEN);

    boolean todo = true;
    // while ((todo) && (bufferRest > 0)) {
    while (todo) {
      TextSegment currentSegment = state.currentSegment;
      if (currentSegment.getType() == TextSegmentType.NEWLINE) {
        switch (state.getColumnInfo().getIndentationMode()) {
          case NO_INDENT_AFTER_NEWLINE:
            state.indent = false;
            break;
          case NO_INDENT_AFTER_DOUBLE_NEWLINE:
            if (state.getSubsequentNewlineCount() >= 2) {
              state.indent = false;
            } else {
              state.indent = true;
            }
            break;
          case INDENT_AFTER_NEWLINE:
            state.indent = true;
            break;
          default :
            throw new IllegalCaseException(IndentationMode.class, state.getColumnInfo().getIndentationMode());
        }
        state.proceedTextSegment();
        todo = false;
      } else {
        // currentSegment is no newline event...
        int segmentStartOffset = state.textIndex - currentSegment.startIndex;
        int segmentRest = currentSegment.getLength() - segmentStartOffset;
        int space = bufferRest - segmentRest;
        TextSegmentType nextType = null;
        if (state.nextSegment != null) {
          nextType = state.nextSegment.type;
        }
        if (hyphenationActive) {
          if (TextSegmentType.PUNCTUATION_CHARACTER == nextType) {
            space--;
          } else if (TextSegmentType.NON_BREAKING_CHARACTER == nextType) {
            // TODO
            // latest|Nmidnight
            // * la-|testNmidnight
            // latestNmid|night
            // * latest|midnight
            // latestNmid|night
            // * latestNmid-|night
            space--;
          }
        }
        if (space >= 0) {
          // entire segment fits...
          bufferRest = buffer.append(currentSegment.text, state.textIndex, currentSegment.endIndex);
          todo = state.proceedTextSegment();
        } else {
          // segment does NOT fit and has to be wrapped...
          if (hyphenationActive && (currentSegment.getType() == TextSegmentType.WORD)) {
            Hyphenation hyphenation = currentSegment.getHyphenatedWord();
            // int hyphenationBefore = bufferRest + segmentStartOffset;
            // space is actually negative here...
            int hyphenationBefore = currentSegment.getLength() + space;
            if (hyphenationBefore == currentSegment.endIndex) {
              hyphenationBefore++;
            }
            int hyphenationPoint = hyphenation.getHyphenationBefore(hyphenationBefore);
            int hyphenationOffset = hyphenationPoint - segmentStartOffset;
            // hyphenation point available that fits?
            if (hyphenationOffset <= 0) {
              // determine ratio of filled text length to space left
              double fillRatio;
              if (bufferRest > 6) {
                // accept no rest longer than 6 spaces...
                fillRatio = 0;
              } else if (bufferRest <= 2) {
                // avoid wrapping word at a single character e.g. break "is" as:
                // i-
                // s
                fillRatio = Double.MAX_VALUE;
              } else {
                fillRatio = buffer.length() / bufferRest;
              }
              if (fillRatio <= 3) {
                hyphenationOffset = bufferRest - 1;
              } else {
                // high ratio means cell is filled okay and only little rest
                // left do NOT append any
                todo = false;
                break;
              }
            }
            int end = state.textIndex + hyphenationOffset;
            if (end + 1 == currentSegment.endIndex) {
              end--;
            }
            bufferRest = buffer.append(currentSegment.text, state.textIndex, end);
            if (end < currentSegment.endIndex) {
              bufferRest = buffer.append(state.hyphenator.getHyphen());
            } else {
              todo = state.proceedTextSegment();
            }
            state.textIndex = end;
            todo = false;
          } else {
            int end = state.textIndex + bufferRest;
            buffer.append(currentSegment.text, state.textIndex, end);
            state.textIndex = end;
            todo = false;
          }
        }
      }
    }
  }

  /**
   * This method appends the text of a single cell (line of a single column).
   * 
   * @param appendable is the {@link Appendable} where to append the text to.
   * @param state the {@link ColumnState} of the current column where to fill a single cell.
   * @param cellBuffer is the {@link CellBuffer} for the payload of the current cell.
   * @throws IOException if caused by the {@link Appendable}.
   */
  protected void append(Appendable appendable, ColumnState state, CellBuffer cellBuffer) throws IOException {

    boolean doIndentThisLine = false;
    int width = state.getWidth();
    if (state.isComplete()) {
      cellBuffer.reset(width);
    } else {
      TextColumnInfo columnInfo = state.getColumnInfo();
      if (width >= TextColumnInfo.MINIMUM_WIDTH_FOR_INDENT_AND_HYPHEN) {
        if (state.indent) {
          doIndentThisLine = true;
          width = width - columnInfo.getIndent().length();
          // omit specific characters at beginning of this line...
          String text = state.getText();
          while (isIn(text.charAt(state.textIndex), columnInfo.getOmitChars())) {
            state.textIndex++;
            if (state.textIndex >= state.currentSegment.endIndex) {
              boolean todo = state.proceedTextSegment();
              if (!todo) {
                break;
              }
            }
          }
        }
        state.indent = true;
      }
      // clear to reuse...
      cellBuffer.reset(width);
      // fill up buffer with cells payload...
      appendCellBuffer(state, cellBuffer);
    }
    // append payload with indent and alignment...
    appendWithAlignment(appendable, state, doIndentThisLine, cellBuffer);
  }

  /**
   * This method actually appends the text for a single line of a single column according to
   * {@link TextColumnInfo#getAlignment() alignment}.
   * 
   * @param appendable is where to append to.
   * @param state is the current {@link ColumnState}.
   * @param doIndentThisLine - <code>true</code> if the current cell should be
   *        {@link TextColumnInfo#getIndent() indented}, <code>false</code> otherwise.
   * @param cellBuffer is the text to align and append.
   * @throws IOException if throw by the {@link Appendable}.
   */
  protected void appendWithAlignment(Appendable appendable, ColumnState state, boolean doIndentThisLine,
      CellBuffer cellBuffer) throws IOException {

    int space = cellBuffer.getRest();
    assert (space >= 0);
    TextColumnInfo columnInfo = state.getColumnInfo();
    switch (columnInfo.getAlignment()) {
      case LEFT:
        if (doIndentThisLine) {
          appendable.append(columnInfo.getIndent());
        }
        appendable.append(cellBuffer.buffer);
        fill(appendable, columnInfo.getFiller(), space);
        break;
      case RIGHT:
        fill(appendable, columnInfo.getFiller(), space);
        appendable.append(cellBuffer.buffer);
        if (doIndentThisLine) {
          appendable.append(columnInfo.getIndent());
        }
        break;
      case CENTER:
        int leftSpace = space / 2;
        int rightSpace = space - leftSpace;
        fill(appendable, columnInfo.getFiller(), leftSpace);
        String rightIndent = "";
        if (doIndentThisLine) {
          String indent = columnInfo.getIndent();
          int indentLength = indent.length();
          int rightIndex = indentLength - (indentLength / 2);
          String leftIndent = indent.substring(0, rightIndex);
          rightIndent = indent.substring(rightIndex);
          appendable.append(leftIndent);
        }
        appendable.append(cellBuffer.buffer);
        if (doIndentThisLine) {
          appendable.append(rightIndent);
        }
        fill(appendable, columnInfo.getFiller(), rightSpace);
        break;
      default :
        throw new IllegalCaseException(HorizontalAlignment.class, columnInfo.getAlignment());
    }
  }

  /**
   * This method fills the {@link Appendable} with the given <code>count</code> of <code>filler</code>
   * characters.
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
   * This enum contains the possbile {@link TextSegment#getType() types} of a {@link TextSegment}.
   */
  protected static enum TextSegmentType {

    /**
     * Indicates a single word. May be {@link TextSegment#getHyphenatedWord() hyphenated} as necessary.
     */
    WORD,

    /**
     * Indicates a {@link net.sf.mmm.util.lang.api.StringUtil#getLineSeparator() newline}.
     */
    NEWLINE,

    /**
     * Indicates a non-breaking character (e.g. '&amp;nbsp;'). Line wrapping should be avoided before and
     * after this character.
     */
    NON_BREAKING_CHARACTER,

    /**
     * Indicates a punctuation character (e.g. '.' or '!'). Line wrapping should be avoided before this
     * character.<br>
     * Please note that "punctuation character" is a local definition and does NOT match with other
     * definitions as from unicode.
     */
    PUNCTUATION_CHARACTER,

    /**
     * Indicates one or multiple arbitrary characters.
     */
    CHARACTERS
  }

  /**
   * This class represents a segment of some {@link #getText() text}. It acts as an event when
   * {@link ColumnState#getCurrentSegment() iterating} the text. It is classified by a {@link #getType() type}
   * and caches a potential {@link #getHyphenatedWord() hyphenation} for performance reasons.
   */
  protected static class TextSegment {

    /** @see #getText() */
    private final String text;

    /** @see #getHyphenatedWord() */
    private final Hyphenator hyphenator;

    /** @see #getStartIndex() */
    private int startIndex;

    /** @see #getEndIndex() */
    private int endIndex;

    /** @see #getType() */
    private TextSegmentType type;

    /** @see #getHyphenatedWord() */
    private Hyphenation hyphenatedWord;

    /**
     * The constructor.
     * 
     * @param text is the {@link #getText()}.
     * @param hyphenator is the {@link Hyphenation} used for {@link #getHyphenatedWord()}.
     */
    protected TextSegment(String text, Hyphenator hyphenator) {

      super();
      this.text = text;
      this.hyphenator = hyphenator;
    }

    /**
     * This method gets the length of this {@link TextSegment}.
     * 
     * @return the result of {@link #getEndIndex() endIndex} - {@link #getStartIndex() startIndex}.
     */
    public int getLength() {

      return this.endIndex - this.startIndex;
    }

    /**
     * This method resets and initializes this object. This is NOT done at construction in order to reuse the
     * object and save performance.
     * 
     * @param start is the {@link #getStartIndex() start-index}.
     * @param end is the {@link #getEndIndex() end-index}.
     * @param textType is the {@link #getType() type}.
     */
    public void initialize(int start, int end, TextSegmentType textType) {

      this.hyphenatedWord = null;
      this.startIndex = start;
      this.endIndex = end;
      this.type = textType;
    }

    /**
     * This method gets the start index of this {@link TextSegment}.
     * 
     * @return the index of the first character of this {@link TextSegment}.
     */
    public int getStartIndex() {

      return this.startIndex;
    }

    /**
     * This method gets the index immediately after the last character of this {@link TextSegment}.
     * 
     * @return the end-index of this {@link TextSegment}.
     */
    public int getEndIndex() {

      return this.endIndex;
    }

    /**
     * This method gets the full text containing this segment.
     * 
     * @return the entire text.
     */
    public String getText() {

      return this.text;
    }

    /**
     * This method determines if this {@link TextSegment} represents a word.
     * 
     * @see #getHyphenatedWord()
     * 
     * @return <code>true</code> if segment is word, <code>false</code> otherwise (special characters, etc.)
     */
    public TextSegmentType getType() {

      return this.type;
    }

    /**
     * This method gets the {@link Hyphenation} if this {@link TextSegment} has the {@link #getType() type}
     * {@link TextSegmentType#WORD}. The {@link Hyphenation} will be build lazy on the first call of this
     * method.
     * 
     * @return the {@link Hyphenation} or <code>null</code> if this is no word.
     */
    public Hyphenation getHyphenatedWord() {

      if ((this.type == TextSegmentType.WORD) && (this.hyphenatedWord == null)) {
        this.hyphenatedWord = this.hyphenator.hyphenate(getText(), getStartIndex(), getEndIndex());
      }
      return this.hyphenatedWord;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {

      return this.text.substring(this.startIndex, this.endIndex);
    }

  }

  /**
   * This class represents the state of a text-column. It contains the {@link ColumnState#getText() text} of
   * the column and its {@link ColumnState#getColumnInfo() metadata}. Further it holds the
   * {@link ColumnState#getTextIndex() current text-index} and acts as some sort of
   * {@link ColumnState#proceedTextSegment() iterator} of {@link ColumnState#getCurrentSegment()
   * text-segments}.
   */
  protected static class ColumnState extends TextColumn {

    /** The {@link Hyphenator} to use. */
    private final Hyphenator hyphenator;

    /** @see #next(TextSegment) */
    private final BreakIterator breakIterator;

    /** @see #next(TextSegment) */
    private int breakIteratorIndex;

    /** @see #next(TextSegment) */
    private int segmentIndex;

    /** @see #getTextIndex() */
    private int textIndex;

    /** @see #getWidth() */
    private int width;

    /** @see #isIndent() */
    private boolean indent;

    /** @see #getCurrentSegment() */
    private TextSegment currentSegment;

    /** @see #getNextSegment() */
    private TextSegment nextSegment;

    /** @see #getSubsequentNewlineCount() */
    private int subsequentNewlineCount;

    /**
     * The constructor.
     * 
     * @param text is the {@link #getText() text}.
     * @param columnInfo is the {@link #getColumnInfo() column-info}.
     * @param hyphenatorBuilder is the {@link HyphenatorBuilder}.
     */
    public ColumnState(String text, TextColumnInfo columnInfo, HyphenatorBuilder hyphenatorBuilder) {

      super(text, columnInfo);
      Locale locale = columnInfo.getLocale();
      this.breakIterator = BreakIterator.getLineInstance(locale);
      this.breakIterator.setText(text);
      this.hyphenator = hyphenatorBuilder.getHyphenator(locale);
      this.segmentIndex = 0;
      this.textIndex = 0;
      this.width = columnInfo.getWidth();
      this.breakIteratorIndex = 0;
      this.subsequentNewlineCount = 0;
      this.indent = false;
      if ((columnInfo.getIndent() == null)
          || ((columnInfo.getWidth() != TextColumnInfo.WIDTH_AUTO_ADJUST) && (columnInfo.getIndent().length() >= columnInfo
              .getWidth()))) {
        throw new NlsIllegalArgumentException(columnInfo.getIndent(), "TextColumnInfo.indent");
      }
      this.currentSegment = next(new TextSegment(text, this.hyphenator));
      this.nextSegment = next(new TextSegment(text, this.hyphenator));
    }

    /**
     * This method gets the current {@link TextSegment}. Initially this is the first {@link TextSegment}
     * available.
     * 
     * @see #proceedTextSegment()
     * 
     * @return the current {@link TextSegment} or <code>null</code> if all {@link TextSegment}s are proceeded.
     */
    public TextSegment getCurrentSegment() {

      return this.currentSegment;
    }

    /**
     * This method gets the next {@link TextSegment} after the {@link #getCurrentSegment() current}. This
     * method exists for lookahead decisions.
     * 
     * @return the next {@link TextSegment} or <code>null</code> if NOT available (
     *         {@link #getCurrentSegment() current segment} is the last segment or also <code>null</code>).
     */
    public TextSegment getNextSegment() {

      return this.nextSegment;
    }

    /**
     * This method gets the current number of subsequent newlines. If {@link #getCurrentSegment() current
     * segment} is a {@link TextSegmentType#NEWLINE}, this method will return the number of
     * {@link TextSegmentType#NEWLINE} segments (including the current) that occurred since the last other
     * segment. Otherwise it will always return <code>0</code>.
     * 
     * @return the subsequentNewlineCount the number of subsequent {@link TextSegmentType#NEWLINE} segments
     *         including the {@link #getCurrentSegment() current segment} or <code>0</code> if the
     *         {@link #getCurrentSegment() current segment} is no {@link TextSegmentType#NEWLINE}.
     */
    public int getSubsequentNewlineCount() {

      return this.subsequentNewlineCount;
    }

    /**
     * This method steps on to the next {@link TextSegment}. The {@link #getCurrentSegment() current segment}
     * is set to the {@link #getNextSegment() next segment} and the {@link #getNextSegment() next segment} is
     * set to the {@link #next(TextSegment) next determined segment}.
     * 
     * @return <code>true</code> if a new {@link #getCurrentSegment() current segment} is available,
     *         <code>false</code> if the entire text has been proceeded.
     */
    public boolean proceedTextSegment() {

      if (this.currentSegment == null) {
        return false;
      }
      this.textIndex = this.currentSegment.endIndex;
      TextSegment old = this.currentSegment;
      this.currentSegment = this.nextSegment;
      this.nextSegment = next(old);
      if (this.currentSegment == null) {
        this.subsequentNewlineCount = 0;
        return false;
      } else if (this.currentSegment.type == TextSegmentType.NEWLINE) {
        this.subsequentNewlineCount++;
      } else {
        this.subsequentNewlineCount = 0;
      }
      return true;
    }

    /**
     * This method {@link TextSegment#initialize(int, int, TextSegmentType) initializes} the given
     * {@link TextSegment} with the next segment-data from the {@link #getText() text}.
     * 
     * @param textSegment a previous {@link TextSegment} that can be reused.
     * @return the {@link TextSegment} with the next segment-data or <code>null</code> if the
     *         {@link #getText() text} is completed.
     */
    private TextSegment next(TextSegment textSegment) {

      String text = getText();
      int length = text.length();
      if (this.segmentIndex >= length) {
        // we are done with text
        return null;
      }
      if ((this.breakIteratorIndex != BreakIterator.DONE) && (this.breakIteratorIndex <= this.segmentIndex)) {
        this.breakIteratorIndex = this.breakIterator.next();
      }
      int newIndex = this.segmentIndex;
      char firstChar = text.charAt(newIndex++);
      int end = this.breakIteratorIndex;
      if (end == BreakIterator.DONE) {
        end = length;
      }
      TextSegmentType type = getCharacterType(firstChar);
      switch (type) {
        case NEWLINE:
          if (newIndex < end) {
            char next = text.charAt(newIndex);
            if ((getCharacterType(next) == TextSegmentType.NEWLINE) && (next != firstChar)) {
              newIndex++;
            }
          }
          break;
        case WORD:
          while ((newIndex < end) && Character.isLetter(text.charAt(newIndex))) {
            newIndex++;
          }
          break;
        case PUNCTUATION_CHARACTER:
          break;
        case NON_BREAKING_CHARACTER:
          break;
        case CHARACTERS:
          while (newIndex < end) {
            char c = text.charAt(newIndex);
            if (getCharacterType(c) != TextSegmentType.CHARACTERS) {
              break;
            }
            newIndex++;
          }
          break;
        default :
          throw new IllegalCaseException(TextSegmentType.class, type);
      }
      textSegment.initialize(this.segmentIndex, newIndex, type);
      this.segmentIndex = newIndex;
      return textSegment;
    }

    /**
     * This method gets the {@link TextSegmentType} corresponding to the given character.
     * 
     * @param c is the character to check.
     * @return the corresponding {@link TextSegmentType}.
     */
    private static TextSegmentType getCharacterType(char c) {

      if ((c == '\n') || (c == '\r')) {
        return TextSegmentType.NEWLINE;
      } else if ((c == UnicodeUtil.NO_BREAK_SPACE) || (c == ',') || (c == '!') || (c == ';') || (c == '?')) {
        return TextSegmentType.NON_BREAKING_CHARACTER;
      } else if ((c == '.') || (c == ',') || (c == '!') || (c == ';') || (c == '?')) {
        return TextSegmentType.PUNCTUATION_CHARACTER;
      } else if (Character.isLetter(c)) {
        return TextSegmentType.WORD;
      } else {
        return TextSegmentType.CHARACTERS;
      }
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
     * This method gets the physical {@link TextColumnInfo#getWidth() width}. A value of
     * {@link TextColumnInfo#WIDTH_AUTO_ADJUST} is replaced with a physical value.
     * 
     * @return the physical {@link TextColumnInfo#getWidth() width}.
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
     * This method determines if this column is complete (all {@link #getText() text} is appended).
     * 
     * @return <code>true</code> if complete, <code>false</code> otherwise.
     */
    public boolean isComplete() {

      return (this.textIndex >= getText().length());
    }

  }

  /**
   * This inner class represents the buffer used for the text of a text-cell (a line of content inside a
   * {@link ColumnState column}).
   */
  protected static class CellBuffer {

    /** @see #append(CharSequence) */
    private final StringBuilder buffer;

    /** @see #reset(int) */
    private int maxLength;

    /** @see #getRest() */
    private int rest;

    /**
     * The constructor.
     */
    protected CellBuffer() {

      super();
      this.buffer = new StringBuilder();
      this.maxLength = 0;
      this.rest = 0;
    }

    /**
     * This method clears this buffer. It has to be called at the beginning of each new cell.
     * 
     * @param maximumLength is the maximum length this buffer should reach. It should therefore be set to the
     *        space available for the current cell ({@link TextColumnInfo#getWidth() column-width} potentially
     *        reduced by indent, etc.).
     */
    protected void reset(int maximumLength) {

      this.buffer.setLength(0);
      this.maxLength = maximumLength;
      this.rest = maximumLength;
    }

    /**
     * This method gets the maximum length of this buffer and the according cell. It is set when this buffer
     * is {@link #reset(int) reseted}.
     * 
     * @return the maximum length.
     */
    public int getMaxLength() {

      return this.maxLength;
    }

    /**
     * This method gets the <em>rest</em>, which is the number of characters available until the buffer has
     * reached its end.
     * 
     * @see #append(CharSequence)
     * 
     * @return the number of characters left for this buffer (current cell).
     */
    public int getRest() {

      return this.rest;
    }

    /**
     * @see Appendable#append(CharSequence)
     * 
     * @param text is the text to append.
     * @return the current {@link #getRest() rest}.
     */
    protected int append(CharSequence text) {

      this.buffer.append(text);
      this.rest = this.maxLength - this.buffer.length();
      assert (this.rest >= 0);
      return this.rest;
    }

    /**
     * @see Appendable#append(CharSequence, int, int)
     * 
     * @param text is the {@link CharSequence} from which a {@link CharSequence#subSequence(int, int)
     *        subsequence} will be appended.
     * @param start is the index of the first character in the subsequence.
     * @param end is the index of the character following the last character in the subsequence.
     * @return the current {@link #getRest() rest}.
     */
    protected int append(CharSequence text, int start, int end) {

      if (text.subSequence(start, end).toString().contains("\n")) {
        throw new IllegalStateException();
      }
      this.buffer.append(text, start, end);
      this.rest = this.rest - (end - start);
      assert (this.rest >= 0);
      return this.rest;
    }

    /**
     * @see Appendable#append(char)
     * 
     * @param c is the character to append.
     * @return the current {@link #getRest() rest}.
     */
    protected int append(char c) {

      this.buffer.append(c);
      this.rest--;
      assert (this.rest >= 0);
      return this.rest;
    }

    /**
     * This method gets the {@link StringBuilder#length() length} of this buffer.
     * 
     * @return the number of characters in this buffer.
     */
    protected int length() {

      return this.buffer.length();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {

      return this.buffer.toString();
    }

  }

  /**
   * @see DefaultLineWrapper#autoAdjustWidthOfColumns(ColumnState[], TextTableInfo)
   */
  protected static final class AutoAdjustInfo implements Comparable<AutoAdjustInfo> {

    /** The {@link ColumnState}. */
    private final ColumnState columnState;

    /** The number of newlines + 1. */
    private int lineCount;

    /** The maximum length of a single unwrapped line. */
    private int lineLengthMax;

    /**
     * @return the number of lines.
     */
    public int getLineCount() {

      return this.lineCount;
    }

    /**
     * The constructor.
     * 
     * @param columnState is the {@link ColumnState}.
     */
    private AutoAdjustInfo(ColumnState columnState) {

      super();
      this.columnState = columnState;
      String text = columnState.getText();
      int textLength = text.length();
      int index = 0;
      while (index >= 0) {
        this.lineCount++;
        int nextIndex = text.indexOf(StringUtil.LINE_SEPARATOR_LF, index);
        int length = nextIndex - index;
        if (nextIndex < 0) {
          length = textLength - index;
          index = -1;
        } else {
          index = nextIndex + 1;
        }
        if (length > this.lineLengthMax) {
          this.lineLengthMax = length;
        }
      }
      // this.lineLengthAvg = textLength / this.lineCount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(AutoAdjustInfo other) {

      NlsNullPointerException.checkNotNull(AutoAdjustInfo.class, other);
      return this.columnState.width - other.columnState.width;
    }

    /**
     * 
     * @param totalTextLength is the total {@link String#length() length} of the {@link TextColumn#getText()
     *        text} of all auto-adjust columns.
     * @return the ratio of the text-length of this column according to the total text length.
     */
    public double getTextLengthRation(double totalTextLength) {

      if (totalTextLength == 0) {
        return 0;
      } else {
        return this.columnState.getText().length() / totalTextLength;
      }

    }
  }

}
