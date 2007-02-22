/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.model;

import java.util.List;
import java.util.Vector;

import net.sf.mmm.ui.toolkit.api.event.UIListModelEvent;
import net.sf.mmm.ui.toolkit.api.event.UIListModelListener;
import net.sf.mmm.util.event.ChangeEvent.Type;

/**
 * This is the default implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.model.UIMutableListModel} interface.
 * 
 * @param <E>
 *        is the templated type of the elements in the list.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class DefaultUIListModel<E> extends AbstractUIMutableListModel<E> {

  /** the items of this list */
  private List<E> elements;

  /**
   * The constructor.
   */
  public DefaultUIListModel() {

    super();
    this.elements = new Vector<E>();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.model.UIListModel#getElementCount()
   */
  public int getElementCount() {

    return this.elements.size();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.model.UIListModel#getElement(int)
   */
  public E getElement(int index) {

    return this.elements.get(index);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.base.model.AbstractUIListModel#handleListenerException(net.sf.mmm.ui.toolkit.api.event.UIListModelListener,
   *      java.lang.Throwable)
   */
  @Override
  protected void handleListenerException(UIListModelListener listener, Throwable t) {

  // we ignore this...
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.model.UIMutableListModel#addElement(Object)
   */
  public void addElement(E element) {

    this.elements.add(element);
    fireChangeEvent(new UIListModelEvent(Type.ADD, this.elements.size() - 1));
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.model.UIMutableListModel#setElement(Object,
   *      int)
   */
  public void setElement(E newItem, int index) {

    this.elements.set(index, newItem);
    fireChangeEvent(new UIListModelEvent(Type.UPDATE, index));
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.model.UIMutableListModel#addElement(Object,
   *      int)
   */
  public void addElement(E item, int index) {

    this.elements.add(index, item);
    fireChangeEvent(new UIListModelEvent(Type.ADD, index));
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.model.UIMutableListModel#removeElement(int)
   */
  public void removeElement(int index) {

    this.elements.remove(index);
    fireChangeEvent(new UIListModelEvent(Type.REMOVE, index));
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.model.UIMutableListModel#removeElement(Object)
   */
  public boolean removeElement(E element) {

    int index = this.elements.indexOf(element);
    if (index >= 0) {
      removeElement(element);
      return true;
    }
    return false;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.model.UIMutableListModel#removeAll()
   */
  @Override
  public void removeAll() {

    int count = this.elements.size();
    if (count > 0) {
      this.elements.clear();
      fireChangeEvent(new UIListModelEvent(Type.REMOVE, 0, count - 1));
    }
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.model.UIListModel#getIndexOf(Object)
   */
  @Override
  public int getIndexOf(E element) {

    return this.elements.indexOf(element);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.model.UIListModel#getIndexOfString(java.lang.String)
   */
  public int getIndexOfString(String element) {

    int len = this.elements.size();
    for (int i = 0; i < len; i++) {
      if (element.equals(toString(getElement(i)))) {
        return i;
      }
    }
    return -1;
  }

}
