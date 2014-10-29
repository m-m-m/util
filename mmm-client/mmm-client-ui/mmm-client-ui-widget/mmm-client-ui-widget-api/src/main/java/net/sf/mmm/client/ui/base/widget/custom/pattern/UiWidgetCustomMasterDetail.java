/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.custom.pattern;

import java.util.List;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.feature.UiFeatureSelectedValue;
import net.sf.mmm.client.ui.api.widget.UiWidgetListContainer;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetDynamicPanel;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetVerticalPanel;
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
  private UiWidgetListContainer<ROW> masterPanel;

  /** @see #getDetailPanel() */
  private UiWidgetCustom<ROW, ?> detailPanel;

  /**
   * The constructor.
   *
   * @param context is the {@link #getContext() context}.
   * @param delegate is the {@link #getDelegate() delegate}.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  protected UiWidgetCustomMasterDetail(UiContext context, UiWidgetDynamicPanel<UiWidgetRegular> delegate) {

    super(context, delegate, (Class) List.class);
  }

  /**
   * The constructor.
   *
   * @param context is the {@link #getContext() context}.
   * @param delegate is the {@link #getDelegate() delegate}.
   * @param masterPanel - see {@link #getMasterPanel()}.
   * @param detailPanel - see {@link #getDetailPanel()}.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public UiWidgetCustomMasterDetail(UiContext context, UiWidgetDynamicPanel<UiWidgetRegular> delegate,
      UiWidgetListContainer<ROW> masterPanel, UiWidgetCustom<ROW, ?> detailPanel) {

    super(context, delegate, (Class) List.class);
    this.masterPanel = masterPanel;
    this.detailPanel = detailPanel;
  }

  /**
   * The constructor.
   *
   * @param context is the {@link #getContext() context}.
   * @param masterPanel - see {@link #getMasterPanel()}.
   * @param detailPanel - see {@link #getDetailPanel()}.
   */
  public UiWidgetCustomMasterDetail(UiContext context, UiWidgetListContainer<ROW> masterPanel,
      UiWidgetCustom<ROW, ?> detailPanel) {

    this(context, context.getWidgetFactory().create(UiWidgetVerticalPanel.class), masterPanel, detailPanel);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetListContainer<ROW> getMasterPanel() {

    return this.masterPanel;
  }

  /**
   * Sets the {@link #getMasterPanel() master panel} in case of lazy initialization. <br>
   * <b>ATTENTION:</b><br>
   * This method has to be called only once and before <code>super.{@link #doInitialize()}</code> has been
   * invoked.
   *
   * @param masterPanel is the {@link #getMasterPanel() master panel} to set.
   */
  protected void setMasterPanel(UiWidgetListContainer<ROW> masterPanel) {

    this.masterPanel = masterPanel;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiFeatureSelectedValue<ROW> getMasterList() {

    return this.masterPanel.asFeatureSelectedValue();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetCustom<ROW, ?> getDetailPanel() {

    return this.detailPanel;
  }

  /**
   * Sets the {@link #getDetailPanel() detail panel} in case of lazy initialization. <br>
   * <b>ATTENTION:</b><br>
   * This method has to be called only once and before <code>super.{@link #doInitialize()}</code> has been
   * invoked.
   *
   * @param detailPanel is the {@link #getDetailPanel() detail panel} to set.
   */
  protected void setDetailPanel(UiWidgetCustom<ROW, ?> detailPanel) {

    this.detailPanel = detailPanel;
  }

}
