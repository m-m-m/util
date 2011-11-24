/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.base;

import java.util.Iterator;
import java.util.List;

import net.sf.mmm.data.api.LinkList;
import net.sf.mmm.data.api.datatype.Link;
import net.sf.mmm.data.api.entity.DataEntity;
import net.sf.mmm.util.collection.base.FilteredIterator;
import net.sf.mmm.util.filter.api.Filter;

/**
 * This is the abstract base implementation of the {@link LinkList} interface.
 * 
 * @param <CLASS> is the type of the linked object. See
 *        {@link net.sf.mmm.data.api.reflection.DataClass#getJavaClass()}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractLinkList<CLASS extends DataEntity> implements LinkList<CLASS> {

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

    return new FilteredIterator<Link<CLASS>>(getLinks(), filter);
  }

}
