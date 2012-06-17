/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.view.widget;

import net.sf.mmm.ui.toolkit.api.view.UiElement;
import net.sf.mmm.ui.toolkit.api.view.composite.UiComposite;

/**
 * This is the abstract interface for a UI widget. Such a widget is an atomic
 * component such as button, label, tree, table, etc.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidget extends UiElement {

  /**
   * {@inheritDoc}
   * 
   * The parent of a widget must be a {@link UiComposite composite}.
   */
  UiComposite<? extends UiElement> getParent();

}
