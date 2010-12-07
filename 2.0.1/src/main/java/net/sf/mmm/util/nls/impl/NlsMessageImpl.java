/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import net.sf.mmm.util.io.api.IoMode;
import net.sf.mmm.util.io.api.RuntimeIoException;
import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.nls.api.NlsTemplate;
import net.sf.mmm.util.nls.api.NlsTemplateResolver;
import net.sf.mmm.util.nls.base.BasicNlsMessage;
import net.sf.mmm.util.nls.base.NlsDependencies;
import net.sf.mmm.util.nls.impl.formatter.NlsMessageFormatterImpl;

/**
 * This is the implementation of {@link net.sf.mmm.util.nls.api.NlsMessage}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class NlsMessageImpl extends BasicNlsMessage {

  /** @see #getNlsDependencies() */
  private final NlsDependencies nlsDependencies;

  /**
   * The constructor.
   * 
   * @param template is the {@link NlsTemplate} for the
   *        {@link #getInternationalizedMessage() raw message}.
   * @param messageArguments are the {@link #getArgument(String) arguments}
   *        filled into the message after nationalization.
   * @param nlsDependencies are the {@link NlsDependencies} to use.
   */
  public NlsMessageImpl(NlsTemplate template, Map<String, Object> messageArguments,
      NlsDependencies nlsDependencies) {

    super(template, messageArguments);
    this.nlsDependencies = nlsDependencies;
  }

  /**
   * The constructor.
   * 
   * @param internationalizedMessage is the
   *        {@link #getInternationalizedMessage() internationalized message}.
   * @param messageArguments are the {@link #getArgument(String) arguments}
   *        filled into the message after nationalization.
   * @param nlsDependencies are the {@link NlsDependencies} to use.
   */
  public NlsMessageImpl(String internationalizedMessage, Map<String, Object> messageArguments,
      NlsDependencies nlsDependencies) {

    super(internationalizedMessage, messageArguments);
    this.nlsDependencies = nlsDependencies;
  }

  /**
   * @return the {@link NlsDependencies}.
   */
  protected NlsDependencies getNlsDependencies() {

    return this.nlsDependencies;
  }

  /**
   * {@inheritDoc}
   */
  public void getLocalizedMessage(Locale locale, NlsTemplateResolver resolver, Appendable buffer) {

    try {
      NlsTemplate nlsTemplate = getTemplate();
      if (nlsTemplate == null) {
        if (locale != LOCALE_ROOT) {
          nlsTemplate = getTemplate(resolver);
        }
      }
      String message = getInternationalizedMessage();
      Map<String, Object> arguments = getArguments();
      if (arguments.isEmpty()) {
        String text = null;
        if (nlsTemplate != null) {
          text = nlsTemplate.translate(locale);
        }
        if (text == null) {
          // if (locale != LOCALE_ROOT) {
          // buffer.append(LOCALIZATION_FAILURE_PREFIX);
          // }
          text = message;
        }
        buffer.append(text);
      } else {
        boolean success = false;
        if (nlsTemplate != null) {
          success = nlsTemplate
              .translate(locale, arguments, buffer, resolver, this.nlsDependencies);
        }
        if (!success) {
          // This does not make sense:
          // If one wants for format "{0,date}" with a specific locale no
          // translation is required...

          // if (locale != LOCALE_ROOT) {
          // buffer.append(LOCALIZATION_FAILURE_PREFIX);
          // }
          NlsMessageFormatterImpl format = new NlsMessageFormatterImpl(message,
              getNlsDependencies());
          format.format(null, locale, arguments, resolver, buffer);
        }
      }
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.WRITE);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getMessage() {

    return getLocalizedMessage(LOCALE_ROOT, null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public NlsMessage toNlsMessage() {

    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return getMessage();
  }

}
