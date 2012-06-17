/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.model;

import java.util.List;
import java.util.Vector;

import net.sf.mmm.ui.toolkit.api.event.UIListModelEvent;
import net.sf.mmm.util.event.api.ChangeType;

/**
 * This is the default implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.model.data.UiMutableListMvcModel} interface.
 * 
 * @param <E> is the templated type of the elements in the list.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
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
   * {@inheritDoc}
   */
  public int getElementCount() {

    return this.elements.size();
  }

  /**
   * {@inheritDoc}
   */
  public E getElement(int index) {

    return this.elements.get(index);
  }

  /**
   * {@inheritDoc}
   */
  public void addElement(E element) {

    this.elements.add(element);
    fireChangeEvent(new UIListModelEvent(ChangeType.ADD, this.elements.size() - 1));
  }

  /**
   * {@inheritDoc}
   */
  public void setElement(E newItem, int index) {

    this.elements.set(index, newItem);
    fireChangeEvent(new UIListModelEvent(ChangeType.UPDATE, index));
  }

  /**
   * {@inheritDoc}
   */
  public void addElement(E item, int index) {

    this.elements.add(index, item);
    fireChangeEvent(new UIListModelEvent(ChangeType.ADD, index));
  }

  /**
   * {@inheritDoc}
   */
  public void removeElement(int index) {

    this.elements.remove(index);
    fireChangeEvent(new UIListModelEvent(ChangeType.REMOVE, index));
  }

  /**
   * {@inheritDoc}
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
   * {@inheritDoc}
   */
  @Override
  public void removeAll() {

    int count = this.elements.size();
    if (count > 0) {
      this.elements.clear();
      fireChangeEvent(new UIListModelEvent(ChangeType.REMOVE, 0, count - 1));
    }
  }

  /**
   * {@inheritDoc}
   */
  public int getIndexOf(E element) {

    return this.elements.indexOf(element);
  }

  /**
   * {@inheritDoc}
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
