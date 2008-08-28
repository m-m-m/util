/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import java.util.Collections;
import java.util.List;

import net.sf.mmm.util.collection.api.ListFactory;
import net.sf.mmm.util.collection.api.TreeNode;
import net.sf.mmm.util.nls.api.NlsIllegalArgumentException;
import net.sf.mmm.util.nls.api.NlsNullPointerException;

/**
 * This is the abstract base implementation of the {@link TreeNode} interface.
 * 
 * @param <NODE> is the generic type for self-references. Each sub-type of this
 *        class should specialize this type to itself.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractTreeNode<NODE extends AbstractTreeNode<NODE>> implements
    TreeNode<NODE> {

  /** @see #getChildren() */
  private final List<NODE> mutableChildList;

  /** @see #getChildren() */
  private final List<NODE> children;

  /** @see #getParent() */
  private NODE parent;

  /**
   * The constructor.
   */
  public AbstractTreeNode() {

    this(null, ArrayListFactory.INSTANCE);
  }

  /**
   * The constructor.
   * 
   * @param parent is the {@link #getParent() parent} node.
   */
  public AbstractTreeNode(NODE parent) {

    this(parent, ArrayListFactory.INSTANCE);
  }

  /**
   * The constructor.
   * 
   * @param listFactory the factory used to create the internal {@link List}.
   */
  public AbstractTreeNode(ListFactory listFactory) {

    this(null, listFactory);
  }

  /**
   * The constructor.
   * 
   * @param parent is the {@link #getParent() parent} node.
   * @param listFactory the factory used to create the internal {@link List}.
   */
  public AbstractTreeNode(NODE parent, ListFactory listFactory) {

    super();
    this.parent = parent;
    this.mutableChildList = listFactory.create();
    this.children = Collections.unmodifiableList(this.mutableChildList);
  }

  /**
   * {@inheritDoc}
   */
  public NODE getParent() {

    return this.parent;
  }

  /**
   * This method sets the {@link #getParent() parent} of this {@link TreeNode}.
   * 
   * @param parent is the {@link #getParent() parent} to set. It may be
   *        <code>null</code>. However you should typically only call this
   *        method once with a non-null argument. It is still legal to
   *        re-arrange the tree-structure on existing {@link TreeNode}s.
   */
  protected void setParent(NODE parent) {

    this.parent = parent;
  }

  /**
   * {@inheritDoc}
   */
  public List<NODE> getChildren() {

    return this.children;
  }

  /**
   * This method adds the given <code>child</code> to the {@link #getChildren()
   * children} of this {@link TreeNode}.
   * 
   * @param child is the {@link #getChildren() child} to add. It's
   *        {@link #getParent() parent} has to be identical to this
   *        {@link TreeNode}.
   */
  protected void addChild(NODE child) {

    if (child == null) {
      throw new NlsNullPointerException("child");
    }
    if (child.parent != this) {
      throw new NlsIllegalArgumentException(child);
    }
    this.mutableChildList.add(child);
  }

  /**
   * This method removes the given <code>child</code> from the
   * {@link #getChildren() children} of this {@link TreeNode}.
   * 
   * @param child is the {@link #getChildren() child} to remove.
   * @return <code>true</code> if the given <code>child</code> was contained in
   *         this {@link TreeNode}s {@link #getChildren() children} and has been
   *         removed successfully, <code>false</code> otherwise.
   */
  protected boolean removeChild(NODE child) {

    if (child == null) {
      throw new NlsNullPointerException("child");
    }
    return this.mutableChildList.remove(child);
  }

  /**
   * This method removes the {@link #getChildren() child} from the
   * {@link #getChildren() children} of this {@link TreeNode} at the given
   * <code>index</code>.
   * 
   * @param index is {@link List#get(int) index} of the {@link #getChildren()
   *        child} to remove.
   * @return the {@link #getChildren() child} that has actually been removed.
   */
  protected NODE removeChild(int index) {

    return this.mutableChildList.remove(index);
  }

  /**
   * {@inheritDoc}
   */
  public boolean isAncestor(TreeNode<?> node) {

    if (node == null) {
      throw new NlsNullPointerException("node");
    }
    TreeNode<?> ancestor = node.getParent();
    while (ancestor != null) {
      if (ancestor == this) {
        return true;
      }
      ancestor = ancestor.getParent();
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isDescendant(TreeNode<?> node) {

    if (node == null) {
      throw new NlsNullPointerException("node");
    }
    return node.isAncestor(this);
  }

}
