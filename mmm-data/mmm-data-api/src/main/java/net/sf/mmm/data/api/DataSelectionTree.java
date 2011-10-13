/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api;

import java.util.List;

import net.sf.mmm.data.api.reflection.DataClassAnnotation;
import net.sf.mmm.data.api.reflection.DataFieldAnnotation;
import net.sf.mmm.data.api.reflection.DataFieldIds;
import net.sf.mmm.util.collection.api.TreeNode;

/**
 * This is the interface for a {@link DataSelection} that represents a tree.
 * Each instance is a tree-node that has a {@link #getParent() parent} and
 * {@link #getChildren() children}.
 * 
 * @param <NODE> is the generic type representing this type itself
 *        (self-reference).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataSelectionTree.CLASS_ID)
public abstract interface DataSelectionTree<NODE extends DataSelectionTree<NODE>> extends
    DataSelection, TreeNode<NODE>, DataNode<NODE> {

  /**
   * The {@link net.sf.mmm.data.api.datatype.DataId#getClassId() class-ID}
   * of the {@link net.sf.mmm.data.api.reflection.DataClass} reflecting
   * this type.
   */
  int CLASS_ID = 5;

  /**
   * {@inheritDoc}
   * 
   * @return the parent or <code>null</code> if this is the root node.
   */
  NODE getParent();

  /**
   * This method gets the children of this node. Implementations should use lazy
   * loading for this property to prevent that the entire tree has to be loaded.
   * 
   * @return the children of this node. May be an empty list.
   */
  @DataFieldAnnotation(id = DataFieldIds.ID_SELECTIONTREE_CHILDREN)
  List<? extends NODE> getChildren();

  /**
   * This method determines if this node is abstract. An abstract node can not
   * be selected in regular situations.
   * 
   * @return <code>true</code> if abstract, <code>false</code> otherwise.
   */
  @DataFieldAnnotation(id = DataFieldIds.ID_SELECTIONTREE_ABSTRACT, isStatic = true, isFinal = true)
  boolean isAbstract();

}
