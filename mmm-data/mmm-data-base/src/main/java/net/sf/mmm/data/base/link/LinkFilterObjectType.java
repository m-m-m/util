/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.base.link;

import net.sf.mmm.data.api.entity.DataEntity;
import net.sf.mmm.data.api.link.Link;
import net.sf.mmm.util.filter.api.Filter;

/**
 * TODO: this class ...
 * 
 * @param <CLASS> is the type of the linked object. See
 *        {@link net.sf.mmm.data.api.reflection.DataClass#getJavaClass()}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class LinkFilterObjectType<CLASS extends DataEntity> implements Filter<Link<CLASS>> {

  /** @see #accept(Link) */
  private final int classId;

  /**
   * The constructor.
   * 
   * @param classId is the {@link DataEntity#getDataClassId() class ID}.
   */
  public LinkFilterObjectType(int classId) {

    super();
    this.classId = classId;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean accept(Link<CLASS> link) {

    return link.getTarget().getDataClassId() == this.classId;
  }

}
