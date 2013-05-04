/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.javafx.widget.adapter;

import net.sf.mmm.client.ui.api.common.Length;
import net.sf.mmm.client.ui.api.common.SizeUnit;
import net.sf.mmm.client.ui.api.feature.UiFeatureEvent;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEvent;
import net.sf.mmm.client.ui.api.widget.UiWidget;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidget;
import net.sf.mmm.client.ui.base.widget.adapter.AbstractUiWidgetAdapterWithEvents;
import net.sf.mmm.client.ui.impl.javafx.handler.event.EventAdapterJavaFx;
import net.sf.mmm.util.nls.api.NlsClassCastException;
import net.sf.mmm.util.nls.api.NlsNullPointerException;

/**
 * This is the top-most implementation of {@link net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapter} for
 * JavaFx. Unfortunately the only common class of JavaFx widgets is {@link Object}.
 * 
 * @param <WIDGET> is the generic type of {@link #getToplevelWidget()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class UiWidgetAdapterJavaFx<WIDGET> extends
    AbstractUiWidgetAdapterWithEvents<WIDGET, EventAdapterJavaFx> {

  /**
   * The constructor.
   */
  public UiWidgetAdapterJavaFx() {

    super();
  }

  /**
   * This method gets the
   * {@link net.sf.mmm.client.ui.api.widget.factory.UiWidgetFactoryNative#getNativeWidget(net.sf.mmm.client.ui.api.widget.UiWidgetRegular)
   * native widget} of the given <code>widget</code>.
   * 
   * @param <W> is the generic type of <code>widgetType</code>.
   * @param widget is the {@link UiWidget} to "unwrap".
   * @param widgetType is the expected type of the native widget.
   * @return the native widget.
   */
  @SuppressWarnings("unchecked")
  protected static final <W> W getToplevelWidget(UiWidget widget, Class<W> widgetType) {

    Object nativeWidget = AbstractUiWidget.getWidgetAdapter(widget).getToplevelWidget();
    try {
      return (W) nativeWidget;
    } catch (ClassCastException e) {
      throw new NlsClassCastException(e, widget, widgetType);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected EventAdapterJavaFx createEventAdapter(UiFeatureEvent source, UiHandlerEvent sender) {

    return new EventAdapterJavaFx(source, sender);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeFromParent() {

    // not needed - will be done via parent.removeChild();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTooltip(String tooltip) {

    // not supported by Object or Node, will be overridden for Control.
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setStyles(String styles) {

    // not supported by Object, will be overridden for Node.
  }

  /**
   * This method converts the given <code>length</code> (width or height) to a double value for JavaFx.
   * 
   * @param length is the size to convert. May be any {@link SizeUnit}.
   * @return the converted size.
   */
  protected double convertLength(Length length) {

    NlsNullPointerException.checkNotNull(Length.class, length);
    assert (length.getUnit() == SizeUnit.PIXEL);
    return length.getAmount();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void dispose() {

    // do nothing...
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String createAbsoluteImageUrl(String relativePath) {

    return "todo/" + getUiWidget().getContext().getConfiguration().getTheme() + "/images/" + relativePath;
  }

}
