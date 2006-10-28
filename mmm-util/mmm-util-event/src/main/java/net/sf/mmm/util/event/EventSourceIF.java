/* $Id$ */
package net.sf.mmm.util.event;

/**
 * This interface allows listeners to be registered and unregistered.
 * 
 * @param <E>
 *        is the templated type of the {@link EventIF events} to send.
 * @param <L>
 *        is the templated type of the
 *        {@link net.sf.mmm.util.event.EventListenerIF listeners} that can be
 *        {@link #addListener(EventListenerIF) registered} here and that will
 *        {@link net.sf.mmm.util.event.EventListenerIF#handleEvent(EventIF) receive}
 *        the events.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface EventSourceIF<E extends EventIF, L extends EventListenerIF<E>> {

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
