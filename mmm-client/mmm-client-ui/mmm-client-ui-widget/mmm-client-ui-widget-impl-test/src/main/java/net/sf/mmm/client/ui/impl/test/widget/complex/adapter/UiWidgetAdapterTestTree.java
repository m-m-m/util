/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.widget.complex.adapter;

import net.sf.mmm.client.ui.api.widget.UiWidget;
import net.sf.mmm.client.ui.api.widget.complex.UiWidgetAbstractTree.UiTreeModel;
import net.sf.mmm.client.ui.api.widget.complex.UiWidgetAbstractTree.UiTreeNodeRenderer;
import net.sf.mmm.client.ui.base.widget.complex.adapter.UiWidgetAdapterTree;
import net.sf.mmm.client.ui.impl.test.widget.adapter.UiWidgetAdapterTestSelectedValue;

/**
 * This is the implementation of {@link UiWidgetAdapterTree} for testing without a native toolkit.
 * 
 * @param <NODE>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterTestTree<NODE> extends UiWidgetAdapterTestSelectedValue<NODE> implements
    UiWidgetAdapterTree<NODE> {

  /**
   * The constructor.
   */
  public UiWidgetAdapterTestTree() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setRootNode(NODE node) {

    // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTreeModel(UiTreeModel<NODE> model) {

    // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTreeNodeRenderer(UiTreeNodeRenderer<NODE, ?> renderer) {

    // TODO Auto-generated method stub
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidget getTreeNodeWidget(NODE node) {

    // TODO Auto-generated method stub
    return null;
  }

}
