/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl.formatter;

import java.io.IOException;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import net.sf.mmm.util.date.api.Iso8601Util;
import net.sf.mmm.util.date.api.Iso8601UtilLimited;
import net.sf.mmm.util.date.base.Iso8601UtilLimitedImpl;
import net.sf.mmm.util.io.api.IoMode;
import net.sf.mmm.util.io.api.RuntimeIoException;
import net.sf.mmm.util.nls.api.IllegalCaseException;
import net.sf.mmm.util.nls.api.NlsFormatterManager;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.nls.api.NlsTemplateResolver;
import net.sf.mmm.util.nls.base.AbstractNlsFormatterPlugin;

/**
 * This is an implementation of {@link net.sf.mmm.util.nls.api.NlsFormatter} for {@link Date}s.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractNlsFormatterDateIso8601 extends AbstractNlsFormatterPlugin<Object> {

  /** @see #format(Date, Locale, Appendable) */
  private Iso8601UtilLimited iso8601Util;

  /**
   * The constructor.
   */
  public AbstractNlsFormatterDateIso8601() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param iso8601Util is the {@link Iso8601Util} instance to use.
   */
  public AbstractNlsFormatterDateIso8601(Iso8601Util iso8601Util) {

    super();
    NlsNullPointerException.checkNotNull(Iso8601Util.class, iso8601Util);
    this.iso8601Util = iso8601Util;
    initialize();
  }

  /**
   * @param iso8601Util is the iso8601Util to set
   */
  @Inject
  public void setIso8601Util(Iso8601Util iso8601Util) {

    getInitializationState().requireNotInitilized();
    this.iso8601Util = iso8601Util;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.iso8601Util == null) {
      this.iso8601Util = new Iso8601UtilLimitedImpl();
    }
  }

  /**
   * This method formats the given <code>calendar</code>.
   * 
   * @param date is the {@link Date} to format.
   * @param locale is the locale used for localized formatting.
   * @param buffer is where to append the formatted <code>calendar</code>.
   */
  protected void format(Date date, Locale locale, Appendable buffer) {

    try {
      String type = getType();
      if (NlsFormatterManager.TYPE_DATE.equals(type)) {
        this.iso8601Util.formatDate(date, true, buffer);
      } else if (NlsFormatterManager.TYPE_TIME.equals(type)) {
        this.iso8601Util.formatTime(date, true, buffer);
        buffer.append('Z');
      } else if (NlsFormatterManager.TYPE_DATETIME.equals(type)) {
        this.iso8601Util.formatDateTime(date, true, true, buffer);
      } else {
        throw new IllegalCaseException(type);
      }
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.WRITE);
    }
  }

  /**
   * {@inheritDoc}
   */
  public String getStyle() {

    return NlsFormatterManager.STYLE_ISO_8601;
  }

  /**
   * {@inheritDoc}
   */
  public void format(Object object, Locale locale, Map<String, Object> arguments, NlsTemplateResolver resolver,
      Appendable buffer) throws IOException {

    Date date = null;
    if (object != null) {
      if (object instanceof Date) {
        date = (Date) object;
      } else if (object instanceof Number) {
        long millis = ((Number) object).longValue();
        date = new Date(millis);
      }
    }
    if (date == null) {
      String string = null;
      if (object != null) {
        string = object.toString();
      }
      if (string == null) {
        string = "null";
      }
      buffer.append(string);
    } else {
      format(date, locale, buffer);
    }
  }
}
