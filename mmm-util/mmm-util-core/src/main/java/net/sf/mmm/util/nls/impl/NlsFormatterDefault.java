/* $Id: NlsFormatterDefault.java 401 2008-01-13 21:02:06Z hohwille $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.nls.api.NlsObject;
import net.sf.mmm.util.nls.api.NlsTemplateResolver;
import net.sf.mmm.util.nls.base.AbstractNlsFormatter;

/**
 * This is an implementation of {@link net.sf.mmm.util.nls.api.NlsFormatter} for
 * default formatting.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class NlsFormatterDefault extends AbstractNlsFormatter<Object> {

  /** The default singleton instance. */
  public static final NlsFormatterDefault INSTANCE = new NlsFormatterDefault();

  /** The template resolver. */
  private final NlsTemplateResolver templateResolver;

  /**
   * The constructor.
   */
  private NlsFormatterDefault() {

    this(null);
  }

  /**
   * The constructor.
   * 
   * @param templateResolver is the resolver used for {@link NlsObject}s.
   */
  public NlsFormatterDefault(NlsTemplateResolver templateResolver) {

    super();
    this.templateResolver = templateResolver;
  }

  /**
   * {@inheritDoc}
   */
  public void format(Object object, Locale locale, Appendable buffer) {

    try {
      String result = null;
      if (object != null) {
        if (object instanceof Number) {
          result = NumberFormat.getInstance(locale).format(object);
        } else if (object instanceof Date) {
          DateFormat format = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT,
              locale);
          result = format.format(object);
        } else if (object instanceof NlsObject) {
          NlsMessage message = ((NlsObject) object).toNlsMessage();
          if (message != null) {
            message.getLocalizedMessage(locale, this.templateResolver, buffer);
            return;
          }
        } else {
          result = object.toString();
        }
      }
      if (result == null) {
        result = "null";
      }
      buffer.append(result);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }
}
