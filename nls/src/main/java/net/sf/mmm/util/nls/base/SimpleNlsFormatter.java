/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import net.sf.mmm.util.lang.api.Formatter;
import net.sf.mmm.util.nls.api.NlsTemplateResolver;

/**
 * This is an abstract base implementation of {@link net.sf.mmm.util.nls.api.NlsFormatter} based on {@link Formatter}.
 *
 * @param <O> is the generic type of the object to {@link #format(Object, Locale, Map, NlsTemplateResolver)}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class SimpleNlsFormatter<O> extends AbstractNlsFormatterPlugin<O> {

  /**
   * The constructor.
   */
  public SimpleNlsFormatter() {

    super();
  }

  /**
   * This method creates the underlying {@link Formatter} to delegate to.
   *
   * @param locale is the locale of the {@link Formatter} to create.
   * @return the according format.
   */
  protected abstract Formatter<O> createFormatter(Locale locale);

  @Override
  public String format(O object, Locale locale, Map<String, Object> arguments, NlsTemplateResolver resolver) {

    return createFormatter(locale).format(object);
  }

  @Override
  public void format(O object, Locale locale, Map<String, Object> arguments, NlsTemplateResolver resolver,
      Appendable buffer) {

    try {
      buffer.append(format(object, locale, arguments, resolver));
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

}
