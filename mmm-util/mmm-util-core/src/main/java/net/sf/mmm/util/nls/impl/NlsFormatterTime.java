/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

import net.sf.mmm.util.nls.api.NlsFormatterManager;

/**
 * This is an implementation of {@link net.sf.mmm.util.nls.api.NlsFormatter}
 * using {@link java.text.DateFormat#getTimeInstance(int, java.util.Locale)}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class NlsFormatterTime extends AbstractSimpleNlsFormatterDate {

  /**
   * The constructor.
   * 
   * @param style is the style used for formatting times (e.g.
   *        {@link java.text.DateFormat#SHORT}).
   */
  public NlsFormatterTime(int style) {

    super(style);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getType() {

    return NlsFormatterManager.TYPE_TIME;
  }

}
