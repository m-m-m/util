/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleRange;

/**
 * This is the implementation of {@link RoleRange}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractRoleRange extends AbstractRoleWithCommonAttributes implements RoleRange {

  /** @see #getValueMin() */
  private double valueMin;

  /** @see #getValueMax() */
  private double valueMax;

  /** @see #getValueNow() */
  private Double valueNow;

  /** @see #getValueText() */
  private String valueText;

  /**
   * The constructor.
   */
  public AbstractRoleRange() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double getValueMax() {

    return this.valueMax;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setValueMax(double max) {

    this.valueMax = max;
    setAttribute(HTML_ATTRIBUTE_ARIA_VALUE_MAX, Double.valueOf(max));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double getValueMin() {

    return this.valueMin;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setValueMin(double min) {

    this.valueMin = min;
    setAttribute(HTML_ATTRIBUTE_ARIA_VALUE_MIN, Double.valueOf(min));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Double getValueNow() {

    return this.valueNow;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setValueNow(Double now) {

    this.valueNow = now;
    setAttribute(HTML_ATTRIBUTE_ARIA_VALUE_NOW, now);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getValueText() {

    return this.valueText;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setValueText(String value) {

    this.valueText = value;
    setAttribute(HTML_ATTRIBUTE_ARIA_VALUE_TEXT, value);
  }

}
