/* $Id$ */
package net.sf.mmm.ui.toolkit.api.model;

/**
 * This is the interface of a
 * {@link net.sf.mmm.ui.toolkit.api.model.UIListModelIF} that can be modified.
 * 
 * @param <E>
 *        is the templated type of the objects in the list.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIMutableListModelIF<E> extends UIListModelIF<E> {

    /**
     * This method adds an element to the end of the list.
     * 
     * @param element
     *        is the new element to be added.
     */
    void addElement(E element);

    /**
     * This method inserts an element at the given index.
     * 
     * @see java.util.List#add(Object)
     * 
     * @param element
     *        is the element to insert.
     * @param index
     *        is the position where to insert the element. The index must be
     *        greater or equal to zero and less than {@link #getElementCount()}.
     */
    void addElement(E element, int index);

    /**
     * This method sets the element at the given index. The current element at
     * the given index will be replaced by the given element.
     * 
     * @param newElement
     *        is the element to set.
     * @param index
     *        is the index where to set the new element. The index must be
     *        greater or equal to zero and less than {@link #getElementCount()}.
     */
    void setElement(E newElement, int index);

    /**
     * This method removes the element at the given index.
     * 
     * @see java.util.List#remove(int)
     * 
     * @param index
     *        is the position of the element to remove. The index must be
     *        greater or equal to zero and less than {@link #getElementCount()}.
     */
    void removeElement(int index);

    /**
     * This method removes the first occurance of the given element.
     * 
     * @see java.util.List#remove(java.lang.Object)
     * 
     * @param element
     *        is the element to remove.
     * @return <code>true</code> if the given element was found in the list,
     *         <code>false</code> otherwise.
     */
    boolean removeElement(E element);

    /**
     * This method removes all elements from the list.
     */
    void removeAll();

}
