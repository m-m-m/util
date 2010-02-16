/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

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
import net.sf.mmm.util.nls.base.AbstractNlsSubFormatter;
import net.sf.mmm.util.scanner.base.CharSequenceScanner;

/**
 * This is the implementation of {@link net.sf.mmm.util.nls.api.NlsFormatter}
 * for {@link net.sf.mmm.util.nls.api.NlsFormatterManager#TYPE_CHOICE
 * choice-format}.<br>
 * Examples:<br>
 * <table border="1">
 * <tr>
 * <th>{@link net.sf.mmm.util.nls.api.NlsMessage}</th>
 * <th>Example result</th>
 * </tr>
 * <tr>
 * <td>{deleteCount} {deleteCount,choice,(?==1)['files'](else)['file']} deleted.
 * </td>
 * <td>1 file deleted.</td>
 * </tr>
 * <tr>
 * <td>{flag,choice,(?==true){date}(else){time}}</td>
 * <td>23:59:59</td>
 * </tr>
 * </table>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.2
 */
public final class NlsFormatterChoice extends AbstractNlsSubFormatter<Object> {

  /** TODO: javadoc. */
  private static final String REQUIRED_FORMAT_COMPARATOR = "(==|!=|>=|>|<=|<)";

  /** TODO: javadoc. */
  private static final String REQUIRED_FORMAT_CONDITION = "(else|?" + REQUIRED_FORMAT_COMPARATOR
      + "[0-9a-zA-Z+-.:])";

  /** The character used to indicate the start of a {@link Choice} condition. */
  public static final char CONDITION_START = '(';

  /** The character used to indicate the end of a {@link Choice} condition. */
  public static final char CONDITION_END = ')';

  /**
   * The character used to indicate the variable object of a {@link Choice}
   * condition.
   */
  public static final char CONDITION_VAR = '?';

  /** The value of a {@link Choice} condition that matches always. */
  public static final String CONDITION_ELSE = "else";

  /** The {@link Filter} for {@link #CONDITION_ELSE}. */
  private static final Filter<Object> FILTER_ELSE = ConstantFilter.getInstance(true);

  /**
   * The {@link CharFilter} for the {@link Comparator#getSymbol() comparator
   * symbol} .
   */
  private static final CharFilter FILTER_COMPARATOR = new ListCharFilter(true, '<', '=', '>', '!');

  /** The {@link CharFilter} for the comparator argument. */
  private static final CharFilter FILTER_COMPARATOR_ARGUMENT = new ConjunctionCharFilter(
      Conjunction.OR, CharFilter.LATIN_DIGIT_OR_LETTER_FILTER, new ListCharFilter(true, '-', '+',
          '.', ':'));

  /** The {@link Iso8601Util} used to parse dates. */
  private final Iso8601Util iso8601Util;

  /** The {@link Choice}s. */
  private final List<Choice> choices;

  /**
   * The constructor.
   * 
   * @param scanner is the {@link CharSequenceScanner} pointing to the choice-
   *        <code>formatStyle</code>.
   * @param argumentParser is the {@link NlsArgumentParser} instance.
   * @param iso8601Util is the {@link Iso8601Util} instance used to parse dates.
   */
  public NlsFormatterChoice(CharSequenceScanner scanner, NlsArgumentParser argumentParser,
      Iso8601Util iso8601Util) {

    super();
    this.iso8601Util = iso8601Util;
    this.choices = new ArrayList<Choice>();
    boolean hasElse = false;
    char c = scanner.forceNext();
    while ((c == CONDITION_START) && (!hasElse)) {
      Choice choice = parseChoice(scanner, argumentParser);
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
   * @param argumentParser is the {@link NlsArgumentParser}.
   * @return the parsed {@link Choice}.
   */
  private Choice parseChoice(CharSequenceScanner scanner, NlsArgumentParser argumentParser) {

    Filter<Object> condition = parseCondition(scanner);
    int index = scanner.getCurrentIndex();
    char c = scanner.forceNext();
    Choice choice;
    if (c == NlsArgumentParser.START_EXPRESSION) {
      NlsArgument argument = argumentParser.parse(scanner);
      choice = new Choice(condition, null, argument);
    } else if ((c == '"') || (c == '\'')) {
      String result = scanner.readUntil(c, false, c);
      choice = new Choice(condition, result, null);
    } else {
      throw new NlsParseException(scanner.substring(index, scanner.getCurrentIndex()),
          "({...}|'.*'])", NlsArgument.class);
    }
    return choice;
  }

  /**
   * This method parses the {@link Condition}.
   * 
   * @param scanner is the {@link CharSequenceScanner}.
   * @return the parsed {@link Condition} or {@link #FILTER_ELSE} in case of
   *         {@link #CONDITION_ELSE}.
   */
  private Filter<Object> parseCondition(CharSequenceScanner scanner) {

    int index = scanner.getCurrentIndex();
    Filter<Object> condition;
    if (scanner.expect(CONDITION_VAR)) {
      // variable choice
      String symbol = scanner.readWhile(FILTER_COMPARATOR);
      Comparator comparator = Comparator.fromSymbol(symbol);
      if (comparator == null) {
        throw new NlsParseException(symbol, REQUIRED_FORMAT_COMPARATOR, Comparator.class);
      }
      Object comparatorArgument = parseComparatorArgument(scanner);
      condition = new Condition(comparator, comparatorArgument);
    } else if (scanner.expect(CONDITION_ELSE, false)) {
      condition = FILTER_ELSE;
    } else {
      throw new NlsParseException(scanner.substring(index, scanner.getCurrentIndex()),
          REQUIRED_FORMAT_CONDITION, getType());
    }
    if (!scanner.expect(CONDITION_END)) {
      throw new NlsParseException(scanner.substring(index, scanner.getCurrentIndex()),
          REQUIRED_FORMAT_CONDITION, getType());
    }
    return condition;
  }

  /**
   * This method parses the {@link Condition#comparatorArgument comparator
   * argument}.
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
        throw new NlsParseException(scanner.substring(index, scanner.getCurrentIndex()),
            REQUIRED_FORMAT_CONDITION, getType());
      }
      if ("null".equals(argument)) {
        comparatorArgument = null;
      } else if (Iso8601Util.PATTERN_ALL.matcher(argument).matches()) {
        comparatorArgument = this.iso8601Util.parseDate(argument);
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
  public void format(Object object, Locale locale, Map<String, Object> arguments,
      NlsTemplateResolver resolver, Appendable buffer) throws IOException {

    for (Choice choice : this.choices) {
      if (choice.condition.accept(object)) {
        if (choice.argument != null) {
          NlsArgumentFormatter.INSTANCE
              .format(choice.argument, locale, arguments, resolver, buffer);
        } else {
          buffer.append(choice.result);
        }
        return;
      }
    }
    buffer.append(toString());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getType() {

    return NlsFormatterManager.TYPE_CHOICE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getStyle() {

    return null;
  }

  /**
   * This inner class represents a single choice.
   */
  private static final class Choice {

    /** The condition that determines when the choice applies. */
    private final Filter<Object> condition;

    /**
     * The {@link NlsArgument} to use as result or <code>null</code> if
     * {@link #result} should be used instead.
     */
    private final NlsArgument argument;

    /** The literal result. */
    private final String result;

    /**
     * The constructor.
     * 
     * @param condition is the {@link #condition}.
     * @param result is the {@link #result}.
     * @param argument is the {@link #argument}.
     */
    private Choice(Filter<Object> condition, String result, NlsArgument argument) {

      super();
      this.condition = condition;
      this.result = result;
      this.argument = argument;
    }
  }

  /**
   * This inner class represents a single choice.
   */
  private class Condition implements Filter<Object> {

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
