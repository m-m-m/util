/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.base;

import java.util.Locale;

import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.nls.api.NlsIllegalArgumentException;
import net.sf.mmm.util.text.api.LineWrapper;
import net.sf.mmm.util.text.api.TextColumnInfo;

/**
 * This is the abstract base-implementation of the {@link LineWrapper}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public abstract class AbstractLineWrapper implements LineWrapper {

  /** @see #getLineSeparator() */
  private final String lineSeparator;

  /** @see #getLocale() */
  private final Locale locale;

  /**
   * The constructor.
   */
  public AbstractLineWrapper() {

    this(StringUtil.LINE_SEPARATOR, Locale.getDefault());
  }

  /**
   * The constructor.
   * 
   * @param lineSeparator is the {@link StringUtil#LINE_SEPARATOR}.
   * @param locale is the {@link #getLocale() locale} to use.
   */
  public AbstractLineWrapper(String lineSeparator, Locale locale) {

    super();
    this.lineSeparator = lineSeparator;
    this.locale = locale;
    if (!StringUtil.LINE_SEPARATOR_CRLF.equals(this.lineSeparator)
        && !StringUtil.LINE_SEPARATOR_LF.equals(this.lineSeparator)
        && !StringUtil.LINE_SEPARATOR_LFCR.equals(this.lineSeparator)
        && !StringUtil.LINE_SEPARATOR_CR.equals(this.lineSeparator)) {
      throw new NlsIllegalArgumentException(this.lineSeparator);
    }
  }

  /**
   * {@inheritDoc}
   */
  public int wrap(Appendable appendable, String text, TextColumnInfo columnInfo) {

    return wrap(appendable, new String[] { text }, new TextColumnInfo[] { columnInfo });
  }

  /**
   * {@inheritDoc}
   */
  public int wrap(Appendable appendable, String column1, TextColumnInfo column1Info,
      String column2, TextColumnInfo column2Info) {

    return wrap(appendable, new String[] { column1, column2 }, new TextColumnInfo[] { column1Info,
        column2Info });
  }

  /**
   * {@inheritDoc}
   */
  public int wrap(Appendable appendable, String column1, TextColumnInfo column1Info,
      String column2, TextColumnInfo column2Info, String column3, TextColumnInfo column3Info) {

    return wrap(appendable, new String[] { column1, column2, column3 }, new TextColumnInfo[] {
        column1Info, column2Info, column3Info });
  }

  /**
   * This method gets the {@link StringUtil#LINE_SEPARATOR} configured for this
   * {@link LineWrapper}.
   * 
   * @return the line-separator.
   */
  public String getLineSeparator() {

    return this.lineSeparator;
  }

  /**
   * This method gets the {@link Locale} to use for i18n-issues.
   * 
   * @return the {@link Locale}.
   */
  public Locale getLocale() {

    return this.locale;
  }

}
