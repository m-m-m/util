/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl.formatter;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.nls.api.NlsFormatterManager;

/**
 * The {@link NlsFormatterTime} for {@link net.sf.mmm.util.nls.api.NlsFormatterManager#STYLE_MEDIUM medium}
 * {@link #getStyle() style}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
@Singleton
@Named
public class NlsFormatterTimeMedium extends NlsFormatterTime {

  /**
   * The constructor.
   */
  public NlsFormatterTimeMedium() {

    super();
  }

  @Override
  public String getStyle() {

    return NlsFormatterManager.STYLE_MEDIUM;
  }

}
