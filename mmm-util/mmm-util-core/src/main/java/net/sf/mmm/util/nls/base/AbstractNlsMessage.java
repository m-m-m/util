/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import java.util.Locale;

import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.nls.api.NlsTemplateResolver;
import net.sf.mmm.util.session.api.UserSessionAccess;

/**
 * This is the abstract base implementation of {@link NlsMessage}.<br>
 * You should extend this class rather than directly implementing the {@link NlsMessage} interface to gain
 * compatibility with further releases.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.1
 */
public abstract class AbstractNlsMessage implements NlsMessage {

  /** UID for serialization. */
  private static final long serialVersionUID = 6426029827430286036L;

  /** Locale.ROOT is only available since java 6. */
  public static final Locale LOCALE_ROOT = new Locale("");

  /**
   * The constructor.
   */
  public AbstractNlsMessage() {

    super();
  }

  /**
   * This method gets the {@link #getArgument(String) Argument} for the given index.
   *
   * @param index is the index of the requested argument.
   * @return the argument at the given index or <code>null</code> if no such argument exists.
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
  public String getLocalizedMessage() {

    return getLocalizedMessage(UserSessionAccess.getUserLocale());
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

    StringBuilder result = new StringBuilder();
    getLocalizedMessage(locale, resolver, result);
    return result.toString();
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
