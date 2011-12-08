/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api;

import net.sf.mmm.data.api.reflection.DataClassAnnotation;

/**
 * This is the interface for a mutable {@link DataNodeView node}.
 * 
 * @param <NODE> is the generic type representing the {@link #getParent()
 *        parent}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataNodeView.CLASS_ID, title = DataNodeView.CLASS_TITLE)
public interface DataNode<NODE extends DataObjectView> extends DataNodeView<NODE>, DataObject {

  /**
   * This method sets the {@link #getParent() parent}.<br/>
   * <b>ATTENTION:</b><br/>
   * This method should be called with care. If possible use <code>move</code>
   * methods of {@link net.sf.mmm.data.api.repository.DataRepository} instead.
   * 
   * @param parent is the parent to set.
   */
  void setParent(NODE parent);

}
