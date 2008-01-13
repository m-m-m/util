/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import java.util.Locale;

/**
 * This is the interface for a formatter of an arbitrary object in a localized
 * way.<br>
 * 
 * @see java.text.Format
 * 
 * @param <O> is the generic type of the object to
 *        {@link #format(Object, Locale)}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface NlsFormatter<O> {

  /**
   * This method formats the given <code>object</code> according to the given
   * <code>locale</code>.
   * 
   * @param object is the object to format.
   * @param locale is the locale used for localized formatting.
   * @return the formatted and localized string of the given <code>object</code>.
   */
  String format(O object, Locale locale);

  /**
   * This method formats the given <code>object</code> according to the given
   * <code>locale</code>.
   * 
   * @param object is the object to format.
   * @param locale is the locale used for localized formatting.
   * @param buffer is where to append the formatted <code>object</code>.
   */
  void format(O object, Locale locale, Appendable buffer);

}
