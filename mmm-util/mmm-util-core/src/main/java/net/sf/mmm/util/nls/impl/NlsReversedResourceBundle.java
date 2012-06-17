/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

/**
 * This is the interface for a reversed {@link java.util.ResourceBundle}. It allows to {@link #getKey(String)
 * get the key for a message}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface NlsReversedResourceBundle {

  /**
   * This method gets the {@link java.util.ResourceBundle#getBundle(String) bundle-name}. This is the
   * {@link Class#getName() class-name} of the {@link java.util.ResourceBundle} for the
   * {@link java.util.Locale#ROOT root locale}.
   * 
   * @return the bundle-name.
   */
  String getName();

  /**
   * This method gets the key for the given message. It is the inverse operation of
   * {@link java.util.ResourceBundle#getString(String)}.
   * 
   * @param message is the {@link java.util.ResourceBundle#getString(String) message}.
   * @return the key for the given <code>message</code>.
   */
  String getKey(String message);

}
