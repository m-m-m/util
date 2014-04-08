/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.gwt.widgets.handler;

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * This is the interface for an object that {@link #addHandlerRegistration(HandlerRegistration) collects}
 * {@link HandlerRegistration}s.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface HandlerRegistrationCollector {

  /**
   * This method is to be called with the {@link HandlerRegistration} for all permanent handlers. This allows
   * to {@link HandlerRegistration#removeHandler() remove} them on
   * {@link net.sf.mmm.util.lang.api.attribute.AttributeWriteDisposed#dispose() disposal}.
   * 
   * @param registration is the {@link HandlerRegistration} to add.
   */
  void addHandlerRegistration(HandlerRegistration registration);

}
