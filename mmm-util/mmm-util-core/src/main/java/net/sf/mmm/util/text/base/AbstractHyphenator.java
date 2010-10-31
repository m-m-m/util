/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.base;

import java.util.Locale;

import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.text.api.Hyphenation;
import net.sf.mmm.util.text.api.Hyphenator;

/**
 * This is the abstract base implementation of the {@link Hyphenator} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public abstract class AbstractHyphenator extends AbstractLoggableComponent implements Hyphenator {

  /** @see #getLocale() */
  private final Locale locale;

  /** @see #getHyphen() */
  private final char hyphen;

  /**
   * The constructor.
   * 
   * @param locale is the {@link #getLocale() locale}.
   * @param hyphen is the {@link #getHyphen() hyphen-character}.
   */
  public AbstractHyphenator(Locale locale, char hyphen) {

    super();
    this.locale = locale;
    this.hyphen = hyphen;
  }

  /**
   * {@inheritDoc}
   */
  public Hyphenation hyphenate(String text, int start) {

    return hyphenate(text.substring(start));
  }

  /**
   * {@inheritDoc}
   */
  public Hyphenation hyphenate(String text, int start, int end) {

    return hyphenate(text.substring(start, end));
  }

  /**
   * {@inheritDoc}
   */
  public Locale getLocale() {

    return this.locale;
  }

  /**
   * {@inheritDoc}
   */
  public char getHyphen() {

    return this.hyphen;
  }

}
