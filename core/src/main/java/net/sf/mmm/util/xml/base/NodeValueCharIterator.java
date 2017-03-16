/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.base;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import net.sf.mmm.util.lang.api.CharIterator;
import net.sf.mmm.util.lang.base.AbstractCharIterator;

/**
 * This is an implementation of {@link CharIterator} that iterates the characters of the {@link Node#getNodeValue()
 * value}(s) of a single node or a node-list.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
class NodeValueCharIterator extends AbstractCharIterator {

  private final NodeList nodeList;

  /** The current {@link NodeList#item(int) index} in {@link #nodeList}. */
  private int nodeIndex;

  /** The current {@link Node#getNodeValue() node-value}. */
  private String value;

  /** The {@link String#length() length} of the current {@link #value}. */
  private int valueLength;

  /** The current {@link String#charAt(int) index} in {@link #value}. */
  private int valueIndex;

  /**
   * The constructor.
   *
   * @param nodeList is the {@link NodeList} containing the {@link Node}s with the {@link Node#getNodeValue() values} to
   *        iterate char by char.
   */
  public NodeValueCharIterator(NodeList nodeList) {

    super();
    this.nodeList = nodeList;
    findFirst();
  }

  /**
   * The constructor.
   *
   * @param singleNode is the single {@link Node} with the {@link Node#getNodeValue() value} to iterate char by char.
   */
  public NodeValueCharIterator(Node singleNode) {

    super();
    this.nodeList = null;
    this.value = singleNode.getNodeValue();
    if (this.value != null) {
      this.valueLength = this.value.length();
    }
    findFirst();
  }

  @Override
  protected char findNext() {

    if ((this.value == null) || (this.valueIndex >= this.valueLength)) {
      if (this.nodeList != null) {
        while (this.nodeIndex < this.nodeList.getLength()) {
          Node node = this.nodeList.item(this.nodeIndex++);
          String nodeValue = node.getNodeValue();
          if ((nodeValue != null) && (nodeValue.length() > 0)) {
            this.value = nodeValue;
            this.valueIndex = 0;
            this.valueLength = nodeValue.length();
            break;
          }
        }
      }
    }
    if (this.value == null) {
      return END_OF_ITERATOR;
    }
    return this.value.charAt(this.valueIndex++);
  }

  @Override
  public String toString() {

    if (this.value == null) {
      return "";
    }
    return this.value;
  }

}
