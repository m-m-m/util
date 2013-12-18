/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.gwtwidgets.richtext;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.mmm.client.ui.api.common.RichTextFeature;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.RichTextArea.FontSize;

/**
 * This is the implementation of {@link FeatureBehavior} for {@link RichTextFeature#FONT_FAMILY}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
class FeatureBehaviorFontSize extends AbstractSelectionFeatureBehavior {

  /** The {@link #createOptions() option} for {@link FontSize#XX_SMALL}. */
  private static final String FONT_SIZE_XX_SMALL = "-3";

  /** The {@link #createOptions() option} for {@link FontSize#X_SMALL}. */
  private static final String FONT_SIZE_X_SMALL = "-2";

  /** The {@link #createOptions() option} for {@link FontSize#SMALL}. */
  private static final String FONT_SIZE_SMALL = "-1";

  /** The {@link #createOptions() option} for {@link FontSize#MEDIUM}. */
  private static final String FONT_SIZE_MEDIUM = "0";

  /** The {@link #createOptions() option} for {@link FontSize#LARGE}. */
  private static final String FONT_SIZE_LARGE = "+1";

  /** The {@link #createOptions() option} for {@link FontSize#X_LARGE}. */
  private static final String FONT_SIZE_X_LARGE = "+2";

  /** The {@link #createOptions() option} for {@link FontSize#XX_LARGE}. */
  private static final String FONT_SIZE_XX_LARGE = "+3";

  /** @see #getFontSizeMap() */
  private Map<String, FontSize> fontSizeMap;

  /**
   * The constructor.
   * 
   * @param richTextToolbar is the {@link RichTextToolbar} owning this {@link FeatureBehavior}.
   */
  public FeatureBehaviorFontSize(RichTextToolbar richTextToolbar) {

    super(richTextToolbar);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public RichTextFeature getFeature() {

    return RichTextFeature.FONT_SIZE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onClick(ClickEvent event) {

    getRichTextToolbar().openFontSettingsPopup();
  }

  /**
   * @return the fontSizeList
   */
  public Map<String, FontSize> getFontSizeMap() {

    if (this.fontSizeMap == null) {
      this.fontSizeMap = new HashMap<String, FontSize>();
      this.fontSizeMap.put(FONT_SIZE_XX_SMALL, FontSize.XX_SMALL);
      this.fontSizeMap.put(FONT_SIZE_X_SMALL, FontSize.X_SMALL);
      this.fontSizeMap.put(FONT_SIZE_SMALL, FontSize.SMALL);
      this.fontSizeMap.put(FONT_SIZE_MEDIUM, FontSize.MEDIUM);
      this.fontSizeMap.put(FONT_SIZE_LARGE, FontSize.LARGE);
      this.fontSizeMap.put(FONT_SIZE_X_LARGE, FontSize.X_LARGE);
      this.fontSizeMap.put(FONT_SIZE_XX_LARGE, FontSize.XX_LARGE);
    }
    return this.fontSizeMap;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected List<String> createOptions() {

    return Arrays.asList(FONT_SIZE_XX_SMALL, FONT_SIZE_X_SMALL, FONT_SIZE_SMALL, FONT_SIZE_MEDIUM, FONT_SIZE_LARGE,
        FONT_SIZE_X_LARGE, FONT_SIZE_XX_LARGE);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void applyFontSettings() {

    String value = getCombobox().getValue();
    FontSize fontSize = getFontSizeMap().get(value);
    if (fontSize != null) {
      getFormatter().setFontSize(fontSize);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void applyFontSettings(String value, Style style) {

    FontSize fontSize = getFontSizeMap().get(value);
    if (fontSize != null) {
      // TODO Joerg Hohwiller (hohwille at users.sourceforge.net) <task>
      // style.setFontSize(, Unit.MM);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getValue() {

    // not available in GWT rich text Formatter...
    return null;
  }
}
