/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.smartgwt.view.widget;

import net.sf.mmm.ui.toolkit.api.view.UiElement;
import net.sf.mmm.ui.toolkit.api.view.composite.UiComposite;
import net.sf.mmm.ui.toolkit.api.view.widget.UiWidget;
import net.sf.mmm.ui.toolkit.base.gwt.view.AbstractUiElement;
import net.sf.mmm.ui.toolkit.impl.smartgwt.UiFactorySmartGwt;

import com.smartgwt.client.widgets.StatefulCanvas;

/**
 * This is the abstract base implementation of {@link UiWidget} using SmartGWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidget extends AbstractUiElement implements UiWidget {

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   */
  public AbstractUiWidget(UiFactorySmartGwt uiFactory) {

    super(uiFactory);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  @Override
  public UiComposite<? extends UiElement> getParent() {

    return (UiComposite<? extends UiElement>) super.getParent();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected abstract StatefulCanvas getNativeUiObject();

  /**
   * {@inheritDoc}
   */
  @Override
  public void doSetEnabled(boolean enabled) {

    getNativeUiObject().setDisabled(!enabled);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTooltip(String tooltip) {

    doSetTooltip(tooltip);
    getNativeUiObject().setTooltip(tooltip);
  }

}
