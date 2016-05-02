/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.event.base;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import javax.inject.Inject;

import net.sf.mmm.util.collection.api.CollectionFactory;
import net.sf.mmm.util.collection.api.MapFactory;
import net.sf.mmm.util.collection.api.QueueFactory;
import net.sf.mmm.util.collection.base.ConcurrentHashMapFactory;
import net.sf.mmm.util.collection.base.ConcurrentLinkedQueueFactory;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.event.api.EventBus;
import net.sf.mmm.util.event.api.EventListener;
import net.sf.mmm.util.event.api.EventListenerIgnore;
import net.sf.mmm.util.exception.api.GlobalExceptionHandler;
import net.sf.mmm.util.exception.api.NlsNullPointerException;

/**
 * This is the default implementation of {@link EventBus}. It is compatible to work in limited environments such as GWT.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 5.0.0
 */
public abstract class AbstractEventBus extends AbstractLoggableComponent implements EventBus {

  /**
   * The {@link CollectionFactory} used to create regular {@link Collection}s e.g. for {@link EventListener}s.
   */
  private final CollectionFactory<?> queueFactory;

  /** @see #sendEvent(Object) */
  @SuppressWarnings("rawtypes")
  private final Map<Class<?>, EventDispatcher> eventType2dispatcherMap;

  /** The {@link Queue} used to ensure the order of the events. */
  private final Queue<Object> eventQueue;

  /** @see #getGlobalExceptionHandler() */
  private GlobalExceptionHandler globalExceptionHandler;

  /**
   * The constructor.
   */
  public AbstractEventBus() {

    this(ConcurrentLinkedQueueFactory.INSTANCE, ConcurrentHashMapFactory.INSTANCE);
  }

  /**
   * The constructor.
   *
   * @param queueFactory is the factory used to create simple {@link Queue}s for {@link net.sf.mmm.util.event.api.Event}
   *        s and {@link EventListener}s.
   * @param mapFactory is the factory used to create {@link Map}s.
   */
  public AbstractEventBus(QueueFactory queueFactory, MapFactory<?> mapFactory) {

    super();
    this.queueFactory = queueFactory;
    this.eventType2dispatcherMap = mapFactory.createGeneric();
    this.eventQueue = queueFactory.createGeneric();
  }

  /**
   * @return the {@link GlobalExceptionHandler} instance.
   */
  public GlobalExceptionHandler getGlobalExceptionHandler() {

    return this.globalExceptionHandler;
  }

  /**
   * @param errorHandler is the {@link GlobalExceptionHandler} to {@link Inject}.
   */
  @Inject
  public void setGlobalExceptionHandler(GlobalExceptionHandler errorHandler) {

    getInitializationState().requireNotInitilized();
    this.globalExceptionHandler = errorHandler;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <E> void sendEvent(E event) {

    if (event == null) {
      throw new NlsNullPointerException("event");
    }
    this.eventQueue.add(event);
    triggerDispatchEvents();
  }

  /**
   * Called from {@link #sendEvent(Object)} to ensure {@link #dispatchEvents()} is triggered. This can be done
   * synchronous or asynchronous.
   */
  protected abstract void triggerDispatchEvents();

  /**
   * Dispatches all events in the event queue.
   */
  protected void dispatchEvents() {

    while (true) {

      Object event = this.eventQueue.poll();
      if (event == null) {
        return;
      }
      Collection<Throwable> errors = new LinkedList<>();
      dispatchEvent(event, errors);
      if (!errors.isEmpty()) {
        handleErrors(errors, event);
      }
    }
  }

  /**
   * Dispatches the given {@link net.sf.mmm.util.event.api.Event}.
   *
   * @param <E> is the generic type of {@code event}.
   * @param event is the {@link net.sf.mmm.util.event.api.Event} to dispatch.
   * @param errors is a {@link Collection} where errors are collected.
   */
  protected <E> void dispatchEvent(E event, Collection<Throwable> errors) {

    @SuppressWarnings({ "unchecked", "rawtypes" })
    Class<E> eventType = (Class) event.getClass();
    EventDispatcher<? super E> eventDispatcher = getEventDispatcherOptional(eventType);
    boolean dispatched = false;
    if (eventDispatcher != null) {
      dispatched = eventDispatcher.handleEvent(event, errors);
    }
    if (!dispatched) {
      handleUndispatchedEvent(event);
    }
  }

  /**
   * Called if an {@link net.sf.mmm.util.event.api.Event} was {@link #sendEvent(Object) send} but not dispatched to any
   * {@link #addListener(Class, EventListener) registered listener}.
   *
   * @param event is the un-dispatched event.
   */
  protected void handleUndispatchedEvent(Object event) {

    getLogger().warn("Event send with no responsible listener registered: {}", event);
  }

  /**
   * This method is called if errors occurred while dispatching events.
   *
   * @param errors is the {@link Collection} with the errors. Will NOT be {@link Collection#isEmpty() empty}.
   * @param event is the event that has been send and caused the errors.
   */
  protected void handleErrors(Collection<Throwable> errors, Object event) {

    if (this.globalExceptionHandler == null) {
      for (Throwable error : errors) {
        getLogger().error("Failed to dispatch event {}", event, error);
      }
    } else {
      this.globalExceptionHandler.handleErrors(event, errors.toArray(new Throwable[errors.size()]));
    }
  }

  /**
   * Gets or creates the {@link EventDispatcher} for the given {@code eventType}.
   *
   * @param <E> is the generic type of {@code eventType}.
   * @param eventType is the {@link Class} reflecting the {@link net.sf.mmm.util.event.api.Event event}.
   * @return the {@link EventDispatcher} responsible for the given {@code eventType}.
   */
  @SuppressWarnings("unchecked")
  protected <E> EventDispatcher<E> getEventDispatcherRequired(Class<E> eventType) {

    return getEventDispatcher(eventType, true);
  }

  /**
   * Gets the most specific {@link EventDispatcher} responsible the given {@code eventType}.
   *
   * @param <E> is the generic type of {@code eventType}.
   * @param eventType is the {@link Class} reflecting the {@link net.sf.mmm.util.event.api.Event event}.
   * @return the most specific {@link EventDispatcher} responsible for the given {@code eventType}. May be
   *         {@code null} if no {@link EventListener} is {@link #addListener(Class, EventListener) registered} for
   *         a compatible {@code eventType}.
   */
  @SuppressWarnings("unchecked")
  protected <E> EventDispatcher<? super E> getEventDispatcherOptional(Class<E> eventType) {

    return getEventDispatcher(eventType, false);
  }

  /**
   * Gets or creates the {@link EventDispatcher} for the given {@code eventType}.
   *
   * @param eventType is the {@link Class} reflecting the {@link net.sf.mmm.util.event.api.Event event}.
   * @param createIfNotExists - if {@code true} the requested {@link EventDispatcher} will be created if it does
   *        not exists, if {@code false} this method will return {@code null} if it does not exist.
   * @return the {@link EventDispatcher} responsible for the given {@code eventType}. May be {@code null} if
   *         {@code createIfNotExists} is {@code null} and not available.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  private EventDispatcher getEventDispatcher(Class<?> eventType, boolean createIfNotExists) {

    EventDispatcher<?> dispatcher = this.eventType2dispatcherMap.get(eventType);
    if (dispatcher == null) {
      Class<?> parentType = eventType.getSuperclass();
      if (createIfNotExists) {
        EventDispatcher<?> parent;
        if (parentType == null) {
          assert (eventType == Object.class);
          parent = null;
        } else {
          parent = getEventDispatcher(parentType, true);
        }
        dispatcher = new EventDispatcher(parent, this.queueFactory);
        this.eventType2dispatcherMap.put(eventType, dispatcher);
      } else {
        return getEventDispatcher(parentType, false);
      }
    }
    return dispatcher;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <E> void addListener(Class<E> eventType, EventListener<E> listener) {

    NlsNullPointerException.checkNotNull("eventType", eventType);
    NlsNullPointerException.checkNotNull(EventListener.class, listener);
    if (eventType.isInterface()) {
      throw new IllegalArgumentException(eventType.getName());
    }
    EventListenerContainer<E> container = new EventListenerContainer<>(eventType, listener);
    EventDispatcher<E> eventDispatcher = getEventDispatcherRequired(eventType);
    // concurrent collection used in real environments...
    eventDispatcher.containerList.add(container);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeListener(EventListener<?> listener) {

    for (EventDispatcher<?> dispatcher : this.eventType2dispatcherMap.values()) {
      for (EventListenerContainer<?> container : dispatcher.containerList) {
        if (container.eventListener == listener) {
          // prevent concurrent modification...
          container.clear();
          return true;
        }
      }
    }
    return false;
  }

  /**
   * A simple container for a {@link EventBus#addListener(Class, EventListener) registered} {@link EventListener}.
   *
   * @param <E> is the generic {@link #getEventType() event type}.
   */
  protected static class EventListenerContainer<E> {

    /** @see #getEventType() */
    private final Class<E> eventType;

    /** @see #getEventListener() */
    private EventListener<E> eventListener;

    /**
     * The constructor.
     *
     * @param eventType - see {@link #getEventType()}.
     * @param eventListener - see {@link #getEventListener()}.
     */
    public EventListenerContainer(Class<E> eventType, EventListener<E> eventListener) {

      super();
      this.eventType = eventType;
      this.eventListener = eventListener;
    }

    /**
     * Clears this event adapter. This will replace the actual listener with a dummy instance that ignores all events.
     */
    protected void clear() {

      this.eventListener = EventListenerIgnore.getInstance();
    }

    /**
     * @return {@code true} if this adapter has been {@link #clear() cleared}, {@code false} otherwise.
     */
    protected boolean isCleared() {

      return (this.eventListener == EventListenerIgnore.getInstance());
    }

    /**
     * @see EventBus#addListener(Class, EventListener)
     *
     * @return the {@link Class} reflecting the {@link net.sf.mmm.util.event.api.Event}s to listen to.
     */
    public Class<E> getEventType() {

      return this.eventType;
    }

    /**
     * @return the {@link EventListener}.
     */
    public EventListener<E> getEventListener() {

      return this.eventListener;
    }

  }

  /**
   *
   * A dispatcher for all {@link EventListener}s of a particular {@link EventListenerContainer#getEventType() event
   * type}.
   *
   * @param <E> is the generic {@link EventListenerContainer#getEventType() event type}.
   */
  protected static class EventDispatcher<E> {

    /** @see #handleEvent(Object, Collection) */
    private final EventDispatcher<? super E> parentDispatcher;

    /** @see #handleEvent(Object, Collection) */
    private final Collection<EventListenerContainer<E>> containerList;

    /**
     * The constructor.
     *
     * @param parent is the {@link EventDispatcher} responsible for the super-class or {@code null} if this is the
     *        root {@link EventDispatcher} responsible for {@link Object}.
     * @param collectionFactory is the {@link CollectionFactory} used to create {@link Collection}s e.g. for the
     *        {@link EventListenerContainer}s.
     */
    public EventDispatcher(EventDispatcher<? super E> parent, CollectionFactory<?> collectionFactory) {

      super();
      this.parentDispatcher = parent;
      this.containerList = collectionFactory.createGeneric();
    }

    /**
     * @see EventListener#handleEvent(Object)
     *
     * @param event is the {@link net.sf.mmm.util.event.api.Event} to dispatch.
     * @param errors is a {@link Collection} where potential exceptions thrown by
     *        {@link EventListener#handleEvent(Object) event handlers} will be {@link Collection#add(Object) added}.
     * @return {@code true} if the event has actually been dispatched, {@code false} otherwise.
     */
    public boolean handleEvent(E event, Collection<Throwable> errors) {

      boolean dispatched = false;
      Iterator<EventListenerContainer<E>> iterator = this.containerList.iterator();
      while (iterator.hasNext()) {
        EventListenerContainer<? super E> container = iterator.next();
        if (container.isCleared()) {
          // tidy up...
          iterator.remove();
        } else {
          try {
            container.eventListener.handleEvent(event);
          } catch (Throwable exception) {
            errors.add(exception);
          }
          dispatched = true;
        }
      }
      if (this.parentDispatcher != null) {
        boolean superDispatched = this.parentDispatcher.handleEvent(event, errors);
        if (superDispatched) {
          dispatched = true;
        }
      }
      return dispatched;
    }

  }

}
