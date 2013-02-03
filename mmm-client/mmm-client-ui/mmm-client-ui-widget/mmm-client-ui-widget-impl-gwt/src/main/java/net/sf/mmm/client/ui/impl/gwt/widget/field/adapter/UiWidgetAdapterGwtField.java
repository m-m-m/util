/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field.adapter;

import net.sf.mmm.client.ui.api.common.IconConstants;
import net.sf.mmm.client.ui.api.feature.UiFeatureValue;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventValueChange;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidgetReal;
import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterField;
import net.sf.mmm.client.ui.impl.gwt.handler.event.ChangeEventAdapterGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.adapter.UiWidgetAdapterGwtWidgetActive;
import net.sf.mmm.util.lang.api.HorizontalAlignment;
import net.sf.mmm.util.nls.api.IllegalCaseException;

import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is the implementation of {@link UiWidgetAdapterField} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <WIDGET> is the generic type of {@link #getToplevelWidget()}.
 * @param <VALUE> is the generic type of the changed value.
 * @param <ADAPTER_VALUE> is the generic type of the {@link #getValue() value} of the adapted
 *        {@link #getToplevelWidget() widget}.
 */
public abstract class UiWidgetAdapterGwtField<WIDGET extends Widget, VALUE, ADAPTER_VALUE> extends
    UiWidgetAdapterGwtWidgetActive<FlowPanel> implements UiWidgetAdapterField<VALUE, ADAPTER_VALUE> {

  /** @see #setValidationFailure(String) */
  private Widget validationFailureWidget;

  /** @see #getWidgetViewMode() */
  private Label widgetViewMode;

  /** @see #getActiveWidget() */
  private WIDGET activeWidget;

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtField() {

    super();
  }

  /**
   * @return the {@link #getToplevelWidget() widget} as {@link HasChangeHandlers} or <code>null</code> if NOT
   *         supported.
   */
  protected abstract HasChangeHandlers getWidgetAsHasChangeHandlers();

  /**
   * @return the {@link #getToplevelWidget() widget} as {@link HasChangeHandlers} or <code>null</code> if NOT
   *         supported.
   */
  protected abstract HasValue<ADAPTER_VALUE> getWidgetAsHasValue();

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMode(boolean editMode, AbstractUiWidgetReal<?, ?> widget) {

    super.setMode(editMode, widget);
    if (editMode) {
      getActiveWidget();
    } else {
      if (this.validationFailureWidget != null) {
        this.validationFailureWidget.setVisible(false);
      }
      getWidgetViewMode();
    }
    if (this.activeWidget != null) {
      this.activeWidget.setVisible(editMode);
    }
    if (this.widgetViewMode != null) {
      this.widgetViewMode.setVisible(!editMode);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setChangeEventSender(final UiFeatureValue<VALUE> source, final UiHandlerEventValueChange<VALUE> sender) {

    HasChangeHandlers widget = getWidgetAsHasChangeHandlers();
    if (widget != null) {
      ChangeEventAdapterGwt<VALUE> handler = new ChangeEventAdapterGwt<VALUE>(source, sender);
      widget.addChangeHandler(handler);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ADAPTER_VALUE getValue() {

    return getWidgetAsHasValue().getValue();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setValue(ADAPTER_VALUE value) {

    getWidgetAsHasValue().setValue(value, false);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected final FlowPanel createToplevelWidget() {

    return new FlowPanel();
  }

  /**
   * @return the widget for the {@link net.sf.mmm.client.ui.api.common.UiMode#VIEW view-mode}.
   */
  protected final Label getWidgetViewMode() {

    if (this.widgetViewMode == null) {
      this.widgetViewMode = createViewWidget();
      getToplevelWidget().add(this.widgetViewMode);
    }
    return this.widgetViewMode;
  }

  /**
   * @return a new {@link Widget} that is used to display the field value in
   *         {@link net.sf.mmm.client.ui.api.common.UiMode#VIEW view-mode}.
   */
  protected Label createViewWidget() {

    return new Label();
  }

  /**
   * @return the widget for the {@link net.sf.mmm.client.ui.api.common.UiMode#EDIT edit-mode}.
   */
  @Override
  public final WIDGET getActiveWidget() {

    if (this.activeWidget == null) {
      this.activeWidget = createActiveWidget();
      this.validationFailureWidget = new Image(IconConstants.ICON_MESSAGE_ERROR);

      switch (getConfiguration().getValidationFailureAlignment()) {
        case LEFT:
          getToplevelWidget().add(this.validationFailureWidget);
          getToplevelWidget().add(this.activeWidget);
          break;
        case RIGHT:
          getToplevelWidget().add(this.activeWidget);
          getToplevelWidget().add(this.validationFailureWidget);
          break;
        case CENTER:
          // TODO: center should add the failure text on top as label instead of failure icon...
        default :
          throw new IllegalCaseException(HorizontalAlignment.class, getConfiguration().getValidationFailureAlignment());
      }

    }
    return this.activeWidget;
  }

  /**
   * @return a new {@link Widget} that is used to display and modify the field value in
   *         {@link net.sf.mmm.client.ui.api.common.UiMode#EDIT edit-mode}.
   */
  protected abstract WIDGET createActiveWidget();

  /**
   * {@inheritDoc}
   */
  @Override
  public void setValidationFailure(String validationFailure) {

    if (this.validationFailureWidget == null) {
      getActiveWidget();
    }
    if ((validationFailure == null) || (validationFailure.isEmpty())) {
      this.validationFailureWidget.setTitle("");
      this.validationFailureWidget.setVisible(false);
    } else {
      this.validationFailureWidget.setTitle(validationFailure);
      this.validationFailureWidget.setVisible(true);
    }
  }

}
