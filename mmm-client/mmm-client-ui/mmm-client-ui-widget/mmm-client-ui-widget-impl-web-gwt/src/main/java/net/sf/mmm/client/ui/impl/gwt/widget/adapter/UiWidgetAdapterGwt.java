/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.adapter;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteImage;
import net.sf.mmm.client.ui.api.common.Length;
import net.sf.mmm.client.ui.api.feature.UiFeatureEvent;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEvent;
import net.sf.mmm.client.ui.api.widget.UiWidget;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetImage;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidget;
import net.sf.mmm.client.ui.base.widget.adapter.AbstractUiWidgetAdapterWithEvents;
import net.sf.mmm.client.ui.impl.gwt.handler.event.EventAdapterGwt;
import net.sf.mmm.util.nls.api.NlsClassCastException;
import net.sf.mmm.util.nls.api.NlsUnsupportedOperationException;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is the implementation of {@link net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapter} using GWT
 * based on {@link UIObject}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <WIDGET> is the generic type of {@link #getToplevelWidget()}.
 */
public abstract class UiWidgetAdapterGwt<WIDGET extends UIObject> extends
    AbstractUiWidgetAdapterWithEvents<WIDGET, EventAdapterGwt> implements AttributeWriteImage<UiWidgetImage> {

  /**
   * The tab index to remove from tab-order. Should actually be <code>-1</code>. See <a
   * href="http://code.google.com/p/google-web-toolkit/issues/detail?id=8323">GWT bug 8323</a>.
   */
  protected static final int TAB_INDEX_NONE = -2;

  /** @see #setImage(UiWidgetImage) */
  private Image image;

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwt() {

    super();
  }

  /**
   * This method is a simplified variant of {@link #getToplevelWidget(UiWidget, Class)}.
   * 
   * @param widget is the {@link UiWidget} to "unwrap".
   * @return the native widget.
   */
  protected static final Widget getToplevelWidget(UiWidget widget) {

    return getToplevelWidget(widget, Widget.class);
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
  protected static final <W extends UIObject> W getToplevelWidget(UiWidget widget, Class<W> widgetType) {

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
  public UIObject getActiveWidget() {

    return getToplevelWidget();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setId(String id) {

    Element element = getToplevelWidget().getElement();
    if (id == null) {
      element.removeAttribute(HTML_ATTRIBUTE_ID);
    } else {
      element.setAttribute(HTML_ATTRIBUTE_ID, id);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTooltip(String tooltip) {

    getToplevelWidget().setTitle(tooltip);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setAttribute(String name, String value) {

    Element element = getToplevelWidget().getElement();
    if (value == null) {
      element.removeAttribute(name);
    } else {
      element.setAttribute(name, value);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setVisible(boolean visible) {

    getToplevelWidget().setVisible(visible);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setStyles(String styles) {

    getToplevelWidget().setStyleName(styles);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean addStyle(String style) {

    getToplevelWidget().addStyleName(style);
    // result will actually be ignored...
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeStyle(String style) {

    getToplevelWidget().removeStyleName(style);
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setPrimaryStyle(String primaryStyle) {

    getToplevelWidget().setStylePrimaryName(primaryStyle);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isStyleDeltaSupported() {

    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Length getWidth() {

    int width = getToplevelWidget().getOffsetWidth();
    return Length.valueOfPixel(width);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Length getHeight() {

    int heigth = getToplevelWidget().getOffsetHeight();
    return Length.valueOfPixel(heigth);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setWidth(Length width) {

    getToplevelWidget().setWidth(width.toString());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setHeight(Length height) {

    getToplevelWidget().setHeight(height.toString());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected EventAdapterGwt createEventAdapter(UiFeatureEvent source, UiHandlerEvent sender) {

    return new EventAdapterGwt(source, sender);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setImage(UiWidgetImage imageWidget) {

    Image newImage = null;
    if (imageWidget != null) {
      newImage = getToplevelWidget(imageWidget, Image.class);
    }
    Element element = getImageParentElement();
    if (this.image == null) {
      if (newImage != null) {
        element.insertFirst(newImage.getElement());
      }
    } else {
      if (newImage == null) {
        element.removeChild(this.image.getElement());
      } else {
        element.replaceChild(newImage.getElement(), this.image.getElement());
      }
    }
    this.image = newImage;
  }

  /**
   * This method gets the parent-{@link Element} where a potential {@link #setImage(UiWidgetImage) image is
   * attached}.
   * 
   * @return the parent-{@link Element} for an image.
   */
  protected Element getImageParentElement() {

    return getToplevelWidget().getElement();
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
  public void dispose() {

    // do nothing...
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String createAbsoluteImageUrl(String relativePath) {

    return GWT.getModuleBaseURL() + "gwt/" + getUiWidget().getContext().getConfiguration().getTheme() + "/images/"
        + relativePath;
  }

}
