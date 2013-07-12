/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.api;

/**
 * This is the interface for the node a tree. The tree itself is defined by the <em>root-node</em> which is
 * its only {@link TreeNode node} in the tree that has no {@link #getParent() parent}.
 * 
 * @param <NODE> is the generic type for self-references. Each sub-type of this interface should specialize
 *        this type to itself. End-users should use implementations where the generic is finally bound. For
 *        generic usage simply use an unbound wildcard ( <code>{@link TreeNode}&lt;?&gt;</code> ).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public interface TreeNode<NODE extends TreeNode<NODE>> extends GenericTreeNode<NODE, NODE> {

  // nothing to add

}
