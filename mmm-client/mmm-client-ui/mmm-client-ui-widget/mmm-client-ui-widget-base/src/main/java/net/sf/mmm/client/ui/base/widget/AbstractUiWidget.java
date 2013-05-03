/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget;

import java.util.LinkedList;
import java.util.List;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.attribute.AttributeReadEventObserver;
import net.sf.mmm.client.ui.api.attribute.AttributeReadFocused;
import net.sf.mmm.client.ui.api.common.EventType;
import net.sf.mmm.client.ui.api.common.Length;
import net.sf.mmm.client.ui.api.common.SizeUnit;
import net.sf.mmm.client.ui.api.common.UiEvent;
import net.sf.mmm.client.ui.api.feature.UiFeatureEvent;
import net.sf.mmm.client.ui.api.handler.UiEventObserver;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEvent;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventValueChange;
import net.sf.mmm.client.ui.api.widget.AbstractUiWidgetWithValue;
import net.sf.mmm.client.ui.api.widget.UiWidget;
import net.sf.mmm.client.ui.api.widget.UiWidgetComposite;
import net.sf.mmm.client.ui.api.widget.UiWidgetFactory;
import net.sf.mmm.client.ui.base.feature.AbstractUiFeatureValue;
import net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapter;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.transferobject.api.AbstractTransferObject;
import net.sf.mmm.util.transferobject.api.TransferObjectUtilLimited;

/**
 * This is the abstract base implementation of {@link net.sf.mmm.client.ui.api.widget.UiWidget}. Below this
 * class there are two inheritance hierarchies {@link AbstractUiWidgetNative} and
 * {@link net.sf.mmm.client.ui.base.widget.custom.UiWidgetCustom}. To avoid problems with the lack of
 * multi-inheritance in Java, we already implement {@link net.sf.mmm.client.ui.api.widget.UiWidgetWithValue}
 * by extending {@link AbstractUiFeatureValue}. For subclasses that have no value {@link Void} is used for
 * {@literal <VALUE>}.
 * 
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidget<VALUE> extends AbstractUiFeatureValue<VALUE> implements
    AbstractUiWidgetWithValue<VALUE> {

  /** @see #getContext() */
  private final UiContext context;

  /** @see #fireEvent(UiEvent, boolean) */
  private EventSender eventSender;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public AbstractUiWidget(UiContext context) {

    super();
    this.context = context;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final UiContext getContext() {

    return this.context;
  }

  /**
   * @return die Instanz von {@link AttributeReadEventObserver} oder <code>null</code> falls nicht verfügbar.
   */
  protected final AttributeReadEventObserver getObserverSource() {

    if (this.context instanceof AttributeReadEventObserver) {
      return (AttributeReadEventObserver) this.context;
    }
    return null;
  }

  /**
   * @return the {@link UiWidgetFactory} via {@link #getContext()} for convenience.
   */
  public final UiWidgetFactory getFactory() {

    return this.context.getWidgetFactory();
  }

  /**
   * @return <code>true</code> if the {@link UiWidgetAdapter} has already been {@link #getWidgetAdapter()
   *         created}. Otherwise <code>false</code> (if {@link #getWidgetAdapter()} has never been called
   *         yet).
   */
  public abstract boolean hasWidgetAdapter();

  /**
   * This method gets or creates the {@link UiWidgetAdapter}.<br/>
   * <b>ATTENTION:</b><br/>
   * On the first call of this method, the {@link UiWidgetAdapter} is created. For the purpose of lazy
   * instantiation this should happen as late as possible. Use {@link #hasWidgetAdapter()} to prevent
   * unnecessary creation.
   * 
   * @return the {@link UiWidgetAdapter}.
   */
  protected abstract UiWidgetAdapter getWidgetAdapter();

  /**
   * This method gives access to {@link #getWidgetAdapter()}.<br/>
   * <b>ATTENTION:</b><br/>
   * This method is only for internal purposes when implementing {@link UiWidget}s. It shall never be used by
   * regular users (what also applies for all classes in this <code>base</code> packages).
   * 
   * @param widget is the widget.
   * @return the {@link #getWidgetAdapter() widget adapter} of the given <code>widget</code>.
   */
  public static final UiWidgetAdapter getWidgetAdapter(UiWidget widget) {

    return ((AbstractUiWidget<?>) widget).getWidgetAdapter();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getSource() {

    return getId();
  }

  /**
   * This method removes this widget from its {@link #getParent() parent}. The {@link #getParent() parent} is
   * set to <code>null</code> and the native widget is removed from its parent.
   */
  protected abstract void removeFromParent();

  /**
   * This method invokes {@link #removeFromParent()} on the given <code>widget</code>.<br/>
   * <b>ATTENTION:</b><br/>
   * This method is only for internal purposes when implementing {@link UiWidget}s. It shall never be used by
   * regular users.
   * 
   * @param widget is the widget that should be removed from its {@link #getParent() parent}.
   */
  public static void removeFromParent(UiWidget widget) {

    ((AbstractUiWidget<?>) widget).removeFromParent();
  }

  /**
   * This method sets the {@link #getParent() parent}.
   * 
   * @param parent is the new {@link #getParent() parent}.
   */
  protected abstract void setParent(UiWidgetComposite<?> parent);

  /**
   * This method sets the {@link #getParent() parent} of the given <code>widget</code>.<br/>
   * <b>ATTENTION:</b><br/>
   * This method is only for internal purposes when implementing {@link UiWidget}s. It shall never be used by
   * regular users (what also applies for all classes in this <code>base</code> packages).
   * 
   * @param widget is the widget where to set the {@link #getParent() parent}.
   * @param newParent is the new {@link #getParent() parent}.
   */
  public static void setParent(UiWidget widget, UiWidgetComposite<?> newParent) {

    ((AbstractUiWidget<?>) widget).setParent(newParent);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected VALUE createCopyOfValue(VALUE value) {

    if (value instanceof AbstractTransferObject) {
      AbstractTransferObject to = (AbstractTransferObject) value;
      TransferObjectUtilLimited transferObjectUtil = this.context.getContainer().get(TransferObjectUtilLimited.class);
      return (VALUE) transferObjectUtil.copy(to);
    }
    return super.createCopyOfValue(value);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setHeightInPercent(double heightInPercent) {

    setHeight(Length.valueOfPercent(heightInPercent));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setHeightInPixel(double heightInPixel) {

    setHeight(Length.valueOfPixel(heightInPixel));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setWidthInPercent(double widthInPercent) {

    setWidth(Length.valueOfPercent(widthInPercent));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setWidthInPixel(double widthInPixel) {

    setWidth(Length.valueOfPixel(widthInPixel));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSize(double widthAmount, double heightAmount, SizeUnit unit) {

    setSize(unit.newLength(widthAmount), unit.newLength(heightAmount));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void addEventHandler(UiHandlerEvent handler) {

    NlsNullPointerException.checkNotNull(UiHandlerEvent.class, handler);
    getEventSender().eventHandlers.add(handler);
  }

  /**
   * @return <code>true</code> if the {@link EventSender} has already been {@link #getEventSender() created}.
   *         Otherwise <code>false</code> (if {@link #getEventSender()} has never been called yet).
   */
  protected final boolean hasEventSender() {

    return (this.eventSender != null);
  }

  /**
   * This method gets the {@link EventSender}. It will be created on the first call.
   * 
   * @return the {@link EventSender}.
   */
  protected final EventSender getEventSender() {

    if (this.eventSender == null) {
      this.eventSender = new EventSender();
      if (hasWidgetAdapter()) {
        // TODO hohwille might be called twice for custom widget
        getWidgetAdapter().setEventSender(this, this.eventSender);
      }
    }
    return this.eventSender;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean removeEventHandler(UiHandlerEvent handler) {

    if (this.eventSender == null) {
      return false;
    }
    return this.eventSender.eventHandlers.remove(handler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addChangeHandler(UiHandlerEventValueChange<VALUE> handler) {

    addEventHandler(handler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeChangeHandler(UiHandlerEventValueChange<VALUE> handler) {

    return removeEventHandler(handler);
  }

  /**
   * Fires an event with the given parameters.
   * 
   * @param event is the {@link UiEvent}.
   * @param programmatic - see
   *        {@link UiHandlerEvent#onEvent(net.sf.mmm.client.ui.api.feature.UiFeatureEvent, UiEvent, boolean)}.
   */
  protected final void fireEvent(UiEvent event, boolean programmatic) {

    if (this.eventSender != null) {
      this.eventSender.onEvent(this, event, programmatic);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void fireValueChange() {

    fireEvent(EventType.VALUE_CHANGE, true);
  }

  /**
   * This inner class is an adapter for the events.
   */
  protected class EventSender implements UiHandlerEvent, AttributeReadFocused {

    /** @see #addEventHandler(UiHandlerEvent) */
    private final List<UiHandlerEvent> eventHandlers;

    /** @see #isFocused() */
    private boolean focused;

    /**
     * The constructor.
     */
    public EventSender() {

      super();
      this.eventHandlers = new LinkedList<UiHandlerEvent>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isFocused() {

      return this.focused;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onEvent(UiFeatureEvent source, UiEvent event, boolean programmatic) {

      if (event.getType() == EventType.FOCUS_GAIN) {
        this.focused = true;
      } else if (event.getType() == EventType.FOCUS_LOSS) {
        this.focused = false;
      }
      UiEventObserver eventObserver = null;
      AttributeReadEventObserver observerSource = getObserverSource();
      if (observerSource != null) {
        eventObserver = observerSource.getEventObserver();
      }
      if (eventObserver != null) {
        eventObserver.beforeHandler(event);
      }
      for (UiHandlerEvent handler : this.eventHandlers) {
        // TODO hohwille if a handler will remove itself in onEvent we might get
        // ConcurrentModificationException
        handler.onEvent(AbstractUiWidget.this, event, programmatic);
      }
      if (eventObserver != null) {
        eventObserver.afterHandler(event);
      }
    }

  }

}
