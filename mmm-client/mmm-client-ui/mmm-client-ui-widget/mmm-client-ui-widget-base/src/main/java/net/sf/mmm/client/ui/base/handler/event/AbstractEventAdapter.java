/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.handler.event;

import net.sf.mmm.client.ui.api.event.EventType;
import net.sf.mmm.client.ui.api.event.UiEvent;
import net.sf.mmm.client.ui.api.event.UiEventClick;
import net.sf.mmm.client.ui.api.event.UiEventClose;
import net.sf.mmm.client.ui.api.event.UiEventCollapse;
import net.sf.mmm.client.ui.api.event.UiEventExpand;
import net.sf.mmm.client.ui.api.event.UiEventFocusGain;
import net.sf.mmm.client.ui.api.event.UiEventFocusLoss;
import net.sf.mmm.client.ui.api.event.UiEventMode;
import net.sf.mmm.client.ui.api.event.UiEventOpen;
import net.sf.mmm.client.ui.api.event.UiEventSelectionChange;
import net.sf.mmm.client.ui.api.event.UiEventValueChange;
import net.sf.mmm.client.ui.api.feature.UiFeatureClick;
import net.sf.mmm.client.ui.api.feature.UiFeatureCollapse;
import net.sf.mmm.client.ui.api.feature.UiFeatureEvent;
import net.sf.mmm.client.ui.api.feature.UiFeatureFocus;
import net.sf.mmm.client.ui.api.feature.UiFeatureMode;
import net.sf.mmm.client.ui.api.feature.UiFeatureOpenClose;
import net.sf.mmm.client.ui.api.feature.UiFeatureSelectedValue;
import net.sf.mmm.client.ui.api.feature.UiFeatureValue;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEvent;
import net.sf.mmm.util.nls.api.IllegalCaseException;
import net.sf.mmm.util.nls.api.NlsClassCastException;

/**
 * This is the abstract base class for a toolkit specific adapter of {@link UiHandlerEvent}. It will adapt
 * from the native events to {@link UiHandlerEvent#onEvent(UiEvent)} in the {@link UiHandlerEvent event
 * handler} given at construction.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractEventAdapter {

  /** The source of the events (UiWidget). */
  private final UiFeatureEvent source;

  /** The event sender. */
  private final UiHandlerEvent sender;

  /** @see #setProgrammaticEventType(EventType) */
  private EventType programmaticEventType;

  /**
   * The constructor.
   * 
   * @param source is the source of the events (typically {@link net.sf.mmm.client.ui.api.widget.UiWidget}).
   * @param sender is the sender of events (an adapter that delegates to the individual handlers/listeners).
   */
  public AbstractEventAdapter(UiFeatureEvent source, UiHandlerEvent sender) {

    super();
    this.source = source;
    this.sender = sender;
  }

  /**
   * Fires an event of the given {@link EventType}.
   * 
   * @param type is the {@link EventType} to fire.
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public void fireEvent(EventType type) {

    boolean programmatic = (this.programmaticEventType == type);
    UiEvent event;
    switch (type) {
      case CLICK:
        try {
          event = new UiEventClick((UiFeatureClick) this.source, programmatic);
        } catch (ClassCastException e) {
          throw new NlsClassCastException(e, this.source, UiFeatureClick.class);
        }
        break;
      case COLLAPSE:
        event = new UiEventCollapse((UiFeatureCollapse) this.source, programmatic);
        break;
      case EXPAND:
        event = new UiEventExpand((UiFeatureCollapse) this.source, programmatic);
        break;
      case FOCUS_GAIN:
        event = new UiEventFocusGain((UiFeatureFocus) this.source, programmatic);
        break;
      case FOCUS_LOSS:
        event = new UiEventFocusLoss((UiFeatureFocus) this.source, programmatic);
        break;
      case MODE:
        event = new UiEventMode((UiFeatureMode) this.source, programmatic);
        break;
      case SELECTION_CHANGE:
        event = new UiEventSelectionChange((UiFeatureSelectedValue) this.source, programmatic);
        break;
      case VALUE_CHANGE:
        event = new UiEventValueChange((UiFeatureValue) this.source, programmatic);
        break;
      case OPEN:
        event = new UiEventOpen((UiFeatureOpenClose) this.source, programmatic);
        break;
      case CLOSE:
        event = new UiEventClose((UiFeatureOpenClose) this.source, programmatic);
        break;
      default :
        throw new IllegalCaseException(EventType.class, type);
    }
    this.sender.onEvent(event);
    if (programmatic) {
      this.programmaticEventType = null;
    }
  }

  /**
   * We can NOT prevent all native UI toolkits from firing events (e.g. when
   * {@link net.sf.mmm.client.ui.api.attribute.AttributeWriteFocused#setFocused() programmatically setting
   * focus}. So we set the {@link EventType} before we programmatically invoke the action that will cause the
   * native event.
   * 
   * @param type is the expected {@link EventType} used for detection to prevent concurrent user-events to
   *        interfere.
   */
  public void setProgrammaticEventType(EventType type) {

    this.programmaticEventType = type;
  }

}
