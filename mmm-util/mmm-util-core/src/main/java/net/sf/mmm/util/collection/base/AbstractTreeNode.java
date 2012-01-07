/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import net.sf.mmm.util.collection.api.ListFactory;
import net.sf.mmm.util.collection.api.TreeNode;

/**
 * This is the abstract base implementation of the {@link TreeNode} interface.
 * 
 * @param <NODE> is the generic type for self-references. Each sub-type of this
 *        class should specialize this type to itself.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public abstract class AbstractTreeNode<NODE extends TreeNode<NODE>> extends
    AbstractGenericTreeNode<NODE, NODE> implements TreeNode<NODE> {

  /**
   * The constructor.
   */
  public AbstractTreeNode() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param parent is the {@link #getParent() parent} node.
   */
  public AbstractTreeNode(NODE parent) {

    super(parent);
  }

  /**
   * The constructor.
   * 
   * @param listFactory the factory used to create the internal
   *        {@link java.util.List}.
   */
  public AbstractTreeNode(ListFactory listFactory) {

    super(listFactory);
  }

  /**
   * The constructor.
   * 
   * @param parent is the {@link #getParent() parent} node.
   * @param listFactory the factory used to create the internal
   *        {@link java.util.List}.
   */
  public AbstractTreeNode(NODE parent, ListFactory listFactory) {

    super(parent, listFactory);
  }

}
