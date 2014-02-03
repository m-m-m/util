/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.gwt.widgets.richtext;

import net.sf.mmm.client.ui.api.common.CssStyles;
import net.sf.mmm.client.ui.gwt.widgets.SimpleToggleButton;
import net.sf.mmm.util.lang.api.attribute.AttributeWriteValue;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.CheckBox;

/**
 * The abstract base implementation of a {@link FeatureBehavior} for a {@link #getFeature() feature} that has
 * a {@link Boolean} state that is toggled if {@link #getToolbarWidget() toolbar widget} is clicked.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
abstract class AbstractToggleFeatureBehavior extends AbstractFeatureBehavior implements ClickHandler,
    AttributeWriteValue<Boolean> {

  /** @see #getToolbarWidget() */
  private final SimpleToggleButton toolbarWidget;

  /** @see #getFontSettingsWidget() */
  private CheckBox fontSettingsWidget;

  /**
   * The constructor.
   * 
   * @param richTextToolbar is the {@link RichTextToolbar} owning this {@link FeatureBehavior}.
   */
  public AbstractToggleFeatureBehavior(RichTextToolbar richTextToolbar) {

    super(richTextToolbar);
    this.toolbarWidget = new SimpleToggleButton();
    this.toolbarWidget.setStylePrimaryName(CssStyles.BUTTON);
    this.toolbarWidget.addStyleName(CssStyles.TOGGLE_BUTTON);
    this.toolbarWidget.setHTML(getIconMarkup());
    this.toolbarWidget.setTitle(getLocalizedLabel());
    this.toolbarWidget.addClickHandler(this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void updateToolbar() {

    super.updateToolbar();
    this.toolbarWidget.setValue(getValue());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void updateFontSettings() {

    super.updateFontSettings();
    Boolean value = getValue();
    getFontSettingsWidget().setValue(value, true);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isEnabled() {

    return getToolbarWidget().isEnabled();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setEnabled(boolean enabled) {

    getToolbarWidget().setEnabled(enabled);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CheckBox getFontSettingsWidget() {

    if (this.fontSettingsWidget == null) {
      this.fontSettingsWidget = new CheckBox(getLocalizedLabel());
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
  public void setFontSettingsPreviewElement(final Element element) {

    super.setFontSettingsPreviewElement(element);
    ValueChangeHandler<Boolean> handler = new ValueChangeHandler<Boolean>() {

      /**
       * {@inheritDoc}
       */
      @Override
      public void onValueChange(ValueChangeEvent<Boolean> event) {

        updateFontSettings(event.getValue().booleanValue(), element.getStyle());
      }
    };
    getFontSettingsWidget().addValueChangeHandler(handler);
  }

  /**
   * @param style the {@link Style} to update.
   */
  public void updateFontSettings(Style style) {

    updateFontSettings(getFontSettingsWidget().getValue().booleanValue(), style);
  }

  /**
   * @param checked - the value of the {@link #getFontSettingsWidget() checkbox}.
   * @param style the {@link Style} to update.
   */
  protected abstract void updateFontSettings(boolean checked, Style style);

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
  public SimpleToggleButton getToolbarWidget() {

    return this.toolbarWidget;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setValue(Boolean value) {

    if (!value.equals(getValue())) {
      toggle();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onClick(ClickEvent event) {

    toggle();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void applyFontSettings() {

    super.applyFontSettings();
    setValue(getFontSettingsWidget().getValue());
  }

  /**
   * Toggles the {@link #getFeature() feature}.
   */
  protected abstract void toggle();

}
