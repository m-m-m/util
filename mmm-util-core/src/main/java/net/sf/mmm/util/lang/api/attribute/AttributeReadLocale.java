/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api.attribute;

import java.util.Locale;

/**
 * This interface gives read access to the {@link #getLocale() locale} of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public abstract interface AttributeReadLocale {

  /**
   * This method gets the locale set for this object. It is used for language or regional specific settings.
   * 
   * @return the current locale.
   */
  Locale getLocale();

}
