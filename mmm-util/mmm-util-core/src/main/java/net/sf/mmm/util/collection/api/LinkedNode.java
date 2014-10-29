/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.api;

import java.util.List;

/**
 * This is the interface for a node of a linked list. <br>
 * <b>ATTENTION:</b><br>
 * According to the generic-type-system of Java this interface can not be used as a proper API until the
 * generic &lt;NODE&gt; is finally bound. Please use {@link net.sf.mmm.util.collection.base.BasicLinkedNode}
 * instead.
 * 
 * 
 * @param <V> is the generic type of the {@link #getValue() value} of this node.
 * @param <NODE> is the generic type of the {@link LinkedNode node} itself.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public interface LinkedNode<V, NODE extends LinkedNode<V, NODE>> {

  /**
   * This method gets the next node of the linked list.
   * 
   * @return the next {@link LinkedNode node} or <code>null</code> if this is the last node (tail).
   */
  NODE getNext();

  /**
   * This method gets the actual value of this node.
   * 
   * @return the value, may be <code>null</code>.
   */
  V getValue();

  /**
   * This method determines if this node is linked. A node is linked if it has a {@link #getNext() next node}.
   * 
   * @return <code>true</code> if linked, <code>false</code> otherwise.
   */
  boolean isLinked();

  /**
   * This method appends the {@link #getValue() values} of the list represented by this node to the given
   * <code>list</code>. <br>
   * Call this method on the head-node with an empty {@link List} to convert a {@link LinkedNode}-list to a
   * regular {@link List}.
   * 
   * @param list is where to append the values.
   */
  void addToList(List<V> list);

  /**
   * This method converts the list represented by this node to a regular {@link List}.
   * 
   * @return the {@link List} of all {@link #getValue() values} of the list represented by this node.
   */
  List<V> toList();

}
