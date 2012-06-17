/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api;

import java.util.List;

import net.sf.mmm.data.api.reflection.DataClassAnnotation;

/**
 * This is the interface of a mutable {@link DataSelectionGenericTreeView
 * generic selection-tree}.<br/>
 * <b>NOTE:</b><br/>
 * We are very sorry for this generic signature but java generics forced us to
 * make it that complicated.
 * 
 * @param <CHILDVIEW> is the genetic view of the {@link #getParent() parent}.
 * @param <PARENTVIEW> is the generic view of the {@link #getChildren()
 *        children}.
 * @param <PARENT> is the genetic type of the {@link #getParent() parent}.
 * @param <CHILD> is the generic type of the {@link #getChildren() children}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataSelectionGenericTreeView.CLASS_ID, title = DataSelectionGenericTreeView.CLASS_TITLE)
public interface DataSelectionGenericTree<CHILDVIEW extends DataNodeView<PARENTVIEW>, //
CHILD extends CHILDVIEW, PARENTVIEW extends DataSelectionGenericTreeView<CHILDVIEW, PARENTVIEW>, PARENT extends PARENTVIEW>
    extends DataSelectionGenericTreeView<CHILDVIEW, PARENTVIEW>, DataNode<PARENTVIEW, PARENT>,
    DataSelection {

  /**
   * {@inheritDoc}
   */
  List<CHILD> getChildren();

}
