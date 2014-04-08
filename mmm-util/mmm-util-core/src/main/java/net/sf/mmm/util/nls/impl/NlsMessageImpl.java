/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
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
import net.sf.mmm.util.nls.base.AbstractNlsDependencies;
import net.sf.mmm.util.nls.base.BasicNlsMessage;
import net.sf.mmm.util.nls.impl.formatter.NlsMessageFormatterImpl;

/**
 * This is the implementation of {@link net.sf.mmm.util.nls.api.NlsMessage}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class NlsMessageImpl extends BasicNlsMessage {

  /** UID for serialization. */
  private static final long serialVersionUID = -610175491381297549L;

  /**
   * The constructor for de-serialization in GWT.
   */
  public NlsMessageImpl() {

    super();
  }

  /**
   * The constructor.
   *
   * @param template is the {@link NlsTemplate} for the {@link #getInternationalizedMessage() raw message}.
   * @param messageArguments are the {@link #getArgument(String) arguments} filled into the message after
   *        nationalization.
   */
  public NlsMessageImpl(NlsTemplate template, Map<String, Object> messageArguments) {

    super(template, messageArguments);
  }

  /**
   * The constructor.
   *
   * @param internationalizedMessage is the {@link #getInternationalizedMessage() internationalized message}.
   * @param messageArguments are the {@link #getArgument(String) arguments} filled into the message after
   *        nationalization.
   */
  public NlsMessageImpl(String internationalizedMessage, Map<String, Object> messageArguments) {

    super(internationalizedMessage, messageArguments);
  }

  /**
   * {@inheritDoc}
   */
  @Override
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
      if ((arguments == null) || arguments.isEmpty()) {
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
          success = nlsTemplate.translate(locale, arguments, buffer, resolver, AbstractNlsDependencies.getInstance());
        }
        if (!success) {
          NlsMessageFormatterImpl format = new NlsMessageFormatterImpl(message, AbstractNlsDependencies.getInstance());
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
