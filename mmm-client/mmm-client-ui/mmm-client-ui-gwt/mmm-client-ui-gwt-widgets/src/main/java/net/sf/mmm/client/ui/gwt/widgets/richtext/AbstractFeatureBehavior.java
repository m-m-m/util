/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.gwt.widgets.richtext;

import net.sf.mmm.client.ui.api.common.CssStyles;
import net.sf.mmm.client.ui.gwt.widgets.HtmlTemplates;
import net.sf.mmm.client.ui.gwt.widgets.LabelWidget;

import com.google.gwt.dom.client.Element;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.RichTextArea.Formatter;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is the abstract base implementation of {@link FeatureBehavior}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
abstract class AbstractFeatureBehavior implements FeatureBehavior {

  /** @see #getRichTextToolbar() */
  private final RichTextToolbar richTextToolbar;

  /** @see #isVisible() */
  private boolean visible;

  /**
   * The constructor.
   * 
   * @param richTextToolbar is the {@link RichTextToolbar} owning this {@link FeatureBehavior}.
   */
  public AbstractFeatureBehavior(RichTextToolbar richTextToolbar) {

    super();
    this.richTextToolbar = richTextToolbar;
    this.visible = true;
  }

  /**
   * @return the richTextToolbar
   */
  protected RichTextToolbar getRichTextToolbar() {

    return this.richTextToolbar;
  }

  /**
   * @return the {@link Formatter} of the {@link com.google.gwt.user.client.ui.RichTextArea} of the
   *         {@link #getRichTextToolbar()}.
   */
  protected Formatter getFormatter() {

    return this.richTextToolbar.getFormatter();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isVisible() {

    return this.visible;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void setVisible(boolean visible) {

    this.visible = visible;
    getToolbarWidget().setVisible(visible);
    if (hasFontSettingsWidget()) {
      getFontSettingsWidget().setVisible(visible);
      LabelWidget fontSettingsLabel = getFontSettingsLabel();
      if (fontSettingsLabel != null) {
        fontSettingsLabel.setVisible(visible);
      }
    }
  }

  /**
   * @return <code>true</code> if the {@link #getFontSettingsWidget() font settings widget} is available and
   *         has already been created, <code>false</code> otherwise.
   */
  protected boolean hasFontSettingsWidget() {

    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void updateToolbar() {

    // nothing by default...
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void updateFontSettings() {

    // nothing by default
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void applyFontSettings() {

    // nothing by default
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setFontSettingsPreviewElement(Element element) {

    // nothing by default
  }

  /**
   * @return the localized label text of the {@link #getFeature() feature}.
   */
  protected String getLocalizedLabel() {

    return getFeature().toNlsMessage().getLocalizedMessage();
  }

  /**
   * @param suffix a suffix unique for the {@link Widget} to identify within the {@link RichTextToolbar}.
   * @return the HTML ID.
   */
  protected String createId(String suffix) {

    return getFeature().name() + suffix;
  }

  /**
   * @return the {@link SafeHtml} with the markup for the icon of the feature button in the toolbar.
   */
  protected SafeHtml getIconMarkup() {

    SafeHtml html = HtmlTemplates.INSTANCE.iconMarkup(getFeature().getIcon());
    return html;
  }

  /**
   * @return the {@link Widget} for the {@link #getFeature() feature} in the
   *         {@link net.sf.mmm.client.ui.gwt.widgets.richtext.RichTextToolbar.FontSettingsPopup}.
   */
  public Widget getFontSettingsWidget() {

    // no such widget by default...
    return null;
  }

  /**
   * @see #getFontSettingsLabel()
   * 
   * @return the new {@link LabelWidget} instance for {@link #getFontSettingsLabel()}.
   */
  protected LabelWidget createFontSettingsLabel() {

    // help method to implement getFontSettingsLabel() consistently
    LabelWidget label = new LabelWidget(getLocalizedLabel());
    label.setId(getFeature().name() + "_LABEL");
    label.setStyleName(CssStyles.FIELD_LABEL);
    label.setLabelledWidget(getFontSettingsWidget());
    return label;
  }

  /**
   * @return the {@link LabelWidget} for the {@link #getFeature() feature} in the
   *         {@link net.sf.mmm.client.ui.gwt.widgets.richtext.RichTextToolbar.FontSettingsPopup} or
   *         <code>null</code> if not intended (e.g. {@link AbstractToggleFeatureBehavior toggle features} use
   *         a {@link com.google.gwt.user.client.ui.CheckBox} as {@link #getFontSettingsWidget() popup widget}
   *         and do not have a separate label).
   */
  public LabelWidget getFontSettingsLabel() {

    // default is no label, override to change...
    return null;
  }

}
