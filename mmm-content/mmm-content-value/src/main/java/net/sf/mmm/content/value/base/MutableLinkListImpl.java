/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.value.base;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Vector;

import net.sf.mmm.content.value.api.LinkFilter;
import net.sf.mmm.content.value.api.Link;
import net.sf.mmm.content.value.api.MutableLinkList;

/**
 * This is the default implementation of the {@link MutableLinkList}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class MutableLinkListImpl implements MutableLinkList {

  /** the list of links ({@link java.util.List} interface does not allow insert) */
  private Vector<Link> list;

  /**
   * The constructor.
   */
  public MutableLinkListImpl() {

    super();
    this.list = new Vector<Link>();
  }

  /**
   * @see net.sf.mmm.content.value.api.MutableLinkList#addLink(net.sf.mmm.content.value.api.Link)
   */
  public void addLink(Link link) {

    this.list.add(link);
  }

  /**
   * @see net.sf.mmm.content.value.api.MutableLinkList#removeLink(int)
   */
  public Link removeLink(int index) {

    return this.list.remove(index);
  }

  /**
   * @see net.sf.mmm.content.value.api.MutableLinkList#setLink(int, net.sf.mmm.content.value.api.Link)
   */
  public Link setLink(int index, Link link) {

    return this.list.set(index, link);
  }

  /**
   * @see net.sf.mmm.content.value.api.MutableLinkList#insertLink(int, net.sf.mmm.content.value.api.Link)
   */
  public void insertLink(int index, Link link) {

    // List API does not allow this, so we need to use Vector as API :(
    this.list.insertElementAt(link, index);
  }

  /**
   * @see net.sf.mmm.content.value.api.LinkList#getLinkCount()
   */
  public int getLinkCount() {

    return this.list.size();
  }

  /**
   * @see net.sf.mmm.content.value.api.LinkList#getLink(int)
   */
  public Link getLink(int index) {

    return this.list.get(index);
  }

  /**
   * @see net.sf.mmm.content.value.api.LinkList#getLinks()
   */
  public Iterator<Link> getLinks() {

    return this.list.iterator();
  }

  /**
   * @see net.sf.mmm.content.value.api.LinkList#getLinks(net.sf.mmm.content.value.api.LinkFilter)
   */
  public Iterator<Link> getLinks(LinkFilter filter) {

    return new FilterIterator(getLinks(), filter);
  }

  /**
   * This inner class represents an iterator of {@link Link links}.
   */
  private class FilterIterator implements Iterator<Link> {

    /** is the plain list iterator */
    private Iterator<Link> iterator;

    /** the filter */
    private LinkFilter filter;

    /** @see #next() */
    private Link next;

    /**
     * The constructor.
     *
     * @param linkIterator
     * @param linkFilter
     */
    public FilterIterator(Iterator<Link> linkIterator, LinkFilter linkFilter) {

      super();
      this.iterator = linkIterator;
      this.filter = linkFilter;
      this.next = null;
    }

    /**
     * @see java.util.Iterator#hasNext()
     */
    public boolean hasNext() {

      if (this.next == null) {
        while (this.iterator.hasNext()) {
          Link nextLink = this.iterator.next();
          if (this.filter.acceptLink(nextLink)) {
            this.next = nextLink;
            return true;
          }
        }
      }
      return false;
    }

    /**
     * @see java.util.Iterator#next()
     */
    public Link next() {

      if (hasNext()) {
        Link nextLink = this.next;
        this.next = null;
        return nextLink;
      }
      throw new NoSuchElementException();
    }

    /**
     * @see java.util.Iterator#remove()
     */
    public void remove() {

      throw new UnsupportedOperationException();
    }

  }

}
