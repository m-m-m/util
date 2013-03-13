/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.custom.pattern;

import java.util.List;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.feature.UiFeatureSelectedValue;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetDynamicPanel;
import net.sf.mmm.client.ui.base.widget.custom.UiWidgetCustom;

/**
 * This is the regular implementation of the {@link AbstractUiWidgetCustomMasterDetail custom master/detail
 * widget}.
 * 
 * @param <ROW> is the generic type of a single row out of the {@link #getValue() value}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetCustomMasterDetail<ROW> extends AbstractUiWidgetCustomMasterDetail<List<ROW>, ROW, ROW> {

  /** @see #getMasterPanel() */
  private UiWidgetCustom<List<ROW>, ?> masterPanel;

  /** @see #getMasterList() */
  private UiFeatureSelectedValue<ROW> masterList;

  /** @see #getDetailPanel() */
  private UiWidgetCustom<ROW, ?> detailPanel;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param delegate is the {@link #getDelegate() delegate}.
   */
  protected UiWidgetCustomMasterDetail(UiContext context, UiWidgetDynamicPanel<UiWidgetRegular> delegate) {

    super(context, delegate);
  }

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param delegate is the {@link #getDelegate() delegate}.
   * @param masterPanel - see {@link #getMasterPanel()}.
   * @param masterList - see {@link #getMasterList()}.
   * @param detailPanel - see {@link #getDetailPanel()}.
   */
  public UiWidgetCustomMasterDetail(UiContext context, UiWidgetDynamicPanel<UiWidgetRegular> delegate,
      UiWidgetCustom<List<ROW>, ?> masterPanel, UiFeatureSelectedValue<ROW> masterList,
      UiWidgetCustom<ROW, ?> detailPanel) {

    super(context, delegate);
    this.masterPanel = masterPanel;
    this.masterList = masterList;
    this.detailPanel = detailPanel;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetCustom<List<ROW>, ?> getMasterPanel() {

    return this.masterPanel;
  }

  /**
   * Sets the {@link #getMasterPanel() master panel} in case of lazy initialization.<br/>
   * <b>ATTENTION:</b><br/>
   * This method has to be called only once and before <code>super.{@link #doInitialize()}</code> has been
   * invoked.
   * 
   * @param masterPanel is the {@link #getMasterPanel() master panel} to set.
   */
  protected void setMasterPanel(UiWidgetCustom<List<ROW>, ?> masterPanel) {

    this.masterPanel = masterPanel;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiFeatureSelectedValue<ROW> getMasterList() {

    return this.masterList;
  }

  /**
   * Sets the {@link #getMasterList() master list} in case of lazy initialization.<br/>
   * <b>ATTENTION:</b><br/>
   * This method has to be called only once and before <code>super.{@link #doInitialize()}</code> has been
   * invoked.
   * 
   * @param masterList is the {@link #getMasterList() master list} to set.
   */
  protected void setMasterList(UiFeatureSelectedValue<ROW> masterList) {

    this.masterList = masterList;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetCustom<ROW, ?> getDetailPanel() {

    return this.detailPanel;
  }

  /**
   * Sets the {@link #getDetailPanel() detail panel} in case of lazy initialization.<br/>
   * <b>ATTENTION:</b><br/>
   * This method has to be called only once and before <code>super.{@link #doInitialize()}</code> has been
   * invoked.
   * 
   * @param detailPanel is the {@link #getDetailPanel() detail panel} to set.
   */
  protected void setDetailPanel(UiWidgetCustom<ROW, ?> detailPanel) {

    this.detailPanel = detailPanel;
  }

}
