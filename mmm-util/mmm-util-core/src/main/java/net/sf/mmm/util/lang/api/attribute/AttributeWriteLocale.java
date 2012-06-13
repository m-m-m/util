/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api.attribute;

import java.util.Locale;

/**
 * This interface gives {@link AttributeReadLocale#getLocale() read} and {@link #setLocale(Locale) write}
 * access to the {@link java.util.Locale} of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.2
 */
public interface AttributeWriteLocale extends AttributeReadLocale {

  /**
   * This method sets the locale to the given value.
   * 
   * @see AttributeReadLocale#getLocale()
   * 
   * @param newLocale is the new locale to use.
   */
  void setLocale(Locale newLocale);

}
