/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import java.util.Locale;

import net.sf.mmm.util.nls.api.NlsFormatter;

/**
 * This is the abstract base implementation of the {@link NlsFormatter}
 * interface.<br>
 * You should extend this class rather than directly implementing the
 * {@link NlsFormatter} interface to gain compatibility with further releases.
 * 
 * @param <O> is the generic type of the object to
 *        {@link #format(Object, Locale)}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractNlsFormatter<O> implements NlsFormatter<O> {

  /**
   * The constructor.
   */
  public AbstractNlsFormatter() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public String format(O object, Locale locale) {

    StringBuilder buffer = new StringBuilder();
    format(object, locale, buffer);
    return buffer.toString();
  }

}
