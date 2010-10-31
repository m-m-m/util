/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import net.sf.mmm.util.nls.base.NlsDependencies;

/**
 * This class is the straight forward implementation of the
 * {@link net.sf.mmm.util.nls.api.NlsTemplate} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class NlsTemplateImpl extends FormattedNlsTemplate {

  /** @see #getName() */
  private final String name;

  /** @see #getKey() */
  private final String key;

  /**
   * The constructor.
   * 
   * @param name is the {@link #getName() name} of the bundle.
   * @param key is the {@link #getKey() key} of the string to lookup in the
   *        bundle.
   * @param nlsDependencies are the {@link NlsDependencies} to use.
   */
  public NlsTemplateImpl(String name, String key, NlsDependencies nlsDependencies) {

    super(nlsDependencies);
    this.name = name;
    this.key = key;
  }

  /**
   * This method gets the
   * {@link java.util.ResourceBundle#getBundle(String, java.util.Locale)
   * base-name} used to lookup the bundle (typically a
   * {@link java.util.ResourceBundle}).
   * 
   * @return the bundleName is the base-name of the associated bundle.
   */
  public String getName() {

    return this.name;
  }

  /**
   * This method gets the {@link java.util.ResourceBundle#getString(String) key}
   * of the string to lookup from the {@link #getName() bundle}. The key is a
   * technical UID like (<code>ERR_VALUE_OUT_OF_RANGE</code>).
   * 
   * @return the bundleKey is the key used to lookup the string from the bundle.
   */
  public String getKey() {

    return this.key;
  }

  /**
   * {@inheritDoc}
   */
  public String translate(Locale locale) {

    try {
      return ResourceBundle.getBundle(this.name, locale).getString(this.key);
    } catch (MissingResourceException e) {
      // getLogger().warn(e);
      return "unresolved (" + this.name + ":" + this.key + ")";
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return this.name + ":" + this.key;
  }

}
