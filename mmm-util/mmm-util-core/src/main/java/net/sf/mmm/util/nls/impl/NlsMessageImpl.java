/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

import java.io.IOException;
import java.util.Locale;

import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.nls.api.NlsObject;
import net.sf.mmm.util.nls.api.NlsTemplate;
import net.sf.mmm.util.nls.api.NlsTemplateResolver;

/**
 * This is the abstract base implementation of {@link NlsMessage}.<br>
 * You should extend this class rather than directly implementing the
 * {@link NlsMessage} interface to gain compatibility with further releases.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class NlsMessageImpl implements NlsMessage {

  /** The {@link #message} as {@link NlsTemplate}. */
  private NlsTemplate template;

  /** @see #getInternationalizedMessage() */
  private String message;

  /** @see #getArgument(int) */
  private final Object[] arguments;

  /**
   * an array for the {@link #arguments} that implement {@link NlsMessage} or
   * <code>null</code> if NONE of them does.
   */
  private final NlsObject[] nlsArguments;

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
    this.nlsArguments = createNlsArguments(this.arguments);
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
    this.nlsArguments = createNlsArguments(this.arguments);
  }

  /**
   * This method creates the {@link #nlsArguments}.
   * 
   * @param arguments are the {@link #arguments}.
   * @return an array for the {@link #arguments} that implement
   *         {@link NlsMessage} or <code>null</code> if NONE of them does.
   */
  private static NlsObject[] createNlsArguments(Object[] arguments) {

    NlsObject[] result = null;
    for (int i = 0; i < arguments.length; i++) {
      if (arguments[i] instanceof NlsObject) {
        if (result == null) {
          result = new NlsObject[arguments.length];
        }
        result[i] = (NlsObject) arguments[i];
      }
    }
    return result;
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
      this.message = this.template.translate(Locale.ROOT);
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
   * This method gets the translated {@link #getArgument(int) arguments} as
   * array. Here <em>translated</em> means that
   * {@link #getArgument(int) arguments} that are {@link NlsObject}s will be
   * translated using the given <code>translator</code>.
   * 
   * @param locale is the locale to translate to.
   * @param translator is the translator used if an
   *        {@link #getArgument(int) argument} is an {@link NlsObject} that
   *        needs to be translated.
   * @return the arguments as array where all arguments that are an instance of
   *         {@link NlsObject} are replaced by their
   *         {@link NlsMessage#getLocalizedMessage(Locale, NlsTemplateResolver) translation}.
   */
  protected Object[] getTranslatedArguments(Locale locale, NlsTemplateResolver translator) {

    Object[] formattedArguments;
    if (this.nlsArguments == null) {
      formattedArguments = this.arguments;
    } else {
      // arguments contain NLS objects
      formattedArguments = new Object[this.arguments.length];
      for (int argIndex = 0; argIndex < this.arguments.length; argIndex++) {
        if (this.nlsArguments[argIndex] == null) {
          formattedArguments[argIndex] = this.arguments[argIndex];
        } else {
          NlsMessage subMessage = this.nlsArguments[argIndex].toNlsMessage();
          // we can not work with out string buffer here...
          formattedArguments[argIndex] = subMessage.getLocalizedMessage(locale, translator);
        }
      }
    }
    return formattedArguments;
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
        Object[] translatedArguments = getTranslatedArguments(locale, resolver);
        boolean success = false;
        if (nlsTemplate != null) {
          success = nlsTemplate.translate(locale, translatedArguments, buffer);
        }
        if (!success) {
          if (resolver != null) {
            buffer.append(LOCALIZATION_FAILURE_PREFIX);
          }
          // TODO:
          NlsMessageFormatterImpl format = new NlsMessageFormatterImpl(this.message,
              NlsFormatterManagerImpl.INSTANCE);
          format.format(translatedArguments, locale, buffer);
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

    return getLocalizedMessage(Locale.ROOT, null);
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
