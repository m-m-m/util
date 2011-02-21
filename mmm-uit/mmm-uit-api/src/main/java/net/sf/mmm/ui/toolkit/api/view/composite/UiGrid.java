/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.view.composite;

/**
 * This is the interface for a
 * {@link net.sf.mmm.ui.toolkit.api.view.composite.UiComposite} that represents
 * a grid. A grid is a table that orders its contents as NxM matrix with N rows
 * and M columns. For flexible organization of the grid it is organized as a
 * list of {@link UiGridRow}s. This makes inserting or removing rows easy.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiGrid<E extends UiGridRow> extends UiExtendableComposite<E> {

  /**
   * This method creates a new {@link UiGridRow}.
   * 
   * @return
   */
  E createRow();

}
