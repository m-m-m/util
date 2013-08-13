/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base;

/**
 * This is the default implementation of {@link net.sf.mmm.client.ui.api.UiConfiguration}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiConfigurationDefault extends AbstractUiConfiguration {

  /**
   * The constructor.
   */
  public UiConfigurationDefault() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTheme() {

    return DEFAULT_THEME;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getLabelResourceBundleName() {

    return DEFAULT_LABEL_RESOURCE_BUNDLE_NAME;
  }

}
