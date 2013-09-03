/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.core;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.aria.role.Role;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetLabel;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidget;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidgetWithLabel;
import net.sf.mmm.client.ui.base.widget.core.adapter.UiWidgetAdapterLabel;

/**
 * This is the abstract base implementation of {@link UiWidgetLabel}.
 * 
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetLabel<ADAPTER extends UiWidgetAdapterLabel> extends
    AbstractUiWidgetWithLabel<ADAPTER> implements UiWidgetLabel {

  /** @see #getLabelledWidget() */
  private AbstractUiWidget<?> labelledWidget;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public AbstractUiWidgetLabel(UiContext context) {

    super(context);
    setPrimaryStyle(STYLE_PRIMARY);
  }

  // /**
  // * {@inheritDoc}
  // */
  // @Override
  // protected Class<? extends Role> getAriaRoleFixedType() {
  //
  // return Role;
  // }

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
      Role ariaRole = this.labelledWidget.getAriaRole();
      if (ariaRole != null) {
        ariaRole.setLabelledBy(getId());
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setId(String newId) {

    super.setId(newId);
    if (this.labelledWidget != null) {
      Role ariaRole = this.labelledWidget.getAriaRole();
      if (ariaRole != null) {
        ariaRole.setLabelledBy(newId);
      }
    }
  }

}
