/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.handler.event;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeReadHandlerObserver;
import net.sf.mmm.ui.toolkit.api.handler.UiHandlerObserver;
import net.sf.mmm.ui.toolkit.api.handler.event.UiHandlerEvent;
import net.sf.mmm.util.nls.api.NlsNullPointerException;

/**
 * This is the abstract base implementation for a class that allows to {@link #addHandler(UiHandlerEvent) add}
 * and {@link #removeHandler(UiHandlerEvent) remove} {@link UiHandlerEvent event handlers} and to fire events
 * to the registered handlers.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <HANDLER> is the generic type of the {@link UiHandlerEvent} to address.
 * @param <SOURCE> is the source of the events.
 */
public abstract class AbstractEventSender<HANDLER extends UiHandlerEvent, SOURCE extends AttributeReadHandlerObserver> {

  /** The type of the managed {@link UiHandlerEvent event handlers}. */
  private final Class<HANDLER> handlerType;

  /** @see #getHandlers() */
  private final List<HANDLER> handlers;

  /** @see #getSource() */
  private final SOURCE source;

  /**
   * The constructor.
   * 
   * @param handlerType is the type of the managed {@link UiHandlerEvent event handlers}.
   * @param source is the source of the events.
   */
  public AbstractEventSender(Class<HANDLER> handlerType, SOURCE source) {

    super();
    this.handlers = new ArrayList<HANDLER>();
    this.handlerType = handlerType;
    this.source = source;
  }

  /**
   * @return the source that triggers the events.
   */
  protected SOURCE getSource() {

    return this.source;
  }

  /**
   * @return the {@link List} of the currently {@link #addHandler(UiHandlerEvent) registered}
   *         {@link UiHandlerEvent event handlers}.
   */
  protected List<HANDLER> getHandlers() {

    return this.handlers;
  }

  /**
   * This method adds the given {@link UiHandlerEvent}.
   * 
   * @param handler is the {@link UiHandlerEvent} to add.
   */
  public void addHandler(HANDLER handler) {

    NlsNullPointerException.checkNotNull(UiHandlerEvent.class, handler);
    this.handlers.add(handler);
  }

  /**
   * This method removes the given {@link UiHandlerEvent}.
   * 
   * @param handler is the {@link UiHandlerEvent} to remove.
   * @return <code>true</code> if the <code>handler</code> has been removed successfully, <code>false</code>
   *         if it was NOT {@link #addHandler(UiHandlerEvent) registered} and nothing has changed.
   */
  public boolean removeHandler(HANDLER handler) {

    return this.handlers.remove(handler);
  }

  /**
   * Invokes {@link UiHandlerObserver#beforeHandler(Class)}.
   */
  protected final void before() {

    UiHandlerObserver handlerObserver = this.source.getHandlerObserver();
    if (handlerObserver != null) {
      handlerObserver.beforeHandler(this.handlerType);
    }
  }

  /**
   * Invokes {@link UiHandlerObserver#afterHandler(Class)}.
   */
  protected final void after() {

    UiHandlerObserver handlerObserver = this.source.getHandlerObserver();
    if (handlerObserver != null) {
      handlerObserver.afterHandler(this.handlerType);
    }
  }

}
