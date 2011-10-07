/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.datatype.impl;

import java.util.LinkedList;
import java.util.List;

import net.sf.mmm.content.datatype.api.Link;
import net.sf.mmm.content.datatype.api.MutableLinkList;
import net.sf.mmm.content.datatype.base.AbstractLinkList;

/**
 * This is the default implementation of the {@link MutableLinkList} interface.
 * 
 * @param <CLASS> is the type of the linked object. See
 *        {@link net.sf.mmm.content.reflection.api.ContentClass#getJavaClass()}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class MutableLinkListImpl<CLASS> extends AbstractLinkList<CLASS> implements
    MutableLinkList<CLASS> {

  /** the list of links ({@link java.util.List} interface does not allow insert) */
  private LinkedList<Link<CLASS>> linkList;

  /**
   * The constructor.
   */
  public MutableLinkListImpl() {

    super();
    this.linkList = new LinkedList<Link<CLASS>>();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected List<Link<CLASS>> getLinkList() {

    return this.linkList;
  }

  /**
   * {@inheritDoc}
   */
  public void addLink(Link<CLASS> link) {

    this.linkList.add(link);
  }

  /**
   * {@inheritDoc}
   */
  public Link<CLASS> removeLink(int index) {

    return this.linkList.remove(index);
  }

  /**
   * {@inheritDoc}
   */
  public Link<CLASS> setLink(int index, Link<CLASS> link) {

    return this.linkList.set(index, link);
  }

  /**
   * {@inheritDoc}
   */
  public void insertLink(int index, Link<CLASS> link) {

    this.linkList.add(index, link);
  }

}
