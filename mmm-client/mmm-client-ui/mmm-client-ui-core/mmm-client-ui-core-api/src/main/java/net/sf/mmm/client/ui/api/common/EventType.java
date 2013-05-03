/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.common;

/**
 * This enum contains the available types of a {@link UiEvent}.<br/>
 * <b>NOTE:</b><br/>
 * We designed the event mechanism for optimal performance without unnecessary overhead such as creating event
 * objects.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public enum EventType implements UiEvent {

  /** The widget (button, menu item, etc.) has been clicked. */
  CLICK,

  /** The widget has gained the focus. This is the opposite of {@link #FOCUS_LOSS}. */
  FOCUS_GAIN,

  /**
   * The widget has lost the focus. This is the opposite of {@link #FOCUS_GAIN} and is also called
   * <em>blur</em>.
   */
  FOCUS_LOSS,

  /** The widget has been collapsed. This is the opposite of {@link #EXPAND}. */
  COLLAPSE,

  /** The widget has been collapsed. This is the opposite of {@link #COLLAPSE}. */
  EXPAND,

  /**
   * The {@link net.sf.mmm.util.lang.api.attribute.AttributeReadValue#getValue() value} of the widget has
   * changed.
   */
  VALUE_CHANGE,

  /**
   * The {@link net.sf.mmm.client.ui.api.attribute.AttributeReadSelectedValue#getSelectedValues() selection}
   * of the widget has changed.
   */
  SELECTION_CHANGE,

  /**
   * The {@link net.sf.mmm.client.ui.api.attribute.AttributeReadMode#getMode() mode} of the widget has
   * changed.
   */
  MODE;

  /**
   * {@inheritDoc}
   */
  @Override
  public EventType getType() {

    return this;
  }

}
