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
     * @see net.sf.mmm.util.collection.PoolIF#add(java.lang.Object)
     * 
     */
    public void add(E element) {

        // nothing to do
    }

    /**
     * @see net.sf.mmm.util.collection.PoolIF#getMaximumSize()
     * 
     */
    public int getMaximumSize() {

        return 0;
    }

    /**
     * @see net.sf.mmm.util.collection.PoolIF#size()
     * 
     */
    public int size() {

        return 0;
    }

    /**
     * @see net.sf.mmm.util.collection.PoolIF#take()
     * 
     */
    public E take() {

        return null;
    }

}
