/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.trash;

import java.util.Collections;
import java.util.List;


/**
 * This is an implementation of {@link net.sf.mmm.data.trash.LinkList}
 * that is immutable. This is useful to create an unmodifiable view on a
 * {@link net.sf.mmm.data.trash.MutableLinkList}.
 * 
 * @param <CLASS> is the type of the linked object. See
 *        {@link net.sf.mmm.data.api.reflection.DataClass#getJavaClass()}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ImmutableLinkList<CLASS> extends AbstractLinkList<CLASS> {

  /** @see #getLinkList() */
  private final List<Link<CLASS>> linkList;

  /**
   * The constructor.
   * 
   * @param linkList is the {@link net.sf.mmm.data.trash.LinkList} to
   *        adapt.
   */
  public ImmutableLinkList(AbstractLinkList<CLASS> linkList) {

    super();
    this.linkList = Collections.unmodifiableList(linkList.getLinkList());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected List<Link<CLASS>> getLinkList() {

    return this.linkList;
  }

}
