/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import net.sf.mmm.util.io.api.IoMode;
import net.sf.mmm.util.io.api.RuntimeIoException;
import net.sf.mmm.util.nls.api.NlsAccess;
import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.nls.api.NlsTemplate;
import net.sf.mmm.util.nls.api.NlsTemplateResolver;
import net.sf.mmm.util.nls.base.NlsDependencies;
import net.sf.mmm.util.nls.impl.formatter.NlsMessageFormatterImpl;

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

  /** @see #getNlsDependencies() */
  private final NlsDependencies nlsDependencies;

  /** The {@link #message} as {@link NlsTemplate}. */
  private NlsTemplate template;

  /** @see #getInternationalizedMessage() */
  private String message;

  /** @see #getArgument(String) */
  private final Map<String, Object> arguments;

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

    super();
    assert (template != null);
    this.nlsDependencies = nlsDependencies;
    this.template = template;
    this.message = null;
    if (messageArguments == null) {
      this.arguments = Collections.emptyMap();
    } else {
      this.arguments = messageArguments;
    }
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

    super();
    assert (internationalizedMessage != null);
    this.nlsDependencies = nlsDependencies;
    this.template = null;
    this.message = internationalizedMessage;
    this.arguments = messageArguments;
  }

  /**
   * {@inheritDoc}
   */
  public Object getArgument(String key) {

    return this.arguments.get(key);
  }

  /**
   * This method gets the {@link #getArgument(String) Argument} for the given
   * index.
   * 
   * @param index is the index of the requested argument.
   * @return the argument at the given index or <code>null</code> if no such
   *         argument exists.
   * @deprecated use {@link #getArgument(String)}
   */
  @Deprecated
  public Object getArgument(int index) {

    return getArgument(Integer.toString(index));
  }

  /**
   * {@inheritDoc}
   */
  public int getArgumentCount() {

    return 0;
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
   * @return the {@link NlsDependencies}.
   */
  protected NlsDependencies getNlsDependencies() {

    return this.nlsDependencies;
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
      synchronized (this) {
        if (this.template == null) {
          NlsTemplateResolver templateResolver;
          if (resolver == null) {
            templateResolver = NlsAccess.getTemplateResolver();
          } else {
            templateResolver = resolver;
          }
          this.template = templateResolver.resolveTemplate(this.message);
        }
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
      NlsTemplate nlsTemplate = this.template;
      if (nlsTemplate == null) {
        if (locale != LOCALE_ROOT) {
          nlsTemplate = getTemplate(resolver);
        }
      }
      if (this.arguments.isEmpty()) {
        String text = null;
        if (nlsTemplate != null) {
          text = nlsTemplate.translate(locale);
        }
        if (text == null) {
          if (locale != LOCALE_ROOT) {
            buffer.append(LOCALIZATION_FAILURE_PREFIX);
          }
          text = this.message;
        }
        buffer.append(text);
      } else {
        boolean success = false;
        if (nlsTemplate != null) {
          success = nlsTemplate.translate(locale, this.arguments, buffer, resolver,
              this.nlsDependencies);
        }
        if (!success) {
          // This does not make sense:
          // If one wants for format "{0,date}" with a specific locale no
          // translation is required...

          // if (locale != LOCALE_ROOT) {
          // buffer.append(LOCALIZATION_FAILURE_PREFIX);
          // }
          NlsMessageFormatterImpl format = new NlsMessageFormatterImpl(this.message,
              getNlsDependencies());
          format.format(null, locale, this.arguments, resolver, buffer);
        }
      }
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.WRITE);
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
