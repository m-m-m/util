/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.menu;

import net.sf.mmm.ui.toolkit.api.UINodeRenamed;
import net.sf.mmm.ui.toolkit.api.attribute.UiReadText;
import net.sf.mmm.ui.toolkit.api.attribute.UiWriteSelectionFlag;
import net.sf.mmm.ui.toolkit.api.widget.ButtonStyle;

/**
 * This is the interface for a menu-item.<br>
 * Use {@link net.sf.mmm.ui.toolkit.api.menu.UIMenu#addItem(String, 
 * net.sf.mmm.ui.toolkit.api.event.UIActionListener, ButtonStyle)} to create a
 * menu-item.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIMenuItem extends UINodeRenamed, UiWriteSelectionFlag, UiReadText {

  /** the type of this object */
  String TYPE = "MenuItem";

  /**
   * This method gets the style of this item.
   * 
   * @return the items style.
   */
  ButtonStyle getButtonStyle();

}
