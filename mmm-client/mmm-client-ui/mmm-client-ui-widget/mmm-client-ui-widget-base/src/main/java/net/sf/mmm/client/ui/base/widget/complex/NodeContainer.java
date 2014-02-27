/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.complex;

/**
 * This is the interface extends {@link ItemContainer} for containing nodes of
 * {@link net.sf.mmm.client.ui.api.widget.complex.UiWidgetAbstractTree tree widget}.
 * 
 * @param <NODE> is the generic type of the {@link #getItem() contained} tree node.
 * @param <SELF> is the generic type of this container itself.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface NodeContainer<NODE, SELF extends NodeContainer<NODE, SELF>> extends ItemContainer<NODE, SELF> {

  /**
   * @return the {@link NodeContainer} with the parent {@link #getItem() node} or <code>null</code> if this is
   *         the {@link NodeContainer} for the root node.
   */
  SELF getParent();

  /**
   * @param parent is the new {@link #getParent() parent}.
   */
  void setParent(SELF parent);

}
