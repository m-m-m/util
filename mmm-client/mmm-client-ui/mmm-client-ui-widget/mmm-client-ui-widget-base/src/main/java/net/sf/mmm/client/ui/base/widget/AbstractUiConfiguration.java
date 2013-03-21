/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget;

import net.sf.mmm.client.ui.api.UiConfiguration;

/**
 * This is the abstract base implementation of {@link UiConfiguration}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiConfiguration implements UiConfiguration {

  /** @see #getTheme() */
  private String theme;

  /**
   * The constructor.
   */
  public AbstractUiConfiguration() {

    super();
    this.theme = DEFAULT_THEME;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTheme() {

    return this.theme;
  }

  /**
   * @param theme is the new {@link #getTheme() theme}.
   */
  protected void setTheme(String theme) {

    this.theme = theme;
  }

}
