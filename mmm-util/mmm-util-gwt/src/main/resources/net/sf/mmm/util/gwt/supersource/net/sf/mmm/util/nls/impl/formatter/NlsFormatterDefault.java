/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl.formatter;

import java.io.IOException;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.nls.api.NlsObject;
import net.sf.mmm.util.nls.api.NlsTemplateResolver;
import net.sf.mmm.util.nls.base.AbstractNlsFormatterPlugin;

/**
 * This is an implementation of {@link net.sf.mmm.util.nls.api.NlsFormatter} for default formatting.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Named
@Singleton
public class NlsFormatterDefault extends AbstractNlsFormatterPlugin<Object> {

  /**
   * The constructor.
   */
  public NlsFormatterDefault() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public void format(Object object, Locale locale, Map<String, Object> arguments, NlsTemplateResolver resolver,
      Appendable buffer) throws IOException {

    String result = null;
    if (object != null) {
      if (object instanceof Number) {
        // result = NumberFormat.getInstance(locale).format(object);
      } else if (object instanceof Date) {
        // DateFormat format = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, locale);
        // result = format.format(object);
      } else if (object instanceof NlsObject) {
        NlsMessage message = ((NlsObject) object).toNlsMessage();
        if (message != null) {
          message.getLocalizedMessage(locale, resolver, buffer);
          return;
        }
      } else if (object instanceof Class<?>) {
        result = ((Class<?>) object).getName();
      } else {
        result = object.toString();
      }
    }
    if (result == null) {
      result = "null";
    }
    buffer.append(result);
  }

  /**
   * {@inheritDoc}
   */
  public String getStyle() {

    return null;
  }

  /**
   * {@inheritDoc}
   */
  public String getType() {

    return null;
  }

}
