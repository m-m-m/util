/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl.formatter;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.date.api.Iso8601Util;
import net.sf.mmm.util.nls.api.NlsFormatterManager;

/**
 * This is an implementation of {@link net.sf.mmm.util.nls.api.NlsFormatter} using
 * {@link net.sf.mmm.util.date.api.Iso8601Util}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Singleton
@Named
public class NlsFormatterDateIso8601 extends AbstractNlsFormatterDateIso8601 {

  /**
   * The constructor.
   */
  public NlsFormatterDateIso8601() {

    super();
  }

  /**
   * The constructor.
   *
   * @param iso8601Util is the {@link Iso8601Util} instance to use.
   */
  public NlsFormatterDateIso8601(Iso8601Util iso8601Util) {

    super(iso8601Util);
  }

  @Override
  public String getType() {

    return NlsFormatterManager.TYPE_DATE;
  }
}
