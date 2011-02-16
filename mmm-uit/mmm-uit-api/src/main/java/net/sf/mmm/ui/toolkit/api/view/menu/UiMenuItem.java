/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.view.menu;

import net.sf.mmm.ui.toolkit.api.UiNode;
import net.sf.mmm.ui.toolkit.api.attribute.UiReadText;
import net.sf.mmm.ui.toolkit.api.attribute.UiWriteSelected;
import net.sf.mmm.ui.toolkit.api.view.widget.ButtonStyle;

/**
 * This is the interface for a menu-item.<br>
 * Use
 * {@link net.sf.mmm.ui.toolkit.api.view.menu.UiMenu#addItem(String, net.sf.mmm.ui.toolkit.api.event.UIActionListener, ButtonStyle)}
 * to create a menu-item.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiMenuItem extends UiNode, UiWriteSelected, UiReadText {

  /** the type of this object */
  String TYPE = "MenuItem";

  /**
   * This method gets the style of this item.
   * 
   * @return the items style.
   */
  ButtonStyle getButtonStyle();

}
