/* $Id$ */
package net.sf.mmm.content.value.api;

import java.util.Iterator;

/**
 * This is the interface for a list of {@link Link links}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface LinkList {

  /**
   * The {@link net.sf.mmm.value.api.ValueManager#getName() name} of this value
   * type.
   */
  String VALUE_NAME = "Linklist";

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
   * @param index
   *        is the position of the requested link.
   * @return the link at the given index.
   */
  Link getLink(int index);

  /**
   * This method gets an iterator over all links in the list.
   * 
   * @return an iterator of all links.
   */
  Iterator<Link> getLinks();

  /**
   * This method gets an iterator over all links in the list.
   * 
   * @param filter
   *        is the filter that determines the {@link #getLinks() links} that are
   *        {@link LinkFilter#acceptLink(Link) accepted} and will be returned.
   * @return an iterator of all links.
   */
  Iterator<Link> getLinks(LinkFilter filter);

}
