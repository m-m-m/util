/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.view.composite;

import net.sf.mmm.ui.toolkit.api.UiElement;

/**
 * This is the abstract interface for a panel. A panel is a {@link UiComposite}
 * that can dynamically aggregate multiple other {@link UiElement}s. <br>
 * 
 * @param <E> is the generic type of the {@link #addChild(UiElement)
 *        child-elements}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiExtendableComposite<E extends UiElement> extends UiMultiComposite<E> {

  /**
   * This method adds the given <code>component</code> to the end of this
   * composite.
   * 
   * @param component is the component to add. The given component instance must
   *        be created by the same factory.
   */
  void addChild(E component);

  /**
   * This method inserts the given <code>component</code> at the given
   * <code>index</code> into this composite.
   * 
   * @param component is the component to add. The given component instance must
   *        be created by the same factory.
   * @param index is the {@link #getChild(int) position} where to insert the
   *        given <code>component</code>.
   */
  void insertChild(E component, int index);

}
