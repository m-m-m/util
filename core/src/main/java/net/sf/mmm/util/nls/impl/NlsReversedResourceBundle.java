/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

import net.sf.mmm.util.nls.api.NlsResourceBundle;

/**
 * This is the interface for a reversed {@link java.util.ResourceBundle}. It allows to {@link #getKey(String)
 * get the key for a message}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface NlsReversedResourceBundle extends NlsResourceBundle {

  /**
   * This method gets the key for the given message. It is the inverse operation of
   * {@link java.util.ResourceBundle#getString(String)}.
   * 
   * @param message is the {@link java.util.ResourceBundle#getString(String) message}.
   * @return the key for the given {@code message}.
   */
  String getKey(String message);

}
