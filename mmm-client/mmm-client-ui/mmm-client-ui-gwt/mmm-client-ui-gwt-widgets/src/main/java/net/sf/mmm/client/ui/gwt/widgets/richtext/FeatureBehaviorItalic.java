/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.gwt.widgets.richtext;

import net.sf.mmm.client.ui.api.common.RichTextFeature;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.FontStyle;

/**
 * This is the implementation of {@link FeatureBehavior} for {@link RichTextFeature#ITALIC}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
class FeatureBehaviorItalic extends AbstractToggleFeatureBehavior {

  /**
   * The constructor.
   * 
   * @param richTextToolbar is the {@link RichTextToolbar} owning this {@link FeatureBehavior}.
   */
  FeatureBehaviorItalic(RichTextToolbar richTextToolbar) {

    super(richTextToolbar);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public RichTextFeature getFeature() {

    return RichTextFeature.ITALIC;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Boolean getValue() {

    return Boolean.valueOf(getFormatter().isItalic());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void toggle() {

    getFormatter().toggleItalic();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void updateFontSettings(boolean checked, Style style) {

    FontStyle fontStyle;
    if (checked) {
      fontStyle = FontStyle.ITALIC;
    } else {
      fontStyle = FontStyle.NORMAL;
    }
    style.setFontStyle(fontStyle);
  }

}
