/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.adapter;

import net.sf.mmm.client.ui.NlsBundleClientUiRoot;
import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.attribute.AttributeReadAccessKey;
import net.sf.mmm.client.ui.api.attribute.AttributeReadAltText;
import net.sf.mmm.client.ui.api.attribute.AttributeReadColumnSpan;
import net.sf.mmm.client.ui.api.attribute.AttributeReadFocused;
import net.sf.mmm.client.ui.api.attribute.AttributeReadHeightInRows;
import net.sf.mmm.client.ui.api.attribute.AttributeReadImage;
import net.sf.mmm.client.ui.api.attribute.AttributeReadLabel;
import net.sf.mmm.client.ui.api.attribute.AttributeReadMaximumTextLength;
import net.sf.mmm.client.ui.api.attribute.AttributeReadResizable;
import net.sf.mmm.client.ui.api.attribute.AttributeReadSelectionMode;
import net.sf.mmm.client.ui.api.attribute.AttributeReadUrl;
import net.sf.mmm.client.ui.api.attribute.AttributeReadValidationFailure;
import net.sf.mmm.client.ui.api.common.SelectionMode;
import net.sf.mmm.client.ui.api.widget.UiWidgetComposite;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetImage;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetLabel;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidgetNative;
import net.sf.mmm.client.ui.base.widget.core.AbstractUiWidgetLabel;
import net.sf.mmm.util.component.api.AlreadyInitializedException;
import net.sf.mmm.util.exception.api.NlsUnsupportedOperationException;
import net.sf.mmm.util.lang.api.attribute.AttributeReadTitle;
import net.sf.mmm.util.nls.api.NlsAccess;

/**
 * This is the abstract base implementation of {@link UiWidgetAdapter}.
 * 
 * @param <WIDGET> is the generic type of {@link #getToplevelWidget()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetAdapter<WIDGET> implements UiWidgetAdapter, AttributeReadAltText,
    AttributeReadUrl, AttributeReadLabel, AttributeReadTitle<String>, AttributeReadImage<UiWidgetImage>,
    AttributeReadValidationFailure, AttributeReadAccessKey, AttributeReadFocused, AttributeReadMaximumTextLength,
    AttributeReadHeightInRows, AttributeReadColumnSpan, AttributeReadResizable, AttributeReadSelectionMode {

  /** @see #getToplevelWidget() */
  private WIDGET toplevelWidget;

  /** @see #getUiWidget() */
  private AbstractUiWidgetNative<?, ?> uiWidget;

  /**
   * The constructor.
   */
  public AbstractUiWidgetAdapter() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param toplevelWidget is the {@link #getToplevelWidget() toplevel widget}.
   */
  public AbstractUiWidgetAdapter(WIDGET toplevelWidget) {

    super();
    this.toplevelWidget = toplevelWidget;
  }

  /**
   * This method creates the {@link #getToplevelWidget() underlying widget}. <br>
   * <b>ATTENTION:</b><br>
   * This method is called from the constructor but implemented in sub-classes. You should NOT access or even
   * modify member variables as they are NOT set at this point (even final members). However, this design is
   * done by intention instead of passing the widget as constructor argument to give more flexibility by
   * overriding and also for potential lazy initialization of the widget.
   * 
   * @return a new instance of the {@link #getToplevelWidget() underlying widget}.
   */
  protected abstract WIDGET createToplevelWidget();

  /**
   * {@inheritDoc}
   */
  @Override
  public WIDGET getToplevelWidget() {

    if (this.toplevelWidget == null) {
      this.toplevelWidget = createToplevelWidget();
    }
    return this.toplevelWidget;
  }

  /**
   * This method gets the active widget. For simple cases this will be the same as
   * {@link #getToplevelWidget()}. In more complex scenarios the {@link #getToplevelWidget() toplevel widget}
   * may only be a container widget and this method returns a child that represents the active part.
   * 
   * @return the active widget.
   */
  public Object getActiveWidget() {

    return this.toplevelWidget;
  }

  /**
   * This method gives access to the {@link AbstractUiWidgetNative native UI widget} wrapping this widget
   * adapter. <br>
   * <b>ATTENTION:</b><br>
   * For the most cases the call hierarchy should go from the widget to the widget adapter. Only access this
   * method when required (to avoid redundant storage of attributes, etc.).
   * 
   * @return the {@link AbstractUiWidgetNative UI widget} wrapping this widget adapter.
   */
  public final AbstractUiWidgetNative<?, ?> getUiWidget() {

    return this.uiWidget;
  }

  /**
   * This method is a design suggestion for implementations of widget adapters that need typed access to
   * {@link #getUiWidget()} and want to perform the cast only in a single place. If you would override
   * {@link #getUiWidget()} to add the cast and do that multiple times across the inheritance hierarchy this
   * would cause multiple/unnecessary casts. Casting is required as we already declare enough generics for
   * widget adapters and also want to keep the code more flexible.
   * 
   * @return the {@link #getUiWidget() UiWidget} potentially specifically typed.
   */
  protected AbstractUiWidgetNative<?, ?> getUiWidgetTyped() {

    return this.uiWidget;
  }

  /**
   * @return the {@link UiContext}.
   */
  public UiContext getContext() {

    return this.uiWidget.getContext();
  }

  /**
   * @param uiWidget is the uiWidget to set
   */
  public void setUiWidget(AbstractUiWidgetNative<?, ?> uiWidget) {

    if (this.uiWidget != null) {
      throw new AlreadyInitializedException();
    }
    this.uiWidget = uiWidget;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setParent(UiWidgetComposite<?> parent) {

    // do nothing by default...
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getId() {

    throw new NlsUnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SelectionMode getSelectionMode() {

    throw new NlsUnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMode(boolean editMode) {

    // do nothing by default
  }

  /**
   * @return the instance of {@link NlsBundleClientUiRoot}.
   */
  protected NlsBundleClientUiRoot getBundle() {

    return NlsAccess.getBundleFactory().createBundle(NlsBundleClientUiRoot.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTooltip() {

    throw new NlsUnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isEnabled() {

    throw new NlsUnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getStyles() {

    throw new NlsUnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isDisposed() {

    throw new NlsUnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getAltText() {

    throw new NlsUnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getUrl() {

    throw new NlsUnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getLabel() {

    throw new NlsUnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTitle() {

    throw new NlsUnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetImage getImage() {

    throw new NlsUnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getValidationFailure() {

    throw new NlsUnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getAttribute(String name) {

    throw new NlsUnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public char getAccessKey() {

    throw new NlsUnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getMaximumTextLength() {

    throw new NlsUnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getHeightInRows() {

    throw new NlsUnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isResizable() {

    throw new NlsUnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getColumnSpan() {

    throw new NlsUnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getPrimaryStyle() {

    throw new NlsUnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasStyle(String style) {

    throw new NlsUnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean addStyle(String style) {

    throw new NlsUnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeStyle(String style) {

    throw new NlsUnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setPrimaryStyle(String primaryStyle) {

    throw new NlsUnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isStyleDeltaSupported() {

    // false by default
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isFocused() {

    // dummy, will never be called in adapter (only in widget)...
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AbstractUiWidgetLabel<?> createLabel(UiContext context) {

    return (AbstractUiWidgetLabel<?>) context.getWidgetFactory().create(UiWidgetLabel.class);
  }

}
