/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.gwtwidgets.richtext;

import net.sf.mmm.client.ui.impl.gwt.gwtwidgets.ColorBox;
import net.sf.mmm.client.ui.impl.gwt.gwtwidgets.LabelWidget;

import com.google.gwt.user.client.ui.Widget;

/**
 * A variant of {@link AbstractClickFeatureBehavior} for a selection {@link #getFeature() feature} that is
 * offered as a combobox.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
abstract class AbstractColorFeatureBehavior extends AbstractClickFeatureBehavior {

  /** @see #getFontSettingsLabel() */
  private LabelWidget fontSettingsLabel;

  /** @see #getFontSettingsWidget() */
  private Widget fontSettingsWidget;

  /** @see #getFontSettingsWidget() */
  private ColorBox colorBox;

  /**
   * The constructor.
   * 
   * @param richTextToolbar is the {@link RichTextToolbar} owning this {@link FeatureBehavior}.
   */
  public AbstractColorFeatureBehavior(RichTextToolbar richTextToolbar) {

    super(richTextToolbar);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public abstract void applyFontSettings();

  /**
   * {@inheritDoc}
   */
  @Override
  public abstract void updateFontSettings();

  /**
   * {@inheritDoc}
   */
  @Override
  public Widget getFontSettingsWidget() {

    if (this.fontSettingsWidget == null) {
      this.fontSettingsWidget = getColorBox();
      if (!isVisible()) {
        this.fontSettingsWidget.setVisible(false);
      }
    }
    return this.fontSettingsWidget;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean hasFontSettingsWidget() {

    return (this.fontSettingsWidget != null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public LabelWidget getFontSettingsLabel() {

    if (this.fontSettingsLabel == null) {
      this.fontSettingsLabel = new LabelWidget(getLocalizedLabel());
      this.fontSettingsLabel.setId(getFeature().name() + "_LABEL");
      this.fontSettingsLabel.setLabelledWidget(getFontSettingsWidget());
    }
    return this.fontSettingsLabel;
  }

  /**
   * @return the {@link ColorBox} with the {@link net.sf.mmm.client.ui.api.color.Color} for the feature
   *         value.
   */
  protected ColorBox getColorBox() {

    if (this.colorBox == null) {
      this.colorBox = new ColorBox();
      this.colorBox.setId(createId("_COMBO"));
      // this.colorBox.setDataList(getDataList());
    }
    return this.colorBox;
  }

}
