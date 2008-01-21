/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.widget;

import net.sf.mmm.ui.toolkit.api.UIComponent;
import net.sf.mmm.ui.toolkit.api.composite.UIComposite;

/**
 * This is the abstract interface for a UI widget. Such a widget is an atomic
 * component such as button, label, tree, table, etc.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIWidget extends UIComponent {

  /**
   * {@inheritDoc}
   * 
   * The parent of a widget must be a {@link UIComposite composite}.
   */
  UIComposite getParent();

}
