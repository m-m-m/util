/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

import java.util.Locale;

import net.sf.mmm.util.nls.api.NlsFormatterManager;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SmartNlsTemplateImpl extends NlsTemplateImpl {

  /** @see #createFormatter(String, Locale) */
  private final NlsFormatterManager formatterManager;

  /**
   * The constructor.
   * 
   * @param name is the {@link #getName() name} of the bundle.
   * @param key is the {@link #getKey() key} of the string to lookup in the
   *        bundle.
   * @param formatterManager is the {@link NlsFormatterManager} to use.
   */
  public SmartNlsTemplateImpl(String name, String key, NlsFormatterManager formatterManager) {

    super(name, key);
    this.formatterManager = formatterManager;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected NlsFormatterManager getFormatterManager() {

    return this.formatterManager;
  }

}
