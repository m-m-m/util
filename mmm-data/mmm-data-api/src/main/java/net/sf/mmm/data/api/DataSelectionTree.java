/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api;

/**
 * This is the interface of a mutable {@link DataSelectionTreeView}.
 * 
 * @param <NODEVIEW> is the generic view representing the {@link #getParent()
 *        parent tree node}.
 * @param <NODE> is the generic type representing the {@link #getParent() parent
 *        tree node}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface DataSelectionTree<NODEVIEW extends DataSelectionTreeView<NODEVIEW>, NODE extends NODEVIEW>
    extends DataSelectionTreeView<NODEVIEW>,
    DataSelectionGenericTree<NODEVIEW, NODE, NODEVIEW, NODE> {

  // nothing to add...

}
