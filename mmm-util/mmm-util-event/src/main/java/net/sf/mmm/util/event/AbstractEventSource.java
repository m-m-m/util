/* $Id$ */
package net.sf.mmm.util.event;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.util.event.EventListener} interface.
 * 
 * @param <E>
 *        is the templated type of the events to send.
 * @param <L>
 *        is the templated type of the listeners that can be
 *        {@link #addListener(EventListener) registered} here and that will
 *        {@link net.sf.mmm.util.event.EventListener#handleEvent(Event) receive}
 *        the sent events.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractEventSource<E extends Event, L extends EventListener<E>> implements
        EventSourceIF<E, L> {

    /** the registered listeners */
    private final List<L> listeners;

    /**
     * The constructor.
     */
    public AbstractEventSource() {

        super();
        this.listeners = new ArrayList<L>();
    }

    /**
     * @see net.sf.mmm.util.event.EventSource#addListener(EventListener)
     */
    public void addListener(L listener) {

        this.listeners.add(listener);
    }

    /**
     * @see net.sf.mmm.util.event.EventSource#removeListener(EventListener)
     */
    public void removeListener(L listener) {

        this.listeners.remove(listener);
    }

    /**
     * This method sends the given <code>event</code> to all
     * {@link #addListener(EventListener) registered} listeners.
     * 
     * @param event
     *        the event to set.
     */
    protected void setEvent(E event) {

        int length = this.listeners.size();
        for (int i = 0; i < length; i++) {
            L listener = this.listeners.get(i);
            try {
                listener.handleEvent(event);
            } catch (RuntimeException e) {
                handleListenerError(listener, event, e);
            }
        }
    }

    /**
     * This method is called if a listener throws something while handling an
     * event.<br>
     * The default implementation is to do nothing. Override this method to
     * change the behaviour (e.g. log the problem, remove the "evil" listener,
     * throw the error anyways).
     * 
     * @param listener
     *        is the listener that caused the error.
     * @param event
     *        is the event that could not be handled.
     * @param error
     *        is the throwable caused by the <code>listener</code> while
     *        handling the <code>event</code>.
     */
    protected void handleListenerError(L listener, E event, Throwable error) {

    // by default do nothing
    }

}
