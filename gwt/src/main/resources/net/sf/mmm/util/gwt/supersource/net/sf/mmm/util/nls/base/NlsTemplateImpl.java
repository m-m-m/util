/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import java.util.Locale;

import com.google.gwt.i18n.client.Dictionary;

/**
 * This class is the implementation of the {@link net.sf.mmm.util.nls.api.NlsTemplate} interface. It uses
 * {@link Dictionary} for localization.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0 (moved, 1.0.0)
 */
public class NlsTemplateImpl extends AbstractNlsTemplate {

  /** @see #getName() */
  private /*final*/ String name;

  /** @see #getKey() */
  private /*final*/ String key;

  /**
   * The constructor for de-serialization in GWT.
   */
  protected NlsTemplateImpl() {

    super();
  }

  /**
   * The constructor.
   *
   * @param name is the {@link #getName() name} of the bundle.
   * @param key is the {@link #getKey() key} of the string to lookup in the bundle.
   */
  public NlsTemplateImpl(String name, String key) {

    super();
    this.name = name;
    this.key = key;
  }

  /**
   * This method gets the {@link java.util.ResourceBundle#getBundle(String, java.util.Locale) base-name} used
   * to lookup the bundle (typically a {@link java.util.ResourceBundle}).
   *
   * @return the bundleName is the base-name of the associated bundle.
   */
  public String getName() {

    return this.name;
  }

  /**
   * This method gets the {@link java.util.ResourceBundle#getString(String) key} of the string to lookup from
   * the {@link #getName() bundle}. The key is a technical UID like ({@code ERR_VALUE_OUT_OF_RANGE}).
   *
   * @return the bundleKey is the key used to lookup the string from the bundle.
   */
  public String getKey() {

    return this.key;
  }

  @Override
  public String translate(Locale locale) {

    // TODO hohwille See https://github.com/m-m-m/mmm/issues/113
    return translateFallback(this.name + ":" + this.key);
  }

  /**
   * Called from {@link #translate(Locale)} if localization failed.
   *
   * @param e is the {@link Exception}.
   * @return the fallback message.
   */
  protected String translateFallback(Exception e) {

    String messageId = this.name + ":" + this.key;
    getLogger().warn("Failed to resolve message (" + messageId + "): " + e.getMessage());
    return translateFallback(messageId);
  }

  /**
   * @see #translateFallback(Exception)
   *
   * @param messageId is the ID of the message composed out of bundle base name and key.
   * @return the fallback message.
   */
  protected String translateFallback(String messageId) {

    return "unresolved (" + messageId + ")";
  }

  @Override
  public String toString() {

    return this.name + ":" + this.key;
  }

}
