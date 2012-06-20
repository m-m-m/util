/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.view.composite;

import net.sf.mmm.ui.toolkit.api.view.composite.UiComposite;
import net.sf.mmm.ui.toolkit.base.AbstractUiFactory;
import net.sf.mmm.ui.toolkit.base.view.AbstractUiElement;

/**
 * This class is the implementation of the {@link net.sf.mmm.ui.toolkit.api.view.UiElement} interface using
 * Swing as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @param <DELEGATE> is the generic type of the {@link #getAdapter() delegate}.
 * @param <CHILD> is the generic type of the {@link #getChild(int) children}.
 * @since 1.0.0
 */
public abstract class AbstractUiComposite<DELEGATE, CHILD extends AbstractUiElement<?>> extends
    AbstractUiElement<DELEGATE> implements UiComposite<CHILD> {

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   */
  public AbstractUiComposite(AbstractUiFactory uiFactory) {

    super(uiFactory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSetEnabled(boolean enabled) {

    super.doSetEnabled(enabled);
    int childCount = getChildCount();
    for (int i = 0; i < childCount; i++) {
      getChild(i).updateEnabledFromParent();
    }
  }

}
