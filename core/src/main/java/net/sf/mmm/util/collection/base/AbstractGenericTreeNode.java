/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import java.util.Collections;
import java.util.List;

import net.sf.mmm.util.collection.api.GenericTreeNode;
import net.sf.mmm.util.collection.api.ListFactory;
import net.sf.mmm.util.collection.api.Node;
import net.sf.mmm.util.exception.api.NlsIllegalArgumentException;
import net.sf.mmm.util.exception.api.NlsNullPointerException;

/**
 * This is the abstract base implementation of the {@link GenericTreeNode} interface.
 *
 * @param <CHILD> is the generic type of the {@link #getChildren() children}.
 * @param <PARENT> is the generic type of the {@link #getParent() parent}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public abstract class AbstractGenericTreeNode<CHILD extends Node<PARENT>, PARENT extends GenericTreeNode<CHILD, PARENT>>
    implements GenericTreeNode<CHILD, PARENT> {

  private  final transient List<CHILD> mutableChildList;

  private  final List<CHILD> children;

  private  PARENT parent;

  /**
   * The constructor.
   */
  public AbstractGenericTreeNode() {

    this(null, ArrayListFactory.INSTANCE);
  }

  /**
   * The constructor.
   *
   * @param parent is the {@link #getParent() parent} node.
   */
  public AbstractGenericTreeNode(PARENT parent) {

    this(parent, ArrayListFactory.INSTANCE);
  }

  /**
   * The constructor.
   *
   * @param listFactory the factory used to create the internal {@link List}.
   */
  public AbstractGenericTreeNode(ListFactory listFactory) {

    this(null, listFactory);
  }

  /**
   * The constructor.
   *
   * @param parent is the {@link #getParent() parent} node.
   * @param listFactory the factory used to create the internal {@link List}.
   */
  public AbstractGenericTreeNode(PARENT parent, ListFactory listFactory) {

    super();
    this.parent = parent;
    this.mutableChildList = listFactory.create();
    this.children = Collections.unmodifiableList(this.mutableChildList);
  }

  @Override
  public PARENT getParent() {

    return this.parent;
  }

  /**
   * This method sets the {@link #getParent() parent} of this {@link GenericTreeNode}.
   *
   * @param parent is the {@link #getParent() parent} to set. It may be {@code null}. However you should
   *        typically only call this method once with a non-null argument. It is still legal to re-arrange the
   *        tree-structure on existing {@link GenericTreeNode}s.
   */
  protected void setParent(PARENT parent) {

    this.parent = parent;
  }

  @Override
  public List<CHILD> getChildren() {

    return this.children;
  }

  /**
   * @return the mutableChildList
   */
  protected List<CHILD> getMutableChildList() {

    return this.mutableChildList;
  }

  /**
   * This method adds the given {@code child} to the {@link #getChildren() children} of this
   * {@link GenericTreeNode}.
   *
   * @param child is the {@link #getChildren() child} to add. It's {@link #getParent() parent} has to be
   *        identical to this {@link GenericTreeNode}.
   */
  protected void addChild(CHILD child) {

    if (child == null) {
      throw new NlsNullPointerException("child");
    }
    if (child.getParent() != this) {
      throw new NlsIllegalArgumentException(child);
    }
    this.mutableChildList.add(child);
  }

  /**
   * This method adds the given {@code child} to the {@link #getChildren() children} of this
   * {@link GenericTreeNode}.
   *
   * @param child is the {@link #getChildren() child} to add. It's {@link #getParent() parent} has to be
   *        identical to this {@link GenericTreeNode}.
   * @param index is the {@link List#get(int) index} where to {@link List#add(int, Object) insert} the new
   *        child.
   */
  protected void addChild(CHILD child, int index) {

    if (child == null) {
      throw new NlsNullPointerException("child");
    }
    if (child.getParent() != this) {
      throw new NlsIllegalArgumentException(child);
    }
    this.mutableChildList.add(index, child);
  }

  /**
   * This method removes the given {@code child} from the {@link #getChildren() children} of this
   * {@link GenericTreeNode}.
   *
   * @param child is the {@link #getChildren() child} to remove.
   * @return {@code true} if the given {@code child} was contained in this {@link GenericTreeNode}s
   *         {@link #getChildren() children} and has been removed successfully, {@code false} otherwise.
   */
  protected boolean removeChild(CHILD child) {

    if (child == null) {
      throw new NlsNullPointerException("child");
    }
    return this.mutableChildList.remove(child);
  }

  /**
   * This method removes the {@link #getChildren() child} from the {@link #getChildren() children} of this
   * {@link GenericTreeNode} at the given {@code index}.
   *
   * @param index is {@link List#get(int) index} of the {@link #getChildren() child} to remove.
   * @return the {@link #getChildren() child} that has actually been removed.
   */
  protected CHILD removeChild(int index) {

    return this.mutableChildList.remove(index);
  }

  @Override
  public boolean isAncestor(PARENT node) {

    if (node == null) {
      throw new NlsNullPointerException("node");
    }
    PARENT ancestor = node.getParent();
    while (ancestor != null) {
      if (ancestor == this) {
        return true;
      }
      ancestor = ancestor.getParent();
    }
    return false;
  }

  @Override
  public boolean isDescendant(CHILD node) {

    if (node == null) {
      throw new NlsNullPointerException("node");
    }
    PARENT descendant = node.getParent();
    while (descendant != null) {
      if (descendant == this) {
        return true;
      }
      descendant = descendant.getParent();
    }
    return false;
  }

}
