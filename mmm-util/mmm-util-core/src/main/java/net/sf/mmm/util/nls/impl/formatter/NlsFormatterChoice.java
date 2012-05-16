/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl.formatter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.sf.mmm.util.date.api.Iso8601Util;
import net.sf.mmm.util.filter.api.CharFilter;
import net.sf.mmm.util.filter.api.Filter;
import net.sf.mmm.util.filter.base.ConjunctionCharFilter;
import net.sf.mmm.util.filter.base.ConstantFilter;
import net.sf.mmm.util.filter.base.ListCharFilter;
import net.sf.mmm.util.lang.api.Comparator;
import net.sf.mmm.util.lang.api.Conjunction;
import net.sf.mmm.util.nls.api.NlsArgument;
import net.sf.mmm.util.nls.api.NlsArgumentParser;
import net.sf.mmm.util.nls.api.NlsFormatterManager;
import net.sf.mmm.util.nls.api.NlsParseException;
import net.sf.mmm.util.nls.api.NlsTemplateResolver;
import net.sf.mmm.util.nls.base.AbstractNlsFormatterPlugin;
import net.sf.mmm.util.nls.base.NlsDependencies;
import net.sf.mmm.util.scanner.base.CharSequenceScanner;

/**
 * This is the implementation of {@link net.sf.mmm.util.nls.api.NlsFormatter} for
 * {@link net.sf.mmm.util.nls.api.NlsFormatterManager#TYPE_CHOICE choice-format}.<br>
 * Examples:<br>
 * <table border="1">
 * <tr>
 * <th>{@link net.sf.mmm.util.nls.api.NlsMessage}</th>
 * <th>Example result</th>
 * </tr>
 * <tr>
 * <td>{deleteCount} {deleteCount,choice,(?==1)['files'](else)['file']} deleted.</td>
 * <td>1 file deleted.</td>
 * </tr>
 * <tr>
 * <td>{flag,choice,(?==true){date}(else){time}}</td>
 * <td>23:59:59</td>
 * </tr>
 * </table>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public final class NlsFormatterChoice extends AbstractNlsFormatterPlugin<Object> {

  /** The format of the segments. */
  private static final String REQUIRED_FORMAT_SEGMENTS = "({...}|'.*'])+";

  /** The format of a comparator. */
  private static final String REQUIRED_FORMAT_COMPARATOR = "(==|!=|>=|>|<=|<)";

  /** The format of a condition. */
  private static final String REQUIRED_FORMAT_CONDITION = "(else|?" + REQUIRED_FORMAT_COMPARATOR + "[0-9a-zA-Z+-.:])";

  /** The character used to indicate the start of a {@link Choice} condition. */
  public static final char CONDITION_START = '(';

  /** The character used to indicate the end of a {@link Choice} condition. */
  public static final char CONDITION_END = ')';

  /**
   * The character used to indicate the variable object of a {@link Choice} condition.
   */
  public static final char CONDITION_VAR = '?';

  /** The value of a {@link Choice} condition that matches always. */
  public static final String CONDITION_ELSE = "else";

  /** The {@link Filter} for {@link #CONDITION_ELSE}. */
  private static final Filter<Object> FILTER_ELSE = ConstantFilter.getInstance(true);

  /**
   * The {@link CharFilter} for the {@link Comparator#getValue() comparator symbol} .
   */
  private static final CharFilter FILTER_COMPARATOR = new ListCharFilter(true, '<', '=', '>', '!');

  /** The {@link CharFilter} for the comparator argument. */
  private static final CharFilter FILTER_COMPARATOR_ARGUMENT = new ConjunctionCharFilter(Conjunction.OR,
      CharFilter.LATIN_DIGIT_OR_LETTER_FILTER, new ListCharFilter(true, '-', '+', '.', ':'));

  /** The {@link NlsDependencies} to use. */
  private final NlsDependencies nlsDependencies;

  /** The {@link Choice}s. */
  private final List<Choice> choices;

  /**
   * The constructor.
   * 
   * @param scanner is the {@link CharSequenceScanner} pointing to the choice- <code>formatStyle</code>.
   * @param nlsDependencies are the {@link NlsDependencies} to use.
   */
  public NlsFormatterChoice(CharSequenceScanner scanner, NlsDependencies nlsDependencies) {

    super();
    this.nlsDependencies = nlsDependencies;
    this.choices = new ArrayList<Choice>();
    boolean hasElse = false;
    char c = scanner.forceNext();
    while ((c == CONDITION_START) && (!hasElse)) {
      Choice choice = parseChoice(scanner);
      if (choice.condition == FILTER_ELSE) {
        hasElse = true;
      }
      this.choices.add(choice);
      c = scanner.forceNext();
    }
    if (!hasElse) {
      throw new NlsFormatterChoiceNoElseConditionException();
    }
    if (this.choices.size() < 2) {
      throw new NlsFormatterChoiceOnlyElseConditionException();
    }
    scanner.stepBack();
  }

  /**
   * This method parses the {@link Choice}.
   * 
   * @param scanner is the {@link CharSequenceScanner}.
   * @return the parsed {@link Choice}.
   */
  private Choice parseChoice(CharSequenceScanner scanner) {

    Filter<Object> condition = parseCondition(scanner);
    List<Segment> segments = new ArrayList<NlsFormatterChoice.Segment>();
    while (scanner.hasNext()) {
      int index = scanner.getCurrentIndex();
      char c = scanner.peek();
      String literal = null;
      if ((c == '"') || (c == '\'')) {
        scanner.next();
        literal = scanner.readUntil(c, false, c);
        if (literal == null) {
          throw new NlsParseException(scanner.substring(index, scanner.getCurrentIndex()), REQUIRED_FORMAT_SEGMENTS,
              NlsArgument.class);
        }
        c = scanner.peek();
      }
      NlsArgument argument = null;
      if (c == NlsArgumentParser.START_EXPRESSION) {
        scanner.next();
        argument = this.nlsDependencies.getArgumentParser().parse(scanner);
      }
      if ((argument != null) || (literal != null)) {
        segments.add(new Segment(literal, argument));
      } else {
        break;
      }
    }
    // char c = scanner.forceNext();
    // if (c == NlsArgumentParser.START_EXPRESSION) {
    // NlsArgument argument = this.nlsDependencies.getArgumentParser().parse(scanner);
    // choice = new Choice(condition, null, argument);
    // } else if ((c == '"') || (c == '\'')) {
    // String result = scanner.readUntil(c, false, c);
    // choice = new Choice(condition, result, null);
    // } else {
    // throw new NlsParseException(scanner.substring(index, scanner.getCurrentIndex()), "({...}|'.*'])",
    // NlsArgument.class);
    // }
    return new Choice(condition, segments);
  }

  /**
   * This method parses the {@link Condition}.
   * 
   * @param scanner is the {@link CharSequenceScanner}.
   * @return the parsed {@link Condition} or {@link #FILTER_ELSE} in case of {@link #CONDITION_ELSE}.
   */
  private Filter<Object> parseCondition(CharSequenceScanner scanner) {

    int index = scanner.getCurrentIndex();
    Filter<Object> condition;
    if (scanner.expect(CONDITION_VAR)) {
      // variable choice
      String symbol = scanner.readWhile(FILTER_COMPARATOR);
      Comparator comparator = Comparator.fromValue(symbol);
      if (comparator == null) {
        throw new NlsParseException(symbol, REQUIRED_FORMAT_COMPARATOR, Comparator.class);
      }
      Object comparatorArgument = parseComparatorArgument(scanner);
      condition = new Condition(comparator, comparatorArgument);
    } else if (scanner.expect(CONDITION_ELSE, false)) {
      condition = FILTER_ELSE;
    } else {
      throw new NlsParseException(scanner.substring(index, scanner.getCurrentIndex()), REQUIRED_FORMAT_CONDITION,
          getType());
    }
    if (!scanner.expect(CONDITION_END)) {
      throw new NlsParseException(scanner.substring(index, scanner.getCurrentIndex()), REQUIRED_FORMAT_CONDITION,
          getType());
    }
    return condition;
  }

  /**
   * This method parses the {@link Condition#comparatorArgument comparator argument}.
   * 
   * @param scanner is the {@link CharSequenceScanner}.
   * @return the parsed comparator argument.
   */
  private Object parseComparatorArgument(CharSequenceScanner scanner) {

    int index = scanner.getCurrentIndex();
    Object comparatorArgument;
    char c = scanner.forcePeek();
    if ((c == '"') || (c == '\'')) {
      scanner.next();
      comparatorArgument = scanner.readUntil(c, false, c);
    } else {
      String argument = scanner.readWhile(FILTER_COMPARATOR_ARGUMENT);
      if (argument.length() == 0) {
        throw new NlsParseException(scanner.substring(index, scanner.getCurrentIndex()), REQUIRED_FORMAT_CONDITION,
            getType());
      }
      if ("null".equals(argument)) {
        comparatorArgument = null;
      } else if (argument.matches(Iso8601Util.PATTERN_STRING_ALL)) {
        comparatorArgument = this.nlsDependencies.getIso8601Util().parseDate(argument);
      } else if (Boolean.TRUE.toString().equals(argument)) {
        comparatorArgument = Boolean.TRUE;
      } else if (Boolean.FALSE.toString().equals(argument)) {
        comparatorArgument = Boolean.FALSE;
      } else {
        // double vs. date?
        comparatorArgument = Double.valueOf(argument);
      }
    }
    return comparatorArgument;
  }

  /**
   * {@inheritDoc}
   */
  public void format(Object object, Locale locale, Map<String, Object> arguments, NlsTemplateResolver resolver,
      Appendable buffer) throws IOException {

    for (Choice choice : this.choices) {
      if (choice.condition.accept(object)) {
        for (Segment segment : choice.segments) {
          buffer.append(segment.literal);
          if (segment.argument != null) {
            this.nlsDependencies.getArgumentFormatter().format(segment.argument, locale, arguments, resolver, buffer);
          }
        }
        return;
      }
    }
    buffer.append(toString());
  }

  /**
   * {@inheritDoc}
   */
  public String getType() {

    return NlsFormatterManager.TYPE_CHOICE;
  }

  /**
   * {@inheritDoc}
   */
  public String getStyle() {

    return null;
  }

  /**
   * This inner class represents a single choice.
   */
  private static final class Choice {

    /** The condition that determines when the choice applies. */
    private final Filter<Object> condition;

    /** The segments */
    private final List<Segment> segments;

    /**
     * The constructor.
     * 
     * @param condition is the {@link #condition}.
     * @param segments is the {@link List} of {@link Segment}s.
     */
    private Choice(Filter<Object> condition, List<Segment> segments) {

      super();
      this.condition = condition;
      this.segments = segments;
    }
  }

  /**
   * This inner class represents a single segment of a {@link Choice}.
   */
  private static class Segment {

    /** The literal result. */
    private final String literal;

    /**
     * The {@link NlsArgument} to use as result or <code>null</code> if this is the last {@link Segment}.
     */
    private final NlsArgument argument;

    /**
     * The constructor.
     * 
     * @param literal is the literal (prefix).
     * @param argument is the {@link NlsArgument} or <code>null</code> if this is the last {@link Segment}.
     */
    public Segment(String literal, NlsArgument argument) {

      super();
      if (literal == null) {
        this.literal = "";
      } else {
        this.literal = literal;
      }
      this.argument = argument;
    }

  }

  /**
   * This inner class represents a single choice.
   */
  private static class Condition implements Filter<Object> {

    /** The {@link Comparator}. */
    private final Comparator comparator;

    /** The argument for the {@link #comparator}. */
    private final Object comparatorArgument;

    /**
     * The constructor.
     * 
     * @param comparator is the {@link #comparator}.
     * @param comparatorArgument is the {@link #comparatorArgument}.
     */
    public Condition(Comparator comparator, Object comparatorArgument) {

      super();
      this.comparator = comparator;
      this.comparatorArgument = comparatorArgument;
    }

    /**
     * {@inheritDoc}
     */
    public boolean accept(Object value) {

      if (this.comparator == null) {
        return true;
      }
      return this.comparator.eval(value, this.comparatorArgument);
    }
  }

}
