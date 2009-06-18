/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

import java.io.IOException;
import java.util.Locale;

import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.nls.api.NlsTemplate;
import net.sf.mmm.util.nls.api.NlsTemplateResolver;

/**
 * This is the abstract base implementation of {@link NlsMessage}.<br>
 * You should extend this class rather than directly implementing the
 * {@link NlsMessage} interface to gain compatibility with further releases.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class NlsMessageImpl implements NlsMessage {

  /** Locale.ROOT is only available since java 6. */
  private static final Locale LOCALE_ROOT = new Locale("");

  /** The {@link #message} as {@link NlsTemplate}. */
  private NlsTemplate template;

  /** @see #getInternationalizedMessage() */
  private String message;

  /** @see #getArgument(int) */
  private final Object[] arguments;

  /**
   * The constructor.
   * 
   * @param template is the {@link NlsTemplate} for the
   *        {@link #getInternationalizedMessage() raw message}.
   * @param messageArguments are the {@link #getArgument(int) arguments} filled
   *        into the message after nationalization.
   */
  public NlsMessageImpl(NlsTemplate template, Object... messageArguments) {

    super();
    assert (template != null);
    this.template = template;
    this.message = null;
    this.arguments = messageArguments;
  }

  /**
   * The constructor.
   * 
   * @param internationalizedMessage is the
   *        {@link #getInternationalizedMessage() internationalized message}.
   * @param messageArguments are the {@link #getArgument(int) arguments} filled
   *        into the message after nationalization.
   */
  public NlsMessageImpl(String internationalizedMessage, Object... messageArguments) {

    super();
    assert (internationalizedMessage != null);
    this.template = null;
    this.message = internationalizedMessage;
    this.arguments = messageArguments;
  }

  /**
   * {@inheritDoc}
   */
  public Object getArgument(int index) {

    return this.arguments[index];
  }

  /**
   * {@inheritDoc}
   */
  public int getArgumentCount() {

    return this.arguments.length;
  }

  /**
   * {@inheritDoc}
   */
  public String getInternationalizedMessage() {

    if (this.message == null) {
      this.message = this.template.translate(LOCALE_ROOT);
    }
    return this.message;
  }

  /**
   * This method gets the {@link NlsTemplate} of this message.
   * 
   * @return the text the {@link NlsTemplate} or <code>null</code> if NOT yet
   *         {@link NlsTemplateResolver#resolveTemplate(String) resolved}.
   */
  public NlsTemplate getTemplate() {

    return this.template;
  }

  /**
   * This method gets the {@link NlsTemplate} of this message.
   * 
   * @param resolver is the {@link NlsTemplateResolver} used to
   *        {@link NlsTemplateResolver#resolveTemplate(String) resolve} the
   *        {@link NlsTemplate} if NOT yet available.
   * @return the text the {@link NlsTemplate}.
   */
  public NlsTemplate getTemplate(NlsTemplateResolver resolver) {

    if (this.template == null) {
      if (resolver != null) {
        this.template = resolver.resolveTemplate(this.message);
      }
    }
    return this.template;
  }

  /**
   * {@inheritDoc}
   */
  public String getLocalizedMessage() {

    return getLocalizedMessage(Locale.getDefault());
  }

  /**
   * {@inheritDoc}
   */
  public String getLocalizedMessage(Locale locale) {

    return getLocalizedMessage(locale, null);
  }

  /**
   * {@inheritDoc}
   */
  public String getLocalizedMessage(Locale locale, NlsTemplateResolver resolver) {

    StringBuilder result = new StringBuilder(this.message.length() + 16);
    getLocalizedMessage(locale, resolver, result);
    return result.toString();
  }

  /**
   * {@inheritDoc}
   */
  public void getLocalizedMessage(Locale locale, NlsTemplateResolver resolver, Appendable buffer) {

    try {
      NlsTemplate nlsTemplate = getTemplate(resolver);
      if (getArgumentCount() == 0) {
        String text = null;
        if (nlsTemplate != null) {
          text = nlsTemplate.translate(locale);
        }
        if (text == null) {
          if (resolver != null) {
            buffer.append(LOCALIZATION_FAILURE_PREFIX);
          }
          text = this.message;
        }
        buffer.append(text);
      } else {
        boolean success = false;
        if (nlsTemplate != null) {
          success = nlsTemplate.translate(locale, this.arguments, buffer);
        }
        if (!success) {
          if (resolver != null) {
            buffer.append(LOCALIZATION_FAILURE_PREFIX);
          }
          NlsMessageFormatterImpl format = new NlsMessageFormatterImpl(this.message,
              NlsFormatterManagerImpl.INSTANCE);
          format.format(this.arguments, locale, buffer);
        }
      }
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  public String getMessage() {

    return getLocalizedMessage(LOCALE_ROOT, null);
  }

  /**
   * {@inheritDoc}
   */
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
