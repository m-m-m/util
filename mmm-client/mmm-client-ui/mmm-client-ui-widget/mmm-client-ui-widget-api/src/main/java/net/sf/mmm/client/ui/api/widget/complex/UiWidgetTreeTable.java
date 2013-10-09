/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.complex;

import net.sf.mmm.client.ui.api.widget.UiWidgetNative;

/**
 * This is the interface for a {@link net.sf.mmm.client.ui.api.widget.UiWidgetRegular regular widget} that is
 * both a {@link UiWidgetAbstractListTable list table} as well as a {@link UiWidgetAbstractTree}.
 * 
 * @param <NODE> is the generic type of the tree-nodes. E.g. {@link net.sf.mmm.util.collection.api.TreeNode}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetTreeTable<NODE> extends UiWidgetAbstractDataTable<NODE>, UiWidgetAbstractTree<NODE>,
    UiWidgetNative {

  // nothing to add...

}
