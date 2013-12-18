/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.gwtwidgets.richtext;

import java.util.List;

import net.sf.mmm.client.ui.api.common.RichTextFeature;
import net.sf.mmm.util.gwt.api.JavaScriptUtil;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;

/**
 * This is the implementation of {@link FeatureBehavior} for {@link RichTextFeature#FONT_FAMILY}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
class FeatureBehaviorFontFamily extends AbstractSelectionFeatureBehavior {

  /**
   * The constructor.
   * 
   * @param richTextToolbar is the {@link RichTextToolbar} owning this {@link FeatureBehavior}.
   */
  public FeatureBehaviorFontFamily(RichTextToolbar richTextToolbar) {

    super(richTextToolbar);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public RichTextFeature getFeature() {

    return RichTextFeature.FONT_FAMILY;
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
  protected List<String> createOptions() {

    return JavaScriptUtil.getInstance().getAvailableFonts();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void applyFontSettings() {

    getFormatter().setFontName(getCombobox().getValue());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void applyFontSettings(String value, Style style) {

    style.setProperty("fontFamily", value);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getValue() {

    // not available in GWT rich text Formatter
    return null;
  }

}
