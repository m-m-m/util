/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import net.sf.mmm.util.collection.api.ListFactory;
import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.lang.api.attribute.AttributeWriteValue;

/**
 * This is a simple but generic implementation of {@link net.sf.mmm.util.collection.api.TreeNode}. It can carry any kind
 * of {@link #getValue() value} that can be {@link #setValue(Object) set} externally.
 *
 * @param <VALUE> is the generic type of the {@link #getValue() value} contained in each node.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class TreeNodeSimple<VALUE> extends AbstractTreeNode<TreeNodeSimple<VALUE>>
    implements AttributeWriteValue<VALUE> {

  /** @see #getValue() */
  private VALUE value;

  /**
   * The constructor.
   */
  public TreeNodeSimple() {

    super();
  }

  /**
   * The constructor.
   *
   * @param value is the {@link #getValue() value}.
   */
  public TreeNodeSimple(VALUE value) {

    super();
    this.value = value;
  }

  /**
   * The constructor.
   *
   * @param parent is the {@link #getParent() parent} node.
   * @param listFactory the factory used to create the internal {@link java.util.List}.
   */
  public TreeNodeSimple(TreeNodeSimple<VALUE> parent, ListFactory listFactory) {

    super(parent, listFactory);
  }

  /**
   * The constructor.
   *
   * @param parent is the {@link #getParent() parent} node.
   */
  public TreeNodeSimple(TreeNodeSimple<VALUE> parent) {

    super(parent);
  }

  /**
   * The constructor.
   *
   * @param parent is the {@link #getParent() parent} node.
   * @param value is the {@link #getValue() value}.
   */
  public TreeNodeSimple(TreeNodeSimple<VALUE> parent, VALUE value) {

    super(parent);
    this.value = value;
  }

  @Override
  public VALUE getValue() {

    return this.value;
  }

  @Override
  public void setValue(VALUE value) {

    this.value = value;
  }

  @Override
  public void addChild(TreeNodeSimple<VALUE> child) {

    super.addChild(child);
  }

  @Override
  public void addChild(TreeNodeSimple<VALUE> child, int index) {

    super.addChild(child, index);
  }

  /**
   * Creates a new {@link TreeNodeSimple} with the given {@code childValue} and {@link #addChild(TreeNodeSimple)
   * adds it as child} of this node.
   *
   * @param childValue is the {@link #getValue() value} of the new child node.
   */
  public void addChildValue(VALUE childValue) {

    addChild(new TreeNodeSimple<>(this, childValue));
  }

  /**
   * Creates a new {@link TreeNodeSimple} with the given {@code childValue} and
   * {@link #addChild(TreeNodeSimple, int) inserts it as child} of this node at the given {@code index}.
   *
   * @param childValue is the {@link #getValue() value} of the new child node.
   * @param index is the {@link java.util.List#get(int) index} where to {@link java.util.List#add(int, Object) insert}
   *        the new child.
   */
  public void addChildValue(VALUE childValue, int index) {

    addChild(new TreeNodeSimple<>(this, childValue), index);
  }

  @Override
  public TreeNodeSimple<VALUE> removeChild(int index) {

    return super.removeChild(index);
  }

  @Override
  public boolean removeChild(TreeNodeSimple<VALUE> child) {

    return super.removeChild(child);
  }

  @Override
  public String toString() {

    if (this.value == null) {
      return StringUtil.NULL;
    }
    return this.value.toString();
  }

}
