/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.gwtwidgets.richtext;

import java.util.List;

import net.sf.mmm.client.ui.impl.gwt.gwtwidgets.ComboBox;
import net.sf.mmm.client.ui.impl.gwt.gwtwidgets.DataList;
import net.sf.mmm.client.ui.impl.gwt.gwtwidgets.LabelWidget;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * A variant of {@link AbstractClickFeatureBehavior} for a selection {@link #getFeature() feature} that is
 * offered as a combobox.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
abstract class AbstractSelectionFeatureBehavior extends AbstractClickFeatureBehavior {

  /** @see #getFontSettingsLabel() */
  private LabelWidget fontSettingsLabel;

  /** @see #getFontSettingsWidget() */
  private FlowPanel fontSettingsWidget;

  /** @see #getFontSettingsWidget() */
  private ComboBox combobox;

  /** The {@link DataList} with the options. */
  private DataList dataList;

  /**
   * The constructor.
   * 
   * @param richTextToolbar is the {@link RichTextToolbar} owning this {@link FeatureBehavior}.
   */
  public AbstractSelectionFeatureBehavior(RichTextToolbar richTextToolbar) {

    super(richTextToolbar);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public abstract void applyFontSettings();

  /**
   * @return the {@link List} of available options for the {@link #getDataList() datalist}.
   */
  protected abstract List<String> createOptions();

  /**
   * {@inheritDoc}
   */
  @Override
  public Widget getFontSettingsWidget() {

    if (this.fontSettingsWidget == null) {
      this.fontSettingsWidget = new FlowPanel();
      this.fontSettingsWidget.add(getCombobox());
      this.fontSettingsWidget.add(getDataList());
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
   * @return the {@link DataList} for the {@link #getFontSettingsWidget() popup widget} combo.
   */
  protected DataList getDataList() {

    if (this.dataList == null) {
      this.dataList = new DataList();
      this.dataList.setId(createId("_LIST"));
      this.dataList.setOptions(createOptions());
    }
    return this.dataList;
  }

  /**
   * @return the {@link ComboBox} with the selection of the feature value from the {@link #createOptions()
   *         options}.
   */
  protected ComboBox getCombobox() {

    if (this.combobox == null) {
      this.combobox = new ComboBox();
      this.combobox.setId(createId("_COMBO"));
      this.combobox.setDataList(getDataList());
    }
    return this.combobox;
  }

}
