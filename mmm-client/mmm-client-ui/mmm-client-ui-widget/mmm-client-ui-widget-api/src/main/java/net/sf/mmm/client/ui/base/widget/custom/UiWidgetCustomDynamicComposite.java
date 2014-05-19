/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.custom;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.UiWidget;
import net.sf.mmm.client.ui.api.widget.UiWidgetDynamicComposite;

/**
 * This is the abstract base class for a {@link UiWidgetCustom custom widget} implementing
 * {@link UiWidgetDynamicComposite}.
 *
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * @param <CHILD> is the generic type of the {@link #getChild(int) children}.
 * @param <DELEGATE> is the generic type of the {@link #getDelegate() delegate}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetCustomDynamicComposite<VALUE, CHILD extends UiWidget, DELEGATE extends UiWidgetDynamicComposite<CHILD>>
    extends UiWidgetCustomComposite<VALUE, CHILD, DELEGATE> implements UiWidgetDynamicComposite<CHILD> {

  /**
   * The constructor.
   *
   * @param context is the {@link #getContext() context}.
   * @param delegate is the {@link #getDelegate() delegate}.
   * @param valueClass is the {@link #getValueClass() value class}.
   */
  public UiWidgetCustomDynamicComposite(UiContext context, DELEGATE delegate, Class<VALUE> valueClass) {

    super(context, delegate, valueClass);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeChild(CHILD child) {

    return getDelegate().removeChild(child);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CHILD removeChild(int index) {

    return getDelegate().removeChild(index);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addChild(CHILD child) {

    getDelegate().addChild(child);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addChild(CHILD child, int index) {

    getDelegate().addChild(child, index);
  }

}
