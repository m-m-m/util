/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.complex;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteStringTitle;
import net.sf.mmm.client.ui.api.widget.UiWidgetNative;

/**
 * This is the interface for a {@link UiWidgetAbstractTree tree based widget} that represents a regular
 * <em>tree</em>. Such widget represents a tree-structure showing tree-nodes that can be collapsed and
 * expanded. For additional details see {@link UiWidgetAbstractTree}. A more complex and flexible alternative
 * is {@link UiWidgetTreeTable}.
 * 
 * @param <NODE> is the generic type of the tree-nodes. E.g. {@link net.sf.mmm.util.collection.api.TreeNode}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetTree<NODE> extends UiWidgetAbstractTree<NODE>, AttributeWriteStringTitle, UiWidgetNative {

  // nothing to add...

}
