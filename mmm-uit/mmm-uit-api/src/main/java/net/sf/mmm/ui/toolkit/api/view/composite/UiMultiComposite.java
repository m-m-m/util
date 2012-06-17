/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.view.composite;

import net.sf.mmm.ui.toolkit.api.view.UiElement;

/**
 * This is the abstract interface for a panel. A panel is a {@link UiComposite}
 * that can dynamically aggregate multiple other {@link UiElement}s. <br>
 * 
 * @param <CHILD> is the generic type of the {@link #getChild(int) children}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface UiMultiComposite<CHILD extends UiElement> extends UiComposite<CHILD> {

  /**
   * This method removes the given <code>component</code> from this panel. <br>
   * It will have no effect, if the component has not been added to this panel.
   * 
   * @param component is the component to remove.
   * @return <code>true</code> if the <code>component</code> has been remove,
   *         <code>false</code> otherwise (it was NOT in the panels component
   *         list).
   */
  boolean removeChild(CHILD component);

  /**
   * This method removes the component at the given index from this panel.
   * 
   * @see UiComposite#getChild(int)
   * 
   * @param index is the position of the component to remove.
   * @return the component that has been removed.
   */
  CHILD removeChild(int index);

}
