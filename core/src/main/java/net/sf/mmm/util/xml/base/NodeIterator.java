/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.base;

import net.sf.mmm.util.collection.base.AbstractIterator;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * This class is used to {@link java.util.Iterator#next() iterate} the {@link Node}s of a {@link NodeList}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public class NodeIterator extends AbstractIterator<Node> {

  /** The {@link NodeList} to iterate. */
  private final NodeList nodeList;

  /** The {@link NodeList#getLength() length} of the {@link #nodeList}. */
  private final int nodeListLength;

  /** The current {@link NodeList#item(int) index} in the {@link #nodeList}. */
  private int index;

  /**
   * The constructor.
   * 
   * @param nodeList is the {@link NodeList} to iterate.
   */
  public NodeIterator(NodeList nodeList) {

    super();
    this.nodeList = nodeList;
    this.nodeListLength = this.nodeList.getLength();
    // this.index = 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Node findNext() {

    Node result = null;
    if (this.index < this.nodeListLength) {
      result = this.nodeList.item(this.index++);
    }
    return result;
  }
}
