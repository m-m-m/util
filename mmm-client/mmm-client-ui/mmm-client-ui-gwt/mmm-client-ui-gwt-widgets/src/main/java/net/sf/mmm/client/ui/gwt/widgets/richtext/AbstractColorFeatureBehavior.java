/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.gwt.widgets.richtext;

import net.sf.mmm.client.ui.gwt.widgets.ColorBox;
import net.sf.mmm.client.ui.gwt.widgets.LabelWidget;
import net.sf.mmm.util.datatype.api.color.Color;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
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
      this.fontSettingsLabel = createFontSettingsLabel();
    }
    return this.fontSettingsLabel;
  }

  /**
   * @return the {@link ColorBox} with the {@link Color} for the feature value.
   */
  protected ColorBox getColorBox() {

    if (this.colorBox == null) {
      this.colorBox = new ColorBox();
      this.colorBox.setId(createId("_COMBO"));
      // this.colorBox.setDataList(getDataList());
    }
    return this.colorBox;
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

  /**
   * {@inheritDoc}
   */
  @Override
  public void updateFontSettings() {

    String colorString = getValue();
    Color color = parseColor(colorString);
    getColorBox().setValue(color, true);
  }

  /**
   * @return the current value of the color in the rich text area.
   */
  protected abstract String getValue();

  /**
   * {@inheritDoc}
   */
  @Override
  public void setFontSettingsPreviewElement(final Element element) {

    super.setFontSettingsPreviewElement(element);
    ValueChangeHandler<Color> handler = new ValueChangeHandler<Color>() {

      /**
       * {@inheritDoc}
       */
      @Override
      public void onValueChange(ValueChangeEvent<Color> event) {

        Color value = event.getValue();
        if (value != null) {
          Style style = element.getStyle();
          applyFontSettings(value, style);
        }
      }
    };
    getColorBox().addValueChangeHandler(handler);
  }

  /**
   * @param style the {@link Style} to modify.
   */
  public void applyFontSettings(Style style) {

    applyFontSettings(getColorBox().getValue(), style);
  }

  /**
   * @param value the {@link Color} to set.
   * @param style the {@link Style} to modify.
   */
  protected abstract void applyFontSettings(Color value, Style style);

}
