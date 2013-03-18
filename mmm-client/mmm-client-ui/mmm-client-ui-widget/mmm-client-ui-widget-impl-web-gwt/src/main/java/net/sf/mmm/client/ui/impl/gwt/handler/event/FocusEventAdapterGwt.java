/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.handler.event;

import net.sf.mmm.client.ui.api.feature.UiFeatureFocus;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventFocus;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;

/**
 * This class is the GWT specific adapter for {@link UiHandlerEventFocus}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class FocusEventAdapterGwt implements FocusHandler, BlurHandler {

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
  public FocusEventAdapterGwt(UiFeatureFocus source, UiHandlerEventFocus sender) {

    super();
    this.source = source;
    this.sender = sender;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onBlur(BlurEvent event) {

    this.sender.onFocusChange(this.source, false, true);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onFocus(FocusEvent event) {

    this.sender.onFocusChange(this.source, this.programmatic, false);
    this.programmatic = false;
  }

  /**
   * We can NOT prevent GWT from firing an event on
   * {@link com.google.gwt.user.client.ui.FocusWidget#setFocus(boolean)}. So we set this flag if we triggered
   * this programatically in
   * {@link net.sf.mmm.client.ui.api.attribute.AttributeWriteFocused#setFocused(boolean)}.
   */
  public void setProgrammatic() {

    this.programmatic = true;
  }

}
