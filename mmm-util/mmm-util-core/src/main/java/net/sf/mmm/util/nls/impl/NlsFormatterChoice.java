/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

import java.io.IOException;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import net.sf.mmm.util.filter.api.Filter;
import net.sf.mmm.util.lang.api.Comparator;
import net.sf.mmm.util.nls.api.NlsFormatterManager;
import net.sf.mmm.util.nls.api.NlsTemplateResolver;
import net.sf.mmm.util.nls.base.AbstractNlsFormatter;
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
public final class NlsFormatterChoice extends AbstractNlsFormatter<Object> {

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

  /** The {@link Choice}s. */
  private final Choice[] choices;

  /**
   * The constructor.
   * 
   * @param scanner is the {@link CharSequenceScanner} pointing to the choice-
   *        <code>formatStyle</code>.
   */
  public NlsFormatterChoice(CharSequenceScanner scanner, NlsFormatterManager formatterManager) {

    super();
    scanner.expect(CONDITION_START);
    // scanner.read(count)
    this.choices = new Choice[0];

  }

  /**
   * {@inheritDoc}
   */
  public void format(Object object, Locale locale, Map<String, Object> arguments,
      Appendable buffer, NlsTemplateResolver resolver) throws IOException {

    for (Choice choice : this.choices) {
      if (choice.accept(object)) {
        Object result = choice.getResult(arguments);
        new NlsFormatterDefault().format(result, locale, arguments, buffer, resolver);
        break;
      }
    }
    buffer.append(toString());
  }

  /**
   * 
   */
  private static class Choice implements Filter<Object> {

    /** The {@link Comparator}. */
    private Comparator comparator;

    private String comparatorArgument;

    private Object result;

    private String resultKey;

    /**
     * {@inheritDoc}
     */
    public boolean accept(Object value) {

      if (this.comparator == null) {
        return true;
      }
      Object o = this.comparatorArgument;
      if (value != null) {
        if (value instanceof Number) {
          o = Double.valueOf(this.comparatorArgument);
        } else if (value instanceof Date) {

        }
      }
      return this.comparator.eval(value, o);
    }

    /**
     * This method determines the result of this {@link Choice} in case it
     * {@link #accept(Object) matched}.
     * 
     * @param arguments are the
     *        {@link net.sf.mmm.util.nls.api.NlsMessage#getArgument(String)
     *        arguments} .
     * @return the result.
     */
    public Object getResult(Map<String, Object> arguments) {

      if (this.resultKey == null) {
        return this.result;
      } else {
        Object arg = arguments.get(this.resultKey);
        if (arg == null) {
          arg = "{" + this.resultKey + "}";
        }
        return arg;
      }
    }
  }

}
