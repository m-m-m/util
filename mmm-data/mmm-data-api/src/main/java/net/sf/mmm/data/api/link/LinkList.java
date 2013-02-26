/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.link;

import java.util.Iterator;

import net.sf.mmm.data.api.entity.DataEntity;
import net.sf.mmm.util.filter.api.Filter;

/**
 * This is the interface for a list of {@link Link links}.
 * 
 * @param <TARGET> is the type of the linked {@link Link#getTarget() target entity}. See
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
   * The same as {@link #getLinkCount()} for compatibility with {@link java.util.List}.
   * 
   * @return the number of links in this list.
   */
  int size();

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
   * This method gets an iterator of all links in the list. It is the same as {@link #iterator()}.
   * 
   * @return an iterator of all links.
   */
  Iterator<Link<TARGET>> getLinks();

  /**
   * This method gets an iterator of all links in the list.
   * 
   * @param filter is the {@link Filter} that determines the {@link #getLinks() links} that are
   *        {@link Filter#accept(Object) accepted} and will be returned.
   * @return an iterator of all links.
   */
  Iterator<Link<TARGET>> getLinks(Filter<Link<TARGET>> filter);

  /**
   * This method gets the the first {@link Link} in this {@link LinkList} that assuming the {@link LinkList}
   * would be ordered by {@link Link#getClassifier() classifiers} according to the given
   * <code>classifierPriority</code>. So e.g. if the {@link LinkList} contains at least one {@link Link}
   * {@link String#equals(Object) matching} first {@link Link#getClassifier() classifier} given by
   * <code>classifierPriority</code> then the first of these {@link Link}s is returned. Otherwise the same
   * applies for the second entry out of <code>classifierPriority</code> and so forth till the end. If none of
   * the {@link Link#getClassifier() classifiers} given by <code>classifierPriority</code> matches, the result
   * depends on <code>acceptUnspecifiedClassifier</code>.
   * 
   * @param acceptUnspecifiedClassifier - if <code>true</code> and none of the {@link Link#getClassifier()
   *        classifiers} given by <code>classifierPriority</code> matches, the first {@link Link} of this
   *        {@link LinkList} is returned (if present). Otherwise the result is <code>null</code>.
   * @param classifierPriority is an array of the {@link Link#getClassifier() classifiers} for the requested
   *        {@link Link} ordered by priority.
   * @return the first {@link Link} in this {@link LinkList} that has the highest priority according to
   *         <code>classifierPriority</code>. If no such {@link Link} exists and
   *         <code>acceptUnspecifiedClassifier</code> is <code>true</code> and this {@link LinkList} is not
   *         {@link #size() empty}, the first {@link Link} is returned. Otherwise the result is
   *         <code>null</code> (e.g. if empty).
   */
  Link<TARGET> getFirstLink(boolean acceptUnspecifiedClassifier, String... classifierPriority);

}
