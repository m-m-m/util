/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.datatype.api;

/**
 * This is the interface for a {@link LinkList} that can be modified.
 * 
 * @param <CLASS> is the type of the linked object. See
 *        {@link net.sf.mmm.content.reflection.api.ContentClass#getJavaClass()}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface MutableLinkList<CLASS> extends LinkList<CLASS> {

  /**
   * This method adds a link to this list.
   * 
   * @param link is the link to add.
   */
  void addLink(Link<CLASS> link);

  /**
   * This method removes the link at the given index.
   * 
   * @param index is the position of the link to remove.
   * @return the link that has been removed.
   */
  Link<CLASS> removeLink(int index);

  /**
   * This method sets the link at the given index.
   * 
   * @param index is the position of the link to set.
   * @param link is the link to set.
   * @return the link that has been overridden and was located at the given
   *         index before this operation.
   */
  Link<CLASS> setLink(int index, Link<CLASS> link);

  /**
   * This method inserts the given link at the given index.
   * 
   * @param index is the position where to insert the link.
   * @param link is the link to insert.
   */
  void insertLink(int index, Link<CLASS> link);

}
