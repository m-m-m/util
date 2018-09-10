/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

/**
 * This is the interface to create an {@link NlsFormatter}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public interface NlsMessageFormatterFactory {

  /**
   * This method creates a new {@link NlsMessageFormatter} for the given {@code message}. <br>
   * The format of the {@code message} is described in {@link NlsMessage}.
   *
   * @param message is the template for the message where potential
   *        {@link net.sf.mmm.util.nls.api.NlsFormatter#format(Object, java.util.Locale, java.util.Map, NlsTemplateResolver)
   *        arguments will be filled in}.
   * @return the {@link NlsMessageFormatter} for the given {@code message}.
   */
  NlsMessageFormatter create(String message);

}
