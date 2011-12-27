/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api;

import net.sf.mmm.data.api.reflection.DataClassAnnotation;

/**
 * This is the interface for a mutable {@link DataSelectionTreeListView
 * selection-tree list}.
 * 
 * @param <NODEVIEW> is the generic view representing the {@link #getParent()
 *        parent tree node}.
 * @param <NODE> is the generic type representing the {@link #getParent() parent
 *        tree node}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataSelectionTreeListView.CLASS_ID, title = DataSelectionTreeListView.CLASS_TITLE)
public interface DataSelectionTreeList<NODEVIEW extends DataSelectionGenericTreeView<NODEVIEW, NODEVIEW>, NODE extends NODEVIEW>
    extends DataSelectionTreeListView<NODEVIEW>, DataNode<NODEVIEW, NODE> {

  /**
   * {@inheritDoc}
   */
  NODE getParent();

}
