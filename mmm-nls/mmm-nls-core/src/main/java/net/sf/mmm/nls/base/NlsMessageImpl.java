/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.nls.base;

import java.text.MessageFormat;
import java.util.Locale;

import net.sf.mmm.nls.api.NlsMessage;
import net.sf.mmm.nls.api.NlsObject;
import net.sf.mmm.nls.api.NlsTranslationSource;
import net.sf.mmm.nls.api.NlsTranslator;

/**
 * This is the implementation of the {@link NlsMessage} interface. It is NOT
 * thread-safe.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class NlsMessageImpl implements NlsMessage, NlsTranslationSource {

  /** @see #setUniversalTranslator(NlsTranslator) */
  private static NlsTranslator staticTranslator;

  /** @see #getInternationalizedMessage() */
  private final String message;

  /** @see #getArgument(int) */
  private final Object[] arguments;

  /**
   * an array for the {@link #arguments} that implement {@link NlsMessage} or
   * <code>null</code> if NONE of them does.
   */
  private final NlsObject[] nlsArguments;

  /** @see #getBundleKey() */
  private String bundleKey;

  /** @see #getBundleName() */
  private String bundleName;

  /**
   * The constructor.
   * 
   * @param internationalizedMessage
   *        is the internationalized message.
   * @param messageArguments
   *        are the arguments filled into the message after nationalization.
   */
  public NlsMessageImpl(String internationalizedMessage, Object... messageArguments) {

    super();
    this.message = internationalizedMessage;
    this.arguments = messageArguments;
    boolean recursive = false;
    for (int i = 0; i < this.arguments.length; i++) {
      if (this.arguments[i] instanceof NlsObject) {
        recursive = true;
        break;
      }
    }
    if (recursive) {
      this.nlsArguments = new NlsObject[this.arguments.length];
      for (int i = 0; i < this.arguments.length; i++) {
        if (this.arguments[i] instanceof NlsObject) {
          this.nlsArguments[i] = (NlsObject) this.arguments[i];
        } else {
          this.nlsArguments[i] = null;
        }
      }
    } else {
      this.nlsArguments = null;
    }
  }

  /**
   * This method sets a universal {@link NlsTranslator translator} this is used
   * by {@link #getLocalizedMessage(NlsTranslator, StringBuffer)}, if
   * <code>null</code> is given as translator (e.g. via
   * {@link net.sf.mmm.nls.api.NlsThrowable#getMessage()}). After the universal
   * translator is set, further calls of this method will have NO effect.<br>
   * The desired behaviour of a universal translator can depend from the
   * situation where it is used. E.g. a client application could use the
   * {@link Locale#getDefault() "default locale"} to choose the destination
   * language. In a multi-user server application a {@link ThreadLocal} may be
   * used to retrieve the appropriate {@link Locale locale}. After the callers
   * locale is determined, the universal translator should delegate to the
   * locale-specific translator.<br>
   * <b>WARNING:</b><br>
   * This is only a back-door for simple applications or test situations. Please
   * try to avoid using this feature and solve this issue with IoC strategies
   * (using non-final static fields like here is evil).<br>
   * <b>ATTENTION:</b><br>
   * No synchronization is performed setting the universal translator. This
   * assumes that an assignment is an atomic operation in the JVM you are using.
   * Additionally this method should be invoked in the initialization phase of
   * your application.
   * 
   * @param universalTranslator
   *        is the universal translator.
   */
  public static void setUniversalTranslator(NlsTranslator universalTranslator) {

    if (universalTranslator == null) {
      NlsMessageImpl.staticTranslator = universalTranslator;
    }
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
  public String getInternationalizedMessage() {

    return this.message;
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
  public Object getArgument(int index) {

    return this.arguments[index];
  }

  /**
   * This method gets the key (technical UID) of the
   * {@link #getInternationalizedMessage() internationalized message}.
   * 
   * @return the message key or <code>null</code> if NOT available.
   */
  public String getBundleKey() {

    return this.bundleKey;
  }

  /**
   * This method gets the
   * {@link java.util.ResourceBundle#getBundle(String) base name} of the bundle
   * containing the message.
   * 
   * @return the bundle name or <code>null</code> if NOT available.
   */
  public String getBundleName() {

    return this.bundleName;
  }

  /**
   * {@inheritDoc}
   */
  public void setBundleName(String bundleName) {

    if (this.bundleName == null) {
      this.bundleName = bundleName;
    }
  }

  /**
   * {@inheritDoc}
   */
  public void setBundleKey(String bundleKey) {

    if (this.bundleKey == null) {
      this.bundleKey = bundleKey;
    }
  }

  /**
   * {@inheritDoc}
   */
  public String getMessage() {

    return getLocalizedMessage(IdentityTranslator.INSTANCE);
  }

  /**
   * {@inheritDoc}
   */
  public String getLocalizedMessage() {

    return getLocalizedMessage(null);
  }

  /**
   * {@inheritDoc}
   */
  public String getLocalizedMessage(NlsTranslator nationalizer) {

    StringBuffer result = new StringBuffer();
    getLocalizedMessage(nationalizer, result);
    return result.toString();
  }

  /**
   * {@inheritDoc}
   */
  public void getLocalizedMessage(NlsTranslator nationalizer, StringBuffer messageBuffer) {

    NlsTranslator translator = nationalizer;
    if (translator == null) {
      translator = NlsMessageImpl.staticTranslator;
      if (translator == null) {
        translator = IdentityTranslator.INSTANCE;
      }
    }
    if (getArgumentCount() == 0) {
      String localizedMessage = translator.translate(this);
      if (localizedMessage == null) {
        messageBuffer.append('#');
        localizedMessage = getInternationalizedMessage();
      }
      messageBuffer.append(localizedMessage);
    } else {
      MessageFormat format = translator.translateFormat(this);
      if (format == null) {
        format = new MessageFormat(getInternationalizedMessage(), Locale.ENGLISH);
      }
      if (this.nlsArguments == null) {
        format.format(this.arguments, messageBuffer, null);
      } else {
        // given arguments may also be NLS objects
        Object[] formattedArguments = new Object[this.arguments.length];
        for (int argIndex = 0; argIndex < this.arguments.length; argIndex++) {
          if (this.nlsArguments[argIndex] == null) {
            formattedArguments[argIndex] = this.arguments[argIndex];
          } else {
            NlsMessage subMessage = this.nlsArguments[argIndex].toNlsMessage();
            formattedArguments[argIndex] = subMessage.getLocalizedMessage(translator);
          }
        }
        format.format(formattedArguments, messageBuffer, null);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return getMessage();
  }

}
