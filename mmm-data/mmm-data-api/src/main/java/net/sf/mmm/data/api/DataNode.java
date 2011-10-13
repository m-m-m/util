/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api;

import net.sf.mmm.data.api.reflection.DataClassAnnotation;
import net.sf.mmm.data.api.reflection.DataFieldAnnotation;
import net.sf.mmm.data.api.reflection.DataFieldIds;

/**
 * This is the interface for a {@link DataObject} that has a
 * {@link #getParent() parent}.
 * 
 * @param <NODE> is the generic type representing the #getParent().
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataNode.CLASS_ID)
public abstract interface DataNode<NODE extends DataObject> extends DataObject {

  /**
   * The {@link net.sf.mmm.data.api.datatype.DataId#getClassId() class-ID}
   * of the {@link net.sf.mmm.data.api.reflection.DataClass} reflecting
   * this type.
   */
  int CLASS_ID = 2;

  /**
   * This method gets the parent node of this selection entry. If the parent is
   * NOT set (<code>null</code>), this entry will not be selectable.
   * 
   * @return the parent node. May be <code>null</code>.
   */
  @DataFieldAnnotation(id = DataFieldIds.ID_NODE_PARENT)
  NODE getParent();

}
