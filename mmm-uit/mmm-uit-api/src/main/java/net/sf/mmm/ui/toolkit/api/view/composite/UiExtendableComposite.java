/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.view.composite;

import net.sf.mmm.ui.toolkit.api.view.UiElement;

/**
 * This is the abstract interface for a panel. A panel is a {@link UiComposite} that can dynamically aggregate
 * multiple other {@link UiElement}s. <br>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @param <CHILD> is the generic type of the {@link #getChild(int) children}.
 * @since 1.0.0
 */
public interface UiExtendableComposite<CHILD extends UiElement> extends UiMultiComposite<CHILD> {

  /**
   * This method adds the given <code>component</code> to the end of this composite.
   * 
   * @param child is the child element to add. The given child instance must be created by the same factory.
   */
  void addChild(CHILD child);

  /**
   * This method inserts the given <code>component</code> at the given <code>index</code> into this composite.
   * 
   * @param child is the child element to add. The given child instance must be created by the same factory.
   * @param index is the {@link #getChild(int) position} where to insert the given <code>component</code>.
   */
  void insertChild(CHILD child, int index);

}
