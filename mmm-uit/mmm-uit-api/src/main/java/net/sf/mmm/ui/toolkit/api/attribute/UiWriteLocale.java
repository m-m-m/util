/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

import java.util.Locale;

/**
 * This interface gives {@link UiReadLocale#getLocale() read} and
 * {@link #setLocale(Locale) write} access to the {@link java.util.Locale} of an
 * object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWriteLocale extends UiReadLocale {

  /**
   * This method sets the locale to the given value.
   * 
   * @see UiReadLocale#getLocale()
   * 
   * @param newLocale is the new locale to use.
   */
  void setLocale(Locale newLocale);

}
