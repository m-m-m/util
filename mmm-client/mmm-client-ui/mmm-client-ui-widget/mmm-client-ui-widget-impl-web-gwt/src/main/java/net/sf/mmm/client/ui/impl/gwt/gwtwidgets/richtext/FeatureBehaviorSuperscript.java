/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.gwtwidgets.richtext;

import net.sf.mmm.client.ui.api.common.RichTextFeature;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.VerticalAlign;

/**
 * This is the implementation of {@link FeatureBehavior} for {@link RichTextFeature#SUPERSCRIPT}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
class FeatureBehaviorSuperscript extends AbstractToggleFeatureBehavior {

  /**
   * The constructor.
   * 
   * @param richTextToolbar is the {@link RichTextToolbar} owning this {@link FeatureBehavior}.
   */
  FeatureBehaviorSuperscript(RichTextToolbar richTextToolbar) {

    super(richTextToolbar);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public RichTextFeature getFeature() {

    return RichTextFeature.SUPERSCRIPT;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Boolean getValue() {

    return Boolean.valueOf(getFormatter().isSuperscript());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void toggle() {

    getFormatter().toggleSuperscript();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void updateFontSettings(boolean checked, Style style) {

    VerticalAlign align;
    if (checked) {
      align = VerticalAlign.SUPER;
      style.setFontSize(0.83, Unit.EM);
    } else {
      align = VerticalAlign.BASELINE;
      style.setFontSize(1.0, Unit.EM);
    }
    style.setVerticalAlign(align);
  }
}
