/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.gwtwidgets.richtext;

import net.sf.mmm.client.ui.api.common.RichTextFeature;
import net.sf.mmm.util.datatype.api.color.Color;

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
  public void updateFontSettings() {

    String colorString = getFormatter().getBackColor();
    Color color = parseColor(colorString);
    getColorBox().setValue(color);
  }

  /**
   * Parses a {@link Color} given as {@link String} from GWT/browser.
   * 
   * @param colorString is the {@link Color} as {@link String}.
   * @return the parsed {@link Color}.
   */
  protected Color parseColor(String colorString) {

    return Color.valueOf(colorString);
  }
}
