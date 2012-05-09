/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl.formatter;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.nls.api.NlsFormatterManager;
import net.sf.mmm.util.nls.api.NlsTemplateResolver;
import net.sf.mmm.util.nls.base.AbstractNlsFormatterPlugin;

/**
 * This is a GWT compatible implementation of {@link net.sf.mmm.util.nls.api.NlsFormatter} for
 * {@link NlsFormatterManager#STYLE_PERCENT}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Singleton
@Named
public final class NlsFormatterPercent extends AbstractNlsFormatterPlugin<Object> {

  /**
   * The constructor.
   */
  public NlsFormatterPercent() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public void format(Object object, Locale locale, Map<String, Object> arguments, NlsTemplateResolver resolver,
      Appendable buffer) throws IOException {

    if (object == null) {
      buffer.append("null");
    } else if (object instanceof Number) {
      Number number = (Number) object;
      // just a brute Hack!
      long percent = (long) (number.doubleValue() * 100);
      buffer.append(Long.toString(percent));
      buffer.append('%');
    } else {
      // fallback...
      buffer.append(object.toString());

    }
  }

  /**
   * {@inheritDoc}
   */
  public String getType() {

    return NlsFormatterManager.TYPE_NUMBER;
  }

  /**
   * {@inheritDoc}
   */
  public String getStyle() {

    return NlsFormatterManager.STYLE_PERCENT;
  }

}
