/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.smartgwt.view.widget;

import net.sf.mmm.ui.toolkit.api.view.UiElement;
import net.sf.mmm.ui.toolkit.api.view.composite.UiComposite;
import net.sf.mmm.ui.toolkit.api.view.widget.UiWidget;
import net.sf.mmm.ui.toolkit.base.view.AbstractUiNode;
import net.sf.mmm.ui.toolkit.impl.smartgwt.UiFactorySmartGwt;

import com.smartgwt.client.widgets.form.fields.FormItem;

/**
 * This is the abstract base implementation of {@link UiWidget} based on a
 * SmartGWT {@link FormItem}. The OO-design of SmartGWT is unfortunately very
 * strange.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetFormItem extends AbstractUiNode implements UiWidget {

  /** @see #getTooltip() */
  private String tooltip;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   */
  public AbstractUiWidgetFormItem(UiFactorySmartGwt uiFactory) {

    super(uiFactory);
    this.tooltip = "";
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
   * This method gets the native UI object of this node.
   * 
   * @return the underlying {@link FormItem}.
   */
  protected abstract FormItem getNativeUiObject();

  /**
   * {@inheritDoc}
   */
  public void setTooltip(String tooltip) {

    this.tooltip = tooltip;
    getNativeUiObject().setTooltip(tooltip);
  }

  /**
   * {@inheritDoc}
   */
  public String getTooltip() {

    return this.tooltip;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSetEnabled(boolean enabled) {

    getNativeUiObject().setDisabled(Boolean.valueOf(!enabled));
  }

  /**
   * {@inheritDoc}
   */
  public void setSize(int width, int height) {

    FormItem formItem = getNativeUiObject();
    formItem.setWidth(width);
    formItem.setHeight(height);
  }

  /**
   * {@inheritDoc}
   */
  public boolean isResizable() {

    // TODO Auto-generated method stub
    return false;
  }

  /**
   * {@inheritDoc}
   */
  public int getWidth() {

    return getNativeUiObject().getWidth();
  }

  /**
   * {@inheritDoc}
   */
  public int getHeight() {

    return getNativeUiObject().getHeight();
  }

  /**
   * {@inheritDoc}
   */
  public int getPreferredWidth() {

    // TODO Auto-generated method stub
    return 0;
  }

  /**
   * {@inheritDoc}
   */
  public int getPreferredHeight() {

    // TODO Auto-generated method stub
    return 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void dispose() {

    // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isDisposed() {

    // TODO Auto-generated method stub
    return false;
  }

}
