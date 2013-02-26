/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.custom.panel;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetButton;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetButtonPanel;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetHorizontalPanel;
import net.sf.mmm.client.ui.base.AbstractUiContext;
import net.sf.mmm.client.ui.base.widget.AbstractUiSingleWidgetFactoryReal;
import net.sf.mmm.client.ui.base.widget.custom.UiWidgetCustomRegularComposite;
import net.sf.mmm.util.validation.api.ValidationState;

/**
 * This is the toolkit independent implementation of {@link UiWidgetButtonPanel} based on
 * {@link UiWidgetCustomRegularComposite} and {@link UiWidgetHorizontalPanel}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetCustomButtonPanel extends
    UiWidgetCustomRegularComposite<Void, UiWidgetButton, UiWidgetHorizontalPanel> implements UiWidgetButtonPanel {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetCustomButtonPanel(UiContext context) {

    super(context, context.getWidgetFactory().create(UiWidgetHorizontalPanel.class));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addChild(UiWidgetButton child) {

    getDelegate().addChild(child);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addChild(UiWidgetButton child, int index) {

    getDelegate().addChild(child, index);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeChild(UiWidgetButton child) {

    return getDelegate().removeChild(child);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetButton removeChild(int index) {

    return (UiWidgetButton) getDelegate().removeChild(index);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSetValue(Void value) {

    // nothing to do for Void.
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Void doGetValue(Void template, ValidationState state) throws RuntimeException {

    return null;
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryReal factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryReal<UiWidgetButtonPanel> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetButtonPanel.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetButtonPanel create(AbstractUiContext context) {

      return new UiWidgetCustomButtonPanel(context);
    }

  }

}
