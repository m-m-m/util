/* $Id$ */
package net.sf.mmm.ui.toolkit.api.menu;

import net.sf.mmm.ui.toolkit.api.UINode;
import net.sf.mmm.ui.toolkit.api.state.UIReadText;
import net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionFlag;
import net.sf.mmm.ui.toolkit.api.widget.ButtonStyle;

/**
 * This is the interface for a menu-item.<br>
 * Use
 * {@link net.sf.mmm.ui.toolkit.api.menu.UIMenu#addItem(String, net.sf.mmm.ui.toolkit.api.event.UIActionListener, ButtonStyle)}
 * to create a menu-item.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIMenuItem extends UINode, UIWriteSelectionFlag, UIReadText {

  /** the type of this object */
  String TYPE = "MenuItem";

  /**
   * This method gets the style of this item.
   * 
   * @return the items style.
   */
  ButtonStyle getStyle();

}
