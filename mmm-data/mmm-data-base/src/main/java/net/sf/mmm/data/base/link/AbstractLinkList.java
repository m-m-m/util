/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.base.link;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.mmm.data.api.entity.DataEntityView;
import net.sf.mmm.data.api.link.Link;
import net.sf.mmm.data.api.link.LinkList;
import net.sf.mmm.util.collection.base.FilteredIterator;
import net.sf.mmm.util.filter.api.Filter;

/**
 * This is the abstract base implementation of the {@link LinkList} interface.
 * 
 * @param <TARGET> is the type of the linked {@link Link#getTarget() target
 *        entity}. See
 *        {@link net.sf.mmm.data.api.reflection.DataClass#getJavaClass()}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractLinkList<TARGET extends DataEntityView> implements LinkList<TARGET> {

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
  protected abstract List<Link<TARGET>> getLinkList();

  /**
   * {@inheritDoc}
   */
  public int getLinkCount() {

    return getLinkList().size();
  }

  /**
   * {@inheritDoc}
   */
  public int size() {

    return getLinkList().size();
  }

  /**
   * {@inheritDoc}
   */
  public Link<TARGET> getLink(int index) {

    return getLinkList().get(index);
  }

  /**
   * {@inheritDoc}
   */
  public Iterator<Link<TARGET>> iterator() {

    return getLinks();
  }

  /**
   * {@inheritDoc}
   */
  public Iterator<Link<TARGET>> getLinks() {

    return getLinkList().iterator();
  }

  /**
   * {@inheritDoc}
   */
  public Iterator<Link<TARGET>> getLinks(Filter<Link<TARGET>> filter) {

    Iterator<Link<TARGET>> iterator = getLinks();
    return new FilteredIterator<Link<TARGET>>(iterator, filter);
  }

  /**
   * {@inheritDoc}
   */
  public Link<TARGET> getFirstLink(boolean acceptUnspecifiedClassifier,
      String... classifierPriority) {

    List<Link<TARGET>> linklist = getLinkList();
    if (linklist.isEmpty()) {
      return null;
    }
    Link<TARGET> result = null;
    Integer priority = null;
    Map<String, Integer> priorityMap = new HashMap<String, Integer>();
    for (int i = 0; i < classifierPriority.length; i++) {
      priorityMap.put(classifierPriority[i], Integer.valueOf(i));
    }
    for (Link<TARGET> link : linklist) {
      Integer currentPriortity = priorityMap.get(link.getClassifier());
      if (currentPriortity != null) {
        // attention: lower priority is better
        if ((priority == null) || (priority.intValue() > currentPriortity.intValue())) {
          result = link;
          priority = currentPriortity;
        }
      } else if ((result == null) && (acceptUnspecifiedClassifier)) {
        result = link;
      }
    }
    return result;
  }
}
