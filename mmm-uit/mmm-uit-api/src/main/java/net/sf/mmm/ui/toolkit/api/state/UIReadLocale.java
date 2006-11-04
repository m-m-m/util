/* $Id$ */
package net.sf.mmm.ui.toolkit.api.state;

import java.util.Locale;

/**
 * This interface gives {@link #getLocale() read} access to the
 * {@link java.util.Locale} of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIReadLocale {

  /**
   * This method gets the locale set for this object. It is used for language
   * or reginal specific settings.
   * 
   * @return the current locale.
   */
  Locale getLocale();

}
