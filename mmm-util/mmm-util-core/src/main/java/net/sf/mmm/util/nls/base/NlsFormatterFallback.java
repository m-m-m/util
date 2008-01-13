/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import java.io.IOException;
import java.util.Locale;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class NlsFormatterFallback extends AbstractNlsFormatter<Object> {

  /** The singleton instance. */
  public static final NlsFormatterFallback INSTANCE = new NlsFormatterFallback();

  /**
   * The constructor.
   */
  private NlsFormatterFallback() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public void format(Object object, Locale locale, Appendable buffer) {

    try {
      String string = null;
      if (object != null) {
        string = object.toString();
      }
      if (string == null) {
        string = "null";
      }
      buffer.append(string);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

}
