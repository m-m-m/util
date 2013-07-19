/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base;


/**
 * This is an implementation of {@link net.sf.mmm.client.ui.api.UiConfiguration} as Java bean.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiConfigurationBean extends UiConfigurationDefault {

  /** @see #getTheme() */
  private String theme;

  /** @see #getLabelSuffix() */
  private String labelSuffix;

  /** @see #getLabelSuffixMandatory() */
  private String labelSuffixMandatory;

  /** @see #getLabelResourceBundleName() */
  private String labelResourceBundleName;

  /**
   * The constructor.
   */
  public UiConfigurationBean() {

    super();
    this.theme = DEFAULT_THEME;
    this.labelSuffix = DEFAULT_LABEL_SUFFIX;
    this.labelSuffixMandatory = DEFAULT_LABEL_SUFFIX_MANDATORY;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTheme() {

    return this.theme;
  }

  /**
   * @param theme is the new value of {@link #getTheme()}.
   */
  public void setTheme(String theme) {

    this.theme = theme;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getLabelSuffix() {

    return this.labelSuffix;
  }

  /**
   * @param labelSuffix is the new value of {@link #getLabelSuffix()}.
   */
  public void setLabelSuffix(String labelSuffix) {

    this.labelSuffix = labelSuffix;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getLabelSuffixMandatory() {

    return this.labelSuffixMandatory;
  }

  /**
   * @param labelSuffixMandatory is the new value of {@link #getLabelSuffixMandatory()}.
   */
  public void setLabelSuffixMandatory(String labelSuffixMandatory) {

    this.labelSuffixMandatory = labelSuffixMandatory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getLabelResourceBundleName() {

    return this.labelResourceBundleName;
  }

  /**
   * @param labelResourceBundleName is the new value of {@link #getLabelResourceBundleName()}.
   */
  public void setLabelResourceBundleName(String labelResourceBundleName) {

    this.labelResourceBundleName = labelResourceBundleName;
  }

}
