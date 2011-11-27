/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.link;

import java.util.Iterator;

import net.sf.mmm.data.api.entity.DataEntity;
import net.sf.mmm.util.filter.api.Filter;

/**
 * This is the interface for a list of {@link Link links}.
 * 
 * @param <TARGET> is the type of the linked {@link Link#getTarget() target
 *        entity}. See
 *        {@link net.sf.mmm.data.api.reflection.DataClass#getJavaClass()}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface LinkList<TARGET extends DataEntity> extends Iterable<Link<TARGET>> {

  /**
   * This method gets the number of links in this list.
   * 
   * @see LinkList#getLink(int)
   * 
   * @return the number of links in this list.
   */
  int getLinkCount();

  /**
   * This method gets the link of this list located at the given position.
   * 
   * @see LinkList#getLinkCount()
   * 
   * @param index is the position of the requested link.
   * @return the link at the given index.
   */
  Link<TARGET> getLink(int index);

  /**
   * This method gets an iterator of all links in the list. It is the same as
   * {@link #iterator()}.
   * 
   * @return an iterator of all links.
   */
  Iterator<Link<TARGET>> getLinks();

  /**
   * This method gets an iterator of all links in the list.
   * 
   * @param filter is the {@link Filter} that determines the {@link #getLinks()
   *        links} that are {@link Filter#accept(Object) accepted} and will be
   *        returned.
   * @return an iterator of all links.
   */
  Iterator<Link<TARGET>> getLinks(Filter<Link<TARGET>> filter);

}
