/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import java.io.IOException;
import java.util.Locale;

import net.sf.mmm.util.io.api.IoMode;
import net.sf.mmm.util.io.api.RuntimeIoException;
import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.nls.api.NlsTemplateResolver;

/**
 * This is a dummy implementation of {@link NlsMessage} that only returns the {@link #getMessage() message}
 * given at {@link #NlsMessagePlain(String) construction} and never has any {@link #getArgument(String)
 * arguments}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public class NlsMessagePlain implements NlsMessage {

  /** UID for serialization. */
  private static final long serialVersionUID = 1L;

  /** @see #getMessage() */
  private/* final */String message;

  /**
   * The constructor for de-serialization in GWT.
   */
  protected NlsMessagePlain() {

    super();
  }

  /**
   * The constructor.
   *
   * @param message is the plain message to return for any locale without modification.
   */
  public NlsMessagePlain(String message) {

    super();
    this.message = message;
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
  public String getInternationalizedMessage() {

    return this.message;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Deprecated
  public int getArgumentCount() {

    return 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object getArgument(String key) {

    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Deprecated
  public Object getArgument(int index) {

    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getMessage() {

    return this.message;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getLocalizedMessage() {

    return this.message;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getLocalizedMessage(Locale locale) {

    return this.message;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void getLocalizedMessage(Locale locale, Appendable buffer) {

    getLocalizedMessage(locale, null, buffer);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getLocalizedMessage(Locale locale, NlsTemplateResolver resolver) {

    return this.message;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void getLocalizedMessage(Locale locale, NlsTemplateResolver resolver, Appendable buffer) {

    try {
      buffer.append(this.message);
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.WRITE);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return this.message;
  }

}
