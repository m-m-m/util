/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This is an implementation of the {@link java.util.List} interface that adapts another {@link Collection} as
 * {@link #getDelegate() delegate}.
 * 
 * @param <E> is the generic type of the elements.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public class CollectionList<E> extends ArrayList<E> {

  /** UID for serialization. */
  private static final long serialVersionUID = -8534525840753920810L;

  /** @see #getDelegate() */
  private Collection<E> delegate;

  /**
   * The constructor.
   * 
   * @param delegate is the {@link Collection} to adapt.
   */
  public CollectionList(Collection<E> delegate) {

    super(delegate);
    this.delegate = delegate;
  }

  /**
   * This method gets the underlying {@link Collection} that is adapted to appear as {@link java.util.List}.
   * 
   * @return the delegate.
   */
  public Collection<E> getDelegate() {

    return this.delegate;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public E set(int index, E element) {

    E old = super.set(index, element);
    this.delegate.remove(old);
    this.delegate.add(element);
    return old;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean add(E element) {

    boolean added = this.delegate.add(element);
    if (added) {
      super.add(element);
    }
    return added;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void add(int index, E element) {

    this.delegate.add(element);
    super.add(index, element);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public E remove(int index) {

    E element = super.remove(index);
    this.delegate.remove(element);
    return element;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean remove(Object o) {

    this.delegate.remove(o);
    return super.remove(o);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean addAll(Collection<? extends E> collection) {

    this.delegate.addAll(collection);
    return super.addAll(collection);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Iterator<E> iterator() {

    return new ListIterator();
  }

  /**
   * This inner class implements an {@link Iterator} for this {@link CollectionList}.
   */
  private class ListIterator implements Iterator<E> {

    /**
     * Index of element to be returned by subsequent call to next.
     */
    private int cursor;

    /**
     * Index of element returned by most recent call to next or previous. Reset to -1 if this element is
     * deleted by a call to remove.
     */
    private int lastRet = -1;

    /**
     * The modCount value that the iterator believes that the backing List should have. If this expectation is
     * violated, the iterator has detected concurrent modification.
     */
    private int expectedModCount = CollectionList.this.modCount;

    /**
     * {@inheritDoc}
     */
    public boolean hasNext() {

      return this.cursor != size();
    }

    /**
     * {@inheritDoc}
     */
    public E next() {

      checkForComodification();
      try {
        E next = get(this.cursor);
        this.lastRet = this.cursor++;
        return next;
      } catch (IndexOutOfBoundsException e) {
        checkForComodification();
        throw new NoSuchElementException();
      }
    }

    /**
     * {@inheritDoc}
     */
    public void remove() {

      if (this.lastRet == -1) {
        throw new IllegalStateException();
      }
      checkForComodification();

      try {
        E element = CollectionList.this.remove(this.lastRet);
        CollectionList.this.delegate.remove(element);
        if (this.lastRet < this.cursor) {
          this.cursor--;
        }
        this.lastRet = -1;
        this.expectedModCount = CollectionList.this.modCount;
      } catch (IndexOutOfBoundsException e) {
        throw new ConcurrentModificationException();
      }
    }

    /**
     * Checks that the collection has NOT been modified outside this iterator.
     */
    final void checkForComodification() {

      if (CollectionList.this.modCount != this.expectedModCount) {
        throw new ConcurrentModificationException();
      }
    }
  }

}
