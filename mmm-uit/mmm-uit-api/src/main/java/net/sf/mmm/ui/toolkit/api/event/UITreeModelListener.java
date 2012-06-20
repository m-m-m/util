/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.event;

import java.util.EventListener;

/**
 * This is the interface of a tree model listener. Such a listener gets notified about any change of
 * {@link net.sf.mmm.ui.toolkit.api.view.widget.UiTree tree}
 * {@link net.sf.mmm.ui.toolkit.api.model.data.UiTreeMvcModel#getChildNode(Object, int) nodes} from the
 * {@link net.sf.mmm.ui.toolkit.api.model.data.UiTreeMvcModel tree-model}.
 * 
 * @see net.sf.mmm.ui.toolkit.api.model.data.UiTreeMvcModel
 * 
 * @param <N> is the templated type of the tree nodes.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UITreeModelListener<N> extends EventListener {

  /**
   * This method is called by the {@link net.sf.mmm.ui.toolkit.api.model.data.UiTreeMvcModel tree-model} if it
   * changed (nodes have been updated, inserted or removed).
   * 
   * @param event notifies about the change.
   */
  void treeModelChanged(UITreeModelEvent<N> event);

}
