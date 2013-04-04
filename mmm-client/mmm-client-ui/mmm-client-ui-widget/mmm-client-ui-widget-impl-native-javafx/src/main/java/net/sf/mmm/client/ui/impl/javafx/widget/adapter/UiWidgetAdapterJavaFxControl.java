/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.javafx.widget.adapter;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.scene.control.Tooltip;
import net.sf.mmm.client.ui.api.common.Length;
import net.sf.mmm.client.ui.api.feature.UiFeatureClick;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventClick;
import net.sf.mmm.client.ui.api.widget.UiWidget;
import net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapterActive;

/**
 * This is the implementation of {@link net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapter} using JavaFx
 * based on {@link Control}.
 * 
 * @param <WIDGET> is the generic type of {@link #getToplevelWidget()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class UiWidgetAdapterJavaFxControl<WIDGET extends Control> extends UiWidgetAdapterJavaFxNode<WIDGET>
    implements UiWidgetAdapterActive {

  /**
   * The constructor.
   * 
   */
  public UiWidgetAdapterJavaFxControl() {

    super();
  }

  /**
   * This method is a simplified variant of {@link #getToplevelWidget(UiWidget, Class)}.
   * 
   * @param widget is the {@link UiWidget} to "unwrap".
   * @return the native widget.
   */
  protected static final Control getToplevelWidget(UiWidget widget) {

    return getToplevelWidget(widget, Control.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Control getActiveWidget() {

    return getToplevelWidget();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTooltip(String tooltip) {

    Tooltip tooltipObject = getToplevelWidget().getTooltip();
    if (tooltipObject == null) {
      tooltipObject = new Tooltip(tooltip);
      getToplevelWidget().setTooltip(tooltipObject);
    } else {
      tooltipObject.setText(tooltip);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Length getWidth() {

    return Length.valueOfPixel(getToplevelWidget().getWidth());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Length getHeight() {

    return Length.valueOfPixel(getToplevelWidget().getHeight());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setWidth(Length width) {

    getToplevelWidget().setPrefWidth(convertLength(width));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setHeight(Length height) {

    getToplevelWidget().setPrefHeight(convertLength(height));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setClickEventSender(final UiFeatureClick eventSource, final UiHandlerEventClick eventSender) {

    EventHandler<? super ActionEvent> handler = new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {

        eventSender.onClick(eventSource, false);
      };
    };
    getActiveWidget().addEventHandler(ActionEvent.ACTION, handler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setEnabled(boolean enabled) {

    getActiveWidget().setDisable(!enabled);
  }

}
