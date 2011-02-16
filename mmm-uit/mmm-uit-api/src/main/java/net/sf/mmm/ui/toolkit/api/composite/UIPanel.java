/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.composite;

import net.sf.mmm.ui.toolkit.api.UiElement;

/**
 * This is the abstract interface for a panel. A panel is a {@link UIComposite}
 * that can dynamically aggregate multiple other {@link UiElement}s. <br>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIPanel extends UIComposite {

  /**
   * This method adds the given <code>component</code> to the end of the
   * panels component list.
   * 
   * @param component is the component to add. The given component instance must
   *        be created by the same factory.
   */
  void addComponent(UiElement component);

  /**
   * This method removes the given <code>component</code> from this panel.
   * <br>
   * It will have no effect, if the component has not been added to this panel.
   * 
   * @param component is the component to remove.
   * @return <code>true</code> if the <code>component</code> has been
   *         remove, <code>false</code> otherwise (it was NOT in the panels
   *         component list).
   */
  boolean removeComponent(UiElement component);

  /**
   * This method removes the component at the given index from this panel.
   * 
   * @see UIComposite#getComponent(int)
   * 
   * @param index is the position of the component to remove.
   * @return the component that has been removed.
   */
  UiElement removeComponent(int index);

}
