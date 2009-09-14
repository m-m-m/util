/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.sf.mmm.util.filter.api.CharFilter;
import net.sf.mmm.util.filter.base.ListCharFilter;
import net.sf.mmm.util.nls.api.NlsFormatter;
import net.sf.mmm.util.nls.api.NlsFormatterManager;
import net.sf.mmm.util.nls.api.NlsMessageFormatter;
import net.sf.mmm.util.nls.base.AbstractNlsFormatter;
import net.sf.mmm.util.scanner.base.CharSequenceScanner;
import net.sf.mmm.util.scanner.base.SimpleCharScannerSyntax;

/**
 * This is the implementation of the {@link NlsMessageFormatter} interface.<br>
 * <b>NOTE:</b><br>
 * This is more or less a rewrite of {@link java.text.MessageFormat} and is
 * syntax-compatible with the
 * {@link java.text.MessageFormat#applyPattern(String) MessageFormat-pattern}
 * -format. Some special (and somewhat sick) features as modifying internal
 * {@link java.text.Format}s or {@link java.text.FieldPosition} are NOT
 * supported. Currently also parsing is NOT supported.<br>
 * Instead this implementation is immutable and thread-safe. Further it works on
 * any {@link Appendable} rather than only on {@link StringBuffer}. It also uses
 * the same {@link Appendable} for recursive invocations.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class NlsMessageFormatterImpl extends AbstractNlsFormatter<Map<String, Object>> implements
    NlsMessageFormatter {

  /** A char filter that accepts everything except ',' and '}'. */
  private static final CharFilter NO_COMMA_OR_CCB = new ListCharFilter(false, ',', '}');

  /** The syntax of the message-format patterns. */
  private static final SimpleCharScannerSyntax SYNTAX = new SimpleCharScannerSyntax();

  static {
    SYNTAX.setQuote('\'');
    SYNTAX.setQuoteEscape('\'');
    SYNTAX.setQuoteEscapeLazy(true);
  }

  /** The manager of the formatters. */
  private final NlsFormatterManager formatterManager;

  /** The parsed segments of the message pattern. */
  private final PatternSegment[] segments;

  /** The suffix (last segment) of the pattern. */
  private final String suffix;

  /**
   * The constructor.
   * 
   * @param pattern is the pattern of the message to format. It is
   *        syntax-compatible with {@link java.text.MessageFormat}.
   * @param formatterManager is used to create {@link NlsFormatter}s according
   *        to <code>FormatType</code> and <code>FormatStyle</code> of the
   *        <code>FormatElement</code>s (see {@link java.text.MessageFormat}).
   */
  public NlsMessageFormatterImpl(String pattern, NlsFormatterManager formatterManager) {

    super();
    this.formatterManager = formatterManager;
    List<PatternSegment> segmentList = new ArrayList<PatternSegment>();
    CharSequenceScanner scanner = new CharSequenceScanner(pattern);
    String prefix = scanner.readUntil('{', true, SYNTAX);
    while (scanner.hasNext()) {
      String key = scanner.readWhile(CharFilter.LATIN_DIGIT_OR_LETTER_FILTER);
      char c = scanner.next();
      String formatType = null;
      String formatStyle = null;
      if (c == ',') {
        formatType = scanner.readWhile(NO_COMMA_OR_CCB);
        formatStyle = null;
        c = scanner.next();
        if (c == ',') {
          formatStyle = scanner.readUntil('}', false);
          if (formatStyle != null) {
            c = '}';
          }
        }
      }
      if (c != '}') {
        throw new IllegalArgumentException("Unmatched braces in the pattern.");
      }
      NlsFormatter<Object> formatter = this.formatterManager.getFormatter(formatType, formatStyle);
      PatternSegment segment = new PatternSegment(prefix, key, formatter);
      segmentList.add(segment);
      prefix = scanner.readUntil('{', true, SYNTAX);
    }
    this.suffix = prefix;
    this.segments = segmentList.toArray(new PatternSegment[segmentList.size()]);
  }

  /**
   * {@inheritDoc}
   */
  public final void format(Map<String, Object> arguments, Locale locale, Appendable buffer) {

    try {
      for (PatternSegment segment : this.segments) {
        buffer.append(segment.prefix);
        Object argument = null;
        if (arguments != null) {
          argument = arguments.get(segment.key);
        }
        if (argument == null) {
          buffer.append('{');
          buffer.append(segment.key);
          buffer.append('}');
        } else {
          NlsFormatter<Object> formatter = segment.formatter;
          if (formatter == null) {
            // should actually never happen...
            formatter = NlsFormatterDefault.INSTANCE;
          }
          formatter.format(argument, locale, buffer);
        }
      }
      buffer.append(this.suffix);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  /**
   * This inner class represents a segment out of the parsed message-pattern.<br>
   * E.g. if the message-pattern is "Hi {0} you have {1} items!" then it is
   * parsed into two {@link PatternSegment}s. The first has a {@link #prefix} of
   * <code>"Hi "</code> and {@link #key} of <code>0</code> and the second has
   * <code>" you have "</code> as {@link #prefix} and {@link #key} of
   * <code>1</code>. The rest of the pattern which is <code>" items!"</code>
   * will be stored in {@link NlsMessageFormatterImpl#suffix}.
   */
  protected static class PatternSegment {

    /** @see #getPrefix() */
    private final String prefix;

    /** @see #getKey() */
    private final String key;

    /** @see #getFormatter() */
    private final NlsFormatter<Object> formatter;

    /**
     * The constructor.
     * 
     * @param prefix is the {@link #getPrefix() prefix}.
     * @param key is the {@link #getKey() key}.
     * @param formatter is the {@link #getFormatter() formatter}.
     */
    public PatternSegment(String prefix, String key, NlsFormatter<Object> formatter) {

      super();
      this.prefix = prefix;
      this.key = key;
      this.formatter = formatter;
    }

    /**
     * This method gets the prefix. This is the raw part of the message-pattern
     * (until the next '{') that will be taken as is.
     * 
     * @return the prefix
     */
    public String getPrefix() {

      return this.prefix;
    }

    /**
     * This method gets the key of the argument to format and append after the
     * {@link #getPrefix() prefix}.
     * 
     * @return the key
     */
    public String getKey() {

      return this.key;
    }

    /**
     * Is the formatter used to format the {@link #getKey() argument}.
     * 
     * @return the formatter
     */
    public NlsFormatter<Object> getFormatter() {

      return this.formatter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {

      return this.prefix + "{" + this.key + "}";
    }

  }

}
