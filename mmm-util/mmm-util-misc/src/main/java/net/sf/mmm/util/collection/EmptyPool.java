/* $Id$ */
package net.sf.mmm.util.collection;


/**
 * This type ...
 * 
 * @param <E>
 *        is the templated type of the elements in the pool.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class EmptyPool<E> extends AbstractPool<E> {

    /**
     * The constructor.
     */
    public EmptyPool() {

        super();
    }

    /**
     * @see net.sf.mmm.util.collection.Pool#add(java.lang.Object)
     * 
     */
    public void add(E element) {

        // nothing to do
    }

    /**
     * @see net.sf.mmm.util.collection.Pool#getMaximumSize()
     * 
     */
    public int getMaximumSize() {

        return 0;
    }

    /**
     * @see net.sf.mmm.util.collection.Pool#size()
     * 
     */
    public int size() {

        return 0;
    }

    /**
     * @see net.sf.mmm.util.collection.Pool#take()
     * 
     */
    public E take() {

        return null;
    }

}
