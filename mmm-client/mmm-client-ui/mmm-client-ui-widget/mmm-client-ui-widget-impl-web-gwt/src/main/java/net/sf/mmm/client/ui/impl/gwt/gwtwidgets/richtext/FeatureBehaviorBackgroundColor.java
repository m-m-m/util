/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.gwtwidgets.richtext;

import net.sf.mmm.client.ui.api.common.RichTextFeature;
import net.sf.mmm.util.datatype.api.color.Color;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;

/**
 * This is the implementation of {@link FeatureBehavior} for {@link RichTextFeature#BACKGROUND_COLOR}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
class FeatureBehaviorBackgroundColor extends AbstractColorFeatureBehavior {

  /**
   * The constructor.
   * 
   * @param richTextToolbar is the {@link RichTextToolbar} owning this {@link FeatureBehavior}.
   */
  public FeatureBehaviorBackgroundColor(RichTextToolbar richTextToolbar) {

    super(richTextToolbar);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public RichTextFeature getFeature() {

    return RichTextFeature.BACKGROUND_COLOR;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onClick(ClickEvent event) {

    getRichTextToolbar().openFontSettingsPopup();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void applyFontSettings() {

    Color value = getColorBox().getValue();
    if (value != null) {
      getFormatter().setBackColor(value.toString());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void applyFontSettings(Color value, Style style) {

    style.setBackgroundColor(value.toString());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getValue() {

    return getFormatter().getBackColor();
  }
}
