/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api;

import net.sf.mmm.data.api.reflection.DataClassAnnotation;

/**
 * This is the interface of a mutable {@link DataSelectionGenericTreeView
 * generic selection-tree}.
 * 
 * @param <PARENT> is the genetic type of the {@link #getParent() parent}.
 * @param <CHILD> is the generic type of the {@link #getChildren() children}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataSelectionGenericTreeView.CLASS_ID, title = DataSelectionGenericTreeView.CLASS_TITLE)
public interface DataSelectionGenericTree<CHILD extends DataNode<PARENT>, PARENT extends DataSelectionGenericTree<CHILD, PARENT>>
    extends DataSelectionGenericTreeView<CHILD, PARENT>, DataNode<PARENT>, DataSelection {

  /**
   * This method sets the {@link #isAbstract() abstract} flag.
   * 
   * @param isAbstract - <code>true</code> if the node should be abstract,
   *        <code>false</code> otherwise.
   */
  void setAbstract(boolean isAbstract);

}
