/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl.formatter;

import net.sf.mmm.util.nls.api.NlsFormatterManager;

/**
 * This is an implementation of {@link net.sf.mmm.util.nls.api.NlsFormatter} using
 * {@link java.text.DateFormat#getDateTimeInstance(int, int, java.util.Locale)}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class NlsFormatterDateTime extends AbstractSimpleNlsFormatterDate {

  /**
   * The constructor.
   */
  public NlsFormatterDateTime() {

    super();
  }

  @Override
  public String getType() {

    return NlsFormatterManager.TYPE_DATETIME;
  }

}
