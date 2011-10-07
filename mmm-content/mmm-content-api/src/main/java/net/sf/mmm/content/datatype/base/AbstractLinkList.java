/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.datatype.base;

import java.util.Iterator;
import java.util.List;

import net.sf.mmm.content.datatype.api.Link;
import net.sf.mmm.content.datatype.api.LinkList;
import net.sf.mmm.util.collection.base.AbstractIterator;
import net.sf.mmm.util.filter.api.Filter;

/**
 * This is the abstract base implementation of the {@link LinkList} interface.
 * 
 * @param <CLASS> is the type of the linked object. See
 *        {@link net.sf.mmm.content.reflection.api.ContentClass#getJavaClass()}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractLinkList<CLASS> implements LinkList<CLASS> {

  /**
   * The constructor.
   */
  public AbstractLinkList() {

    super();
  }

  /**
   * This method gets the underlying {@link List} of {@link Link}s.
   * 
   * @return the {@link List} of {@link Link}s.
   */
  protected abstract List<Link<CLASS>> getLinkList();

  /**
   * {@inheritDoc}
   */
  public int getLinkCount() {

    return getLinkList().size();
  }

  /**
   * {@inheritDoc}
   */
  public Link<CLASS> getLink(int index) {

    return getLinkList().get(index);
  }

  /**
   * {@inheritDoc}
   */
  public Iterator<Link<CLASS>> iterator() {

    return getLinks();
  }

  /**
   * {@inheritDoc}
   */
  public Iterator<Link<CLASS>> getLinks() {

    return getLinkList().iterator();
  }

  /**
   * {@inheritDoc}
   */
  public Iterator<Link<CLASS>> getLinks(Filter<Link<CLASS>> filter) {

    return new FilterIterator(getLinks(), filter);
  }

  /**
   * This inner class represents an iterator of {@link Link links}.
   */
  private class FilterIterator extends AbstractIterator<Link<CLASS>> {

    /** is the plain list iterator */
    private Iterator<Link<CLASS>> iterator;

    /** the filter */
    private Filter<Link<CLASS>> filter;

    /**
     * The constructor.
     * 
     * @param iterator is the raw iterator to adapt.
     * @param filter is the {@link Filter}.
     */
    public FilterIterator(Iterator<Link<CLASS>> iterator, Filter<Link<CLASS>> filter) {

      super();
      this.iterator = iterator;
      this.filter = filter;
      findFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Link<CLASS> findNext() {

      while (this.iterator.hasNext()) {
        Link<CLASS> nextLink = this.iterator.next();
        if (this.filter.accept(nextLink)) {
          return nextLink;
        }
      }
      return null;
    }

  }

}
