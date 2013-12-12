/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.javafx.widget.adapter;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteFocused;
import net.sf.mmm.client.ui.impl.javafx.handler.event.EventAdapterJavaFx;

/**
 * This is the implementation of {@link net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapter} using JavaFx
 * based on {@link Node}.
 * 
 * @param <WIDGET> is the generic type of {@link #getToplevelWidget()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class UiWidgetAdapterJavaFxNode<WIDGET extends Node> extends UiWidgetAdapterJavaFx<WIDGET> implements
    AttributeWriteFocused {

  /**
   * The constructor.
   */
  public UiWidgetAdapterJavaFxNode() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Node getActiveWidget() {

    return getToplevelWidget();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setId(String id) {

    getToplevelWidget().setId(id);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setAttribute(String name, String value) {

    // TODO hohwille: for aria support and other stuff...
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setVisible(boolean visible, boolean programmatic) {

    getToplevelWidget().setVisible(visible);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setStyles(String styles) {

    String[] styleArray = styles.split(" ");
    ObservableList<String> styleList = getToplevelWidget().getStyleClass();
    styleList.clear();
    styleList.addAll(styleArray);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean addStyle(String style) {

    getToplevelWidget().getStyleClass().add(style);
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeStyle(String style) {

    getToplevelWidget().getStyleClass().remove(style);
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setPrimaryStyle(String primaryStyle) {

    ObservableList<String> styles = getToplevelWidget().getStyleClass();
    if (styles.isEmpty()) {
      styles.add(primaryStyle);
    } else {
      styles.set(0, primaryStyle);
    }
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
  protected void applyEventAdapterForClick(EventAdapterJavaFx adapter) {

    getToplevelWidget().addEventHandler(ActionEvent.ACTION, getEventAdapter());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void applyEventAdapterForFocus(EventAdapterJavaFx adapter) {

    getActiveWidget().focusedProperty().addListener(getEventAdapter());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean setFocused() {

    getToplevelWidget().requestFocus();
    return true;
  }

}
