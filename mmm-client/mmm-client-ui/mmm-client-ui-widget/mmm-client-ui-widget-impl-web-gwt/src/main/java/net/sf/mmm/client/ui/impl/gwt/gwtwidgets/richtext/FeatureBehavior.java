/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.gwtwidgets.richtext;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteEnabled;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteVisible;
import net.sf.mmm.client.ui.api.common.RichTextFeature;
import net.sf.mmm.client.ui.impl.gwt.gwtwidgets.LabelWidget;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;

/**
 * The interface for the behavior of a {@link RichTextFeature}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
interface FeatureBehavior extends AttributeWriteVisible, AttributeWriteEnabled {

  /**
   * Updates the status of the {@link FeatureBehavior} in the {@link #getToolbarWidget() toolbar widget} after
   * a change. E.g. toggle buttons are updated to reflect the current state.
   */
  void updateToolbar();

  /**
   * Updates the value of the widget for the font-settings according to the state of the
   * {@link com.google.gwt.user.client.ui.RichTextArea}.
   */
  void updateFontSettings();

  /**
   * Updates the {@link com.google.gwt.user.client.ui.RichTextArea} according to the value from the widget for
   * the font-settings.
   */
  void applyFontSettings();

  /**
   * Updates the {@link com.google.gwt.user.client.ui.RichTextArea} according to the value from the widget for
   * the font-settings.
   * 
   * @param element is the {@link Element} where to apply font settings whenever the value changed.
   */
  void setFontSettingsPreviewElement(Element element);

  /**
   * @return the {@link RichTextFeature} implemented by this object.
   */
  RichTextFeature getFeature();

  /**
   * @return the {@link Widget} (typically a icon button) for the {@link #getFeature() feature} in the
   *         {@link RichTextToolbar}.
   */
  Widget getToolbarWidget();

  /**
   * @return the {@link Widget} for the {@link #getFeature() feature} in the
   *         {@link net.sf.mmm.client.ui.impl.gwt.gwtwidgets.richtext.RichTextToolbar.FontSettingsPopup}.
   */
  Widget getFontSettingsWidget();

  /**
   * @return the {@link LabelWidget} for the {@link #getFeature() feature} in the
   *         {@link net.sf.mmm.client.ui.impl.gwt.gwtwidgets.richtext.RichTextToolbar.FontSettingsPopup} or
   *         <code>null</code> if not intended (e.g. {@link AbstractToggleFeatureBehavior toggle features} use
   *         a {@link com.google.gwt.user.client.ui.CheckBox} as {@link #getFontSettingsWidget() popup widget}
   *         and do not have a separate label).
   */
  LabelWidget getFontSettingsLabel();

}
