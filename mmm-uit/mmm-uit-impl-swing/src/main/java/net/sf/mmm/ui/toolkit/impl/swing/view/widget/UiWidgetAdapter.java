/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.widget;

import javax.swing.JComponent;

import net.sf.mmm.ui.toolkit.base.view.widget.AbstractUiWidget;
import net.sf.mmm.ui.toolkit.impl.swing.view.UiElementAdapterSwing;

/**
 * This is the implementation of
 * {@link net.sf.mmm.ui.toolkit.base.view.UiElementAdapter} for a swing widget.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @param <DELEGATE> is the generic type of the {@link #getDelegate() delegate}.
 * @since 1.0.0
 */
public class UiWidgetAdapter<DELEGATE extends JComponent> extends UiElementAdapterSwing<DELEGATE> {

  /**
   * The constructor.
   * 
   * @param node is the owning {@link #getNode() node}.
   * @param delegate is the {@link #getDelegate() delegate}.
   */
  public UiWidgetAdapter(AbstractUiWidget<DELEGATE> node, DELEGATE delegate) {

    super(node, delegate);
  }

}
