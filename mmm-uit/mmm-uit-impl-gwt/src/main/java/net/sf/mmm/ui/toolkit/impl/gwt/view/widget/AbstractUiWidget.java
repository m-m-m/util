/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.view.widget;

import net.sf.mmm.ui.toolkit.api.view.UiElement;
import net.sf.mmm.ui.toolkit.api.view.widget.UiWidget;
import net.sf.mmm.ui.toolkit.base.gwt.view.AbstractUiElementGwt;
import net.sf.mmm.ui.toolkit.impl.gwt.UiFactoryGwt;
import net.sf.mmm.ui.toolkit.impl.gwt.view.composite.AbstractUiComposite;

import com.google.gwt.user.client.ui.Widget;

/**
 * This class is the implementation of the {@link UiWidget} interface using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidget extends AbstractUiElementGwt implements UiWidget {

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   */
  public AbstractUiWidget(UiFactoryGwt uiFactory) {

    super(uiFactory);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  @Override
  public AbstractUiComposite<? extends UiElement> getParent() {

    return (AbstractUiComposite<? extends UiElement>) super.getParent();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Widget getNativeUiObject() {

    // TODO Auto-generated method stub
    return null;
  }

}
