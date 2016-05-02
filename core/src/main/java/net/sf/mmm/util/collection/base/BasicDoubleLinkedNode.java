/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import net.sf.mmm.util.collection.api.DoubleLinkedNode;

/**
 * This is a basic implementation of the {@link DoubleLinkedNode} interface.
 *
 * @param <V> is the generic type of the {@link #getValue() value} of this node.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class BasicDoubleLinkedNode<V> extends AbstractLinkedNode<V, BasicDoubleLinkedNode<V>> implements
    DoubleLinkedNode<V, BasicDoubleLinkedNode<V>> {

  /** @see #getPrevious() */
  private BasicDoubleLinkedNode<V> previous;

  /**
   * The constructor.
   */
  public BasicDoubleLinkedNode() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public BasicDoubleLinkedNode<V> getPrevious() {

    return this.previous;
  }

  /**
   * This method sets the {@link #getPrevious() previous node}.
   *
   * @param previous is the {@link #getPrevious() previous node} to set. May be {@code null} to indicate that this
   *        is the first node of the list.
   */
  public void setPrevious(BasicDoubleLinkedNode<V> previous) {

    this.previous = previous;
  }

  /**
   * This method inserts the given {@code value} into the list at the position represented by this node. It will
   * typically create a new node containing the given {@code value} and {@link #setNext(AbstractLinkedNode) set it
   * as next}. It will guarantee the following equation:
   *
   * <pre>
   * this.{@link #getNext()}.{@link #getPrevious()} == this
   * </pre>
   *
   * If {@code overrideNullValue} is {@code true} and the {@link #getValue() value} of this node is
   * {@code null}, then its value is set to the given {@code value} instead of creating a new node.
   *
   * @param value is the value to insert.
   * @param overrideNullValue - {@code true} if a {@link #getValue() value} of {@code null} should be replaced
   *        with the given {@code value}, {@code false} otherwise.
   * @return the node containing the given {@code value}.
   */
  public BasicDoubleLinkedNode<V> insertAsNext(V value, boolean overrideNullValue) {

    if (overrideNullValue && (getValue() == null)) {
      setValue(value);
      return this;
    } else {
      BasicDoubleLinkedNode<V> node = new BasicDoubleLinkedNode<>();
      node.setValue(value);
      node.previous = this;
      setNext(node);
      return node;
    }
  }

  /**
   * This method inserts the given {@code node} into the list immediately after the position represented by this
   * node.
   *
   * @param node is the {@link BasicDoubleLinkedNode node} to add.
   */
  public void insertAsNext(BasicDoubleLinkedNode<V> node) {

    BasicDoubleLinkedNode<V> next = getNext();
    if (next != null) {
      next.previous = node;
      node.setNext(next);
    }
    node.previous = this;
    setNext(node);
  }

  /**
   * This method inserts the given {@code node} into the list immediately before the position represented by this
   * node.
   *
   * @param node is the {@link BasicDoubleLinkedNode node} to add.
   */
  public void insertAsPrevious(BasicDoubleLinkedNode<V> node) {

    if (this.previous != null) {
      this.previous.setNext(node);
      node.previous = this.previous;
    }
    node.setNext(this);
    this.previous = node;
  }

  /**
   * This method inserts the given {@code value} into the list at the position represented by this node. It will
   * typically create a new node containing the given {@code value} and {@link #setNext(AbstractLinkedNode) set it
   * as next}. It will guarantee the following equation:
   *
   * <pre>
   * this.{@link #getNext()}.{@link #getPrevious()} == this
   * </pre>
   *
   * If {@code overrideNullValue} is {@code true} and the {@link #getValue() value} of this node is
   * {@code null}, then its value is set to the given {@code value} instead of creating a new node.
   *
   * @param value is the value to insert.
   * @param overrideNullValue - {@code true} if a {@link #getValue() value} of {@code null} should be replaced
   *        with the given {@code value}, {@code false} otherwise.
   * @return the node containing the given {@code value}.
   */
  public BasicDoubleLinkedNode<V> insertAsPrevious(V value, boolean overrideNullValue) {

    if (overrideNullValue && (getValue() == null)) {
      setValue(value);
      return this;
    } else {
      BasicDoubleLinkedNode<V> node = new BasicDoubleLinkedNode<>();
      node.setValue(value);
      node.setNext(this);
      this.previous = node;
      return node;
    }
  }

  /**
   * This method removes this node from the double linked list.
   */
  public void remove() {

    BasicDoubleLinkedNode<V> next = getNext();
    if (next != null) {
      next.previous = this.previous;
    }
    if (this.previous != null) {
      this.previous.setNext(next);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isLinked() {

    if (this.previous != null) {
      return true;
    }
    return super.isLinked();
  }
}
