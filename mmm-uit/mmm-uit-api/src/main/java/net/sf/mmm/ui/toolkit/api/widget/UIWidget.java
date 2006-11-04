/* $Id$ */
package net.sf.mmm.ui.toolkit.api.widget;

import net.sf.mmm.ui.toolkit.api.UIComponent;
import net.sf.mmm.ui.toolkit.api.composite.UIComposite;

/**
 * This is the abstract interface for a UI widget. Such a widget is an atomic
 * componet such as button, label, tree, table, etc.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIWidget extends UIComponent {

  /**
   * The parent of a widget must be a {@link UIComposite composite}.
   * 
   * @see net.sf.mmm.ui.toolkit.api.UINode#getParent()
   */
  public UIComposite getParent();

}
