/* $Id$ */
package net.sf.mmm.ui.toolkit.api.model;

import net.sf.mmm.ui.toolkit.api.event.UIListModelListenerIF;

/**
 * This is the interface for the model of a list widget.
 * 
 * @see net.sf.mmm.ui.toolkit.api.widget.UIListIF
 * 
 * @param <E>
 *        is the templated type of the objects in the list.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIListModelIF<E> {

    /**
     * This method registers the given change listener to this model.
     * 
     * @param listener
     *        is the change listener to add.
     */
    void addListener(UIListModelListenerIF listener);

    /**
     * This method unregisters the given change listener from this model. The
     * listener should have been registered via the addChangeListener method
     * before.
     * 
     * @param listener
     *        is the change listener to remove.
     */
    void removeListener(UIListModelListenerIF listener);

    /**
     * This method gets the number of elements in this list.
     * 
     * @see java.util.List#size()
     * 
     * @return the element count.
     */
    int getElementCount();

    /**
     * This method gets the element in the list at the given position.
     * 
     * @see java.util.List#get(int)
     * 
     * @param index
     *        is the position of the requested item in the list. The index must
     *        be greater or equal to zero and less than
     *        {@link #getElementCount()}.
     * @return the element at the given index.
     */
    E getElement(int index);

    /**
     * This method gets the element in the list at the given position as string.
     * 
     * @see #getElement(int)
     * 
     * @param index
     *        is the position of the requested item in the list. The index must
     *        be greater or equal to zero and less than
     *        {@link #getElementCount()}.
     * @return the element at the given index as string.
     */
    String getElementAsString(int index);

    /**
     * This method gets the (first) index of the given element or
     * <code>-1</code> if the given element is NOT contained in this list.<br>
     * This method is only required by
     * {@link net.sf.mmm.ui.toolkit.api.widget.UISpinBoxIF} - if used in other
     * widgets, it is acceptable if this method will always return
     * <code>-1</code> if no acceptable or tivial implementation for this
     * method is possible.<br>
     * It is NOT required but suggested, that there are no duplicate elements in
     * the list. This means that there is no index <code>i</code> so that the
     * following equation is <code>true</code>: <br>
     * 
     * <pre>i != list.{@link #getIndexOf(Object) getIndexOf}(list.{@link #getElement(int) getElement}(i))</pre>
     * 
     * @see java.util.List#indexOf(java.lang.Object)
     * 
     * @param element
     *        is the (potential) element of this list (as received by
     *        {@link #getElement(int)}).
     * @return the index of the first occurrence of the given element or
     *         <code>-1</code> if the given element is NOT contained in this
     *         list.
     */
    int getIndexOf(E element);

    /**
     * This method gets the (first) index of an element with the
     * {@link String#equals(java.lang.Object) same} string representation as the
     * given <code>element</code> or <code>-1</code> if NO such element
     * exists.<br>
     * If a value other than -1 is returned, the following equation must be
     * <code>true</code>:<br>
     * 
     * <pre>element.{@link Object#equals(java.lang.Object) equals}(model.{@link #getElementAsString(int) getElementAsString}({@link #getIndexOfString(String) getIndexOfString}(element)))</pre>
     * 
     * This method is only required by
     * {@link net.sf.mmm.ui.toolkit.api.widget.UISpinBoxIF} and
     * {@link net.sf.mmm.ui.toolkit.api.widget.UISlideBarIF} - if used in other
     * widgets, it is acceptable if this method will always return
     * <code>-1</code> if no acceptable or tivial implementation for this
     * method is possible.<br>
     * 
     * @param element
     *        is the string representation of the (potential) element of this
     *        list (as received by {@link #getElementAsString(int)})
     * @return the (first) index of an element with the
     *         {@link String#equals(java.lang.Object) same} string
     *         representation as the given <code>element</code> or
     *         <code>-1</code> if NO such element exists.
     */
    int getIndexOfString(String element);

}
