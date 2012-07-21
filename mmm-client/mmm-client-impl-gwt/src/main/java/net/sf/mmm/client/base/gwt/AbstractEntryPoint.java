/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.base.gwt;

import java.util.Set;

import net.sf.mmm.client.impl.gwt.gin.ClientGinjector;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.shared.UmbrellaException;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * This is the abstract base implementation for an application client using the framework based on GWT, GWTP
 * and GIN.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <GINJECTOR> is the generic type of the {@link #getGinjector() ginjector}.
 */
public abstract class AbstractEntryPoint<GINJECTOR extends ClientGinjector> implements EntryPoint {

  /** @see #getGinjector() */
  private GINJECTOR ginjector;

  /**
   * The constructor.
   */
  public AbstractEntryPoint() {

    super();
  }

  /**
   * This method creates the actual {@link ClientGinjector} instance using {@link GWT#create(Class)}.
   * 
   * @return the {@link ClientGinjector} instance.
   */
  protected abstract GINJECTOR createGinjector();

  /**
   * @return the {@link ClientGinjector} instance.
   */
  public GINJECTOR getGinjector() {

    return this.ginjector;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void onModuleLoad() {

    this.ginjector = createGinjector();
    GwtClientContext context = new GwtClientContext();
    context.setGinjector(this.ginjector);
    GWT.setUncaughtExceptionHandler(createExceptionHandler());
    cancelBackspaceNavigation();
    Scheduler.get().scheduleDeferred(new ScheduledCommand() {

      @Override
      public void execute() {

        onModuleLoadDeferred();
      }
    });
  }

  /**
   * This method registers a central handler that prevents the backspace key from performing a
   * "back navigation".
   */
  private void cancelBackspaceNavigation() {

    Event.addNativePreviewHandler(new NativePreviewHandler() {

      @Override
      public void onPreviewNativeEvent(NativePreviewEvent event) {

        if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_BACKSPACE) {
          if (event.getNativeEvent().getEventTarget() != null) {
            Element as = Element.as(event.getNativeEvent().getEventTarget());
            if (as == RootPanel.getBodyElement()) {
              event.getNativeEvent().stopPropagation();
              event.getNativeEvent().preventDefault();
            }
          }
        }
      }
    });
  }

  /**
   * This method creates an {@link UncaughtExceptionHandler}. It may be overridden to customize.
   * 
   * @return the {@link UncaughtExceptionHandler} to use.
   */
  protected UncaughtExceptionHandler createExceptionHandler() {

    return new ExceptionHandler();
  }

  /**
   * This method is called deferred from {@link #onModuleLoad()} to ensure that the initial setup (esp. GIN)
   * is already in place.
   */
  protected void onModuleLoadDeferred() {

    // this.ginjector.getPlaceManager().revealCurrentPlace();
  }

  /**
   * This is the default implementation of {@link UncaughtExceptionHandler}.
   */
  protected class ExceptionHandler implements UncaughtExceptionHandler {

    /**
     * {@inheritDoc}
     */
    @Override
    public void onUncaughtException(Throwable e) {

      Throwable error = normalizeError(e);
      Log.fatal("Uncaught exception on client!", error);
      if (AbstractEntryPoint.this.ginjector != null) {
        AbstractEntryPoint.this.ginjector.getPopupManager().showPopup(error);
      }
    }

    /**
     * This method normalizes the given <code>error</code>.
     * 
     * @param error is the {@link Throwable} to normalize.
     * @return the normalized exception. In many cases the same as <code>error</code>.
     */
    protected Throwable normalizeError(Throwable error) {

      if (error instanceof UmbrellaException) {
        Set<Throwable> causes = ((UmbrellaException) error).getCauses();
        if (causes.size() == 1) {
          return causes.iterator().next();
        }
      }
      return error;
    }
  }

}
