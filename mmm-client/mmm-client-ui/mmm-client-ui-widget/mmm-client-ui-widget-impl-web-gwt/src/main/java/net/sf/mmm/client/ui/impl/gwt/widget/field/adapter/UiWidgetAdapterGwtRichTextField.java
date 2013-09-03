/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field.adapter;

import java.util.HashSet;
import java.util.Set;

import net.sf.mmm.client.ui.api.widget.field.RichTextFeature;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetRichTextField;
import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterRichTextField;
import net.sf.mmm.client.ui.impl.gwt.gwtwidgets.RichTextToolbar;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.HasAllFocusHandlers;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SimpleHtmlSanitizer;
import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.RichTextArea;

/**
 * This is the implementation of {@link UiWidgetAdapterRichTextField} using GWT based on {@link RichTextArea}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtRichTextField extends UiWidgetAdapterGwtField<FlowPanel, String, String> implements
    UiWidgetAdapterRichTextField {

  // TODO hohwille getSelectedText(), Popup with all formatting and preview, color picker, etc.

  /** @see #getRichTextArea() */
  private MyRichTextArea richTextArea;

  /** @see #getRichTextToolbar() */
  private RichTextToolbar richTextToolbar;

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtRichTextField() {

    super();
  }

  /**
   * @return the richTextArea
   */
  public MyRichTextArea getRichTextArea() {

    if (this.richTextArea == null) {
      this.richTextArea = new MyRichTextArea();
    }
    return this.richTextArea;
  }

  /**
   * @return the richTextToolbar
   */
  public RichTextToolbar getRichTextToolbar() {

    if (this.richTextToolbar == null) {
      this.richTextToolbar = new RichTextToolbar(getRichTextArea());
    }
    return this.richTextToolbar;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected FlowPanel createActiveWidget() {

    FlowPanel activeWidget = new FlowPanel();
    activeWidget.add(getRichTextToolbar());
    activeWidget.add(getRichTextArea());
    return activeWidget;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setHeightInRows(int rows) {

    getRichTextArea().setHeight(rows + "em");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setEnabled(boolean enabled) {

    getRichTextToolbar().setEnabled(enabled);
    getRichTextArea().setEnabled(enabled);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected HasChangeHandlers getWidgetAsHasChangeHandlers() {

    return getRichTextArea();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected TakesValue<String> getWidgetAsTakesValue() {

    return getRichTextArea();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Focusable getWidgetAsFocusable() {

    return getRichTextArea();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected HasKeyPressHandlers getWidgetAsKeyPressHandlers() {

    return getRichTextArea();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected HasAllFocusHandlers getWidgetAsHasAllFocusHandlers() {

    return getRichTextArea();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMaximumTextLength(int length) {

    getRichTextArea().getElement().setAttribute("maxlength", Integer.toString(length));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setAvailableFeatures(RichTextFeature... features) {

    Set<RichTextFeature> featureSet = new HashSet<RichTextFeature>();
    for (RichTextFeature feature : features) {
      featureSet.add(feature);
    }
    getRichTextToolbar();
    for (RichTextFeature feature : RichTextFeature.values()) {
      boolean available = featureSet.contains(feature);
      this.richTextToolbar.setFeatureAvailable(feature, available);
    }
  }

  /**
   * This inner class makes {@link RichTextArea} implement {@link HasValue}.
   */
  public static class MyRichTextArea extends RichTextArea implements HasValue<String>, HasChangeHandlers, FocusHandler,
      BlurHandler {

    /** The CSS style name if the rich-text-area is focused. */
    private static final String STYLE_RICH_TEXT_AREA_FOCUSED = "Focused";

    /** @see #addValueChangeHandler(ValueChangeHandler) */
    private HandlerRegistration changeHandlerRegistration;

    /**
     * The constructor.
     */
    public MyRichTextArea() {

      super();
      setStylePrimaryName(UiWidgetRichTextField.STYLE_RICH_TEXT_AREA);
      addFocusHandler(this);
      addBlurHandler(this);
      // final JavaScriptUtil javaScriptUtil = JavaScriptUtil.getInstance();
      // Runnable callback = new Runnable() {
      //
      // @Override
      // public void run() {
      //
      // // <link type="text/css" rel="stylesheet" href="Mmm.css">
      // Element linkElement = DOM.createElement("link");
      // linkElement.setAttribute("type", "text/css");
      // linkElement.setAttribute("rel", "stylesheet");
      // linkElement.setAttribute("href", GWT.getHostPageBaseURL() + "mmm/gwt/standard/richtext.css");
      // Element headElement = javaScriptUtil.getHeadElement(getElement());
      // headElement.appendChild(linkElement);
      // }
      // };
      // javaScriptUtil.onLoadFrame(getElement(), callback);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {

      if (this.changeHandlerRegistration == null) {
        this.changeHandlerRegistration = addChangeHandler(new ChangeHandler() {

          @Override
          public void onChange(ChangeEvent event) {

            ValueChangeEvent.fire(MyRichTextArea.this, getValue());
          }
        });
      }
      return addHandler(handler, ValueChangeEvent.getType());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HandlerRegistration addChangeHandler(ChangeHandler handler) {

      return addDomHandler(handler, ChangeEvent.getType());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValue() {

      return getHTML();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(String value) {

      setValue(value, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(String value, boolean fireEvents) {

      String oldValue = getValue();
      SafeHtml html = SimpleHtmlSanitizer.sanitizeHtml(value);
      setHTML(html);
      if (fireEvents) {
        ValueChangeEvent.fireIfNotEqual(this, oldValue, value);
      }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onFocus(FocusEvent event) {

      addStyleName(STYLE_RICH_TEXT_AREA_FOCUSED);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBlur(BlurEvent event) {

      removeStyleName(STYLE_RICH_TEXT_AREA_FOCUSED);
    }

  }

}
