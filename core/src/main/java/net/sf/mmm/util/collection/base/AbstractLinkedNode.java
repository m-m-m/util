/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.util.collection.api.LinkedNode;

/**
 * This is the abstract base-implementation of the {@link LinkedNode} interface.
 *
 * @param <V> is the generic type of the {@link #getValue() value} of this node.
 * @param <NODE> is the generic type of the {@link AbstractLinkedNode node} itself.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public abstract class AbstractLinkedNode<V, NODE extends AbstractLinkedNode<V, NODE>> implements
    LinkedNode<V, NODE> {

  /** @see #getValue() */
  private V value;

  /** @see #getNext() */
  private NODE next;

  /**
   * The constructor.
   */
  public AbstractLinkedNode() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public NODE getNext() {

    return this.next;
  }

  /**
   * This method sets the {@link #getNext() next} node.
   *
   * @param next is the next to set. May be <code>null</code> to indicate that this is the last node of the list.
   */
  public void setNext(NODE next) {

    this.next = next;
  }

  /**
   * {@inheritDoc}
   */
  public V getValue() {

    return this.value;
  }

  /**
   * This method sets the {@link #getValue() value}.
   *
   * @param value is the value to set. May be <code>null</code>.
   */
  public void setValue(V value) {

    this.value = value;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isLinked() {

    return (this.next != null);
  }

  /**
   * {@inheritDoc}
   */
  public void addToList(List<V> list) {

    list.add(this.value);
    NODE node = this.next;
    while (node != null) {
      // bug in compiler of Java7: node.value and node.next are not accessible, need to access via getters
      list.add(node.getValue());
      node = node.getNext();
    }
  }

  /**
   * {@inheritDoc}
   */
  public List<V> toList() {

    List<V> list = new ArrayList<>();
    addToList(list);
    return list;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return getClass().getSimpleName() + ": " + this.value;
  }

}
