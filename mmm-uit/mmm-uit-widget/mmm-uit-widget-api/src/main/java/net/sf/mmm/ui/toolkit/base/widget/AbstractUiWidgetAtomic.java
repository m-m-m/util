/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.widget;

import net.sf.mmm.ui.toolkit.api.feature.UiFeatureClick;
import net.sf.mmm.ui.toolkit.api.handler.event.UiHandlerEventClick;
import net.sf.mmm.ui.toolkit.api.widget.UiWidgetAtomic;
import net.sf.mmm.ui.toolkit.base.handler.event.ClickEventSender;
import net.sf.mmm.ui.toolkit.base.widget.adapter.UiWidgetAdapter;

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
    getWidgetAdapter().setClickEventSender(this, this.clickEventSender);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addClickHandler(UiHandlerEventClick handler) {

    if (this.clickEventSender == null) {
      this.clickEventSender = new ClickEventSender(this);
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

}
