/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.view.composite;

import net.sf.mmm.ui.toolkit.api.UiElement;

/**
 * This is the interface of a {@link UiComposite} that represents a row of a
 * {@link UiGrid}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiGridRow<E extends UiElement> extends UiExtendableComposite<E> {

  /**
   * This method sets the given <code>child</code> as cell in this row at the
   * specified <code>column</code>. A {@link UiGrid} is created
   * 
   * @param child is the {@link UiElement} to add.
   * @param column is the index of the column where to add the given
   *        <code>child</code>.
   */
  void setChild(UiElement child, int column);

}
