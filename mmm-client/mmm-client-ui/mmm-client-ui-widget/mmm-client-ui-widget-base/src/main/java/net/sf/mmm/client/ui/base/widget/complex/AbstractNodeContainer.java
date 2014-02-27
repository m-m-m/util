/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.complex;

/**
 * This is the abstract base implementation of {@link NodeContainer}.
 * 
 * @param <NODE> is the generic type of the {@link #getItem() contained} tree node.
 * @param <SELF> is the generic type of this container itself.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractNodeContainer<NODE, SELF extends AbstractNodeContainer<NODE, SELF>> extends
    AbstractItemContainer<NODE, SELF> implements NodeContainer<NODE, SELF> {

  /** @see #getParent() */
  private SELF parent;

  /**
   * The constructor.
   * 
   * @param widget is the {@link AbstractUiWidgetAbstractDataSet widget} creating and owning this
   *        {@link AbstractItemContainer}.
   */
  public AbstractNodeContainer(AbstractUiWidgetAbstractDataSet<?, ?, NODE, SELF> widget) {

    this(widget, null);
  }

  /**
   * The constructor.
   * 
   * @param widget is the {@link AbstractUiWidgetAbstractDataSet widget} creating and owning this
   *        {@link AbstractItemContainer}.
   * @param parent is the {@link #getParent() parent container}.
   */
  public AbstractNodeContainer(AbstractUiWidgetAbstractDataSet<?, ?, NODE, SELF> widget, SELF parent) {

    super(widget);
    this.parent = parent;
  }

  /**
   * @return the {@link AbstractNodeContainer} with the parent {@link #getItem() node} or <code>null</code> if
   *         this is the {@link AbstractNodeContainer} for the root node.
   */
  public SELF getParent() {

    return this.parent;
  }

  /**
   * @param parent is the new {@link #getParent() parent}.
   */
  public void setParent(SELF parent) {

    this.parent = parent;
  }

}
