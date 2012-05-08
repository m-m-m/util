/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

/**
 * This is the interface for a formatter of an arbitrary object in a localized way.<br>
 * 
 * @see java.text.Format
 * 
 * @param <O> is the generic type of the object to {@link #format(Object, Locale, Map, NlsTemplateResolver)}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface NlsFormatter<O> {

  /**
   * This method formats the given <code>object</code> according to the given <code>locale</code>.
   * 
   * @param object is the object to format.
   * @param locale is the locale used for localized formatting.
   * @param arguments is the {@link Map} of arguments.
   * @param resolver is the {@link NlsTemplateResolver}.
   * @return the formatted and localized string for the given <code>object</code>.
   */
  String format(O object, Locale locale, Map<String, Object> arguments, NlsTemplateResolver resolver);

  /**
   * This method formats the given <code>object</code> according to the given <code>locale</code>.
   * 
   * @param object is the object to format.
   * @param locale is the locale used for localized formatting.
   * @param arguments is the {@link Map} of {@link NlsMessage#getArgument(String) arguments}.
   * @param resolver is the {@link NlsTemplateResolver}.
   * @param buffer is where to append the formatted <code>object</code>.
   * @throws IOException if the given {@link Appendable} caused such exception.
   */
  void format(O object, Locale locale, Map<String, Object> arguments, NlsTemplateResolver resolver, Appendable buffer)
      throws IOException;

}
