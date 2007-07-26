/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.value.api;

/**
 * This is the interface for a {@link LinkList link-list}that can be modified.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface MutableLinkList extends LinkList {

  /**
   * This method adds a link to this linklist.
   * 
   * @param link is the link to add.
   */
  void addLink(Link link);

  /**
   * This method removes the link at the given index.
   * 
   * @param index is the position of the link to remove.
   * @return the link that has been removed.
   */
  Link removeLink(int index);

  /**
   * This method sets the link at the given index.
   * 
   * @param index is the position of the link to set.
   * @param link is the link to set.
   * @return the link that has been overridden and was located at the given
   *         index before this operation.
   */
  Link setLink(int index, Link link);

  /**
   * This method inserts the given link at the given index.
   * 
   * @param index is the position where to insert the link.
   * @param link is the link to insert.
   */
  void insertLink(int index, Link link);

}
