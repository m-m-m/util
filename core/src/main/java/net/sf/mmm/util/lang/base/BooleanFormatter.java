/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import java.io.IOException;
import java.util.Locale;

import net.sf.mmm.util.NlsBundleUtilCoreRoot;
import net.sf.mmm.util.lang.api.Formatter;
import net.sf.mmm.util.nls.api.NlsAccess;
import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * This is the default implementation of {@link net.sf.mmm.util.lang.api.Formatter} for the type
 * {@link Boolean}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class BooleanFormatter extends AbstractFormatter<Boolean> {

  /** @see #getInstance() */
  private static final Formatter<Boolean> INSTANCE = new BooleanFormatter();

  /** @see #doFormat(Boolean, Appendable) */
  private final NlsBundleUtilCoreRoot nlsBundle;

  /** @see #doFormat(Boolean, Appendable) */
  private final Locale locale;

  /**
   * The constructor.
   */
  public BooleanFormatter() {

    this(null);
  }

  /**
   * The constructor.
   * 
   * @param locale is the fixed {@link Locale} to use or {@code null} for default locale.
   */
  public BooleanFormatter(Locale locale) {

    super();
    this.nlsBundle = NlsAccess.getBundleFactory().createBundle(NlsBundleUtilCoreRoot.class);
    this.locale = null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String formatNull() {

    return formatMessage(this.nlsBundle.infoUndefined());
  }

  /**
   * @return the instance
   */
  public static Formatter<Boolean> getInstance() {

    return INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doFormat(Boolean value, Appendable buffer) throws IOException {

    NlsMessage message;
    if (value.booleanValue()) {
      message = this.nlsBundle.infoYes();
    } else {
      message = this.nlsBundle.infoNo();
    }
    buffer.append(formatMessage(message));
  }

  /**
   * @param message the {@link NlsMessage} to format as {@link String}.
   * @return the formatted message.
   */
  protected String formatMessage(NlsMessage message) {

    String result;
    if (this.locale == null) {
      result = message.getLocalizedMessage();
    } else {
      result = message.getLocalizedMessage(this.locale);
    }
    return result;
  }

}
