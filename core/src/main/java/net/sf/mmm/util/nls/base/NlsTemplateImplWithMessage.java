/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import java.util.Locale;

/**
 * This class extends {@link NlsTemplateImpl} with the
 * {@link net.sf.mmm.util.nls.api.NlsMessage#getInternationalizedMessage() internationalized message} as fallback.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public class NlsTemplateImplWithMessage extends NlsTemplateImpl {

  private static final long serialVersionUID = 3682086732302203021L;

  /**
   * The {@link net.sf.mmm.util.nls.api.NlsMessage#getInternationalizedMessage() internationalized message}.
   */
  private/* final */String message;

  /**
   * The constructor for de-serialization in GWT.
   */
  protected NlsTemplateImplWithMessage() {

    super();
  }

  /**
   * The constructor.
   *
   * @param name is the {@link #getName() name} of the bundle.
   * @param key is the {@link #getKey() key} of the string to lookup in the bundle.
   * @param message is the {@link net.sf.mmm.util.nls.api.NlsMessage#getInternationalizedMessage() internationalized
   *        message}.
   */
  public NlsTemplateImplWithMessage(String name, String key, String message) {

    super(name, key);
    this.message = message;
  }

  @Override
  public String translate(Locale locale) {

    if ((this.message != null) && (locale == AbstractNlsMessage.LOCALE_ROOT)) {
      return this.message;
    }
    return super.translate(locale);
  }

  @Override
  protected String translateFallback(String messageId) {

    if (this.message != null) {
      return this.message;
    }
    return super.translateFallback(messageId);
  }

}
