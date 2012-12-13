/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.core;

import net.sf.mmm.client.ui.api.widget.core.UiWidgetLabel;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidget;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidgetFactory;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidgetWithLabel;
import net.sf.mmm.client.ui.base.widget.core.adapter.UiWidgetAdapterLabel;

/**
 * This is the abstract base implementation of {@link UiWidgetLabel}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 */
public abstract class AbstractUiWidgetLabel<ADAPTER extends UiWidgetAdapterLabel<?>> extends
    AbstractUiWidgetWithLabel<ADAPTER> implements UiWidgetLabel {

  /** @see #getLabelledWidget() */
  private AbstractUiWidget<?> labelledWidget;

  /**
   * The constructor.
   * 
   * @param factory is the {@link #getFactory() factory}.
   */
  public AbstractUiWidgetLabel(AbstractUiWidgetFactory<?> factory) {

    super(factory);
  }

  /**
   * @return the {@link AbstractUiWidget widget}
   *         {@link net.sf.mmm.client.ui.api.aria.attribute.AttributeReadAriaLabelledBy#getLabelledBy()
   *         labelled by} this label or <code>null</code> if no associated widget is set.
   */
  public AbstractUiWidget<?> getLabelledWidget() {

    return this.labelledWidget;
  }

  /**
   * @param labelForWidget is the new value for {@link #getLabelledWidget()}.
   */
  public void setLabelledWidget(AbstractUiWidget<?> labelForWidget) {

    this.labelledWidget = labelForWidget;
    if (this.labelledWidget != null) {
      this.labelledWidget.getAriaRole().setLabelledBy(getId());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setId(String newId) {

    super.setId(newId);
    if (this.labelledWidget != null) {
      this.labelledWidget.getAriaRole().setLabelledBy(newId);
    }
  }

}
