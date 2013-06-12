/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget;

import net.sf.mmm.client.ui.api.widget.UiWidgetWithValue;

/**
 * This is the default implementation of {@link net.sf.mmm.client.ui.api.UiConfiguration}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiConfigurationDefault extends AbstractUiConfiguration {

  /** The default for {@link #getLabelSuffix()}. */
  public static final String DEFAULT_LABEL_SUFFIX = ":";

  /** The default for {@link #getLabelSuffixMandatory()}. */
  public static final String DEFAULT_LABEL_SUFFIX_MANDATORY = "*";

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
   * @return the static label suffix.
   */
  public String getLabelSuffix() {

    return DEFAULT_LABEL_SUFFIX;
  }

  /**
   * @return the label suffix for mandatory fields.
   */
  public String getLabelSuffixMandatory() {

    return DEFAULT_LABEL_SUFFIX_MANDATORY;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String buildLabel(String label, UiWidgetWithValue<?> widget) {

    StringBuilder buffer = new StringBuilder(label);
    buffer.append(getLabelSuffix());
    if (widget.isMandatory()) {
      buffer.append(getLabelSuffixMandatory());
    }
    return buffer.toString();
  }

}
