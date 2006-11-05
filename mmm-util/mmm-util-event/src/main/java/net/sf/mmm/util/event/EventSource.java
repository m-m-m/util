/* $Id$ */
package net.sf.mmm.util.event;

/**
 * This interface allows listeners to be registered and unregistered.
 * 
 * @param <E>
 *        is the templated type of the {@link Event events} to send.
 * @param <L>
 *        is the templated type of the
 *        {@link net.sf.mmm.util.event.EventListener listeners} that can be
 *        {@link #addListener(EventListener) registered} here and that will
 *        {@link net.sf.mmm.util.event.EventListener#handleEvent(Event) receive}
 *        the events.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface EventSource<E extends Event, L extends EventListener<E>> {

    /**
     * This method registers a listener that is interested in events about
     * changes of the content model.
     * 
     * @param listener
     *        is the listener to add.
     */
    void addListener(L listener);

    /**
     * This method removes a listener. If the listener was not registered before
     * this method does not do any change.
     * 
     * @param listener
     *        is the listener to remove.
     */
    void removeListener(L listener);

}
