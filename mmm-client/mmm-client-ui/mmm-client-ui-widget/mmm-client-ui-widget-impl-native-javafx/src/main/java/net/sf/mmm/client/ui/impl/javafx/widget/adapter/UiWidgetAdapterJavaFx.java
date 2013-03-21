/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.javafx.widget.adapter;

import net.sf.mmm.client.ui.api.common.SizeUnit;
import net.sf.mmm.client.ui.api.widget.UiWidget;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidget;
import net.sf.mmm.client.ui.base.widget.adapter.AbstractUiWidgetAdapter;
import net.sf.mmm.util.nls.api.IllegalCaseException;
import net.sf.mmm.util.nls.api.NlsClassCastException;

/**
 * This is the top-most implementation of {@link net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapter} for
 * JavaFx. Unfortunately the only common class of JavaFx widgets is {@link Object}.
 * 
 * @param <WIDGET> is the generic type of {@link #getToplevelWidget()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class UiWidgetAdapterJavaFx<WIDGET> extends AbstractUiWidgetAdapter<WIDGET> {

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
  public void removeFromParent() {

    // not supported - will be done via parent.removeChild();
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
   * This method converts the given <code>size</code> (width or height) to a double value for JavaFx.
   * 
   * @param size is the size to convert. May be any {@link SizeUnit}.
   * @return the converted size.
   */
  protected double convertSizeUnit(String size) {

    double widthDouble;
    String pixelSuffix = SizeUnit.PIXEL.getValue();
    if (size.endsWith(pixelSuffix)) {
      String widthPixels = size.substring(0, size.length() - pixelSuffix.length());
      widthDouble = Double.parseDouble(widthPixels);
      // getToplevelWidget().setWidth(widthDouble);
    } else {
      // not implemented...
      throw new IllegalCaseException(size);
    }
    return widthDouble;
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
