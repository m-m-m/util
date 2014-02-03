/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.gwt.widgets.richtext;

import net.sf.mmm.client.ui.api.common.RichTextFeature;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.VerticalAlign;

/**
 * This is the implementation of {@link FeatureBehavior} for {@link RichTextFeature#SUBSCRIPT}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
class FeatureBehaviorSubscript extends AbstractToggleFeatureBehavior {

  /**
   * The constructor.
   * 
   * @param richTextToolbar is the {@link RichTextToolbar} owning this {@link FeatureBehavior}.
   */
  FeatureBehaviorSubscript(RichTextToolbar richTextToolbar) {

    super(richTextToolbar);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public RichTextFeature getFeature() {

    return RichTextFeature.SUBSCRIPT;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Boolean getValue() {

    return Boolean.valueOf(getFormatter().isSubscript());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void toggle() {

    getFormatter().toggleSubscript();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void updateFontSettings(boolean checked, Style style) {

    VerticalAlign align;
    if (checked) {
      align = VerticalAlign.SUB;
    } else {
      align = VerticalAlign.BASELINE;
    }
    // small, sub, sup { font-size: .83em }
    // sub { vertical-align: sub }
    // sup { vertical-align: super }
    style.setVerticalAlign(align);
    style.setFontSize(0.83, Unit.EM);
  }
}
