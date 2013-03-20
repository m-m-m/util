/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.javafx.widget.core.adapter;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import net.sf.mmm.client.ui.api.feature.UiFeatureFocus;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventFocus;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetImage;
import net.sf.mmm.client.ui.base.widget.core.adapter.UiWidgetAdapterButton;
import net.sf.mmm.client.ui.impl.javafx.handler.event.FocusEventAdapterJavaFx;
import net.sf.mmm.client.ui.impl.javafx.widget.adapter.UiWidgetAdapterJavaFxLabeled;
import net.sf.mmm.util.nls.api.NlsIllegalStateException;
import net.sf.mmm.util.nls.api.NlsUnsupportedOperationException;

/**
 * This is the implementation of {@link UiWidgetAdapterButton} using JavaFx based on {@link Button}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterJavaFxButton extends UiWidgetAdapterJavaFxLabeled<Button> implements UiWidgetAdapterButton {

  /** @see #setFocusEventSender(UiFeatureFocus, UiHandlerEventFocus) */
  private FocusEventAdapterJavaFx focusEventAdapter;

  /**
   * The constructor.
   * 
   */
  public UiWidgetAdapterJavaFxButton() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Button createToplevelWidget() {

    return new Button();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setImage(UiWidgetImage imageWidget) {

    ImageView newImage = null;
    if (imageWidget != null) {
      newImage = getToplevelWidget(imageWidget, ImageView.class);
    }
    getToplevelWidget().setGraphic(newImage);
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
  public void setLabel(String label) {

    getToplevelWidget().setText(label);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setAccessKey(char accessKey) {

    // Not supported, we need to add a central access key manager to ui-widget-base and register a native
    // implementation there to focus/click manually the associated widget...

    // getActiveWidget().setAccelerator()
    // getActiveWidget().setAcccessKey
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setFocusEventSender(UiFeatureFocus source, UiHandlerEventFocus sender) {

    if (this.focusEventAdapter != null) {
      throw new NlsIllegalStateException();
    }
    this.focusEventAdapter = new FocusEventAdapterJavaFx(source, sender);
    getActiveWidget().focusedProperty().addListener(this.focusEventAdapter);
  }

}
