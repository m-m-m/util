/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.base;

import net.sf.mmm.util.xml.api.XmlCompareMode;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * This class is used to {@link java.util.Iterator#next() iterate} the {@link Node}s of a {@link NodeList}.
 * Depending on the {@link XmlCompareMode} given at {@link #JoiningNodeIterator(NodeList, XmlCompareMode)
 * construction} it automatically joins {@link Node}s of according {@link Node#getNodeType() types} by
 * returning them as a {@link org.w3c.dom.DocumentFragment}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
class JoiningNodeIterator extends NodeIterator {

  /** The mode of comparison. */
  private final XmlCompareMode compareMode;

  /** Buffer for lookahead or <code>null</code>. */
  private Node next;

  /**
   * The constructor.
   * 
   * @param nodeList is the {@link NodeList} to iterate.
   * @param mode is the {@link XmlCompareMode}.
   */
  public JoiningNodeIterator(NodeList nodeList, XmlCompareMode mode) {

    super(nodeList);
    this.compareMode = mode;
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("null")
  @Override
  protected Node findNext() {

    Node result = this.next;
    this.next = null;
    if (result == null) {
      result = super.findNext();
      if (result != null) {
        short nodeType = result.getNodeType();
        // skip comments?
        if (!this.compareMode.isCheckComments()) {
          while (nodeType == Node.COMMENT_NODE) {
            result = super.findNext();
            if (result == null) {
              nodeType = -1;
            } else {
              nodeType = result.getNodeType();
            }
          }
        }
        if (((nodeType == Node.TEXT_NODE) && this.compareMode.isJoinText())
            || ((nodeType == Node.CDATA_SECTION_NODE) && this.compareMode.isJoinCData())
            || ((nodeType == Node.COMMENT_NODE) && this.compareMode.isJoinComment())) {
          Node currentNode = result;
          result = result.getOwnerDocument().createDocumentFragment();
          short alternativeNodeType = nodeType;
          if (this.compareMode.isJoinText() && this.compareMode.isJoinCData()) {
            if (nodeType == Node.TEXT_NODE) {
              alternativeNodeType = Node.CDATA_SECTION_NODE;
            } else if (nodeType == Node.CDATA_SECTION_NODE) {
              alternativeNodeType = Node.TEXT_NODE;
            }
          }
          do {
            result.appendChild(currentNode);
            currentNode = super.findNext();
            if (currentNode != null) {
              short nextNodeType = currentNode.getNodeType();
              if ((nextNodeType != nodeType) && (nextNodeType != alternativeNodeType)) {
                // lookahead was too far, store as next.
                this.next = currentNode;
                currentNode = null;
              }
            }
          } while (currentNode != null);
        }
      }
    }
    return result;
  }
}
