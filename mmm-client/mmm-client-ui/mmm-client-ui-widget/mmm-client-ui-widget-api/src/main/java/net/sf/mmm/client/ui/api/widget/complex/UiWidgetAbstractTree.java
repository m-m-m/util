/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.complex;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteSelectionMode;
import net.sf.mmm.client.ui.api.feature.UiFeatureSelectedValue;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.api.widget.model.UiTreeModel;

/**
 * This is the interface for a {@link UiWidgetRegular regular widget} that represents a <em>tree</em>. Such
 * widget represents a tree-structure showing tree-nodes that can be collapsed and expanded. It has the
 * following features:
 * <ul>
 * <li>Configured via {@link #setTreeModel(UiTreeModel)} with a number of</li>
 * </ul>
 * 
 * @param <NODE> is the generic type of the tree-nodes. E.g. {@link net.sf.mmm.util.collection.api.TreeNode}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface UiWidgetAbstractTree<NODE> extends UiWidgetRegular, UiFeatureSelectedValue<NODE>,
    AttributeWriteSelectionMode {

  /**
   * This method gets the {@link UiTreeModel} adapting from {@literal <NODE>} to this tree-widget.
   * 
   * @return the {@link UiTreeModel}. May be <code>null</code> if NOT set.
   */
  UiTreeModel<NODE> getTreeModel();

  /**
   * This method sets the {@link UiTreeModel} adapting from {@literal <NODE>} to this tree-widget.
   * 
   * @param model is the {@link UiTreeModel} to set.
   */
  void setTreeModel(UiTreeModel<NODE> model);

}
