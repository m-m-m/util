/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api;

import net.sf.mmm.data.api.reflection.DataClassAnnotation;

/**
 * This is the interface for a {@link DataSelection selection} that is chosen
 * from a lists that are embedded in a {@link DataSelectionGenericTree tree-nodes}. In
 * other words: if you have a {@link DataSelectionGenericTree} you can declare an
 * associated {@link DataSelectionTreeList} so that instances are assigned to
 * particular nodes of the associated tree. The selection will then happen by
 * embedding the options in the tree. This is used for
 * {@link net.sf.mmm.data.api.reflection.DataField}s. Other examples could be CD
 * albums or artists that are embedded in a tree of genres.
 * 
 * @param <NODE> is the generic type representing the {@link #getParent() parent
 *        tree node}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataSelectionTreeList.CLASS_ID)
public abstract interface DataSelectionTreeList<NODE extends DataSelectionGenericTree<NODE, NODE>> extends
    DataSelection, DataNode<NODE> {

  /**
   * The {@link net.sf.mmm.data.api.datatype.DataId#getClassId() class-ID} of
   * the {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  int CLASS_ID = 6;

  /**
   * This method gets the parent node of this selection entry. If the parent is
   * NOT set (<code>null</code>), this entry will not be selectable.
   * 
   * @return the parent node. May be <code>null</code>.
   */
  NODE getParent();

}
