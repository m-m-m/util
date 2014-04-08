/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Generic {@link ResourceBundle} for dynamic creation from key/value pairs.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public class GenericResourceBundle extends ResourceBundle {

  /** @see #handleGetObject(String) */
  private final Hashtable<String, String> bundle;

  /**
   * The constructor.
   * 
   * @param bundle are the actual key/value pairs with the bundle texts.
   */
  public GenericResourceBundle(Hashtable<String, String> bundle) {

    super();
    this.bundle = bundle;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Object handleGetObject(String key) {

    return this.bundle.get(key);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Enumeration<String> getKeys() {

    return this.bundle.keys();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean containsKey(String key) {

    return this.bundle.containsKey(key);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<String> keySet() {

    return this.bundle.keySet();
  }

}
