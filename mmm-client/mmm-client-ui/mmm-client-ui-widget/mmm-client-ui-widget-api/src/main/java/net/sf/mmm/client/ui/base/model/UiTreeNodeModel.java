/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.model;

import java.util.List;

import net.sf.mmm.client.ui.api.widget.complex.UiWidgetAbstractTree.UiTreeModelMutable;
import net.sf.mmm.util.collection.base.TreeNodeSimple;
import net.sf.mmm.util.lang.api.function.Consumer;

/**
 * This is a simple implementation of {@link UiTreeModelMutable mutable tree model}.
 *
 * @param <VALUE> is the generic type of the {@link TreeNodeSimple#getValue() value} contained in each node.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiTreeNodeModel<VALUE> implements UiTreeModelMutable<TreeNodeSimple<VALUE>> {

  /**
   * {@inheritDoc}
   */
  @Override
  public List<TreeNodeSimple<VALUE>> getChildren(TreeNodeSimple<VALUE> node) {

    return node.getChildren();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void getChildrenAsync(TreeNodeSimple<VALUE> node, Consumer<List<TreeNodeSimple<VALUE>>> callback) {

    callback.accept(node.getChildren());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addChild(TreeNodeSimple<VALUE> parent, TreeNodeSimple<VALUE> child, int index) {

    parent.addChild(child, index);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addChild(TreeNodeSimple<VALUE> parent, TreeNodeSimple<VALUE> child) {

    parent.addChild(child);
  }

  /**
   * Creates a new {@link TreeNodeSimple} for the given <code>childValue</code> and adds it to the given
   * <code>parent</code>.
   *
   * @param parent is the parent {@link TreeNodeSimple node}.
   * @param childValue is the {@link TreeNodeSimple#getValue() value} of the new child node.
   * @return the new child node.
   */
  public TreeNodeSimple<VALUE> addChild(TreeNodeSimple<VALUE> parent, VALUE childValue) {

    TreeNodeSimple<VALUE> child = new TreeNodeSimple<VALUE>(parent, childValue);
    parent.addChild(child);
    return child;
  }

  /**
   * Creates a new {@link TreeNodeSimple} for the given <code>childValue</code> and adds it to the given
   * <code>parent</code>.
   *
   * @param parent is the parent {@link TreeNodeSimple node}.
   * @param childValue is the {@link TreeNodeSimple#getValue() value} of the new child node.
   * @param index is the {@link List#get(int) index} where to {@link List#add(int, Object) insert} the new
   *        child.
   * @return the new child node.
   */
  public TreeNodeSimple<VALUE> addChild(TreeNodeSimple<VALUE> parent, VALUE childValue, int index) {

    TreeNodeSimple<VALUE> child = new TreeNodeSimple<VALUE>(childValue);
    parent.addChild(child, index);
    return child;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeChilden(TreeNodeSimple<VALUE> parent,
      @SuppressWarnings("unchecked") TreeNodeSimple<VALUE>... children) {

    for (TreeNodeSimple<VALUE> child : children) {
      parent.removeChild(child);
    }
  }

}
