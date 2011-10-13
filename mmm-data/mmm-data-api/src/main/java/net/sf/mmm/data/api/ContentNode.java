/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api;

/**
 * This is the interface for a {@link ContentObject} that has a
 * {@link #getParent() parent}.
 * 
 * @param <NODE> is the generic type representing the #getParent().
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@ContentClassAnnotation(id = ContentNode.CLASS_ID)
public abstract interface ContentNode<NODE extends ContentObject> extends ContentObject {

  /**
   * The {@link net.sf.mmm.data.datatype.api.ContentId#getClassId() class-ID}
   * of the {@link net.sf.mmm.data.reflection.api.ContentClass} reflecting
   * this type.
   */
  int CLASS_ID = 2;

  /**
   * This method gets the parent node of this selection entry. If the parent is
   * NOT set (<code>null</code>), this entry will not be selectable.
   * 
   * @return the parent node. May be <code>null</code>.
   */
  @ContentFieldAnnotation(id = ContentFieldIds.ID_NODE_PARENT)
  NODE getParent();

}
