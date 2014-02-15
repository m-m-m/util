/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field.adapter;

import net.sf.mmm.client.ui.api.common.UiMode;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetField;
import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterField;
import net.sf.mmm.client.ui.gwt.widgets.HorizontalFlowPanel;
import net.sf.mmm.client.ui.impl.gwt.handler.event.EventAdapterGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.adapter.UiWidgetAdapterGwtWidgetActive;
import net.sf.mmm.util.gwt.api.JavaScriptUtil;
import net.sf.mmm.util.lang.api.attribute.AttributeReadMaximumValue;
import net.sf.mmm.util.lang.api.attribute.AttributeReadMinimumValue;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is the implementation of {@link UiWidgetAdapterField} using GWT.
 * 
 * @param <WIDGET> is the generic type of {@link #getToplevelWidget()}.
 * @param <VALUE> is the generic type of the changed value.
 * @param <ADAPTER_VALUE> is the generic type of the {@link #getValue() value} of the adapted
 *        {@link #getToplevelWidget() widget}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class UiWidgetAdapterGwtField<WIDGET extends Widget, VALUE, ADAPTER_VALUE> extends
    UiWidgetAdapterGwtWidgetActive<Widget> implements UiWidgetAdapterField<VALUE, ADAPTER_VALUE>,
    AttributeReadMinimumValue<ADAPTER_VALUE>, AttributeReadMaximumValue<ADAPTER_VALUE> {

  /** @see #getWidgetViewMode() */
  private Widget widgetViewMode;

  /** @see #getActiveWidget() */
  private WIDGET activeWidget;

  /**
   * A marker displayed after the {@link #activeWidget} in {@link net.sf.mmm.client.ui.api.common.UiMode#EDIT
   * edit mode}. Empty by default. May be used to show error markers.
   */
  private InlineLabel widgetMarkerEditMode;

  /** @see #getValue() */
  private ADAPTER_VALUE recentValue;

  /** @see #getValueAsString() */
  // TODO this is somewhat inconsistent, according to issue #85 both recentValue and recentStringValue might
  // be removed
  private String recentStringValue;

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtField() {

    super();
  }

  /**
   * @return the {@link #getActiveWidget() widget} as {@link HasChangeHandlers} or <code>null</code> if NOT
   *         supported.
   */
  protected abstract HasChangeHandlers getWidgetAsHasChangeHandlers();

  /**
   * @return the {@link #getActiveWidget() widget} as {@link TakesValue}.
   */
  protected abstract TakesValue<ADAPTER_VALUE> getWidgetAsTakesValue();

  /**
   * @return the {@link #getActiveWidget() widget} as {@link TakesValue} for the value type {@link String}.
   */
  protected abstract TakesValue<String> getWidgetAsTakesValueString();

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
    if (this.widgetMarkerEditMode != null) {
      this.widgetMarkerEditMode.setVisible(editMode);
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

    try {
      ADAPTER_VALUE currentValue = getValue();
      setAdapterValueInViewMode(currentValue);
    } catch (Exception e) {
      getUiWidget().getContext().getLogger().error("Error while updating value for view mode.", e);
    }
  }

  /**
   * This method updates the {@link #getWidgetViewMode() view-mode-widget} such that it displays the given
   * <code>value</code>. By default a label is {@link #createViewWidget() created} for view-mode. If you
   * change this you will also need to override this method to properly apply the value.
   * 
   * @param value is the value to display.
   */
  protected void setAdapterValueInViewMode(ADAPTER_VALUE value) {

    String valueAsString = convertValueToString(value);
    setValueInViewMode(valueAsString);
  }

  /**
   * This method updates the {@link #getWidgetViewMode() view-mode-widget} such that it displays the given
   * <code>value</code>. By default a label is {@link #createViewWidget() created} for view-mode. If you
   * change this you will also need to override this method to properly apply the value.
   * 
   * @param value is the value to display.
   */
  protected void setValueInViewMode(String value) {

    ((Label) getWidgetViewMode()).setText(value);
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
  protected void applyEventAdapterForChange(EventAdapterGwt adapter) {

    HasChangeHandlers widget = getWidgetAsHasChangeHandlers();
    if (widget != null) {
      HandlerRegistration registration = widget.addChangeHandler(adapter);
      addHandlerRegistration(registration);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ADAPTER_VALUE getValue() {

    if (this.activeWidget == null) {
      return this.recentValue;
    }
    return getWidgetAsTakesValue().getValue();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setValue(ADAPTER_VALUE value) {

    if (this.activeWidget != null) {
      getWidgetAsTakesValue().setValue(value);
    }
    if ((this.widgetViewMode != null) && (!getUiWidget().getMode().isEditable())) {
      setAdapterValueInViewMode(value);
    }
    this.recentValue = value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getValueAsString() {

    if (this.activeWidget == null) {
      return this.recentStringValue;
    } else {
      return getWidgetAsTakesValueString().getValue();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setValueAsString(String value) {

    // TODO this is inconsistent for view mode, according to issue #85 it will change anyways...
    if (this.activeWidget != null) {
      getWidgetAsTakesValueString().setValue(value);
    }
    this.recentStringValue = value;
    if (!getUiWidget().getMode().isEditable()) {
      updateWidgetViewMode();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected final Widget createToplevelWidget() {

    return new HorizontalFlowPanel();
  }

  /**
   * @return the widget for the {@link net.sf.mmm.client.ui.api.common.UiMode#VIEW view-mode}.
   */
  protected final Widget getWidgetViewMode() {

    if (this.widgetViewMode == null) {
      this.widgetViewMode = createViewWidget();
      this.widgetViewMode.setStylePrimaryName(UiWidgetField.STYLE_VIEW);
      ((ComplexPanel) getToplevelWidget()).add(this.widgetViewMode);
    }
    return this.widgetViewMode;
  }

  /**
   * Creates the {@link Widget} that is used to display the field value in
   * {@link net.sf.mmm.client.ui.api.common.UiMode#VIEW view-mode}. If you override this method and change the
   * widget type you will also need to override {@link #updateWidgetViewMode()}.
   * 
   * @return a new view {@link Widget}.
   */
  protected Widget createViewWidget() {

    Label view = new Label();
    return view;
  }

  /**
   * @return the widget for the {@link net.sf.mmm.client.ui.api.common.UiMode#EDIT edit-mode}.
   */
  @Override
  public final WIDGET getActiveWidget() {

    if (this.activeWidget == null) {
      this.activeWidget = createActiveWidget();
      getInputElement().setAttribute("oninput", "this.setCustomValidity('');");
      this.widgetMarkerEditMode = new InlineLabel();
      UiMode mode = getUiWidget().getMode();
      if ((mode != null) && (!mode.isEditable())) {
        this.activeWidget.setVisible(false);
        this.widgetMarkerEditMode.setVisible(false);
      }
      ComplexPanel panel = (ComplexPanel) getToplevelWidget();
      attachActiveWidget(panel);
      panel.add(this.widgetMarkerEditMode);
    }
    return this.activeWidget;
  }

  /**
   * Attaches the {@link #getActiveWidget() active widget} after {@link #createActiveWidget() creation} to
   * {@link #getToplevelWidget()}. May be overridden for composed active widgets.
   * 
   * @param panel is the {@link ComplexPanel} (see {@link #getToplevelWidget()}) where to
   *        {@link ComplexPanel#add(com.google.gwt.user.client.ui.Widget) attach}.
   */
  protected void attachActiveWidget(ComplexPanel panel) {

    panel.add(this.activeWidget);
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

    String failure = validationFailure;
    if (failure == null) {
      failure = "";
    }
    boolean success = JavaScriptUtil.getInstance().setCustomValidity(getInputElement(), failure);
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
