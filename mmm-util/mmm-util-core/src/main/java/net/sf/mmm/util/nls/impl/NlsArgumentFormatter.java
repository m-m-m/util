/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import net.sf.mmm.util.nls.api.NlsArgument;
import net.sf.mmm.util.nls.api.NlsArgumentParser;
import net.sf.mmm.util.nls.api.NlsFormatter;
import net.sf.mmm.util.nls.api.NlsTemplateResolver;
import net.sf.mmm.util.nls.base.AbstractNlsFormatter;
import net.sf.mmm.util.text.api.Justification;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public final class NlsArgumentFormatter extends AbstractNlsFormatter<NlsArgument> {

  /** the singleton instance. */
  static final NlsArgumentFormatter INSTANCE = new NlsArgumentFormatter();

  /**
   * The constructor.
   */
  private NlsArgumentFormatter() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public void format(NlsArgument argument, Locale locale, Map<String, Object> arguments,
      NlsTemplateResolver resolver, Appendable buffer) throws IOException {

    Object value = null;
    if (arguments != null) {
      value = arguments.get(argument.getKey());
    }
    if (value == null) {
      buffer.append(NlsArgumentParser.START_EXPRESSION);
      buffer.append(argument.getKey());
      buffer.append(NlsArgumentParser.END_EXPRESSION);
    } else {
      @SuppressWarnings("unchecked")
      NlsFormatter<Object> formatter = (NlsFormatter<Object>) argument.getFormatter();
      Justification justification = argument.getJustification();
      if (justification == null) {
        formatter.format(value, locale, arguments, resolver, buffer);
      } else {
        StringBuilder sb = new StringBuilder();
        formatter.format(value, locale, arguments, resolver, sb);
        justification.justify(sb, buffer);
      }
    }
  }

}
