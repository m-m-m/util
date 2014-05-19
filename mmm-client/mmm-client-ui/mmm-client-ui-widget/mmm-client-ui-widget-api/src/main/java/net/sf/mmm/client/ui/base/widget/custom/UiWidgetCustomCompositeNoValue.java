/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.custom;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.UiWidget;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegularComposite;

/**
 * This is the abstract base class for a {@link UiWidgetCustomComposite custom regular composite widget} that
 * has no {@link #getValue() value}.
 *
 * @param <CHILD> is the generic type of the {@link #getChild(int) children}.
 * @param <DELEGATE> is the generic type of the {@link #getDelegate() delegate}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetCustomCompositeNoValue<CHILD extends UiWidget, DELEGATE extends UiWidgetRegularComposite<CHILD>>
    extends UiWidgetCustomComposite<Void, CHILD, DELEGATE> {

  /**
   * The constructor.
   *
   * @param context is the {@link #getContext() context}.
   * @param delegate is the {@link #getDelegate() delegate}.
   */
  public UiWidgetCustomCompositeNoValue(UiContext context, DELEGATE delegate) {

    super(context, delegate, Void.class);
  }

}
