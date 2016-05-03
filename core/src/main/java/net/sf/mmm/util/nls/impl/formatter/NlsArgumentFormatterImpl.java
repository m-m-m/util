/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl.formatter;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.nls.api.NlsArgument;
import net.sf.mmm.util.nls.api.NlsArgumentParser;
import net.sf.mmm.util.nls.api.NlsFormatter;
import net.sf.mmm.util.nls.api.NlsTemplateResolver;
import net.sf.mmm.util.nls.base.AbstractNlsFormatter;
import net.sf.mmm.util.nls.base.NlsArgumentFormatter;
import net.sf.mmm.util.text.api.Justification;

/**
 * The {@link NlsFormatter} for an actual {@link NlsArgument}. It performs the higher-level formatting with
 * {@link NlsArgument#getJustification() justification} delegating the lower-level formatting to the
 * {@link NlsArgument#getFormatter() according sub-formatter} (typically a
 * {@link net.sf.mmm.util.nls.api.NlsFormatterPlugin}).
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
@Singleton
@Named(NlsArgumentFormatter.CDI_NAME)
public class NlsArgumentFormatterImpl extends AbstractNlsFormatter<NlsArgument> implements NlsArgumentFormatter {

  /**
   * The constructor.
   */
  public NlsArgumentFormatterImpl() {

    super();
  }

  @Override
  public void format(NlsArgument argument, Locale locale, Map<String, Object> arguments, NlsTemplateResolver resolver,
      Appendable buffer) throws IOException {

    Object value = null;
    boolean hasValue = false;
    if (arguments != null) {
      value = arguments.get(argument.getKey());
      if (value == null) {
        if (arguments.containsKey(argument.getKey())) {
          hasValue = true;
          // } else if (argument.getFormatter() instanceof NlsFormatterChoice) {
          // hasValue = true;
        }
      } else {
        hasValue = true;
      }
    }
    if (!hasValue) {
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
