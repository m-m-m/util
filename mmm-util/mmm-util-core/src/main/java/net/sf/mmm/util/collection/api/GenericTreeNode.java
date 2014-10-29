/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.api;

import java.util.List;

/**
 * This is the interface for the node of a tree. The tree itself is defined by the <em>root-node</em> which is
 * its only {@link GenericTreeNode node} in the tree that has no {@link #getParent() parent}. <br>
 * A {@link GenericTreeNode} is generic and allows different types for {@literal <CHILD>} and
 * {@literal <PARENT>} (e.g. to implement a <em>folder</em> that can only have a <em>folder</em> as parent but
 * can have any <em>resource</em> as child that is of type <em>folder</em> or <em>file</em>). For a regular
 * simple tree node you can use {@link TreeNode}.
 * 
 * @param <CHILD> is the generic type of the {@link #getChildren() children}.
 * @param <PARENT> is the generic type of the {@link #getParent() parent}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public interface GenericTreeNode<CHILD extends Node<PARENT>, PARENT extends GenericTreeNode<CHILD, PARENT>> extends
    Node<PARENT> {

  /**
   * This method gets the parent of this {@link GenericTreeNode}.
   * 
   * @return the parent {@link GenericTreeNode} or <code>null</code> if this is the root-
   *         {@link GenericTreeNode}.
   */
  PARENT getParent();

  /**
   * This method gets the {@link List} containing all children of this {@link GenericTreeNode}. The
   * {@link List} may be {@link List#isEmpty() empty} - in such case we call this {@link GenericTreeNode} a
   * <em>leaf</em> . Each item of the returned {@link List} is called a <em>child</em> or <em>child-node</em>.
   * It should return this {@link GenericTreeNode} as {@link #getParent() parent}.
   * 
   * @return the {@link List} containing all children of this {@link GenericTreeNode}.
   * 
   */
  List<? extends CHILD> getChildren();

  /**
   * This method determines if this {@link GenericTreeNode} is a descendant of the given <code>node</code>. In
   * other words this method checks if this {@link GenericTreeNode} is the direct or indirect
   * {@link #getChildren() child} of the given <code>node</code>.
   * 
   * @param node is the {@link GenericTreeNode} to check (the potential {@link #isAncestor(GenericTreeNode)
   *        ancestor}).
   * @return <code>true</code> if this {@link GenericTreeNode} is an descendant of the given <code>node</code>
   *         .
   */
  boolean isDescendant(CHILD node);

  /**
   * This method determines if this {@link GenericTreeNode} is an ancestor of the given <code>node</code>. In
   * other words this method checks if this {@link GenericTreeNode} is the direct or indirect
   * {@link #getParent() parent} of the given <code>node</code>.
   * 
   * @param node is the {@link GenericTreeNode} to check (the potential {@link #isDescendant(Node) descendant}
   *        ).
   * @return <code>true</code> if this {@link GenericTreeNode} is an ancestor of the given <code>node</code>.
   */
  boolean isAncestor(PARENT node);

}
