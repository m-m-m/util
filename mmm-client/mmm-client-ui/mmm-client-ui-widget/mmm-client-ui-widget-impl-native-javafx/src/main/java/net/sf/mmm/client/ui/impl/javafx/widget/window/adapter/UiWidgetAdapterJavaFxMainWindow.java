/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.javafx.widget.window.adapter;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.sf.mmm.client.ui.api.widget.menu.UiWidgetMenuBar;
import net.sf.mmm.client.ui.base.widget.window.adapter.UiWidgetAdapterMainWindow;

/**
 * This is the implementation of {@link UiWidgetAdapterMainWindow} using JavaFx based on {@link Stage}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterJavaFxMainWindow extends UiWidgetAdapterJavaFxBaseWindow<Stage> implements
    UiWidgetAdapterMainWindow {

  private VBox verticalPanel;

  /**
   * The constructor.
   */
  public UiWidgetAdapterJavaFxMainWindow() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isResizable() {

    return getToplevelWidget().isResizable();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMenuBar(UiWidgetMenuBar menuBar) {

    // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Stage createToplevelWidget() {

    // TODO get the toplevel stage - we need some access via UiContext or via some static...
    Stage stage = new Stage();
    this.verticalPanel = new VBox();
    Scene scene = new Scene(this.verticalPanel);
    stage.setScene(scene);
    return stage;
  }

}
