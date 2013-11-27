/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.panel;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetAbstractButtonContainer;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetButtonGroup;
import net.sf.mmm.client.ui.base.widget.panel.adapter.UiWidgetAdapterAbstractButtonContainer;

/**
 * This is the abstract base implementation of {@link UiWidgetAbstractButtonContainer}.
 * 
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetAbstractButtonContainer<ADAPTER extends UiWidgetAdapterAbstractButtonContainer>
    extends AbstractUiWidgetDynamicPanel<ADAPTER, UiWidgetRegular> implements UiWidgetAbstractButtonContainer {

  /** The current {@link UiWidgetButtonGroup} or <code>null</code>. */
  private UiWidgetButtonGroup currentButtonGroup;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  public AbstractUiWidgetAbstractButtonContainer(UiContext context, ADAPTER widgetAdapter) {

    super(context, widgetAdapter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void startGroup() throws IllegalStateException {

    if (this.currentButtonGroup != null) {
      throw new IllegalStateException();
    }
    this.currentButtonGroup = getContext().getWidgetFactory().create(UiWidgetButtonGroup.class);
    super.addChild(this.currentButtonGroup);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean endGroup() {

    boolean result = (this.currentButtonGroup != null);
    this.currentButtonGroup = null;
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addChild(UiWidgetRegular child) {

    if (this.currentButtonGroup != null) {
      this.currentButtonGroup.addChild(child);
    } else {
      super.addChild(child);
    }
  }

}
