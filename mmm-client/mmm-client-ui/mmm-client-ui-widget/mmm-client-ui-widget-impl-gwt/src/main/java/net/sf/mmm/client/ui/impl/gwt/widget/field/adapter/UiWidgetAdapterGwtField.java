/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field.adapter;

import net.sf.mmm.client.ui.api.attribute.AttributeReadMaximumValue;
import net.sf.mmm.client.ui.api.attribute.AttributeReadMinimumValue;
import net.sf.mmm.client.ui.api.feature.UiFeatureValue;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventValueChange;
import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterField;
import net.sf.mmm.client.ui.impl.gwt.handler.event.ChangeEventAdapterGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.adapter.UiWidgetAdapterGwtWidgetActive;
import net.sf.mmm.util.gwt.api.JavaScriptUtil;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasValue;
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
    UiWidgetAdapterGwtWidgetActive<FlowPanel> implements UiWidgetAdapterField<VALUE, ADAPTER_VALUE>,
    AttributeReadMinimumValue<ADAPTER_VALUE>, AttributeReadMaximumValue<ADAPTER_VALUE> {

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
  public void setMode(boolean editMode) {

    super.setMode(editMode);
    if (editMode) {
      getActiveWidget();
    } else {
      getWidgetViewMode();
    }
    if (this.activeWidget != null) {
      this.activeWidget.setVisible(editMode);
    }
    if (this.widgetViewMode != null) {
      if (!editMode) {
        updateWidgetViewMode();
      }
      this.widgetViewMode.setVisible(!editMode);
    }
  }

  /**
   * This method updates the {@link #getWidgetViewMode() view-mode-widget} such that it displays the
   * {@link #getValue() current value}.
   */
  protected void updateWidgetViewMode() {

    ADAPTER_VALUE value = getValue();
    String valueAsString = convertValueToString(value);
    getWidgetViewMode().setText(valueAsString);
  }

  /**
   * Converts the given value to {@link String}.
   * 
   * @param value the {@link #getValue() value} to convert.
   * @return the {@link String} representation of the given <code>value</code>.
   */
  protected String convertValueToString(ADAPTER_VALUE value) {

    if (value == null) {
      return null;
    }
    return value.toString();
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
      getInputElement().setAttribute("oninput", "mmmClearValidation(this)");
      attachActiveWidget();
    }
    return this.activeWidget;
  }

  /**
   * Attaches the {@link #getActiveWidget() active widget} after {@link #createActiveWidget() creation} to
   * {@link #getToplevelWidget()}. May be overridden for composed active widgets.
   */
  protected void attachActiveWidget() {

    getToplevelWidget().add(this.activeWidget);
  }

  /**
   * @return a new {@link Widget} that is used to display and modify the field value in
   *         {@link net.sf.mmm.client.ui.api.common.UiMode#EDIT edit-mode}.
   */
  protected abstract WIDGET createActiveWidget();

  /**
   * @return the input {@link Element} representing the actual editable field.
   */
  protected Element getInputElement() {

    return getActiveWidget().getElement();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setValidationFailure(String validationFailure) {

    boolean success = JavaScriptUtil.getInstance().setCustomValidity(getInputElement(), validationFailure);
    if (!success) {
      // IE6-9 and other crap

    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ADAPTER_VALUE getMinimumValue() {

    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ADAPTER_VALUE getMaximumValue() {

    return null;
  }
}
