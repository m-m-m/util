/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import java.util.MissingResourceException;

/**
 * This is an interface abstracts from {@link java.util.ResourceBundle} to support features like
 * {@link NlsBundle}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public interface NlsResourceBundle {

  /**
   * @return the {@link java.util.ResourceBundle#getBundle(String) bundle name} in case of a
   *         {@link java.util.ResourceBundle} and the {link NlsBundleLocation bundle location} in case of an
   *         {@link NlsBundle}.
   */
  String getName();

  /**
   * @see java.util.ResourceBundle#getString(String)
   * @see NlsBundle
   * 
   * @param key is the key of the localized string to get.
   * @return the localized {@link String} for the given {@code key}.
   * @throws MissingResourceException if the requested object does NOT exist.
   */
  String getString(String key) throws MissingResourceException;

}
