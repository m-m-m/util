/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.view.composite;

import net.sf.mmm.ui.toolkit.api.view.composite.UiComposite;
import net.sf.mmm.ui.toolkit.base.gwt.view.AbstractUiElementGwt;
import net.sf.mmm.ui.toolkit.impl.gwt.UiFactoryGwt;

import com.google.gwt.user.client.ui.Composite;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.UiElement} interface using Swing as the
 * UI toolkit.
 * 
 * @param <E> is the generic type of the {@link #getChild(int) child-elements}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiComposite<E extends AbstractUiElementGwt> extends
    AbstractUiElementGwt implements UiComposite<E> {

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   */
  public AbstractUiComposite(UiFactoryGwt uiFactory) {

    super(uiFactory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected abstract Composite getNativeUiObject();
}
