/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.api;

import java.util.List;

/**
 * This is the interface for the node a tree. The tree itself is defined by the
 * <em>root-node</em> which is its only {@link TreeNode node} in the tree that
 * has no {@link #getParent() parent}.
 * 
 * @param <NODE> is the generic type for self-references. Each sub-type of this
 *        interface should specialize this type to itself. End-users should
 *        simply use an unbound wildcard (<code>{@link TreeNode}&lt;?&gt;</code>
 *        ).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public interface TreeNode<NODE extends TreeNode<NODE>> {

  /**
   * This method gets the parent of this {@link TreeNode}.
   * 
   * @return the parent {@link TreeNode} or <code>null</code> if this is the
   *         root- {@link TreeNode}.
   */
  NODE getParent();

  /**
   * This method gets the {@link List} containing all children of this
   * {@link TreeNode}. The {@link List} may be {@link List#isEmpty() empty} - in
   * such case we call this {@link TreeNode} a <em>leaf</em>. Each item of the
   * returned {@link List} is called a <em>child</em> or <em>child-node</em>. It
   * should return this {@link TreeNode} as {@link #getParent() parent}.
   * 
   * @return the {@link List} containing all children of this {@link TreeNode}.
   * 
   */
  List<? extends NODE> getChildren();

  /**
   * This method determines if this {@link TreeNode} is a descendant of the
   * given <code>node</code>. In other words this method checks if this
   * {@link TreeNode} is the direct or indirect {@link #getChildren() child} of
   * the given <code>node</code>.
   * 
   * @param node is the {@link TreeNode} to check (the potential
   *        {@link #isAncestor(TreeNode) ancestor}).
   * @return <code>true</code> if this {@link TreeNode} is an descendant of the
   *         given <code>node</code>.
   */
  boolean isDescendant(NODE node);

  /**
   * This method determines if this {@link TreeNode} is an ancestor of the given
   * <code>node</code>. In other words this method checks if this
   * {@link TreeNode} is the direct or indirect {@link #getParent() parent} of
   * the given <code>node</code>.
   * 
   * @param node is the {@link TreeNode} to check (the potential
   *        {@link #isDescendant(TreeNode) descendant}).
   * @return <code>true</code> if this {@link TreeNode} is an ancestor of the
   *         given <code>node</code>.
   */
  boolean isAncestor(NODE node);

}
