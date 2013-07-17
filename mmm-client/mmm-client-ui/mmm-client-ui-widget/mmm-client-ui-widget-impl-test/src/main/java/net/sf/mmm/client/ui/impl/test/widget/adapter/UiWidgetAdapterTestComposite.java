/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.widget.adapter;

import net.sf.mmm.client.ui.api.widget.UiWidget;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidget;
import net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapterSingleMutableComposite;
import net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapterSwitchComposite;

/**
 * This is the implementation of {@link net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapterComposite} and
 * various sub-interfaces for testing without a native toolkit.
 * 
 * @param <CHILD>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterTestComposite<CHILD extends UiWidget> extends UiWidgetAdapterTest implements
    UiWidgetAdapterSwitchComposite<CHILD>, UiWidgetAdapterSingleMutableComposite<CHILD> {

  /**
   * The constructor.
   */
  public UiWidgetAdapterTestComposite() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setChild(CHILD child) {

    verifyNotDisposed();
    AbstractUiWidget.getWidgetAdapter(child);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeChild(CHILD child, int index) {

    verifyNotDisposed();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addChild(CHILD child, int index) {

    verifyNotDisposed();
    AbstractUiWidget.getWidgetAdapter(child);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void showChild(int index) {

    verifyNotDisposed();
  }

}
