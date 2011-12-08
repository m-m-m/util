/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api;

/**
 * A {@link DataSelectionGenericTreeView} where {@link #getChildren() children} and
 * {@link #getParent() parents} have the same type.
 * 
 * @param <NODE> is the generic type of the tree nodes.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface DataSelectionTreeView<NODE extends DataSelectionTreeView<NODE>> extends
    DataSelectionGenericTreeView<NODE, NODE> {

  // nothing to add, just bound generics

}
