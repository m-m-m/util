/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.api;

/**
 * This is the interface for a node of a double linked list. <br/>
 * <b>ATTENTION:</b><br/>
 * According to the generic-type-system of Java this interface can not be used
 * as a proper API until the generic &lt;NODE&gt; is finally bound. Please use
 * {@link net.sf.mmm.util.collection.base.BasicDoubleLinkedNode} instead.
 * 
 * @param <V> is the generic type of the {@link #getValue() value} of this node.
 * @param <NODE> is the generic type of the {@link DoubleLinkedNode node}
 *        itself.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public interface DoubleLinkedNode<V, NODE extends DoubleLinkedNode<V, NODE>> extends
    LinkedNode<V, NODE> {

  /**
   * This method gets the previous node of the double linked list.
   * 
   * @return the previous node or <code>null</code> if this is the first node
   *         (head).
   */
  NODE getPrevious();

  /**
   * {@inheritDoc}
   * 
   * A node is also linked if it has a {@link #getPrevious() previous node}.
   */
  boolean isLinked();

}
