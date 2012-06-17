/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * This class is contains the reversed {@link Map} for a {@link ResourceBundle}. It allows to get the key for
 * a {@link ResourceBundle#getString(String) message}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public class NlsReversedResourceBundleImpl implements NlsReversedResourceBundle {

  /** @see #getName() */
  private final String name;

  /** @see #getKey(String) */
  private final Map<String, String> message2KeyMap;

  /**
   * The constructor.
   * 
   * @param resourceBundle is the {@link ResourceBundle}.
   */
  public NlsReversedResourceBundleImpl(ResourceBundle resourceBundle) {

    super();
    this.name = resourceBundle.getClass().getName();
    this.message2KeyMap = new HashMap<String, String>();
    for (String key : resourceBundle.keySet()) {
      Object message = resourceBundle.getObject(key);
      if (message instanceof String) {
        this.message2KeyMap.put((String) message, key);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  public String getName() {

    return this.name;
  }

  /**
   * This method is the inverse of {@link ResourceBundle#getString(String)}.
   * 
   * @param message is the {@link ResourceBundle#getString(String) message} for which the key is requested.
   * @return the key for the given <code>message</code> or <code>null</code> if no such message is contained
   *         in the associated {@link ResourceBundle}.
   */
  public String getKey(String message) {

    return this.message2KeyMap.get(message);
  }
}
