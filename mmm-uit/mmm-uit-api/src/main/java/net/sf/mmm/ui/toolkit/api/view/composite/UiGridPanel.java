/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.view.composite;

import net.sf.mmm.ui.toolkit.api.view.UiElement;

/**
 * This is the interface for a
 * {@link net.sf.mmm.ui.toolkit.api.view.composite.UiComposite} that represents
 * a grid. A grid orders its contents as NxM matrix with N rows and M columns.
 * For flexible organization of the grid it is organized as a list of
 * {@link UiGridRow}s. This makes inserting or removing rows easy.
 * 
 * @param <E> is the generic type of the {@link #getChild(int) child-elements}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiGridPanel<E extends UiGridRow<? extends UiElement>> extends
    UiExtendableComposite<E> {

  /**
   * This method creates a new {@link UiGridRow}. The number of columns in this
   * {@link UiGridPanel} will be preallocated for the new row. The cells will be
   * empty and the row is not connected with this {@link UiGridPanel} and
   * therefore not displayed to the user, yet.<br/>
   * You can fill the cells and then
   * {@link #insertChild(net.sf.mmm.ui.toolkit.api.view.UiElement, int) insert}
   * or {@link #addChild(net.sf.mmm.ui.toolkit.api.view.UiElement) append} the
   * new row.
   * 
   * @return the new {@link UiGridRow}.
   */
  E createRow();

}
