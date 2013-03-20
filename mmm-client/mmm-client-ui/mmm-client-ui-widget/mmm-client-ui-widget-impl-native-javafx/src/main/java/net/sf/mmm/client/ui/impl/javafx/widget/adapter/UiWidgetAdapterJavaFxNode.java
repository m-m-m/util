/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.javafx.widget.adapter;

import javafx.scene.Node;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteFocused;

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
  public void setVisible(boolean visible) {

    getToplevelWidget().setVisible(visible);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setStyles(String styles) {

    // TODO hohwille
    getToplevelWidget().getStyleClass().add(styles);
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
  public void setFocused() {

    getToplevelWidget().requestFocus();
  }

}
