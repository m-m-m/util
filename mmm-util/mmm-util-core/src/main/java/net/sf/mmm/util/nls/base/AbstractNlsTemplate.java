/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import net.sf.mmm.util.component.base.AbstractLoggableObject;
import net.sf.mmm.util.nls.api.NlsMessageFormatter;
import net.sf.mmm.util.nls.api.NlsTemplate;
import net.sf.mmm.util.nls.api.NlsTemplateResolver;

/**
 * This is the abstract base implementation of the {@link NlsTemplate} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractNlsTemplate extends AbstractLoggableObject implements NlsTemplate {

  /**
   * The constructor.
   */
  public AbstractNlsTemplate() {

    super();
  }

  /**
   * This method creates an {@link NlsMessageFormatter} for the given <code>messageTemplate</code> and
   * <code>locale</code>.
   * 
   * @param messageTemplate is the template of the message for the given <code>locale</code>.
   * @param locale is the locale to use. The implementation may ignore it here because it is also supplied at
   *        {@link NlsMessageFormatter#format(Void, Locale, Map, NlsTemplateResolver)} . Anyhow it allows the
   *        implementation to do smart caching of the parsed formatter in association with the locale.
   * @param nlsDependencies are the {@link NlsDependencies}.
   * @return the formatter instance.
   */
  protected NlsMessageFormatter createFormatter(String messageTemplate, Locale locale, NlsDependencies nlsDependencies) {

    return nlsDependencies.getMessageFormatterFactory().create(messageTemplate);
  }

  /**
   * {@inheritDoc}
   */
  public boolean translate(Locale locale, Map<String, Object> arguments, Appendable buffer,
      NlsTemplateResolver resolver, NlsDependencies nlsDependencies) throws IOException {

    String translation = translate(locale);
    if (translation == null) {
      return false;
    } else {
      try {
        NlsMessageFormatter formatter = createFormatter(translation, locale, nlsDependencies);
        formatter.format(null, locale, arguments, resolver, buffer);
        return true;
      } catch (Exception e) {
        buffer.append(translation);
        buffer.append("@");
        buffer.append(arguments.toString());
        // true lies...
        return true;
      }
    }
  }

}
