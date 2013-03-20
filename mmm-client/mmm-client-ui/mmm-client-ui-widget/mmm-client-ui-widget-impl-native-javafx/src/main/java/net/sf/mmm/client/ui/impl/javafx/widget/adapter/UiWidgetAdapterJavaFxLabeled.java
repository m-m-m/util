/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.javafx.widget.adapter;

import javafx.scene.control.Labeled;

/**
 * This is the implementation of {@link net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapter} using JavaFx
 * based on {@link Labeled}.
 * 
 * @param <WIDGET> is the generic type of {@link #getToplevelWidget()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class UiWidgetAdapterJavaFxLabeled<WIDGET extends Labeled> extends UiWidgetAdapterJavaFxControl<WIDGET> {

  /**
   * The constructor.
   */
  public UiWidgetAdapterJavaFxLabeled() {

    super();
  }

}
