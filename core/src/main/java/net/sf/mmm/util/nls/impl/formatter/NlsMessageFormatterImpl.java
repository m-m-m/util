/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl.formatter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.sf.mmm.util.exception.api.NlsNullPointerException;
import net.sf.mmm.util.exception.api.NlsParseException;
import net.sf.mmm.util.nls.api.NlsArgument;
import net.sf.mmm.util.nls.api.NlsArgumentParser;
import net.sf.mmm.util.nls.api.NlsTemplateResolver;
import net.sf.mmm.util.nls.base.AbstractNlsMessageFormatter;
import net.sf.mmm.util.nls.base.NlsDependencies;
import net.sf.mmm.util.scanner.base.CharSequenceScanner;

/**
 * This is the implementation of the {@link net.sf.mmm.util.nls.api.NlsMessageFormatter} interface. <br>
 * <b>NOTE:</b><br>
 * This is more or less a rewrite of {@link java.text.MessageFormat} and is syntax-compatible with the
 * {@link java.text.MessageFormat#applyPattern(String) MessageFormat-pattern} -format. Some special (and somewhat sick)
 * features as modifying internal {@link java.text.Format}s or {@link java.text.FieldPosition} are NOT supported.
 * Currently also parsing is NOT supported. <br>
 * Instead this implementation is immutable and thread-safe. Further it works on any {@link Appendable} rather than only
 * on {@link StringBuffer}. It also uses the same {@link Appendable} for recursive invocations.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class NlsMessageFormatterImpl extends AbstractNlsMessageFormatter {

  /** The parsed segments of the message pattern. */
  private final PatternSegment[] segments;

  /** The suffix (last segment) of the pattern. */
  private final String suffix;

  /** The {@link NlsDependencies} to use. */
  private final NlsDependencies nlsDependnecies;

  /**
   * The constructor.
   *
   * @param pattern is the pattern of the message to format. It is syntax-compatible with
   *        {@link java.text.MessageFormat}.
   * @param nlsDependencies are the {@link NlsDependencies}.
   */
  public NlsMessageFormatterImpl(String pattern, NlsDependencies nlsDependencies) {

    super();
    NlsNullPointerException.checkNotNull(NlsDependencies.class, nlsDependencies);
    this.nlsDependnecies = nlsDependencies;
    List<PatternSegment> segmentList = new ArrayList<>();
    CharSequenceScanner scanner = new CharSequenceScanner(pattern);
    String prefix = scanner.readUntil(NlsArgumentParser.START_EXPRESSION, true, SYNTAX);
    while (scanner.hasNext()) {
      NlsArgument argument;
      int index = scanner.getCurrentIndex() - 1;
      try {
        argument = this.nlsDependnecies.getArgumentParser().parse(scanner);
      } catch (Exception e) {
        throw new NlsParseException(e, scanner.substring(index, scanner.getCurrentIndex()), NlsArgument.class);
      }
      PatternSegment segment = new PatternSegment(prefix, argument);
      segmentList.add(segment);
      prefix = scanner.readUntil(NlsArgumentParser.START_EXPRESSION, true, SYNTAX);
    }
    this.suffix = prefix;
    this.segments = segmentList.toArray(new PatternSegment[segmentList.size()]);
  }

  @Override
  public final void format(Void nothing, Locale locale, Map<String, Object> arguments,
      NlsTemplateResolver resolver, Appendable buffer) throws IOException {

    for (PatternSegment segment : this.segments) {
      buffer.append(segment.prefix);
      NlsArgument argument = segment.argument;
      this.nlsDependnecies.getArgumentFormatter().format(argument, locale, arguments, resolver, buffer);
    }
    buffer.append(this.suffix);
  }

  /**
   * @return the suffix to append after all {@link PatternSegment}s.
   */
  public String getSuffix() {

    return this.suffix;
  }

  /**
   * This inner class represents a segment out of the parsed message-pattern. <br>
   * E.g. if the message-pattern is "Hi {0} you have {1} items!" then it is parsed into two {@link PatternSegment}s. The
   * first has a {@link #getPrefix() prefix} of {@code "Hi "} and {@link #getArgument() argument} of
   * <code>{0}</code> and the second has {@code " you have "} as {@link #getPrefix() prefix} and
   * {@link #getArgument() argument} of <code>{1}</code>. The rest of the pattern which is {@code " items!"} will
   * be stored in {@link NlsMessageFormatterImpl#getSuffix() suffix}.
   */
  protected static class PatternSegment {

    /** @see #getPrefix() */
    private final String prefix;

    /** @see #getArgument() */
    private final NlsArgument argument;

    /**
     * The constructor.
     *
     * @param prefix is the {@link #getPrefix() prefix}.
     * @param argument is the {@link #getArgument() argument}.
     */
    public PatternSegment(String prefix, NlsArgument argument) {

      super();
      this.prefix = prefix;
      this.argument = argument;
    }

    /**
     * This method gets the prefix. This is the raw part of the message-pattern (until the next '{') that will be taken
     * as is.
     *
     * @return the prefix
     */
    public String getPrefix() {

      return this.prefix;
    }

    /**
     * This method gets the {@link NlsArgument}.
     *
     * @return the argument.
     */
    public NlsArgument getArgument() {

      return this.argument;
    }

    @Override
    public String toString() {

      StringBuilder sb = new StringBuilder();
      sb.append(this.prefix);
      sb.append(this.argument);
      return sb.toString();
    }

  }

}
