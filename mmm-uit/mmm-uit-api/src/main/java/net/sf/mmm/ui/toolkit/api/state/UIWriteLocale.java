/* $Id$ */
package net.sf.mmm.ui.toolkit.api.state;

import java.util.Locale;

/**
 * This interface gives {@link UIReadLocale#getLocale() read} and
 * {@link #setLocale(Locale) write} access to the {@link java.util.Locale} of an
 * object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIWriteLocale extends UIReadLocale {

  /**
   * This method sets the locale to the given value.
   * 
   * @see UIReadLocale#getLocale()
   * 
   * @param newLocale
   *        is the new locale to use.
   */
  void setLocale(Locale newLocale);

}
