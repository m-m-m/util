/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget;

import net.sf.mmm.client.ui.api.common.UiMode;
import net.sf.mmm.client.ui.api.feature.UiFeatureClick;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventClick;
import net.sf.mmm.client.ui.api.widget.UiWidgetAtomic;
import net.sf.mmm.client.ui.base.handler.event.ClickEventSender;
import net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapter;
import net.sf.mmm.util.validation.api.ValidationState;

/**
 * This is the abstract base implementation of {@link UiWidgetAtomic}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 */
public abstract class AbstractUiWidgetAtomic<ADAPTER extends UiWidgetAdapter<?>> extends AbstractUiWidget<ADAPTER>
    implements UiWidgetAtomic, UiFeatureClick {

  /** @see #addClickHandler(UiHandlerEventClick) */
  private ClickEventSender clickEventSender;

  /**
   * The constructor.
   * 
   * @param factory is the {@link #getFactory() factory}.
   */
  public AbstractUiWidgetAtomic(AbstractUiWidgetFactory<?> factory) {

    super(factory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initializeWidgetAdapter(ADAPTER adapter) {

    super.initializeWidgetAdapter(adapter);
    if (this.clickEventSender != null) {
      adapter.setClickEventSender(this, this.clickEventSender);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addClickHandler(UiHandlerEventClick handler) {

    if (this.clickEventSender == null) {
      this.clickEventSender = new ClickEventSender(this, getFactory());
      if (hasWidgetAdapter()) {
        getWidgetAdapter().setClickEventSender(this, this.clickEventSender);
      }
    }
    this.clickEventSender.addHandler(handler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeClickHandler(UiHandlerEventClick handler) {

    if (this.clickEventSender != null) {
      return this.clickEventSender.removeHandler(handler);
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void click() {

    if (this.clickEventSender != null) {
      this.clickEventSender.onClick(this, true);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  final boolean isModifiedRecursive() {

    return super.isModifiedRecursive();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  final void setModeRecursive(UiMode newMode) {

    super.setModeRecursive(newMode);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  final void validateRecursive(ValidationState state) {

    super.validateRecursive(state);
  }

}
