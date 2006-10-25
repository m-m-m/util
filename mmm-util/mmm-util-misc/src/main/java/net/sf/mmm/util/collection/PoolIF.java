/* $Id$ */
package net.sf.mmm.util.collection;

import java.util.Collection;

/**
 * This is the interface for a simple pool. It stores elements
 * {@link #add(Object) added} to the pool. If a
 * {@link #getMaximumSize() maximum size} is reached {@link #add(Object) added}
 * elements are ignored. Parallel to this elements can be {@link #take() taken}
 * from the pool until the pool is {@link #isEmpty() empty}.
 * 
 * @param <E>
 *        is the templated type of the elements in the pool.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface PoolIF<E> {

    /**
     * This method takes an element from the pool.
     * 
     * @return an element from the pool or <code>null</code> if the pool is
     *         empty.
     */
    E take();

    /**
     * This method puts the given <code>element</code> into the pool.
     * 
     * @see Collection#add(Object)
     * 
     * @param element
     *        is the element to add to the pool.
     */
    void add(E element);

    /**
     * This method determines if the pool is empty (has a {@link #size() size}
     * of <code>0</code>).
     * 
     * @see Collection#isEmpty()
     * 
     * @return <code>true</code> if the pool is emtpy.
     */
    boolean isEmpty();

    /**
     * @see Collection#size()
     * 
     * @return the current size of this pool.
     */
    int size();

    /**
     * This method gets the maximum size of the pool.
     * 
     * @return the maximum pool size.
     */
    int getMaximumSize();

}
