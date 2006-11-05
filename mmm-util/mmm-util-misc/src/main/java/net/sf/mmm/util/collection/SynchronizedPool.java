/* $Id$ */
package net.sf.mmm.util.collection;

/**
 * This class is a synchronized version of the {@link SimplePool}.
 * 
 * @param <E>
 *        is the templated type of the elements in the pool.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SynchronizedPool<E> extends SimplePool<E> {

    /** the lock for synchronization */
    private final Object lock;

    /**
     * @see SimplePool#SimplePool(Class, int)
     */
    public SynchronizedPool(Class<E> type, int maximumSize) {

        super(type, maximumSize);
        this.lock = this;
    }

    /**
     * The constructor.
     * 
     * @param type
     * @param maximumSize
     * @param synchronizationLock
     */
    public SynchronizedPool(Class<E> type, int maximumSize, Object synchronizationLock) {

        super(type, maximumSize);
        this.lock = synchronizationLock;
    }

    /**
     * @see net.sf.mmm.util.collection.SimplePool#take()
     */
    @Override
    public E take() {

        synchronized (this.lock) {
            return super.take();
        }
    }

    /**
     * @see net.sf.mmm.util.collection.SimplePool#add(java.lang.Object)
     */
    @Override
    public void add(E element) {

        synchronized (this.lock) {
            super.add(element);
        }
    }

}
