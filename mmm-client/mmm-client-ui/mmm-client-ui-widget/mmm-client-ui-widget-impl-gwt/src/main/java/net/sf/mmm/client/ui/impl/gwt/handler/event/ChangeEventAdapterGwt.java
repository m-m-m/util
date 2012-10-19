/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.handler.event;

import net.sf.mmm.client.ui.api.feature.UiFeatureValue;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventValueChange;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

/**
 * This class is the GWT specific adapter for {@link UiHandlerEventValueChange}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the changed {@link UiFeatureValue#getValue() value}.
 */
public class ChangeEventAdapterGwt<VALUE> implements ChangeHandler, ClickHandler {

  /** The source of the events (UiWidget). */
  private final UiFeatureValue<VALUE> source;

  /** The event sender. */
  private final UiHandlerEventValueChange<VALUE> sender;

  /**
   * The constructor.
   * 
   * @param source is the source of the events (typically {@link net.sf.mmm.client.ui.api.widget.UiWidget}).
   * @param sender is the sender of events (will typically be
   *        {@link net.sf.mmm.client.ui.base.handler.event.ChangeEventSender}).
   */
  public ChangeEventAdapterGwt(UiFeatureValue<VALUE> source, UiHandlerEventValueChange<VALUE> sender) {

    super();
    this.source = source;
    this.sender = sender;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onChange(ChangeEvent event) {

    this.sender.onValueChange(this.source, false);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onClick(ClickEvent event) {

    this.sender.onValueChange(this.source, false);
  }

}
