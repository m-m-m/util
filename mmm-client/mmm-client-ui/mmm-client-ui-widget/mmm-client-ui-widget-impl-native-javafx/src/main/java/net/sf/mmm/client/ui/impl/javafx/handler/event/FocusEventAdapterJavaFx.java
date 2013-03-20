/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.javafx.handler.event;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import net.sf.mmm.client.ui.api.feature.UiFeatureFocus;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventFocus;

/**
 * This class is the GWT specific adapter for {@link UiHandlerEventFocus}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class FocusEventAdapterJavaFx implements ChangeListener<Boolean> {

  /** The source of the events (UiWidget). */
  private final UiFeatureFocus source;

  /** The event sender. */
  private final UiHandlerEventFocus sender;

  /** @see #setProgrammatic() */
  private boolean programmatic;

  /**
   * The constructor.
   * 
   * @param source is the source of the events (typically {@link net.sf.mmm.client.ui.api.widget.UiWidget}).
   * @param sender is the sender of events (will typically be
   *        {@link net.sf.mmm.client.ui.base.handler.event.FocusEventSender}).
   */
  public FocusEventAdapterJavaFx(UiFeatureFocus source, UiHandlerEventFocus sender) {

    super();
    this.source = source;
    this.sender = sender;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

    if (Boolean.TRUE.equals(newValue)) {
      this.sender.onFocusChange(this.source, this.programmatic, false);
      this.programmatic = false;
    } else {
      this.sender.onFocusChange(this.source, false, true);
    }
  }

  /**
   * We can NOT prevent JavaFx from firing an event on {@link javafx.scene.Node#requestFocus()}. So we set
   * this flag if we triggered this programatically in
   * {@link net.sf.mmm.client.ui.api.attribute.AttributeWriteFocused#setFocused()}.
   */
  public void setProgrammatic() {

    this.programmatic = true;
  }

}
