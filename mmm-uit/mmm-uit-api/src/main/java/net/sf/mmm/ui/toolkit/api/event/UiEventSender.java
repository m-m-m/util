/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.event;

/**
 * This is the interface for a sender of
 * {@link UiEventListener#onEvent(net.sf.mmm.ui.toolkit.api.view.UiNode, UiEventType)
 * events}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiEventSender {

  /**
   * This method registers a {@link UiEventListener} that is interested in
   * {@link UiEventListener#onEvent(net.sf.mmm.ui.toolkit.api.view.UiNode, UiEventType)
   * events}.
   * 
   * @param listener is the {@link UiEventListener} to add.
   */
  void addListener(UiEventListener listener);

  /**
   * This method removes a {@link UiEventListener}. If that
   * <code>listener</code> was not registered before this method does not do any
   * change.
   * 
   * @param listener is the {@link UiEventListener} to remove.
   */
  void removeListener(UiEventListener listener);

  /**
   * This method sends an
   * {@link UiEventListener#onEvent(net.sf.mmm.ui.toolkit.api.view.UiNode, UiEventType)
   * event} of the given <code>eventType</code>.
   * 
   * @param eventType is the {@link UiEventType}.
   */
  void sendEvent(UiEventType eventType);

}
